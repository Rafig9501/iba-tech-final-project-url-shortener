package com.stepproject.ibatechurlshortener.service.user;

import com.stepproject.ibatechurlshortener.model.User;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface UserService {

    ResponseEntity<Optional<User>> findByEmail(String email);

    ResponseEntity<User> save(User url);

}
