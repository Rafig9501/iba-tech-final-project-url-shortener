package com.stepproject.ibatechurlshortener.database.service.user;

import com.stepproject.ibatechurlshortener.database.repository.UserRepository;
import com.stepproject.ibatechurlshortener.database.service.CrudService;
import com.stepproject.ibatechurlshortener.model.User_;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDBService implements CrudService<User_> {

    private final UserRepository userRepository;

    public UserDBService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User_ save(User_ user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            return new User_();
        }
    }

    @Override
    public Optional<User_> getById(Long id) {
        try {
            return userRepository.findById(id);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User_> getAll() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public boolean delete(User_ user) {
        try {
            userRepository.delete(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<User_> findByEmail(String email) {
        try {
            return userRepository.findByEmail(email);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<User_> getUserByActivationCode(String activationCode) {
        try {
            return userRepository.findUserByActivationCode(activationCode);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
