package com.stepproject.ibatechurlshortener.service.user;

import com.stepproject.ibatechurlshortener.model.User;
import com.stepproject.ibatechurlshortener.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<Optional<User>> findByEmail(String email) {
        try {
            Optional<User> user = userRepository.findByEmail(email);
            return user.isPresent() ? new ResponseEntity<>(user, HttpStatus.FOUND)
                    : new ResponseEntity<>(Optional.empty(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(Optional.empty(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<User> save(User user) {
        try {
            return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}