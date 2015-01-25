package com.thoughtworks.appsec.xssDemo;

import org.fluentlenium.adapter.FluentTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Ignore
public class GuestBookUATest extends FluentTest {

    public static final String BASE_APP_URL = "http://localhost:8080";
    private static final String HOME_PAGE = "/";
    private static final String ENTRY_FORM = "#entry-form";
    private static final String ENTRY = ".entry";

    private GuestBookClient client = new GuestBookClient(BASE_APP_URL);

    @Before
    public void setUp(){
        client.waitForPing();
        client.clearEntries();
    }

    @Override
    public String getDefaultBaseUrl() {
        return BASE_APP_URL;
    }

    @Test
    public void testWriteInGuestBookCreatesNewEntry() {
        goTo(HOME_PAGE).await().untilPage().isLoaded()
                .fill(ENTRY_FORM)
                .with("Hi Mom!")
                .submit(ENTRY_FORM)
                .await().untilPage().isLoaded();
        assertThat(findFirst(ENTRY).getText(), is("Hi Mom!"));
    }

}
