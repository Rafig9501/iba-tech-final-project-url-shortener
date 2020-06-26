package com.stepproject.ibatechurlshortener.repository;

import com.stepproject.ibatechurlshortener.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Override
    Optional<User> findById(Long aLong);
}
