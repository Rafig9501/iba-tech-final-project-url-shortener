package com.stepproject.ibatechurlshortener.service.url;

import com.stepproject.ibatechurlshortener.dto.UrlDto;
import com.stepproject.ibatechurlshortener.model.Url;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UrlShortenerService {

    private static final String allowedString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final char[] allowedCharacters = allowedString.toCharArray();
    private final int baseLength = allowedCharacters.length;
    private final UrlServiceImpl urlService;

    public UrlShortenerService(@Lazy UrlServiceImpl urlService) {
        this.urlService = urlService;
    }

    public Url convertToShortUrl(UrlDto urlRequested) {
        ResponseEntity<Url> saved = urlService.save(urlRequested);
        Url url = saved.getBody();
        Long id = 0L;
        if (url != null) {
            id = url.getId();
        }
        assert url != null;
        url.setShortcut(encode(id));
        return url;
    }

    private String encode(long id) {
        StringBuilder encodedString = new StringBuilder();

        if (id == 0) {
            return String.valueOf(allowedCharacters[0]);
        }

        while (id > 0) {
            encodedString.append(allowedCharacters[(int) (id % baseLength)]);
            id = id / baseLength;
        }
        return encodedString.reverse().toString();
    }
}
