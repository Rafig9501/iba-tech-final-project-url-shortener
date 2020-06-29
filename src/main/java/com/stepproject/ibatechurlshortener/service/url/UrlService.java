package com.stepproject.ibatechurlshortener.service.url;

import com.stepproject.ibatechurlshortener.model.Url;
import org.springframework.http.ResponseEntity;

public interface UrlService {

    ResponseEntity<Url> getByShortened(String shortened);

    ResponseEntity<Url> save(Url url);
}
