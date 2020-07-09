package com.stepproject.ibatechurlshortener.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "url")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Url extends BaseEntity {

    @NonNull
    @Column(name = "shortened_url", unique = true)
    private String shortcut;

    @NonNull
    @Column(name = "full_url")
    private String full;

    @Column(name = "count")
    private Long count;

    @NonNull
    @Column(name = "creation_date")
    private LocalDateTime date;

    @ManyToMany(mappedBy = "urls")
    @JsonBackReference
    private Set<User> users = new HashSet<>();
}
