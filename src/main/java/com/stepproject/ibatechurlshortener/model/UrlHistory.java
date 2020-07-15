package com.stepproject.ibatechurlshortener.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "url_visit_history")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class UrlHistory extends BaseEntity{

    @NonNull
    @Column(name = "url_visit_date")
    private LocalDateTime dateTime;

    @NonNull
    @Column(name = "ip_address")
    private String IPAddress;

    @ManyToMany(mappedBy = "urlHistory")
    private Set<Url> urls = new HashSet<>();
}
