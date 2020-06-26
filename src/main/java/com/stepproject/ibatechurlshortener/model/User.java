package com.stepproject.ibatechurlshortener.model;

import lombok.Getter;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "user_")
@Getter
public class User extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToMany
    @JoinTable(name = "user_url",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "url_id"))
    private Set<Url> urls;
}
