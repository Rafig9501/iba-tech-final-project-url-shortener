package com.stepproject.ibatechurlshortener.service.url;

import com.stepproject.ibatechurlshortener.dto.UrlDto;
import com.stepproject.ibatechurlshortener.model.Url;
import com.stepproject.ibatechurlshortener.model.User_;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UrlService {

    ResponseEntity<Url> getByShortened(String shortened);
    ResponseEntity<List<Url>> getAllUrlsByUser(User_ user);
    ResponseEntity<Url> save(UrlDto url);
    ResponseEntity<Url> saveAndShorten(UrlDto urlDto, User_ user);
    ResponseEntity<List<Url>> findByKeyword(String keyword);
    ResponseEntity<Url> deleteUrlByShortcut(String shortUrl, String email);
}
