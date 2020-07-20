package com.stepproject.ibatechurlshortener.service.user;

import com.stepproject.ibatechurlshortener.dto.UserDto;
import com.stepproject.ibatechurlshortener.model.User_;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
public interface UserService extends UserDetailsService {

    ResponseEntity<User_> findByEmail(String email);

    ResponseEntity<User_> save(UserDto userDto);

    ResponseEntity<User_> registerUser(UserDto userDto);

    ResponseEntity<String> setUserActivationCode(String email);

    ResponseEntity<User_> checkUserActivationCode(String token);

    ResponseEntity<User_> updatePassword(String email, String password);
}
