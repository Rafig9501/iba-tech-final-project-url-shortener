package com.stepproject.ibatechurlshortener.service.url;

import com.stepproject.ibatechurlshortener.dto.UrlDto;
import com.stepproject.ibatechurlshortener.model.Url;
import com.stepproject.ibatechurlshortener.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UrlService {

    ResponseEntity<Url> getByShortened(String shortened);
    ResponseEntity<List<Url>> getAllByUser(User user);
    ResponseEntity<Url> save(UrlDto url);
    ResponseEntity<Url> save(Url url);
    ResponseEntity<Url> saveAndShorten(UrlDto urlDto, User user);
}
