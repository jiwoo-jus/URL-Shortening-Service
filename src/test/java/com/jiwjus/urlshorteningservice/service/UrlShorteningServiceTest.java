package com.jiwjus.urlshorteningservice.service;

import com.jiwjus.urlshorteningservice.dto.UrlResponseDto;
import com.jiwjus.urlshorteningservice.entity.Url;
import com.jiwjus.urlshorteningservice.repository.UrlRepository;
import com.jiwjus.urlshorteningservice.util.Base62;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UrlShorteningServiceTest {
    @InjectMocks
    private UrlShorteningService urlShorteningService;
    @Mock
    private Base62 base62;
    @Mock
    private UrlRepository urlRepository;
    @Mock
    private UrlCombineService urlCombineService;

    @Test
    @DisplayName("shortenUrl - 요청 보낸 url이 저장되어 있는 경우")
    public void shorten_existingUrl(){
        //given
        String originalUrl = "https://github.com/jiwoo-jus";
        Optional<Url> existingUrl = Optional.of(Url.builder()
                .originalUrl(originalUrl)
                .requestCount(1).build());
        //when
        given(urlRepository.findByOriginalUrl(originalUrl)).willReturn(existingUrl);
        given(base62.encode(existingUrl.get().getId())).willReturn("b");
        given(urlCombineService.combineUrl(anyString())).willReturn("http://localhost:8088/b");

        UrlResponseDto urlResponseDto = urlShorteningService.shortenUrl(originalUrl);

        //then
        assertAll(
                ()->assertThat(urlResponseDto.getOriginalUrl()).isEqualTo("https://github.com/jiwoo-jus"),
                ()->assertThat(urlResponseDto.getShortUrl()).isEqualTo("http://localhost:8088/b"),
                ()->assertThat(urlResponseDto.getRequestCount()).isEqualTo(2)
        );
    }

    @Test
    @DisplayName("shortenUrl - 요청 보낸 url이 저장되어 있지 않은 경우")
    public void shorten_newUrl(){
        //given
        String originalUrl = "https://github.com/jiwoo-jus";
        Optional<Url> emptyUrl = Optional.empty();
        Url newUrl = Url.builder()
                .id(1L)
                .originalUrl(originalUrl)
                .requestCount(1).build();

        //when
        given(urlRepository.findByOriginalUrl(originalUrl)).willReturn(emptyUrl);
        given(urlRepository.save(any(Url.class))).willReturn(newUrl);
        given(base62.encode(newUrl.getId())).willReturn("b");
        given(urlCombineService.combineUrl(anyString())).willReturn("http://localhost:8088/b");

        UrlResponseDto urlResponseDto = urlShorteningService.shortenUrl(originalUrl);

        //then
        assertAll(
                ()->assertThat(urlResponseDto.getOriginalUrl()).isEqualTo("https://github.com/jiwoo-jus"),
                ()->assertThat(urlResponseDto.getShortUrl()).isEqualTo("http://localhost:8088/b"),
                ()->assertThat(urlResponseDto.getRequestCount()).isEqualTo(1)
        );
    }

    @Test
    @DisplayName("redirect - 요청 보낸 shortPath에 상응하는 url이 저장되어 있는 경우")
    public void redirect_existingUrl(){
        //given
        String shortUrl = "http://localhost:8088/b";
        Optional<Url> url = Optional.of(Url.builder()
                .originalUrl("https://github.com/jiwoo-jus")
                .requestCount(1).build());

        //when
        given(urlRepository.findById(anyLong())).willReturn(url);

        String originalUrl = urlShorteningService.redirect(shortUrl);

        //then
        assertThat(originalUrl).isEqualTo("https://github.com/jiwoo-jus");
    }

    @Test
    @DisplayName("redirect - 요청 보낸 shortPath에 상응하는 url이 저장되어 있지 않은 경우")
    public void redirect_unknownUrl(){
        //given
        String shortUrl = "http://localhost:8088/b";
        Optional<Url> emptyUrl = Optional.empty();

        //when
        given(urlRepository.findById(anyLong())).willReturn(emptyUrl);
        given(urlCombineService.combineUrl(anyString())).willReturn("http://localhost:8088/error");

        String originalUrl = urlShorteningService.redirect(shortUrl);

        //then
        assertThat(originalUrl).isEqualTo("http://localhost:8088/error");
    }
}