package com.thoughtworks.appsec.xssDemo.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.thoughtworks.appsec.xssDemo.TestException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

@Component
public class GuestBookClient {

    /** TODO: refactor this ***/
    private String root;

    public GuestBookClient() {
        this("http://localhost:8080");
    }

    public GuestBookClient(String appRoot) {
        this.root = appRoot;
    }

    public void waitForPing() {
        waitFor(() -> ping().equals(Optional.of(200)));
    }

    private void waitFor(Supplier<Boolean> test) {
        waitFor(test, 5000);
    }

    private void waitFor(Supplier<Boolean> test, long maxWait) {
        long timeout = maxWait + System.currentTimeMillis();
        while (System.currentTimeMillis() < timeout) {
            if (test.get()) {
                return;
            }
        }
        throw new TestException("Timed out waiting for condition.");
    }

    private Optional<Integer> ping() {
        HttpGet get = new HttpGet(root + "/health");
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            final CloseableHttpResponse response = client.execute(get);
            return Optional.of(response.getStatusLine().getStatusCode());
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    //TODO: serious refactoring of this class
    public void postEntry(final String text) {
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            HttpPost post = new HttpPost(root + "/service/entries");
            post.setEntity(new UrlEncodedFormEntity(ImmutableList.of(new BasicNameValuePair("content", text))));
            final CloseableHttpResponse response = client.execute(post);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException(String.format("Failed with status message: %s", response.getStatusLine().getReasonPhrase()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Entry> getEntries() {
        return doHttpRequest(new HttpGet(root + "/service/entries"), response->{
            ObjectMapper mapper = new ObjectMapper();
            JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, Entry.class);
            try {
                return new ObjectMapper().readValue(response.getEntity().getContent(), type);
            } catch (IOException e) {
                throw new TestException("Failed to clean entries.", e);
            }
        });
    }

    public void clearEntries() {
        BasicCookieStore store = new BasicCookieStore();
        try (CloseableHttpClient client = HttpClientBuilder.create().setDefaultCookieStore(store).build()) {
            checkResponse(client.execute(createLoginPost()));
            checkResponse(client.execute(new HttpDelete(String.format("%s/service/entries/", root))));
        } catch (IOException e) {
            throw new TestException("Failed to clean entries.", e);
        }
    }

    private void checkResponse(final CloseableHttpResponse response) {
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new TestException(String.format("Failed to delete: %s",
                    response.getStatusLine().getReasonPhrase()));
        }
    }

    private HttpUriRequest createLoginPost() throws UnsupportedEncodingException {
        String username = System.getProperty("app.admin.username", "testuser");
        String password = System.getProperty("app.admin.password", "testpassword");
        HttpPost post = new HttpPost(String.format("%s/service/login", root));
        post.setEntity(new UrlEncodedFormEntity(ImmutableList.of(
                new BasicNameValuePair("username", username),
                new BasicNameValuePair("password", password))));
        return post;
    }

    public <T> T doHttpRequest(HttpRequestBase request, Function<CloseableHttpResponse, T> handler) {
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            final CloseableHttpResponse response = client.execute(request);

            return handler.apply(response);

        } catch (IOException e) {
            throw new TestException("Failed to clean entries.", e);
        }
    }

    @ToString
    public static class Entry {
        @Getter
        @Setter
        private String contents;
    }

}
