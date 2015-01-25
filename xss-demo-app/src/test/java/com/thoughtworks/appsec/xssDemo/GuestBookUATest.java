package com.thoughtworks.appsec.xssDemo;

import com.thoughtworks.appsec.xssDemo.GuestBookClient.Entry;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Ignore
public class GuestBookUATest {

    private GuestBookServer server;

    private GuestBookClient client;

    @Before
    public void setup() {
        server.start();
        client.waitForPing();
    }

    @After
    public void teardown() {
        server.stop();
        client.waitForPingFailure();
    }

    @Test
    public void testWriteInGuestBookCreatesNewEntry() {
        client.postEntry("Hi Mom!");
        List<Entry> entries = client.getEntries();
        assertThat(entries.size(), is(1));
        assertThat(entries.get(0).getMessage(), is("Hi Mom!"));
    }

}
