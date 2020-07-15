package com.stepproject.ibatechurlshortener.controller;

import com.stepproject.ibatechurlshortener.dto.UrlDto;
import com.stepproject.ibatechurlshortener.model.Url;
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
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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
    public String main(Model model, HttpSession session, String text) {
        UserDetails user1 = (UserDetails) session.getAttribute("user");
        ResponseEntity<User> byEmail = userService.findByEmail(user1.getUsername());
        if (byEmail.getStatusCode().equals(HttpStatus.FOUND)) {
            String name = byEmail.getBody().getName();
            String lastName = byEmail.getBody().getLastName();
            String fullName = name + " " + lastName;
            model.addAttribute("user", fullName);
        }
        model.addAttribute("url", "main-page");
        if (text == null || text.isEmpty()) {
            List<Url> urlList = urlService.userUrls(byEmail);
            model.addAttribute("urlList", urlList);
        } else {
            ResponseEntity<List<Url>> byKeyword = urlService.findByKeyword(text);
            System.out.println("filtering is " + byKeyword.getBody());
            model.addAttribute("urlList", byKeyword.getBody());
        }
        return "html/main-page";
    }

    @PostMapping("main-page")
    public RedirectView shortUrl(@RequestParam(name = "url") String urlParam, HttpSession session) {
        UserDetails userDetails = (UserDetails) session.getAttribute("user");
        User user = userService.findByEmail(userDetails.getUsername()).getBody();
        UrlDto urlDto = new UrlDto(urlParam);
        urlService.saveAndShorten(urlDto, user);
        return new RedirectView("main-page");
    }
}