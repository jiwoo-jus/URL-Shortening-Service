package com.jiwjus.urlshorteningservice.dto;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UrlRequestDtoTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    public static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    public static void close() {
        factory.close();
    }


    @DisplayName("올바른 url 형식인 경우")
    @Test
    void valid_url_format(){
        //given
        UrlRequestDto case1 = new UrlRequestDto("https://github.com/jiwoo-jus");

        //when
        Set<ConstraintViolation<UrlRequestDto>> violationCase = validator.validate(case1);

        //then
        assertThat(violationCase).isEmpty();
    }


    @DisplayName("올바른 url 형식이 아닌 경우")
    @Test
    void invalid_url_format() {
        //given
        UrlRequestDto case1 = new UrlRequestDto("");
        UrlRequestDto case2 = new UrlRequestDto(" ");
        UrlRequestDto case3 = new UrlRequestDto("https://g ithub.com/jiwoo-jus");
        UrlRequestDto case4 = new UrlRequestDto("https:/github.com/jiwoo-jus");

        //when
        ArrayList<Set<ConstraintViolation<UrlRequestDto>>> violationCases = new ArrayList<>();
        violationCases.add(validator.validate(case1));
        violationCases.add(validator.validate(case2));
        violationCases.add(validator.validate(case3));
        violationCases.add(validator.validate(case4));

        //then
        for(Set<ConstraintViolation<UrlRequestDto>> violationCase : violationCases){
            assertThat(violationCase)
                    .extracting(ConstraintViolation::getMessage)
                    .containsOnly("Invalid URL");
        }
    }
}