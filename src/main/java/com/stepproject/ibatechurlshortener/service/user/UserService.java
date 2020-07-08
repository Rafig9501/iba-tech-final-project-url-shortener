package com.stepproject.ibatechurlshortener.service.user;

import com.stepproject.ibatechurlshortener.dto.UserDto;
import com.stepproject.ibatechurlshortener.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService  extends UserDetailsService {

    ResponseEntity<User> findByEmail(String email);

    ResponseEntity<User> save(UserDto userDto);

    List<User> findAllUsers();

    User findUserByID(Long id);

    ResponseEntity<User> registerUser(UserDto userDto);
}
