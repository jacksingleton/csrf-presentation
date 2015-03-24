package com.thoughtworks.appsec.xssDemo;

public interface AuthService {
    boolean doAuth(String username, String password);
}
