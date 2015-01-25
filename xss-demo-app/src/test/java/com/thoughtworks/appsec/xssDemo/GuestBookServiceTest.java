package com.thoughtworks.appsec.xssDemo;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GuestBookServiceTest {

    @Test
    public void testGuestBookAdd() {
        GuestBook guestBook = new InMemoryGuestBook();
        guestBook.addEntry("Hi mom.");
        List<GuestBookEntry> entries = guestBook.getEntries();
        assertThat(entries.size(), is(1));
    }
}
