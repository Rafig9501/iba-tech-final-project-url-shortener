package com.stepproject.ibatechurlshortener.database.service.user;

import com.stepproject.ibatechurlshortener.database.repository.UserRepository;
import com.stepproject.ibatechurlshortener.database.service.CrudService;
import com.stepproject.ibatechurlshortener.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDBService implements CrudService<User> {

    private final UserRepository userRepository;

    public UserDBService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            return new User();
        }
    }

    @Override
    public Optional<User> getById(Long id) {
        try {
            return userRepository.findById(id);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> getAll() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public boolean delete(User user) {
        try {
            userRepository.delete(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<User> findByEmail(String email) {
        try {
            return userRepository.findByEmail(email);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
