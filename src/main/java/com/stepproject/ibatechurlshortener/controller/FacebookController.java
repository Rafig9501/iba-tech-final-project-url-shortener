package com.stepproject.ibatechurlshortener.controller;

import com.stepproject.ibatechurlshortener.configuration.ApplicationSecurityConfig;
import com.stepproject.ibatechurlshortener.model.User_;
import com.stepproject.ibatechurlshortener.service.facebook.FacebookService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

@Controller
public class FacebookController {

    private final FacebookService facebookService;
    private final ApplicationSecurityConfig applicationSecurityConfig;

    public FacebookController(FacebookService facebookService,
                              ApplicationSecurityConfig applicationSecurityConfig) {
        this.facebookService = facebookService;
        this.applicationSecurityConfig = applicationSecurityConfig;
    }

    @GetMapping(value = "/facebook/login")
    public RedirectView facebookLogin() {
        RedirectView redirectView = new RedirectView();
        String url = facebookService.facebookLogin();
        redirectView.setUrl(url);
        return redirectView;
    }

    @GetMapping(value = "/facebook")
    public String facebook(@RequestParam("code") String code) {
        String accessToken = facebookService.getFacebookAccessToken(code);
        return "redirect:/facebook/profiledata/" + accessToken;
    }

    @GetMapping(value = "/facebook/profiledata/{accessToken:.+}")
    public RedirectView facebookProfileData(@PathVariable String accessToken, HttpSession session, Model model) {
        User userFromFacebook = facebookService.getFacebookUserProfile(accessToken);
        User_ user_ = facebookService.registerOrLoginUserFromFacebook(userFromFacebook);
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        UserDetails user = new org.springframework.security.core.userdetails.User(user_.getEmail(), "", authorities);
        session.setAttribute("user", user);
        String name = user_.getName();
        String lastName = user_.getLastName();
        String fullName = name + " " + lastName;
        model.addAttribute("user", fullName);
        applicationSecurityConfig.autoLogin(user);
        return new RedirectView("/landing");
    }
}
