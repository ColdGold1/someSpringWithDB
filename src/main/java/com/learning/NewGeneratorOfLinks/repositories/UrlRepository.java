package com.learning.NewGeneratorOfLinks.repositories;

import com.learning.NewGeneratorOfLinks.models.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UrlRepository extends JpaRepository<Url,Long> {
    Url findUrlByShortUrl(String shortUrl);

    Url findUrlByFullUrl(String fullUrl);

    @Query("SELECT t FROM Url t ORDER BY t.countOf DESC, t.lastTimeUsed DESC limit 10")
    List<Url> getTop10Urls();
}
