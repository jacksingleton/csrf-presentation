package com.thoughtworks.appsec.xssDemo.controllers;

import com.thoughtworks.appsec.xssDemo.Constants;
import com.thoughtworks.appsec.xssDemo.GuestBook;
import com.thoughtworks.appsec.xssDemo.GuestBookEntry;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RestController
public class GuestBookServiceController {

    private GuestBook guestBook;

    @Autowired
    public GuestBookServiceController(GuestBook guestBook) {
        this.guestBook = guestBook;
    }

    @RequestMapping(value="/service/entries",  method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    @SneakyThrows(IOException.class)
    public DeleteResult deleteAll(HttpServletResponse response, HttpSession session) {
        UserState userState = (UserState) session.getAttribute(Constants.USER_STATE_SESSION_ATTRIBUTE);
        if (userState == null || !userState.isLoggedIn()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have permission for this operation");
        }
        return new DeleteResult(guestBook.clearEntries());
    }

    @RequestMapping(value="/service/entries", method= RequestMethod.POST)
    public void postEntry(@RequestParam String content) {
        guestBook.addEntry(content);
    }

    @RequestMapping(value="/service/entries", method= RequestMethod.GET)
    public List<GuestBookEntry> allEntries() {
        return guestBook.getEntries();
    }

    @Data
    public static class DeleteResult {
        private final int count;
    }
}
