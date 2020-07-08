package com.stepproject.ibatechurlshortener.controller;

import com.stepproject.ibatechurlshortener.dto.UrlDto;
import com.stepproject.ibatechurlshortener.model.User;
import com.stepproject.ibatechurlshortener.service.url.UrlService;
import com.stepproject.ibatechurlshortener.service.user.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Log4j2
@Controller
public class UrlController {

    private final UrlService urlService;
    private final UserService userService;

    public UrlController(UrlService urlService, UserService userService) {
        this.urlService = urlService;
        this.userService = userService;
    }

    @GetMapping("main-page")
    public String main(Model model, HttpSession session) {
        UserDetails user1 = (UserDetails) session.getAttribute("user");
        ResponseEntity<User> byEmail = userService.findByEmail(user1.getUsername());
        if (byEmail.getStatusCode().equals(HttpStatus.FOUND)) {
            String name = byEmail.getBody().getName();
            String lastName = byEmail.getBody().getLastName();
            String fullName = name + " " + lastName;
            model.addAttribute("user", fullName);
        }
        model.addAttribute("url", "main-page");
        return "html/main-page";
    }

    @PostMapping("main-page")
    public String shortUrl(@RequestParam(name = "url") String urlParam) {
        UrlDto urlDto = new UrlDto(urlParam);
        urlService.saveAndShorten(urlDto);
        return "html/main-page";
    }
}