package com.jiwjus.urlshorteningservice.util;

import org.springframework.stereotype.Service;

@Service
public class Base62 {

    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public String encode(Long id) {
        StringBuilder shortPath = new StringBuilder("");
        while (id > 0) {
            shortPath.append(BASE62.charAt((int) (id % 62)));
            id /= 62;
        }
        return shortPath.reverse().toString();
    }

}
