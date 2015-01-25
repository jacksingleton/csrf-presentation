package com.thoughtworks.appsec.xssDemo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @RequestMapping("/ping")
    public String ping() {
        return "pong!";
    }
}
