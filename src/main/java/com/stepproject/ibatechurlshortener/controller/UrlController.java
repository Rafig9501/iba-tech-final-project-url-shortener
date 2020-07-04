package com.stepproject.ibatechurlshortener.controller;

import com.stepproject.ibatechurlshortener.dto.UrlDto;
import com.stepproject.ibatechurlshortener.service.url.UrlService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Log4j2
@Controller
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("main-page")
    public String main(Model model) {
        model.addAttribute("url", "main-page/shorten");
        return "html/main-page";
    }

    @PostMapping("main-page/shorten")
    public String shortUrl(@RequestParam(name = "url") String urlParam) {
        UrlDto urlDto = new UrlDto(urlParam);
        urlService.saveAndShorten(urlDto);
        return "html/main-page";
    }
}