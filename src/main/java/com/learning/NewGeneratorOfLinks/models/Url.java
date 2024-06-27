package com.learning.NewGeneratorOfLinks.models;


import jakarta.persistence.*;
import lombok.*;

import javax.annotation.processing.Generated;
import java.time.LocalDateTime;
import java.util.Random;

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


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public Long getCountOf() {
        return countOf;
    }

    public void setCountOf(Long countOf) {
        this.countOf = countOf;
    }

    public LocalDateTime getLastTimeUsed() {
        return lastTimeUsed;
    }

    public void setLastTimeUsed(LocalDateTime lastTimeUsed) {
        this.lastTimeUsed = lastTimeUsed;
    }

    public static String makeShortUrl() {
        StringBuilder newShortUrl = new StringBuilder();
        String s = "abcdefghijklmonpqrstuvwxyzABCDEFGHIJKLMOPQRSTUVWXYZ0123456789";
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            newShortUrl.append(s.charAt(random.nextInt(s.length())));
        }

        return newShortUrl.toString();
    }

}
