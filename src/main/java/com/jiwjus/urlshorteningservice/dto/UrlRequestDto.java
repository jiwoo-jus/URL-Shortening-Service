package com.jiwjus.urlshorteningservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Pattern;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class UrlRequestDto {

    @Pattern(message = "Invalid URL", regexp="^((http(s?))\\:\\/\\/)([0-9a-zA-Z\\-]+\\.)+[a-zA-Z]{2,6}(\\:[0-9]+)?(\\/\\S*)?$")
    private String originalUrl;
}
