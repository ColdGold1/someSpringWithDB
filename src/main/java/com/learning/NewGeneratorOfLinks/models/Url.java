package com.learning.NewGeneratorOfLinks.models;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Setter
@Getter
@Entity
@Table(name = "urls")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String fullUrl;

    @Column(nullable = false, unique = true)
    private String shortUrl;

    @Column(nullable = false)
    private LocalDateTime createdTime;

    @Column(nullable = false)
    private Long countOf;

    private LocalDateTime lastTimeUsed;

}
