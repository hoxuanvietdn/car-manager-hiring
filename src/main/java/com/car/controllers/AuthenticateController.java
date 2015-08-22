package com.car.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created by hxviet on 6/9/2015.
 */
@Controller
public class AuthenticateController {

    public static Logger logger = LoggerFactory.getLogger(AuthenticateController.class);

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout, HttpSession session) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AuthenticationException exception = ((AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION));

        ModelAndView model = new ModelAndView();

        if (exception instanceof DisabledException){
            session.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, null);
            model.addObject("messageType", "Danger");
            model.addObject("message", exception.getMessage());
        }

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return new ModelAndView("redirect:/");
        }

        if (exception instanceof BadCredentialsException){
            session.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, null);
            BadCredentialsException ex = (BadCredentialsException) exception;
            if (error != null && ex.getStackTrace() != null) {
                model.addObject("error", "Invalid username and password!");
            }
        }

        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("authentication/login");

        return model;
    }

}
