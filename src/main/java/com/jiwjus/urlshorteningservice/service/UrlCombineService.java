package com.jiwjus.urlshorteningservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UrlCombineService {
    @Value("${server.host}")
    private String host;
    @Value("${server.port}")
    private String port;

    public String combineUrl(String path){
        return "http://" + host + ":" + port + "/" + path;
    }
}
