package com.thoughtworks.appsec.xssDemo;

import java.util.UUID;

public class GuestBookEntry {
    private final String contents;

    public GuestBookEntry(final String contents) {
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }
}
