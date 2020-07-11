package com.stepproject.ibatechurlshortener.service.url_history;


import com.stepproject.ibatechurlshortener.database.service.url.UrlDBService;
import com.stepproject.ibatechurlshortener.database.service.url_history.UrlHistoryDBService;
import com.stepproject.ibatechurlshortener.model.Url;
import com.stepproject.ibatechurlshortener.model.UrlHistory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UrlHistoryServiceImpl implements UrlHistoryService {

    private final UrlDBService urlDBService;
    private final UrlHistoryDBService urlHistoryDBService;

    public UrlHistoryServiceImpl(UrlDBService urlDBService, UrlHistoryDBService urlHistoryDBService) {
        this.urlDBService = urlDBService;
        this.urlHistoryDBService = urlHistoryDBService;
    }

    @Override
    public void saveToUrlHistory(String shortcut) {
        Optional<Url> urlByShortcut = urlDBService.getByShortcut(shortcut);
        if (urlByShortcut.isPresent()) {
            Url url = urlByShortcut.get();
            Set<UrlHistory> urlHistories = url.getUrlHistory();
            UrlHistory urlHistory = new UrlHistory(LocalDateTime.now());
            urlHistoryDBService.save(urlHistory);
            urlHistories.add(urlHistory);
            url.setUrlHistory(urlHistories);
            urlDBService.save(url);
        }
    }

    @Override
    public Set<UrlHistory> getUrlHistoryByShortcut(String shortcut) {
        Optional<Url> url = urlDBService.getByShortcut(shortcut);
        return url.isPresent() ? urlHistoryDBService.findUrlHistory(url.get())
                : new HashSet<>();
    }
}
