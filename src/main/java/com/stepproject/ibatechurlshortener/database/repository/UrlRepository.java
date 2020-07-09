package com.stepproject.ibatechurlshortener.database.repository;

import com.stepproject.ibatechurlshortener.model.Url;
import com.stepproject.ibatechurlshortener.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url,Long> {

    Optional<Url> findByShortcut(String shortCut);

    List<Url> findUrlByUsers(User user);
}
