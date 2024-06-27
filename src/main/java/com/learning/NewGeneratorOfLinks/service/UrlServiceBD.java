package com.learning.NewGeneratorOfLinks.service;

import com.learning.NewGeneratorOfLinks.models.Url;
import com.learning.NewGeneratorOfLinks.repositories.UrlRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;


@Primary
@AllArgsConstructor
@Service
public class UrlServiceBD implements IUrlService{

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
            throw new RuntimeException(e);
        }

        String shortUrl = findUniqueUrl();

//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
//        LocalDateTime nw = LocalDateTime.parse(LocalDateTime.now().format(dateTimeFormatter),dateTimeFormatter);

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

    private boolean isAvailable(String fullUrl) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(fullUrl).openConnection();
        connection.setRequestMethod("HEAD");
        return connection.getResponseCode() != HttpURLConnection.HTTP_OK;
    }

    @Override
    public String getFullUrl(String shortUrl) {

        return (urlRepository.findUrlByShortUrl(shortUrl) == null) ? null : urlRepository.findUrlByShortUrl(shortUrl).getFullUrl();
    }

    @Override
    public List<Url> getTop10Urls() {
        return urlRepository.getTop10Urls();
    }

    @Override
    public Url getFullInfo(String shortUrl) {
        return urlRepository.findUrlByShortUrl(shortUrl);
    }


    public void incrementCount(String shortUrl){
        Url url = urlRepository.findUrlByShortUrl(shortUrl);
        url.setCountOf(url.getCountOf() + 1);

//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
//        LocalDateTime nw = LocalDateTime.parse(LocalDateTime.now().format(dateTimeFormatter),dateTimeFormatter);

        url.setLastTimeUsed(LocalDateTime.now());
        urlRepository.save(url);
    }

    private String findUniqueUrl() {
        StringBuilder result = new StringBuilder();
        do {
            result.delete(0, result.length());
            result.append(Url.makeShortUrl());
        } while (!uniqueShortUrls.add(result.toString()));
        return result.toString();
    }
}
