package com.stepproject.ibatechurlshortener.database.service.url_history;

import com.stepproject.ibatechurlshortener.database.repository.UrlHistoryRepository;
import com.stepproject.ibatechurlshortener.database.service.CrudService;
import com.stepproject.ibatechurlshortener.model.Url;
import com.stepproject.ibatechurlshortener.model.UrlHistory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UrlHistoryDBService implements CrudService<UrlHistory> {

    private final UrlHistoryRepository urlHistoryRepository;

    public UrlHistoryDBService(UrlHistoryRepository urlHistoryRepository) {
        this.urlHistoryRepository = urlHistoryRepository;
    }

    @Override
    public UrlHistory save(UrlHistory urlHistory) {
        try {
            return urlHistoryRepository.save(urlHistory);
        } catch (Exception e) {
            return new UrlHistory();
        }
    }

    @Override
    public Optional<UrlHistory> getById(Long id) {
        try {
            return urlHistoryRepository.findById(id);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<UrlHistory> getAll() {
        try {
            return urlHistoryRepository.findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public boolean delete(UrlHistory urlHistory) {
        try {
            urlHistoryRepository.delete(urlHistory);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Set<UrlHistory> findUrlHistory(Url url) {
        try {
            Set<UrlHistory> urlHistories = new HashSet<>();
            List<UrlHistory> urlHistoriesByUrls = urlHistoryRepository.findUrlHistoriesByUrls(url);
            urlHistoriesByUrls.addAll(urlHistories);
            return urlHistories;
        } catch (Exception e) {
            return new HashSet<>();
        }
    }
}
