package com.stepproject.ibatechurlshortener.service.user;

import com.stepproject.ibatechurlshortener.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

    public ResponseEntity<User> get(Long id);

    public ResponseEntity<User> save(User url);
}
