package com.stepproject.ibatechurlshortener.controller;

import com.stepproject.ibatechurlshortener.model.Url;
import com.stepproject.ibatechurlshortener.service.url.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/url")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping(path = "{url}")
    public RedirectView getOriginUrl(@PathVariable String url) {
        Url shortened = urlService.getByShortened(url).getBody();
        if (shortened != null)
            return new RedirectView(shortened.getFull());
        else throw new RuntimeException();
    }
}
