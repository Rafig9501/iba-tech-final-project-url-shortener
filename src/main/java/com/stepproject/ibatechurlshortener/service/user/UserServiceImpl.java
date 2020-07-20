package com.stepproject.ibatechurlshortener.service.user;

import com.stepproject.ibatechurlshortener.database.service.user.UserDBService;
import com.stepproject.ibatechurlshortener.dto.UserDto;
import com.stepproject.ibatechurlshortener.model.User_;
import com.stepproject.ibatechurlshortener.service.mail.MailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserDBService userDBService;
    private final BCryptPasswordEncoder encoder;
    private final MailService mailService;

    public UserServiceImpl(UserDBService userDBService, BCryptPasswordEncoder encoder, MailService mailService) {
        this.userDBService = userDBService;
        this.encoder = encoder;
        this.mailService = mailService;
    }

    @Override
    public ResponseEntity<User_> findByEmail(String email) {
        try {
            Optional<User_> user = userDBService.findByEmail(email);
            return user.map(value -> new ResponseEntity<>(value, HttpStatus.FOUND)).orElseGet(() ->
                    new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<User_> save(UserDto userDto) {
        try {
            User_ user = new User_(userDto.getName(), userDto.getLastName(),
                    userDto.getEmail(), encoder.encode(userDto.getPassword()));
            User_ saved = userDBService.save(user);
            return new ResponseEntity<>(saved, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User_> userOptional = userDBService.findByEmail(email);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        User_ user = userOptional.get();
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), authorities);
    }

    @Override
    public ResponseEntity<User_> registerUser(UserDto userDto) {
        try {
            ResponseEntity<User_> byEmail = findByEmail(userDto.getEmail());
            if (byEmail.getStatusCode().equals(HttpStatus.FOUND)) {
                return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
            } else if (byEmail.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                return save(userDto);
            }
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> setUserActivationCode(String email) {
        String token = mailService.generateToken();
        ResponseEntity<User_> userByEmail = findByEmail(email);
        if (userByEmail.getStatusCode().equals(HttpStatus.FOUND)) {
            User_ user = userByEmail.getBody();
            user.setActivationCode(token);
            userDBService.save(user);
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<User_> checkUserActivationCode(String activationCode) {
        Optional<User_> user = userDBService.getUserByActivationCode(activationCode);
        return user.map(u -> new ResponseEntity<>(u, HttpStatus.FOUND)).orElseGet(() ->
                new ResponseEntity<>(new User_(), HttpStatus.NO_CONTENT));
    }

    @Override
    public ResponseEntity<User_> updatePassword(String email, String password) {
        Optional<User_> userOptional = userDBService.findByEmail(email);
        if (userOptional.isPresent()) {
            User_ user = userOptional.get();
            user.setPassword(encoder.encode(password));
            user.setActivationCode(null);
            User_ saved = userDBService.save(user);
            return new ResponseEntity<>(saved, HttpStatus.OK);
        } else return new ResponseEntity<>(new User_(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
