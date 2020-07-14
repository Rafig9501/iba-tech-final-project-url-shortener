package com.stepproject.ibatechurlshortener.service.user;

import com.stepproject.ibatechurlshortener.dto.UserDto;
import com.stepproject.ibatechurlshortener.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {

    ResponseEntity<User> findByEmail(String email);

    ResponseEntity<User> save(UserDto userDto);

    ResponseEntity<User> registerUser(UserDto userDto);

    ResponseEntity<String> setUserActivationCode(String email);

    ResponseEntity<User> checkUserActivationCode(String token);

    ResponseEntity<User> updatePassword(String email, String password);
}
