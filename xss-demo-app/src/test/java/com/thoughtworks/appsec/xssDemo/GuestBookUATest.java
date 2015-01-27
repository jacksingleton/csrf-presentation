package com.thoughtworks.appsec.xssDemo;

import org.fluentlenium.adapter.FluentTest;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GuestBookUATest extends FluentTest {

    private static final String HOME_PAGE = "/";
    private static final String ENTRY_FORM_TEXT = "#entry-form-text";
    private static final String ENTRY = ".entry";
    private static final String ENTRY_FORM_SUBMIT = "#entry-form-submit";

    private GuestBookClient client = new GuestBookClient();

    @Before
    public void setUp(){
        client.waitForPing();
        client.clearEntries();
    }

    @Override
    public String getDefaultBaseUrl() {
        return "http://localhost:8080";
    }

    @Test
    public void testPing() {
        client.waitForPing();
    }

    @Test
    public void testWriteInGuestBookCreatesNewEntry() {
        goTo(HOME_PAGE).await().untilPage().isLoaded()
                .fill(ENTRY_FORM_TEXT)
                .with("Hi Mom!")
                .click(ENTRY_FORM_SUBMIT)
                .await().atMost(5, TimeUnit.SECONDS).until(ENTRY).hasSize(1);

        assertThat(findFirst(ENTRY).getText(), is("Hi Mom!"));
    }

}
