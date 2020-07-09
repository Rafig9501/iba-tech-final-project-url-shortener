package com.stepproject.ibatechurlshortener.database.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CrudService<T> {

    T save(T t);
    Optional<T> getById(Long id);
    List<T> getAll();
    boolean delete(T t);
}
