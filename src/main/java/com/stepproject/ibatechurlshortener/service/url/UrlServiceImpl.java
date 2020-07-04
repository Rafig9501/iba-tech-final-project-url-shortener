package com.stepproject.ibatechurlshortener.service.url;

import com.stepproject.ibatechurlshortener.dto.UrlDto;
import com.stepproject.ibatechurlshortener.model.Url;
import com.stepproject.ibatechurlshortener.repository.UrlRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UrlServiceImpl implements UrlService {
    private final UrlRepository urlRepository;
    private final UrlShortenerService urlShortenerService;

    public UrlServiceImpl(UrlRepository urlRepository, UrlShortenerService urlShortenerService) {
        this.urlRepository = urlRepository;
        this.urlShortenerService = urlShortenerService;
    }

    @Override
    public ResponseEntity<Url> getByShortened(String shortCut) {
        try {
            Optional<Url> url = urlRepository.findByShortcut(shortCut);
            return url.map(value -> new ResponseEntity<>(value, HttpStatus.FOUND)).orElseGet(() ->
                    new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Url> save(UrlDto urlDto) {
        try {
            Url url = new Url();
            url.setFull(urlDto.getFullUrl());
            return new ResponseEntity<>(urlRepository.save(url), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Url> saveAndShorten(UrlDto urlDto) {
        try {
            Url url = urlShortenerService.convertToShortUrl(urlDto);
            url.setDate(LocalDateTime.now());
            Url save = urlRepository.save(url);
            return new ResponseEntity<>(save, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
