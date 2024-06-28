package com.learning.NewGeneratorOfLinks.service;

import com.learning.NewGeneratorOfLinks.models.Url;
import com.learning.NewGeneratorOfLinks.repositories.UrlRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;


@Primary
@AllArgsConstructor
@Service
public class UrlServiceBD implements UrlService {

    private UrlRepository urlRepository;

    @Override
    public List<Url> getUrls() {
        return urlRepository.findAll();
    }

    @Override
    public Url addUrl(String fullUrl) {
        try {
            boolean b = isAvailable(fullUrl);
        } catch (Exception e) {
            return null;
        }


        String shortUrl = findUniqueUrl();
        Url url = Url.builder()
                .fullUrl(fullUrl)
                .shortUrl(shortUrl)
                .createdTime(LocalDateTime.now())
                .countOf(0L)
                .lastTimeUsed(LocalDateTime.now())
                .build();

        if (urlRepository.findUrlByFullUrl(fullUrl) == null) {
            urlRepository.save(url);
            return url;
        }

        return urlRepository.findUrlByFullUrl(fullUrl);
    }

    private String findUniqueUrl() {
        StringBuilder result = new StringBuilder();
        do {
            result.delete(0, result.length());
            result.append(makeShortUrl());
        } while (urlRepository.findUrlByShortUrl(result.toString()) != null);
        return result.toString();
    }

    public String makeShortUrl() {
        StringBuilder newShortUrl = new StringBuilder();
        String s = "abcdefghijklmonpqrstuvwxyzABCDEFGHIJKLMOPQRSTUVWXYZ0123456789";
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            newShortUrl.append(s.charAt(random.nextInt(s.length())));
        }

        return newShortUrl.toString();
    }

    private boolean isAvailable(String fullUrl) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(fullUrl)
                .openConnection();
        connection.setRequestMethod("HEAD");
        return connection.getResponseCode() != HttpURLConnection.HTTP_OK;
    }

    @Override
    public String getFullUrl(String shortUrl) {

        return (urlRepository.findUrlByShortUrl(shortUrl) == null)
               ? null
               : urlRepository.findUrlByShortUrl(shortUrl).getFullUrl();
    }

    @Override
    public List<Url> getTop10Urls() {
        return urlRepository.getTop10Urls();
    }

    @Override
    public Url getFullInfo(String shortUrl) {
        return urlRepository.findUrlByShortUrl(shortUrl);
    }

    public void incrementCount(String shortUrl) {
        Url url = urlRepository.findUrlByShortUrl(shortUrl);
        url.setCountOf(url.getCountOf() + 1);

        url.setLastTimeUsed(LocalDateTime.now());
        urlRepository.save(url);
    }

}
