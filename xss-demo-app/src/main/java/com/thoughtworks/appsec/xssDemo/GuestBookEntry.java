package com.thoughtworks.appsec.xssDemo;

import java.util.UUID;

public class GuestBookEntry {
    private final UUID uuid;
    private final String contents;

    public GuestBookEntry(final UUID uuid, final String contents) {
        this.uuid = uuid;
        this.contents = contents;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getContents() {
        return contents;
    }
}
