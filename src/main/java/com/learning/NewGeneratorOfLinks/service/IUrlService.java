package com.learning.NewGeneratorOfLinks.service;


import com.learning.NewGeneratorOfLinks.models.Url;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public interface IUrlService {
    //HashMap<String, Url> urls = new HashMap<>();
    HashSet<String> uniqueShortUrls = new HashSet<>();

    List<Url> getUrls();
    Url addUrl(String fullUrl);
    String getFullUrl(String shortUrl);
    List<Url> getTop10Urls();
    Url getFullInfo(String shortUrl);
    void incrementCount(String shortUrl);

}
