package com.jiwjus.urlshorteningservice.util;

import org.springframework.stereotype.Service;

@Service
public class Base62 {

    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public String encode(Long key) {
        StringBuilder value = new StringBuilder("");
        while (key > 0) {
            value.append(BASE62.charAt((int) (key % 62)));
            key /= 62;
        }
        return value.reverse().toString();
    }

    public Long decode(String value){
        long key = 0L;
        long power = 1L;
        for (int i = 0; i < value.length(); i++) {
            int digit = BASE62.indexOf(value.charAt(i));
            key += digit * power;
            power *= 62;
        }
        return key;
    }

}
