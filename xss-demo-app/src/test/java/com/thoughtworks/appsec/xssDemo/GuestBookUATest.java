package com.thoughtworks.appsec.xssDemo;

import com.thoughtworks.appsec.xssDemo.utils.GuestBookClient;
import org.fluentlenium.adapter.FluentTest;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GuestBookUATest extends FluentTest {

    private static final String HOME_PAGE = "/";
    private static final String ENTRY_FORM_TEXT = "#entry-form-text";
    private static final String ENTRY = ".entry";
    private static final String ENTRY_FORM_SUBMIT = "#entry-form-submit";
    private static final String USERNAME_TEXT = "#username-text";
    private static final String PASSWORD_TEXT = "#password-text";
    private static final String LOGIN_BUTTON = "#login-button";
    public static final String DELETE_ALL_BUTTON = "#delete-all-button";

    private GuestBookClient client = new GuestBookClient();

    private WebDriver driver = new ChromeDriver();

    public WebDriver getDefaultDriver() {
        return driver;
    }

    @Before
    public void setUp(){
        withDefaultPageWait(10, SECONDS);
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
                .await().atMost(5, SECONDS).until(ENTRY).hasSize(1);

        assertThat(findFirst(ENTRY).getText(), is("Hi Mom!"));
    }

    @Test
    public void testDeleteAllAfterLogin() {
        goTo(HOME_PAGE).await().untilPage().isLoaded();

        assertThat(find(DELETE_ALL_BUTTON).first().isDisplayed(), is(false));
        fill(USERNAME_TEXT).with("testuser")
                .fill(PASSWORD_TEXT).with("testpassword")
                .click(LOGIN_BUTTON)
                .await().atMost(5, SECONDS).until(DELETE_ALL_BUTTON).areDisplayed();

        click(DELETE_ALL_BUTTON)
                .await().atMost(5, SECONDS).until(ENTRY).hasSize(0);
    }

}
