package com.stepproject.ibatechurlshortener.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Value;

import java.time.LocalDateTime;

@AllArgsConstructor
@Value
@Getter
public class UrlDto {

    @NotNull
    String fullUrl;

    @NotNull
    String shortUrl;

    @NotNull
    LocalDateTime creationDate;

    @NotNull
    Long count;
}
