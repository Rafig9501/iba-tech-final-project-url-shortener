package com.stepproject.ibatechurlshortener.service.url_history;

import com.stepproject.ibatechurlshortener.model.UrlHistory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface UrlHistoryService {

    ResponseEntity<UrlHistory> saveToUrlHistory(String shortcut, String IPAddress);

    ResponseEntity<Set<UrlHistory>> getUrlHistoryByShortcut(String shortcut, String email);

    ResponseEntity<String> addNewHistory(String shortcut, String IPAddress);
}
