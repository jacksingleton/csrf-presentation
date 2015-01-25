package com.thoughtworks.appsec.xssDemo;

import com.thoughtworks.appsec.xssDemo.GuestBookClient.Entry;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Ignore
public class GuestBookUATest {

    private GuestBookClient client = new GuestBookClient("http://localhost:8080");

    @Test
    public void setUp(){
        client.clearEntries();
    }

    @Test
    public void testWriteInGuestBookCreatesNewEntry() {
        client.postEntry("Hi Mom!");
        List<Entry> entries = client.getEntries();
        assertThat(entries.size(), is(1));
        assertThat(entries.get(0).getMessage(), is("Hi Mom!"));
    }

}
