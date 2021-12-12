package com.jiwjus.urlshorteningservice.service;

import com.jiwjus.urlshorteningservice.dto.UrlResponseDto;
import com.jiwjus.urlshorteningservice.entity.Url;
import com.jiwjus.urlshorteningservice.repository.UrlRepository;
import com.jiwjus.urlshorteningservice.util.Base62;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlShorteningService {

    private final Base62 base62;
    private final UrlRepository urlRepository;
    private final UrlCombineService urlCombineService;
    private final String errorPath = "error";

    public UrlResponseDto shortenUrl(String originalUrl){
        Optional<Url> optUrl = urlRepository.findByOriginalUrl(originalUrl);
        if(optUrl.isPresent()){
            Url url = optUrl.get();
            url.addRequestCount();
            urlRepository.save(url);
            return UrlResponseDto
                    .builder()
                    .originalUrl(originalUrl)
                    .shortUrl(urlCombineService.combineUrl(url.getShortPath()))
                    .requestCount(url.getRequestCount())
                    .build();
        }
        else{
            Url url = urlRepository.save(
                    Url.builder()
                    .originalUrl(originalUrl)
                    .requestCount(1)
                    .build());
            String shortPath = base62.encode(url.getId());
            url.setShortPath(shortPath);
            urlRepository.save(url);
            return UrlResponseDto
                    .builder()
                    .originalUrl(originalUrl)
                    .shortUrl(urlCombineService.combineUrl(url.getShortPath()))
                    .requestCount(url.getRequestCount())
                    .build();
        }
    }

    public String redirect(String shortPath){
        Optional<Url> optUrl = urlRepository.findByShortPath(shortPath);
        if(optUrl.isPresent())
            return optUrl.get().getOriginalUrl();
        else
            return urlCombineService.combineUrl(errorPath);
    }
}