package com.thoughtworks.appsec.xssDemo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GuestBookController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
