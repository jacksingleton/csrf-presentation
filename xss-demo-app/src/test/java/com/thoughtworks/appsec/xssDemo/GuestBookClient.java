package com.thoughtworks.appsec.xssDemo;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Component
public class GuestBookClient {

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
            post.setEntity(new UrlEncodedFormEntity(ImmutableList.of(new BasicNameValuePair("entry", text))));
            final CloseableHttpResponse response = client.execute(post);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException(String.format("Failed with status message: %s", response.getStatusLine().getReasonPhrase()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Entry> getEntries() {
        HttpGet get = new HttpGet(root + "/service/entries");
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            final CloseableHttpResponse response = client.execute(get);
            ObjectMapper mapper = new ObjectMapper();
            JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, Entry.class);
            return new ObjectMapper().readValue(response.getEntity().getContent(), type);
        } catch (IOException e) {
            throw new TestException("Get entries failed.", e);
        }
    }

    public void clearEntries() {
        HttpDelete delete = new HttpDelete(String.format("%s/service/entries/", root));
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            final CloseableHttpResponse response = client.execute(delete);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new TestException(String.format("Failed to delete: %s",
                        response.getStatusLine().getReasonPhrase()));
            }

            response.getEntity().writeTo(System.out);
        } catch (IOException e) {
            throw new TestException("Failed to clean entries.", e);
        }
    }

    public static class Entry {
        @Getter
        @Setter
        private String contents;
    }

}
