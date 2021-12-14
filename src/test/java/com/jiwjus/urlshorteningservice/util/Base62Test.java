package com.jiwjus.urlshorteningservice.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class Base62Test {
    @InjectMocks
    private Base62 base62;

    @Test
    @DisplayName("base62 인코딩")
    void encode() {
        //given
        Long key1 = 1L;
        Long key2 = 218340105584895L; // 8자리로 표현 가능한 최대범위
        Long key3 = 9223372036854775806L; // Long 최대범위 -1 (약 구백이십이경)

        //do
        String value1 = base62.encode(key1);
        String value2 = base62.encode(key2);
        String value3 = base62.encode(key3);

        //then
        assertThat(value1).isEqualTo("b");
        assertThat(value2).isEqualTo("ZZZZZZZZ");
        assertThat(value3).isEqualTo("kZviNa9fiMh");
    }
}