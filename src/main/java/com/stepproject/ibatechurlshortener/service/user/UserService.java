package com.stepproject.ibatechurlshortener.service.user;

import com.stepproject.ibatechurlshortener.dto.UserDto;
import com.stepproject.ibatechurlshortener.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService  extends UserDetailsService {

    ResponseEntity<Optional<User>> findByEmail(String email);

    ResponseEntity<User> save(UserDto userDto);

    List<User> findAllUsers();

    User findUserByID(Long id);
}
