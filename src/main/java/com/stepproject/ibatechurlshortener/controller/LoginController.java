package com.stepproject.ibatechurlshortener.controller;

import com.stepproject.ibatechurlshortener.model.User;
import com.stepproject.ibatechurlshortener.service.mail.MailServiceImpl;
import com.stepproject.ibatechurlshortener.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final UserService userService;
    private final MailServiceImpl mailService;

    public LoginController(UserService userService, MailServiceImpl mailService) {
        this.userService = userService;
        this.mailService = mailService;
    }

    @GetMapping("/login")
    public String login() {
        return "html/login";
    }

    @GetMapping("/landing")
    public String getStarted(Authentication authentication, Model model, HttpSession session) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        session.setAttribute("user", user);
        ResponseEntity<User> byEmail = userService.findByEmail(user.getUsername());
        if (byEmail.getStatusCode().equals(HttpStatus.FOUND)) {
            sendTestEmail(user.getUsername());
            String name = byEmail.getBody().getName();
            String lastName = byEmail.getBody().getLastName();
            String fullName = name + " " + lastName;
            model.addAttribute("user", fullName);
        }
        return "html/landing";
    }

    private void sendTestEmail(String email){
        mailService.send(email,"Testing email","Hello from my Spring app");
    }
}
