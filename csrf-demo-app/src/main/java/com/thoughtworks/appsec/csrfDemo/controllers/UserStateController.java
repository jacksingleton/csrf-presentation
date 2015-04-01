package com.thoughtworks.appsec.csrfDemo.controllers;

import com.thoughtworks.appsec.csrfDemo.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class UserStateController {

    @RequestMapping(value = "/service/userState", method = RequestMethod.GET)
    @ResponseBody
    public UserState getUserState(final HttpSession session) {
        final UserState loggedIn = (UserState) session.getAttribute(Constants.USER_STATE_SESSION_ATTRIBUTE);
        return new UserState(loggedIn != null && loggedIn.isLoggedIn());
    }

}
