package com.stepproject.ibatechurlshortener.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    private final UserController userController;

    public LoginController(UserController userController) {
        this.userController = userController;
    }

    @GetMapping("/login")
    public String login(){
        System.out.println("@GetMapping public String login()");
        return "html/login";
    }
}
