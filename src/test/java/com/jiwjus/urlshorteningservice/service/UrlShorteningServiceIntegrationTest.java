package com.jiwjus.urlshorteningservice.service;

import com.jiwjus.urlshorteningservice.dto.UrlResponseDto;
import com.jiwjus.urlshorteningservice.entity.Url;
import com.jiwjus.urlshorteningservice.repository.UrlRepository;
import com.jiwjus.urlshorteningservice.util.Base62;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyString;

@Transactional
@SpringBootTest
class UrlShorteningServiceIntegrationTest {

    @Autowired
    UrlShorteningService urlShorteningService;
    @Autowired
    UrlRepository urlRepository;
    @Autowired
    UrlCombineService urlCombineService;
    @Autowired
    Base62 base62;


    @Test
    @DisplayName("shortenUrl - 요청 보낸 url이 저장되어 있는 경우")
    public void shorten_existingUrl() {
        //given
        String originalUrl = "https://github.com/jiwoo-jus/URL-Shortening-Service";
        Url url = Url.builder()
                .originalUrl(originalUrl)
                .requestCount(1).build();
        urlRepository.save(url);
        String shortPath = base62.encode(url.getId());
        url.setShortPath(shortPath);
        String shortUrl = urlCombineService.combineUrl(shortPath);

        //do
        UrlResponseDto urlResponseDto = urlShorteningService.shortenUrl(originalUrl);

        //then
        assertAll(
                () -> assertThat(urlResponseDto.getOriginalUrl()).isEqualTo(originalUrl),
                () -> assertThat(urlResponseDto.getShortUrl()).isEqualTo(shortUrl),
                () -> assertThat(urlResponseDto.getRequestCount()).isEqualTo(2)
        );
    }

    @Test
    @DisplayName("shortenUrl - 요청 보낸 url이 저장되어 있지 않은 경우")
    public void shorten_newUrl(){
        //given
        String originalUrl = "https://github.com/jiwoo-jus/URL-Shortening-Service";

        //do
        UrlResponseDto urlResponseDto = urlShorteningService.shortenUrl(originalUrl);
        String shortUrl = urlCombineService.combineUrl(base62.encode(urlRepository.findByOriginalUrl(originalUrl).get().getId()));

        //then
        assertAll(
                ()->assertThat(urlResponseDto.getOriginalUrl()).isEqualTo(originalUrl),
                ()->assertThat(urlResponseDto.getShortUrl()).isEqualTo(shortUrl),
                ()->assertThat(urlResponseDto.getRequestCount()).isEqualTo(1)
        );
    }

    @Test
    @DisplayName("redirect - 요청 보낸 shortPath가 저장되어 있는 경우")
    public void redirect_existingUrl(){
        //given
        String originalUrl = "https://github.com/jiwoo-jus/URL-Shortening-Service";
        Url url = Url.builder()
                .originalUrl(originalUrl)
                .requestCount(1).build();
        urlRepository.save(url);
        String shortPath = base62.encode(url.getId());
        url.setShortPath(shortPath);

        //do
        String returnUrl = urlShorteningService.redirect(shortPath);

        //then
        assertThat(returnUrl).isEqualTo(originalUrl);
    }

    @Test
    @DisplayName("redirect - 요청 보낸 shortPath가 저장되어 있지 않은 경우")
    public void redirect_unknownUrl(){
        //given
        String shortPath = anyString();

        //do
        String returnUrl = urlShorteningService.redirect(shortPath);

        //then
        assertThat(returnUrl).isEqualTo(urlCombineService.combineUrl("error"));
    }
}