package com.thoughtworks.appsec.xssDemo.controllers;

import com.thoughtworks.appsec.xssDemo.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class MissileController {

    private boolean missilesLaunched = false;

    @RequestMapping(value = "/service/missileStatus", method = RequestMethod.GET)
    @ResponseBody
    public String missileStatus() {
        if (missilesLaunched) {
            return "Here comes world war three! 🚀";
        } else {
            return "Missiles not launched yet; the world survives another day";
        }
    }

    @RequestMapping(value = "/service/launchTheMissiles", method = RequestMethod.POST)
    @ResponseBody
    public void launchTheMissiles(final HttpSession session) {
        if (loggedIn(session)) {
            missilesLaunched = true;
        }
    }

    private boolean loggedIn(final HttpSession session) {
        final UserState loggedIn = (UserState) session.getAttribute(Constants.USER_STATE_SESSION_ATTRIBUTE);
        return loggedIn.isLoggedIn();
    }

}
