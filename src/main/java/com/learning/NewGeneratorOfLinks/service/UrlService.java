package com.learning.NewGeneratorOfLinks.service;


import com.learning.NewGeneratorOfLinks.models.Url;

import java.util.List;


public interface UrlService {
    List<Url> getUrls();

    Url addUrl(String fullUrl);

    String getFullUrl(String shortUrl);

    List<Url> getTop10Urls();

    Url getFullInfo(String shortUrl);

    void incrementCount(String shortUrl);

}
