package com.stepproject.ibatechurlshortener.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDto {

    private final String name;
    private final String lastName;
}
