package com.thoughtworks.appsec.csrfDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class MissileServerMain {
    public static void main(String[] args) {
        SpringApplication.run(MissileServerMain.class, args);
    }
}

