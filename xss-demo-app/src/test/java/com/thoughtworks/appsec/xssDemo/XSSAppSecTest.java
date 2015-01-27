package com.thoughtworks.appsec.xssDemo;

import com.thoughtworks.appsec.xssDemo.utils.GuestBookClient;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.Stream;

import static java.lang.System.out;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class XSSAppSecTest {

    private GuestBookClient client = new GuestBookClient();

    @Before
    public void setup() {
        client.waitForPing();
        client.clearEntries();
    }

    @Test
    public void testEncodesMarkupInPost() {
        client.postEntry("<b>Hello</b>");
        assertThat(
                getEntryWithText("&lt;b&gt;Hello&lt;/b&gt;").count(),
                is(1l));
    }

    private Stream<GuestBookClient.Entry> getEntryWithText(String text) {
        return client.getEntries().stream().filter(entry -> entry.getContents().equals(text));
    }
}
