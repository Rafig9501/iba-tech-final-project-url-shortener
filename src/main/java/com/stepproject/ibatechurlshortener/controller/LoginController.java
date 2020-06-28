package com.stepproject.ibatechurlshortener.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final UserController userController;

    public LoginController(UserController userController) {
        this.userController = userController;
    }

    @GetMapping
    public String login(){
        return "html/login";
    }
}
