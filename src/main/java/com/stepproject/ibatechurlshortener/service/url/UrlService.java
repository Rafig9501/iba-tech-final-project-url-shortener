package com.stepproject.ibatechurlshortener.service.url;

import com.stepproject.ibatechurlshortener.model.Url;
import org.springframework.http.ResponseEntity;

public interface UrlService {

    public ResponseEntity<Url> getByShortened(String shortened);

    public ResponseEntity<Url> save(Url url);
}
