package com.stepproject.ibatechurlshortener.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
@Value
@Getter
public class UrlDto {

    @NonNull
    String fullUrl;
}
