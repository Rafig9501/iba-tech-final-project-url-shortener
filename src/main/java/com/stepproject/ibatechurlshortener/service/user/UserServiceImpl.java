package com.stepproject.ibatechurlshortener.service.user;

import com.stepproject.ibatechurlshortener.dto.UserDto;
import com.stepproject.ibatechurlshortener.model.User;
import com.stepproject.ibatechurlshortener.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public ResponseEntity<User> findByEmail(String email) {
        try {
            Optional<User> user = userRepository.findByEmail(email);
            return user.map(value -> new ResponseEntity<>(value, HttpStatus.FOUND)).orElseGet(() ->
                    new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<User> save(UserDto userDto) {
        try {
            User user = new User(userDto.getName(), userDto.getLastName(),
                    userDto.getEmail(), encoder.encode(userDto.getPassword()));
            User saved = userRepository.saveAndFlush(user);
            return new ResponseEntity<>(saved, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByID(Long id) {
        return userRepository.findById(id).orElse(new User());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        User user = userOptional.get();
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), authorities);
    }

    @Override
    public ResponseEntity<User> registerUser(UserDto userDto) {
        try {
            ResponseEntity<User> byEmail = findByEmail(userDto.getEmail());
            if (byEmail.getStatusCode().equals(HttpStatus.FOUND))
                return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
            if (byEmail.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                return save(userDto);
            }
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
