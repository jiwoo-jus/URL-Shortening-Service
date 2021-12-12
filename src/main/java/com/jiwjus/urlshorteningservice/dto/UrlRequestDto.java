package com.jiwjus.urlshorteningservice.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

@Getter
public class UrlRequestDto {
    @URL
    @NotBlank
    private String originalUrl;
}
