package com.thoughtworks.appsec.xssDemo;

import java.util.List;

public interface GuestBook {
    void addEntry(final String contents);

    List<GuestBookEntry> getEntries();

    int clearEntries();
}
