package com.stepproject.ibatechurlshortener.database.repository;

import com.stepproject.ibatechurlshortener.model.Url;
import com.stepproject.ibatechurlshortener.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url,Long> {

    Optional<Url> findByShortcut(String shortCut);

    List<Url> findUrlByUsers(User user);

    @Query(value = "select u from url u where u .full like %:keyword% or u .shortcut like %:keyword%")
    List<Url> findUrlByKeyword(@Param("keyword") String keyword);
}
