package com.learning.NewGeneratorOfLinks.service;


import com.learning.NewGeneratorOfLinks.models.Url;
import com.learning.NewGeneratorOfLinks.repositories.UrlRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UrlServiceTest {
    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private UrlServiceBD urlService;



    @BeforeEach
    public void setUpService() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_addUrl_returningUrl() {
        String fullUrl = "https://wago.io/";
        Url url = Url.builder()
                .fullUrl(fullUrl)
                .shortUrl(Url.makeShortUrl())
                .createdTime(LocalDateTime.now())
                .countOf(0L)
                .lastTimeUsed(LocalDateTime.now())
                .build();

        when(urlRepository.save(any(Url.class))).thenReturn(url);

        Url result = urlService.addUrl(fullUrl);
        Assertions.assertEquals(fullUrl, result.getFullUrl());
        Assertions.assertNotNull(result.getShortUrl());
        verify(urlRepository,times(1)).save(any(Url.class));
    }

    @Test
    public void test_addUrl_returningNull(){
        String fullUrl = "123";
        Url url = Url.builder()
                .fullUrl(fullUrl)
                .shortUrl(Url.makeShortUrl())
                .createdTime(LocalDateTime.now())
                .countOf(0L)
                .lastTimeUsed(LocalDateTime.now())
                .build();

        when(urlRepository.save(any(Url.class))).thenReturn(url);

        Assertions.assertThrows(RuntimeException.class, () -> urlService.addUrl(url.getShortUrl()));
    }

    @Test
    public void test_getFullUrl_returningString() {
        String fullUrl = "https://wago.io/";
        String shortUrl = Url.makeShortUrl();
        Url url = Url.builder()
                .fullUrl(fullUrl)
                .shortUrl(shortUrl)
                .createdTime(LocalDateTime.now())
                .countOf(0L)
                .lastTimeUsed(LocalDateTime.now())
                .build();

        when(urlRepository.findUrlByShortUrl(anyString())).thenReturn(url);

        String result = urlService.getFullUrl(shortUrl);
        Assertions.assertEquals(fullUrl, result);
    }

    @Test
    public void test_incrementCount(){
        String fullUrl = "https://wago.io/";
        String shortUrl = Url.makeShortUrl();
        Url url = Url.builder()
                .fullUrl(fullUrl)
                .shortUrl(shortUrl)
                .createdTime(LocalDateTime.now())
                .countOf(0L)
                .lastTimeUsed(LocalDateTime.now())
                .build();

        when(urlRepository.findUrlByShortUrl(anyString())).thenReturn(url);
        when(urlRepository.save(any(Url.class))).thenReturn(url);

        urlService.incrementCount(shortUrl);
        verify(urlRepository,times(1)).save(any(Url.class));
    }

    @Test
    public void test_getTop10(){
        urlService.getTop10Urls();
        verify(urlRepository,times(1)).getTop10Urls();
    }

    @Test
    public void test_getInfo(){
        String fullUrl = "https://wago.io/";
        String shortUrl = Url.makeShortUrl();
        Url url = Url.builder()
                .fullUrl(fullUrl)
                .shortUrl(shortUrl)
                .createdTime(LocalDateTime.now())
                .countOf(0L)
                .lastTimeUsed(LocalDateTime.now())
                .build();

        when(urlRepository.findUrlByShortUrl(anyString())).thenReturn(url);

        Url result = urlService.getFullInfo(shortUrl);
        Assertions.assertEquals(fullUrl,result.getFullUrl());
        verify(urlRepository,times(1)).findUrlByShortUrl(anyString());
    }
}
