package com.stepproject.ibatechurlshortener.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
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
    @Column(name = "full_url", length = 10000)
    private String full;

    @Column(name = "count")
    private Long count;

    @NonNull
    @Column(name = "creation_date")
    private LocalDateTime date;

    @ManyToMany(mappedBy = "urls")
    @JsonBackReference
    private Set<User> users = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "url_history",
            joinColumns = @JoinColumn(name = "url_id"),
            inverseJoinColumns = @JoinColumn(name = "url_visit_history_id"))
    private Set<UrlHistory> urlHistory = new HashSet<>();
}
