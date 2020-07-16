package com.stepproject.ibatechurlshortener.controller;

import com.stepproject.ibatechurlshortener.model.Url;
import com.stepproject.ibatechurlshortener.model.UrlHistory;
import com.stepproject.ibatechurlshortener.service.url.UrlService;
import com.stepproject.ibatechurlshortener.service.url_history.UrlHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Set;

@Controller
public class ShortcutController {

    private final UrlService urlService;
    private final UrlHistoryService urlHistoryService;

    public ShortcutController(UrlService urlService, UrlHistoryService urlHistoryService) {
        this.urlService = urlService;
        this.urlHistoryService = urlHistoryService;
    }

    @GetMapping("/short/{shortcut}")
    public RedirectView redirecting(@PathVariable String shortcut, HttpServletRequest request) {
        if (shortcut != null && urlService.getByShortened(shortcut).getStatusCode().equals(HttpStatus.FOUND)) {
            ResponseEntity<String> fullUrlResponse = urlHistoryService.addNewHistory(shortcut, request.getRemoteAddr());
            return fullUrlResponse.getStatusCode().equals(HttpStatus.OK) ?
                    new RedirectView(fullUrlResponse.getBody()) : new RedirectView("/main-page");
        } else
            return new RedirectView("/main-page");
    }

    @GetMapping("history/{shortcut}")
    public String urlHistory(@PathVariable String shortcut, Model model, HttpSession session) {
        ResponseEntity<Url> shortened = urlService.getByShortened(shortcut);
        UserDetails user = (UserDetails) session.getAttribute("user");
        if (shortened.getStatusCode().equals(HttpStatus.FOUND)) {
            ResponseEntity<Set<UrlHistory>> urlHistoryByShortcut = urlHistoryService
                    .getUrlHistoryByShortcut(shortcut, user.getUsername());
            if (urlHistoryByShortcut.getStatusCode().equals(HttpStatus.FOUND)) {
                model.addAttribute("url", shortened.getBody());
                model.addAttribute("urlHistoryList", urlHistoryByShortcut.getBody());
                return "html/history";
            } else {
                model.addAttribute("url", shortened.getBody());
                return "html/history";
            }
        } else return "html/main-page";
    }

    @GetMapping("/delete/{shortcut}/**")
    public String deleteUser(@PathVariable("shortcut") String shortcut, HttpSession session, Model model) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        ResponseEntity<Url> deleteUrlResponse = urlService.deleteUrlByShortcut(shortcut, user.getUsername());
        if (deleteUrlResponse.getStatusCode().equals(HttpStatus.OK)) {
            return "redirect:/main-page";
        } else {
            model.addAttribute("info", "Something went wrong, please login again");
            return "/info";
        }
    }
}
