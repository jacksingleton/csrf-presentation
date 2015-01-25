package com.thoughtworks.appsec.xssDemo;

import java.util.List;

 interface GuestBook {
     void addEntry(final String contents);

     List<GuestBookEntry> getEntries();

     void clearEntries();
 }
