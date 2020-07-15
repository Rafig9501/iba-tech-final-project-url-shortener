package com.stepproject.ibatechurlshortener.service.url;

import com.stepproject.ibatechurlshortener.dto.UrlDto;
import com.stepproject.ibatechurlshortener.model.Url;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UrlShortenerService {

    private final UrlServiceImpl urlService;

    public UrlShortenerService(@Lazy UrlServiceImpl urlService) {
        this.urlService = urlService;
    }

    private static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyz0123456789";

    public static String randomAlphaNumeric() {
        StringBuilder builder = new StringBuilder();
        int count = 10;
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public Url convertToShortUrl(UrlDto urlRequested) {
        ResponseEntity<Url> saved = urlService.save(urlRequested);
        Url url = saved.getBody();
        if (url != null) {
            url.setShortcut(randomAlphaNumeric());
        }
        return url;
    }
}
