package com.stepproject.ibatechurlshortener.service.url_history;

import com.stepproject.ibatechurlshortener.database.service.url.UrlDBService;
import com.stepproject.ibatechurlshortener.database.service.url_history.UrlHistoryDBService;
import com.stepproject.ibatechurlshortener.database.service.user.UserDBService;
import com.stepproject.ibatechurlshortener.model.Url;
import com.stepproject.ibatechurlshortener.model.UrlHistory;
import com.stepproject.ibatechurlshortener.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UrlHistoryServiceImpl implements UrlHistoryService {

    private final UrlDBService urlDBService;
    private final UrlHistoryDBService urlHistoryDBService;
    private final UserDBService userDBService;

    public UrlHistoryServiceImpl(UrlDBService urlDBService, UrlHistoryDBService urlHistoryDBService, UserDBService userDBService) {
        this.urlDBService = urlDBService;
        this.urlHistoryDBService = urlHistoryDBService;
        this.userDBService = userDBService;
    }

    @Override
    public ResponseEntity<UrlHistory> saveToUrlHistory(String shortcut, String IPAddress) {
        Optional<Url> urlByShortcut = urlDBService.getByShortcut(shortcut);
        if (urlByShortcut.isPresent()) {
            try {
                Url url = urlByShortcut.get();
                Set<UrlHistory> urlHistories = url.getUrlHistory();
                UrlHistory urlHistory = new UrlHistory(LocalDateTime.now(), IPAddress);
                UrlHistory saveUrlHistory = urlHistoryDBService.save(urlHistory);
                urlHistories.add(urlHistory);
                url.setUrlHistory(urlHistories);
                urlDBService.save(url);
                return new ResponseEntity<>(saveUrlHistory, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(new UrlHistory(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(new UrlHistory(), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Set<UrlHistory>> getUrlHistoryByShortcut(String shortcut, String email) {
        Optional<User> user = userDBService.findByEmail(email);
        Optional<Url> url = urlDBService.getByShortcut(shortcut);
        if (user.isPresent() && url.isPresent() && urlDBService.findAllByUser(user.get()).contains(url.get())) {
            return new ResponseEntity<>(urlHistoryDBService.findUrlHistory(url.get()), HttpStatus.FOUND);
        } else return new ResponseEntity<>(new HashSet<>(), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> addNewHistory(String shortcut, String IPAddress) {
        Optional<Url> urlByShortcut = urlDBService.getByShortcut(shortcut);
        if (urlByShortcut.isPresent()) {
            Url url = urlByShortcut.get();
            String fullUrl = url.getFull();
            url.setCount(url.getCount() + 1);
            return saveToUrlHistory(shortcut, IPAddress).getStatusCode().equals(HttpStatus.OK) ?
                    new ResponseEntity<>(fullUrl, HttpStatus.OK) : new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
