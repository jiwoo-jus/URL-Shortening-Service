package com.jiwjus.urlshorteningservice.repository;

import com.jiwjus.urlshorteningservice.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByOriginalUrl(String originalUrl);
    Optional<Url> findByShortPath(String shortPath);

}
