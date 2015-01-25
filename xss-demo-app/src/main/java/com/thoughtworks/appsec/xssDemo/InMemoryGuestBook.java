package com.thoughtworks.appsec.xssDemo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class InMemoryGuestBook implements GuestBook {

    private List<GuestBookEntry> entries = new ArrayList<>();

    public void addEntry(final String contents) {
        synchronized (this) {
            entries.add(new GuestBookEntry(UUID.randomUUID(), contents));
        }
    }

    public List<GuestBookEntry> getEntries() {
        synchronized (this) {
            return Collections.unmodifiableList(entries);
        }
    }
}
