package com.thoughtworks.appsec.xssDemo.controllers;

import com.thoughtworks.appsec.xssDemo.Constants;
import com.thoughtworks.appsec.xssDemo.GuestBook;
import com.thoughtworks.appsec.xssDemo.GuestBookEntry;
import com.thoughtworks.appsec.xssDemo.AuthService;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GuestBookServiceController {

    private GuestBook guestBook;
    private AuthService authService;

    @Autowired
    public GuestBookServiceController(GuestBook guestBook) {
        this.guestBook = guestBook;
    }

    @RequestMapping(value="/service/deleteEntries",  method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @SneakyThrows(IOException.class)
    public DeleteResult deleteAll(HttpServletResponse response, HttpSession session) {
        validateDelete(response, session);
        return new DeleteResult(guestBook.clearEntries());
    }

    private void validateDelete(final HttpServletResponse response, final HttpSession session) throws IOException {
        UserState userState = (UserState) session.getAttribute(Constants.USER_STATE_SESSION_ATTRIBUTE);
        if (userState == null || !userState.isLoggedIn()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have permission for this operation");
        }
    }

    @RequestMapping(value="/service/entries",  method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    @SneakyThrows(IOException.class)
    public DeleteResult deleteAllRest(HttpServletResponse response, HttpSession session) {
        validateDelete(response, session);
        return new DeleteResult(guestBook.clearEntries());
    }

    @RequestMapping(value="/service/entries", method= RequestMethod.POST)
    public void postEntry(@RequestParam String content) {
        guestBook.addEntry(content);
    }

    @RequestMapping(value="/service/entries", method= RequestMethod.GET)
    public SearchResult findEntries(@RequestParam(defaultValue = "") String filter) {
        List<GuestBookEntry> found = guestBook.getEntries().stream()
                .filter(entry->entry.getContents().contains(filter))
                .collect(Collectors.toList());

        return new SearchResult(found, filter);
    }

    @Data
    public static class SearchResult {
        private final List<GuestBookEntry> found;
        private final String filter;
    }

    @Data
    public static class DeleteResult {
        private final int count;
    }
}
