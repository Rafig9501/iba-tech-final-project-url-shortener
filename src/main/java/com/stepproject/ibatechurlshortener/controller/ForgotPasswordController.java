package com.stepproject.ibatechurlshortener.controller;

import com.stepproject.ibatechurlshortener.model.User_;
import com.stepproject.ibatechurlshortener.service.mail.MailServiceImpl;
import com.stepproject.ibatechurlshortener.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/forgot-password")
public class ForgotPasswordController {

    private final UserService userService;
    private final MailServiceImpl mailService;

    public ForgotPasswordController(UserService userService, MailServiceImpl mailService) {
        this.userService = userService;
        this.mailService = mailService;
    }

    @GetMapping
    public String forgetPassword(Model model) {
        model.addAttribute("email", "forgot-password/info");
        return "html/forgot-password";
    }

    @PostMapping("/info")
    public String checkEmail(Model model, String email) {
        ResponseEntity<String> activationCode = userService.setUserActivationCode(email);
        if (activationCode.getStatusCode().equals(HttpStatus.OK) &&
                mailService.send(email, activationCode.getBody())) {
            model.addAttribute("info", "Activation code has been sent to your email");
        } else model.addAttribute("info", "There is no user under this email");
        return "html/info";
    }

    @GetMapping("/token/{activationCode}")
    public RedirectView getActivationCode(@PathVariable String activationCode, Model model) {
        ResponseEntity<User_> user = userService.checkUserActivationCode(activationCode);
        if (user.getStatusCode().equals(HttpStatus.FOUND)) {
            model.addAttribute("email", user.getBody().getEmail());
            return new RedirectView("/forgot-password/password-reset");
        } else {
            return new RedirectView("/login");
        }
    }

    @GetMapping("/password-reset")
    public String getNewPassword(Model model, String email, HttpSession session) {
        session.setAttribute("email", email);
        model.addAttribute("password", "/forgot-password/password-reset/take");
        return "html/password-reset";
    }

    @PostMapping("password-reset/take")
    public String changePassword(String password, Model model, HttpSession session) {
        ResponseEntity<User_> email = userService.updatePassword(String.valueOf(session.getAttribute("email")), password);
        if (email.getStatusCode().equals(HttpStatus.OK)) {
            model.addAttribute("info", "PASSWORD HAS BEEN CHANGED");
        } else {
            session.invalidate();
            model.addAttribute("info", "SOMETHING WENT WRONG, PLEASE TRY AGAIN");
        }
        return "html/info";
    }
}
