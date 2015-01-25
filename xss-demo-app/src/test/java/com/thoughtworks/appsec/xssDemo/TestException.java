package com.thoughtworks.appsec.xssDemo;

import java.io.IOException;

public class TestException extends RuntimeException {
    public TestException(final String message) {
        super(message);
    }

    public TestException(final String message, final IOException e) {
        super(message, e);
    }
}
