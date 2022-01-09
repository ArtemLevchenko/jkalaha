package com.artbender.web.controller;

import com.artbender.core.exceptions.AuthenticationKalahaException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Main Navigation Controller
 *
 * @author Artsiom Leuchanka
 */
@Controller
public class OverviewGameController {

    @GetMapping("/")
    public ModelAndView home() {
        return gamePage();
    }

    @GetMapping("/game")
    public ModelAndView gamePage() {
        ModelAndView modelAndView = new ModelAndView();
        String attributeValue;
        try {
            attributeValue = currentUsername();
        } catch (AuthenticationKalahaException e) {
            return new ModelAndView("redirect:/login");
        }
        modelAndView.addObject("username", attributeValue);
        modelAndView.setViewName("game");
        return modelAndView;
    }

    private String currentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (User.class.isAssignableFrom(principal.getClass())) {
            User user = (User) principal;
            return user.getUsername();
        }
        throw new AuthenticationKalahaException("Authenticated problem. Please check your user in security context");
    }
}
