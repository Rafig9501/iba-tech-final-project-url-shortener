package com.stepproject.ibatechurlshortener.controller;

import com.stepproject.ibatechurlshortener.dto.UserDto;
import com.stepproject.ibatechurlshortener.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("registration")
    public String register(Model model) {
        model.addAttribute("user","registration/info");
        return "html/registration";
    }

    @PostMapping("registration/info")
    public String takingParams(UserDto user) {
        userService.registerUser(user);
        return "html/info"; // TODO implement fix info.html
    }
}
