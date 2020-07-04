package com.stepproject.ibatechurlshortener.dto;

import com.sun.istack.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Value
@Getter
public class UrlDto {

    @NotNull
    String fullUrl;
}
