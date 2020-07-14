package com.stepproject.ibatechurlshortener.controller;

import com.stepproject.ibatechurlshortener.dto.UserDto;
import com.stepproject.ibatechurlshortener.model.User;
import com.stepproject.ibatechurlshortener.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        model.addAttribute("user", "registration/info");
        return "html/registration";
    }

    @PostMapping("registration/info")
    public String takingParams(UserDto user, Model model) {
        ResponseEntity<User> response = userService.registerUser(user);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            model.addAttribute("info", "Successfully registered");
        } else {
            model.addAttribute("info", "User with this email already exist");
        }
        return "html/info";
    }
}
