package com.thoughtworks.appsec.csrfDemo;

public interface AuthService {
    boolean doAuth(String username, String password);
}
