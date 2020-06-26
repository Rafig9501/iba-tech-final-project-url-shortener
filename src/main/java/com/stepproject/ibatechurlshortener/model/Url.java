package com.stepproject.ibatechurlshortener.model;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.text.SimpleDateFormat;
import java.util.Set;

@Entity(name = "url")
@Getter
public class Url extends BaseEntity {

    @Column(name = "shortened_url")
    private String shortcut;

    @Column(name = "full_url")
    private String full;

    @Column(name = "count")
    private Long count;

    @Column(name = "creation_date")
    private SimpleDateFormat date;

    @ManyToMany(mappedBy = "urls")
    private Set<User> users;
}
