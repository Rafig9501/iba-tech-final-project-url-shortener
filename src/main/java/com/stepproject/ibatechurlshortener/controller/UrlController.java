package com.stepproject.ibatechurlshortener.controller;

import com.stepproject.ibatechurlshortener.dto.UrlDto;
import com.stepproject.ibatechurlshortener.model.User;
import com.stepproject.ibatechurlshortener.service.url.UrlService;
import com.stepproject.ibatechurlshortener.service.user.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

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
    public String main(Model model, Authentication authentication) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        ResponseEntity<Optional<User>> byEmail = userService.findByEmail(user.getUsername());
        if (Objects.requireNonNull(byEmail.getBody()).isPresent()) {
            String name = byEmail.getBody().get().getName();
            String lastName = byEmail.getBody().get().getLastName();
            String fullName = name + " " + lastName;
            model.addAttribute("user", fullName);
        }
        model.addAttribute("url", "main-page/shorten");
        return "html/main-page";
    }

    @PostMapping("main-page/shorten")
    public String shortUrl(@RequestParam(name = "url") String urlParam) {
        UrlDto urlDto = new UrlDto(urlParam);
        urlService.saveAndShorten(urlDto);
        return "html/main-page";
    }
//    (
//    Authentication authentication , Model model){
//            UserDetails user = (UserDetails) authentication.getPrincipal();
//            ResponseEntity<Optional<User>> byEmail = userService.findByEmail(user.getUsername());
//            if(Objects.requireNonNull(byEmail.getBody()).isPresent()){
//                String name = byEmail.getBody().get().getName();
//                String lastName = byEmail.getBody().get().getLastName();
//                String fullName = name + " " + lastName;
//                model.addAttribute("user",fullName);
//            }
}