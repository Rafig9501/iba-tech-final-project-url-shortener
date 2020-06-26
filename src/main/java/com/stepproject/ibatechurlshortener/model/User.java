package com.stepproject.ibatechurlshortener.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "user_")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class User extends BaseEntity{

    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @Column(name = "last_name")
    private String lastName;

    @NonNull
    @Column(name = "email")
    private String email;

    @NonNull
    @Column(name = "password")
    private String password;

    @NonNull
    @ManyToMany
    @JoinTable(name = "user_url",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "url_id"))
    @JsonManagedReference
    private Set<Url> urls;
}
