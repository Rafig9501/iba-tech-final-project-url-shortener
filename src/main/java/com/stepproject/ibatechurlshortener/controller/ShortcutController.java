package com.stepproject.ibatechurlshortener.controller;

import com.stepproject.ibatechurlshortener.model.Url;
import com.stepproject.ibatechurlshortener.model.UrlHistory;
import com.stepproject.ibatechurlshortener.service.url.UrlService;
import com.stepproject.ibatechurlshortener.service.url_history.UrlHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Controller
public class ShortcutController {

    private final UrlService urlService;
    private final UrlHistoryService urlHistoryService;

    public ShortcutController(UrlService urlService, UrlHistoryService urlHistoryService) {
        this.urlService = urlService;
        this.urlHistoryService = urlHistoryService;
    }

    @GetMapping("short/{shortcut}")
    public RedirectView redirecting(@PathVariable String shortcut, HttpServletRequest request) {
        if (shortcut != null && urlService.getByShortened(shortcut).getStatusCode().equals(HttpStatus.FOUND)) {
            String fullUrl = urlHistoryService.addNewHistory(shortcut, request.getRemoteAddr());
            return new RedirectView(fullUrl);
        } else
            return new RedirectView("/main-page");
    }

    @GetMapping("history/{shortcut}")
    public String urlHistory(@PathVariable String shortcut, Model model) {
        ResponseEntity<Url> shortened = urlService.getByShortened(shortcut);
        if (shortened.getStatusCode().equals(HttpStatus.FOUND)) {
            Set<UrlHistory> urlHistory = urlHistoryService.getUrlHistoryByShortcut(shortcut);
            model.addAttribute("url", shortened.getBody());
            model.addAttribute("urlHistoryList", urlHistory);
        }
        return "html/history";
    }
}
