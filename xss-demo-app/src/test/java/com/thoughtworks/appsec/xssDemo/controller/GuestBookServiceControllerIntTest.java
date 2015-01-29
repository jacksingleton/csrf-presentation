package com.thoughtworks.appsec.xssDemo.controller;

import com.thoughtworks.appsec.xssDemo.utils.GuestBookClient;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GuestBookServiceControllerIntTest {

    private GuestBookClient client = new GuestBookClient();

    @Before
    public void setup() {
        client.waitForPing();
        client.clearEntries();
    }

    @Test
    public void testAddEntry() {
        client.postEntry("hello world");
        assertTrue(client.getEntries().getFound().stream().allMatch(entry -> entry.getContents().equals("hello world")));
    }
}
