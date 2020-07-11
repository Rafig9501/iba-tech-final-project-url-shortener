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

import java.util.Set;

@Controller
public class ShortcutRedirectController {

    private final UrlService urlService;
    private final UrlHistoryService urlHistoryService;

    public ShortcutRedirectController(UrlService urlService, UrlHistoryService urlHistoryService) {
        this.urlService = urlService;
        this.urlHistoryService = urlHistoryService;
    }

    @GetMapping("{shortcut}")
    public RedirectView redirecting(@PathVariable String shortcut) {
        System.out.println(" in method public RedirectView redirecting(@PathVariable String shortcut)");
        ResponseEntity<Url> shortened = urlService.getByShortened(shortcut);
        if (shortened.getStatusCode().equals(HttpStatus.FOUND)) {
            Url url = shortened.getBody();
            String fullUrl = url.getFull();
            url.setCount(url.getCount() + 1);
            urlHistoryService.saveToUrlHistory(shortcut);
            return new RedirectView(fullUrl);
        }
        return new RedirectView("/main-page");
    }

    @GetMapping("history/{shortcut}")
    public String urlHistory(@PathVariable String shortcut, Model model) {
        ResponseEntity<Url> shortened = urlService.getByShortened(shortcut);
        if (shortened.getStatusCode().equals(HttpStatus.FOUND)) {
            Set<UrlHistory> urlHistory = urlHistoryService.getUrlHistoryByShortcut(shortcut);
            System.out.println("salaaam" + urlHistory);
            model.addAttribute("urlHistoryList",urlHistory);
        }
        return "html/history";
    }
}
