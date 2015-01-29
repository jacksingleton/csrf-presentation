package com.thoughtworks.appsec.xssDemo.uat;

import com.thoughtworks.appsec.xssDemo.utils.GuestBookClient;
import org.junit.Test;

public class GuestBookServiceUATest {

    private GuestBookClient client = new GuestBookClient();

    @Test
    public void testPing() {
        client.waitForPing();
    }
}
