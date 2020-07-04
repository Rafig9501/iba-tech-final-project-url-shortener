package com.stepproject.ibatechurlshortener.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "url")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Url extends BaseEntity {

//    public Url(@NonNull String shortcut, @NonNull String full, @NonNull Long count, @NonNull LocalDateTime date) {
//        this.shortcut = shortcut;
//        this.full = full;
//        this.count = count;
//        this.date = date;
//    }

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
    private Set<User> users;
}
