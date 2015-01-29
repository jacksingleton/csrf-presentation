package com.thoughtworks.appsec.xssDemo;

import org.springframework.stereotype.Component;

@Component
public class HardCodedAuthService implements AuthService {

    @Override
    public boolean doAuth(final String username, final String password) {
        return System.getProperty("app.admin.username", "admin").equals(username) &&
                System.getProperty("app.admin.password", "password").equals(password);
    }
}
