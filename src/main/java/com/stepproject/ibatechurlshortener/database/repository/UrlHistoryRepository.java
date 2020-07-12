package com.stepproject.ibatechurlshortener.database.repository;

import com.stepproject.ibatechurlshortener.model.Url;
import com.stepproject.ibatechurlshortener.model.UrlHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlHistoryRepository extends JpaRepository<UrlHistory,Long> {

    List<UrlHistory> findUrlHistoryByUrls(Url url);
}
