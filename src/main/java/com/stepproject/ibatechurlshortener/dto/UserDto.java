package com.stepproject.ibatechurlshortener.dto;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor
@Getter
@Value
public class UserDto {

    @NotNull
    String name;

    @NotNull
    String lastName;

    @NotNull
    String email;

    @NotNull
    String password;
}
