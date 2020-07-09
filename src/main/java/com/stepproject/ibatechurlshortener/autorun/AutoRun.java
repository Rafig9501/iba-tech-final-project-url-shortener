package com.stepproject.ibatechurlshortener.autorun;

import com.stepproject.ibatechurlshortener.database.service.url.UrlDBService;
import com.stepproject.ibatechurlshortener.database.service.user.UserDBService;
import com.stepproject.ibatechurlshortener.model.Url;
import com.stepproject.ibatechurlshortener.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class AutoRun implements CommandLineRunner {

    private final UrlDBService urlDBService;
    private final UserDBService userDBService;
    private final BCryptPasswordEncoder encoder;

    public AutoRun(UrlDBService urlDBService, UserDBService userDBService, BCryptPasswordEncoder encoder) {
        this.urlDBService = urlDBService;
        this.userDBService = userDBService;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {
        loadData();
    }

    private void loadData() {

        User user1 = new User("Rafig", "Mammadzada", "rafig@gmail.com",encoder.encode("123"));

        userDBService.save(user1);

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
        Set<User> users = new HashSet<>();
        users.add(user1);
        url1.setUsers(users);

        Url save = urlDBService.save(url1);
        Url save1 = urlDBService.save(url2);

        Set<Url> urls = new HashSet<>();
        urls.add(url1);
        urls.add(url2);
        user1.setUrls(urls);
        userDBService.save(user1);
    }
}
