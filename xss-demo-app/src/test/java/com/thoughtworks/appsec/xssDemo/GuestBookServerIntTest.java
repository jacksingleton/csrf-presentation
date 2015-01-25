package com.thoughtworks.appsec.xssDemo;

import org.junit.Test;

public class GuestBookServerIntTest {

    private GuestBookServer server = new GuestBookServer(8080);
    private GuestBookClient client = new GuestBookClient("http://localhost:8080");

    @Test
    public void testStartUpEnablesPingEndpoint() {
        server.start();
        client.waitForPing();
    }
}
