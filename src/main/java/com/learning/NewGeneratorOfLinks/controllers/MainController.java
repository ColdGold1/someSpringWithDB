package com.learning.NewGeneratorOfLinks.controllers;


import com.learning.NewGeneratorOfLinks.models.Url;
import com.learning.NewGeneratorOfLinks.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/urls")
@AllArgsConstructor
public class MainController {

    private final UrlService service;

    @GetMapping("")
    public List<Url> getUrls(){
        return service.getUrls();
    }

    @PutMapping("addUrl")
    public Url addUrl(@RequestParam String fullUrl, HttpServletResponse response) throws IOException {
        Url url = service.addUrl(fullUrl);
        if (url == null){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        return url;
    }

    @GetMapping("findUrl/{shortUrl}")
    public void findUrl(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
        String fullUrl = service.getFullUrl(shortUrl);
        if (fullUrl != null) {
            response.sendRedirect(fullUrl);
            service.incrementCount(shortUrl);

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @GetMapping("/top10")
    public List<Url> getTop10(){
        return service.getTop10Urls();
    }

    @GetMapping("/info/{shortUrl}")
    public Url info(@PathVariable String shortUrl){
        return service.getFullInfo(shortUrl);
    }

}
