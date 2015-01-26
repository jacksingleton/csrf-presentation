package com.thoughtworks.appsec.xssDemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GuestBookTestConfiguration {

    @Bean
    public String appRoot() {
        return "http://localhost:8080";
    }
}
