package com.thoughtworks.appsec.csrfDemo.presentation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Presentation {
    public static void main(String[] args) {
        SpringApplication.run(Presentation.class, args);
    }
}
