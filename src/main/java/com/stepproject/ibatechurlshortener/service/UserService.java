package com.stepproject.ibatechurlshortener.service;

import com.stepproject.ibatechurlshortener.model.User;
import com.stepproject.ibatechurlshortener.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<User> get(Long id) {
        try {
            Optional<User> url = userRepository.findById(id);
            return url.map(value -> new ResponseEntity<>(value, HttpStatus.FOUND)).orElseGet(() ->
                    new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<User> create(User user) {
        try {
            return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
