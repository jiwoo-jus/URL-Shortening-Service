package com.jiwjus.urlshorteningservice.controller;

import com.jiwjus.urlshorteningservice.dto.UrlRequestDto;
import com.jiwjus.urlshorteningservice.dto.UrlResponseDto;
import com.jiwjus.urlshorteningservice.service.UrlShorteningService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UrlController {

    private final UrlShorteningService urlShorteningService;

    @PostMapping
    public ResponseEntity<?> shortenUrl(@RequestBody UrlRequestDto urlRequestDto){
        UrlResponseDto urlResponseDto = urlShorteningService.shortenUrl(urlRequestDto.getOriginalUrl());
        return ResponseEntity.ok(urlResponseDto);
    }

    @GetMapping("/{shortPath}")
    public void redirectUrl(@PathVariable String shortPath, HttpServletResponse httpServletResponse) throws IOException {
        String originalUrl = urlShorteningService.redirect(shortPath);
        httpServletResponse.sendRedirect(originalUrl);
    }

}
