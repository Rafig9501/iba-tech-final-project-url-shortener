package com.stepproject.ibatechurlshortener.service.url;

import com.stepproject.ibatechurlshortener.database.service.url.UrlDBService;
import com.stepproject.ibatechurlshortener.database.service.user.UserDBService;
import com.stepproject.ibatechurlshortener.dto.UrlDto;
import com.stepproject.ibatechurlshortener.model.Url;
import com.stepproject.ibatechurlshortener.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UrlServiceImpl implements UrlService {

    private final UrlShortenerService urlShortenerService;
    private final UrlDBService urlDBService;
    private final UserDBService userDBService;


    public UrlServiceImpl(UrlShortenerService urlShortenerService, UrlDBService urlDBService, UserDBService userDBService) {
        this.urlShortenerService = urlShortenerService;
        this.urlDBService = urlDBService;
        this.userDBService = userDBService;
    }

    @Override
    public ResponseEntity<Url> getByShortened(String shortCut) {
        try {
            Optional<Url> url = urlDBService.getByShortcut(shortCut);
            return url.map(value -> new ResponseEntity<>(value, HttpStatus.FOUND)).orElseGet(() ->
                    new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Url>> getAllUrlsByUser(User user) {
        try {
            return new ResponseEntity<>(urlDBService.findAllByUser(user), HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Url> save(UrlDto urlDto) {
        try {
            Url url = new Url();
            url.setFull(urlDto.getFullUrl());
            return new ResponseEntity<>(urlDBService.save(url), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Url> saveAndShorten(UrlDto urlDto, User user) {
        try {
            Url url = urlShortenerService.convertToShortUrl(urlDto);
            while (urlDBService.getByShortcut(url.getShortcut()).isPresent()) {
                url = urlShortenerService.convertToShortUrl(urlDto);
            }
            url.setDate(LocalDateTime.now());
            url.setCount(0L);
            Url save = urlDBService.save(url);
            user.getUrls().add(url);
            userDBService.save(user);
            return new ResponseEntity<>(save, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Url>> findByKeyword(String keyword) {
        try {
            List<Url> urls = urlDBService.findByKeyword(keyword);
            return urls.isEmpty() ? new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT)
                    : new ResponseEntity<>(urls, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Url> deleteUrlByShortcut(String shortUrl, String email) {
        try {
            Optional<Url> urlOptional = urlDBService.getByShortcut(shortUrl);
            Optional<User> userOptional = userDBService.findByEmail(email);
            if (urlOptional.isPresent() && userOptional.isPresent() &&
                    urlDBService.findAllByUser(userOptional.get()).contains(urlOptional.get())) {
                User user = userOptional.get();
                user.getUrls().remove(urlOptional.get());
                userDBService.save(user);
                return urlDBService.delete(urlOptional.get()) ? new ResponseEntity<>(new Url(), HttpStatus.OK)
                        : new ResponseEntity<>(new Url(), HttpStatus.FORBIDDEN);
            } else return new ResponseEntity<>(new Url(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new Url(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
