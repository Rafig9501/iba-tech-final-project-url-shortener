package com.stepproject.ibatechurlshortener.service.url;

import com.stepproject.ibatechurlshortener.model.Url;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UrlShortenerService {

    private static final String allowedString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final char[] allowedCharacters = allowedString.toCharArray();
    private final int baseLength = allowedCharacters.length;

    public String convertToShortUrl(Url urlRequest) {
        String full = urlRequest.getFull();
        Long id = urlRequest.getId();
        return encode(full, id);
    }

    private String encode(String fullUrl, long id) {
        StringBuilder encodedString = new StringBuilder();

        if (id == 0) {
            return String.valueOf(allowedCharacters[0]);
        }

        while (id > 0) {
            encodedString.append(allowedCharacters[(int) (id % baseLength)]);
            id = id / baseLength;
        }

        StringBuilder pageName = new StringBuilder();
        for (int i = 8; i < fullUrl.length(); i++) {
            pageName.append(fullUrl.charAt(i));
            if (fullUrl.charAt(i) == '.')
                break;
        }
        pageName.insert(3, ".");
        return pageName + encodedString.reverse().toString();
    }
}
