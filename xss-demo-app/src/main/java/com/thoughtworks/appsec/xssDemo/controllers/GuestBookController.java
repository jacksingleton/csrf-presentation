package com.thoughtworks.appsec.xssDemo.controllers;

import com.thoughtworks.appsec.xssDemo.GuestBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GuestBookController {

    private GuestBook guestBook;

    @Autowired
    public GuestBookController(final GuestBook guestBook) {
        this.guestBook = guestBook;
    }

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("entries", guestBook.getEntries());
        return "index";
    }

    @RequestMapping(value="/entries/", method = RequestMethod.POST)
    public String addEntry(@RequestParam String entryText) {
        this.guestBook.addEntry(entryText);
        return "redirect:/";
    }
}
