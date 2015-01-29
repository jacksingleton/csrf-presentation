package com.thoughtworks.appsec.xssDemo.appSec;

import com.thoughtworks.appsec.xssDemo.utils.GuestBookClient;
import org.fluentlenium.adapter.FluentTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * NOTE: To test this, you need to have example.com mapping to 127.0.0.1. You can do this in your hosts file.
 */
public class CSRFAppSecTest extends FluentTest {

    private GuestBookClient client = new GuestBookClient();

    @Test
    public void testProtectsAgainstCSRF() {
        client.clearEntries();
        client.postEntry("test");
        client.deleteAllEntriesViaPost();
        assertThat(client.getEntries().getFound().size(), is(1));
    }
}
