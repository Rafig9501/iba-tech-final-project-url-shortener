package com.stepproject.ibatechurlshortener.database.service.url;

import com.stepproject.ibatechurlshortener.database.repository.UrlRepository;
import com.stepproject.ibatechurlshortener.database.service.CrudService;
import com.stepproject.ibatechurlshortener.model.Url;
import com.stepproject.ibatechurlshortener.model.User_;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UrlDBService implements CrudService<Url> {

    private final UrlRepository urlRepository;

    public UrlDBService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public Url save(Url url) {
        try {
            return urlRepository.save(url);
        } catch (Exception e) {
            return new Url();
        }
    }

    @Override
    public Optional<Url> getById(Long id) {
        try {
            return urlRepository.findById(id);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Url> getAll() {
        try {
            return urlRepository.findAll();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean delete(Url url) {
        try {
            urlRepository.delete(url);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<Url> getByShortcut(String shortcut) {
        try {
            return urlRepository.findByShortcut(shortcut);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Url> findAllByUser(User_ user) {
        try {
            return urlRepository.findUrlByUsers(user);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<Url> findByKeyword(String keyword) {
        try {
            return urlRepository.findUrlByKeyword(keyword);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
