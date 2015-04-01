package com.thoughtworks.appsec.csrfDemo.controllers;

import lombok.Data;

@Data
public class UserState {
    private final boolean isLoggedIn;
}
