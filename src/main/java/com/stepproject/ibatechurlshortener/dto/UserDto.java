package com.stepproject.ibatechurlshortener.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

@AllArgsConstructor
@Getter
@Value
public class UserDto {

    @NonNull
    String name;

    @NonNull
    String lastName;

    @NonNull
    String email;

    @NonNull
    String password;
}
