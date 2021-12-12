package com.jiwjus.urlshorteningservice.dto;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
public class UrlResponseDto {
    private String originalUrl;
    private String shortUrl;
    private int requestCount;
}
