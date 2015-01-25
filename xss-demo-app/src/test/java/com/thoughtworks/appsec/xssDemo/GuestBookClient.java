package com.thoughtworks.appsec.xssDemo;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class GuestBookClient {

    private String root;

    public GuestBookClient(String root) {
        this.root = root;
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

    private Optional<Integer> ping(){
        HttpGet get = new HttpGet(root + "/ping");
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            final CloseableHttpResponse response = client.execute(get);
            return Optional.of(response.getStatusLine().getStatusCode());
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public void waitForPingFailure() {
        throw new UnsupportedOperationException(); // TODO
    }

    public void postEntry(final String s) {
        throw new UnsupportedOperationException(); // TODO
    }

    public List<Entry> getEntries() {
        throw new UnsupportedOperationException(); // TODO
    }

    public static class Entry {
        public String getMessage() {
            throw new UnsupportedOperationException(); // TODO
        }
    }

}
