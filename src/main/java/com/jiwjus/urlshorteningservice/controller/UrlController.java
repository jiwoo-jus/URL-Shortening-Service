package com.jiwjus.urlshorteningservice.controller;

import com.jiwjus.urlshorteningservice.dto.UrlRequestDto;
import com.jiwjus.urlshorteningservice.dto.UrlResponseDto;
import com.jiwjus.urlshorteningservice.service.UrlShorteningService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
@Controller
public class UrlController {

    private final UrlShorteningService urlShorteningService;

    @GetMapping("/")
    public String home(){
        return "index";
    }

    @GetMapping("/error")
    public String error(){
        return "error";
    }

    @PostMapping
    public ResponseEntity<?> shortenUrl(@Valid @RequestBody UrlRequestDto urlRequestDto, Errors errors){
        if (errors.hasErrors()){
            String errMsg = Objects.requireNonNull(errors.getFieldError()).getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errMsg);
        }
        UrlResponseDto urlResponseDto = urlShorteningService.shortenUrl(urlRequestDto.getOriginalUrl());
        return ResponseEntity.status(HttpStatus.OK).body(urlResponseDto);
    }

    @GetMapping("/{shortPath}")
    public void redirectUrl(@PathVariable String shortPath, HttpServletResponse httpServletResponse) throws IOException {
        String responseUrl = urlShorteningService.redirect(shortPath);
        httpServletResponse.sendRedirect(responseUrl);
    }

}
