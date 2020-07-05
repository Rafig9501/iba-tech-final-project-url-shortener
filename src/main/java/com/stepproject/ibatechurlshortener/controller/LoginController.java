package com.stepproject.ibatechurlshortener.controller;

import com.stepproject.ibatechurlshortener.service.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(){
        return "html/login";
    }

    @GetMapping("/landing")
    public String getStarted(Authentication authentication){
        UserDetails user = (UserDetails) authentication.getPrincipal();
        userService.findByEmail(user.getUsername());
        System.out.println(user.getUsername());
        return "html/landing";
    }
}
