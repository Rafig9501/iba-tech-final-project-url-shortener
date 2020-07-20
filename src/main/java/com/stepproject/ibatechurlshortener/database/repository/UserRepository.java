package com.stepproject.ibatechurlshortener.database.repository;

import com.stepproject.ibatechurlshortener.model.User_;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User_,Long> {

    Optional<User_> findByEmail(String email);

    Optional<User_> findUserByActivationCode(String activationCode);
}
