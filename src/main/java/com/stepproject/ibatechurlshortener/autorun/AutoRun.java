package com.stepproject.ibatechurlshortener.autorun;

import com.stepproject.ibatechurlshortener.dto.UserDto;
import com.stepproject.ibatechurlshortener.model.Url;
import com.stepproject.ibatechurlshortener.model.User;
import com.stepproject.ibatechurlshortener.service.url.UrlService;
import com.stepproject.ibatechurlshortener.service.url.UrlShortenerService;
import com.stepproject.ibatechurlshortener.service.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AutoRun implements CommandLineRunner {

    private final UserService userService;
    private final UrlService urlService;
    private final UrlShortenerService shortenerService;

    public AutoRun(UserService userService, UrlService urlService, UrlShortenerService shortenerService) {
        this.userService = userService;
        this.urlService = urlService;
        this.shortenerService = shortenerService;
    }

    @Override
    public void run(String... args) throws Exception {
        loadData();
    }

    private void loadData() {

        UserDto user1 = new UserDto("Rafig","Mammadzada","rafig@gmail.com","123");

        UserDto user2 = new UserDto("Aga","Agayev","aga@gmail.com","123");

        userService.save(user1);
        userService.save(user2);

        Url url1 = new Url();
        url1.setDate(LocalDateTime.now());
        url1.setShortcut("https://github.com/Rafig9501/ibdsa");
        url1.setFull("https://github.com/Rafig9501/iba-tech-final-project-url-shortener");
        url1.setCount(8L);

        Url url2 = new Url();
        url2.setDate(LocalDateTime.now());
        url2.setShortcut("https://medium.com/javarevisited/how-implement");
        url2.setFull("https://medium.com/javarevisited/how-implement-url-shortening-by-java-and-spring-boot-cd87b35b548b");
        url2.setCount(8L);

        ResponseEntity<Url> saveUrl1 = urlService.save(url1);
        urlService.save(url2);

        Url saved = saveUrl1.getBody();
        if (saved != null) {
            String shortUrl = shortenerService.convertToShortUrl(saveUrl1.getBody());
            saved.setShortcut(shortUrl);
            urlService.save(saved);
        }
    }
}
