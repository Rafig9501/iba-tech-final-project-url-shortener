package com.stepproject.ibatechurlshortener.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UrlRequest {

    private String fullUrl;

    private String shortUrl;

    private LocalDateTime creationDate;

    private Long count;
}
