package com.stepproject.ibatechurlshortener.service.url_history;

import com.stepproject.ibatechurlshortener.model.UrlHistory;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface UrlHistoryService {

    void saveToUrlHistory(String shortcut);

    Set<UrlHistory> getUrlHistoryByShortcut(String shortcut);
}
