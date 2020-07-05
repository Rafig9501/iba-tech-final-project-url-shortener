package com.stepproject.ibatechurlshortener.controller;

import com.stepproject.ibatechurlshortener.model.User;
import com.stepproject.ibatechurlshortener.service.user.UserService;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;
import java.util.Optional;

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
    public String getStarted(Authentication authentication , Model model){
        UserDetails user = (UserDetails) authentication.getPrincipal();
        ResponseEntity<Optional<User>> byEmail = userService.findByEmail(user.getUsername());
        if(Objects.requireNonNull(byEmail.getBody()).isPresent()){
            String name = byEmail.getBody().get().getName();
            String lastName = byEmail.getBody().get().getLastName();
            String fullName = name + " " + lastName;
            model.addAttribute("user",fullName);
        }
        return "html/landing";
    }
}
