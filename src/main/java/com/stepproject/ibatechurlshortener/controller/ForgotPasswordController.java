package com.stepproject.ibatechurlshortener.controller;

import com.stepproject.ibatechurlshortener.model.User;
import com.stepproject.ibatechurlshortener.service.mail.MailServiceImpl;
import com.stepproject.ibatechurlshortener.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ForgotPasswordController {

    private final UserService userService;
    private final MailServiceImpl mailService;

    public ForgotPasswordController(UserService userService, MailServiceImpl mailService) {
        this.userService = userService;
        this.mailService = mailService;
    }

    @GetMapping("forgot-password")
    public String forgetPassword(Model model) {
        model.addAttribute("email", "forgot-password/info");
        return "html/forgot-password";
    }

    @PostMapping("forgot-password/info")
    public String checkEmail(Model model, String email) {
        ResponseEntity<User> byEmail = userService.findByEmail(email);
        if (byEmail.getStatusCode().equals(HttpStatus.FOUND)) {
            User user = byEmail.getBody();
            resetPassword(user.getEmail());
            model.addAttribute("info", "Please check your email.");
        } else {
            model.addAttribute("info", "The user with this email does not exist.");
        }
        return "html/info";
    }

    private void resetPassword(String email) {
        mailService.send(email, "Reset password", "Please check this link , for reset password \n link ");
    }
}
