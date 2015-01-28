package com.thoughtworks.appsec.xssDemo;

import org.springframework.stereotype.Component;

@Component
public class HardCodedLoginService implements LoginService {

    @Override
    public boolean login(final String username, final String password) {
        return "admin".equals(username) && "admin".equals(password);
    }
}
