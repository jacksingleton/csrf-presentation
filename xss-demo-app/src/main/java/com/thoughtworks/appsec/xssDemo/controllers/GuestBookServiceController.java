package com.thoughtworks.appsec.xssDemo.controllers;

import com.thoughtworks.appsec.xssDemo.GuestBook;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GuestBookServiceController {

    private GuestBook guestBook;

    @Autowired
    public GuestBookServiceController(GuestBook guestBook) {
        this.guestBook = guestBook;
    }

    @RequestMapping(value="/service/entries",  method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public DeleteResult deleteAll() {
        return new DeleteResult(guestBook.clearEntries());
    }

    @Data
    public static class DeleteResult {
        private final int count;
    }
}
