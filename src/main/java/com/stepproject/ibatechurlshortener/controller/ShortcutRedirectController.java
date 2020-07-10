package com.stepproject.ibatechurlshortener.controller;

import com.stepproject.ibatechurlshortener.model.Url;
import com.stepproject.ibatechurlshortener.service.url.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ShortcutRedirectController {

    private final UrlService urlService;

    public ShortcutRedirectController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("{shortcut}")
    public RedirectView redirecting(@PathVariable String shortcut) {
        ResponseEntity<Url> shortened = urlService.getByShortened(shortcut);
        if (shortened.getStatusCode().equals(HttpStatus.FOUND)) {
            Url url = shortened.getBody();
            String fullUrl = url.getFull();
            url.setCount(url.getCount() + 1);
            urlService.save(url);
            return new RedirectView(fullUrl);
        }
        return new RedirectView("/main-page");
    }
}
