package com.jiwjus.urlshorteningservice.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class UrlRequestDto {
    @NotBlank(message = "url을 입력하세요.")
    @Pattern(message = "올바른 url 형식이 아닙니다.", regexp="^((http(s?))\\:\\/\\/)([0-9a-zA-Z\\-]+\\.)+[a-zA-Z]{2,6}(\\:[0-9]+)?(\\/\\S*)?$")
    private String originalUrl;
}
