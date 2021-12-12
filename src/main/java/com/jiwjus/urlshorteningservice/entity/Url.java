package com.jiwjus.urlshorteningservice.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originalUrl;

    @Column(nullable = false)
    private int requestCount;


    public void addRequestCount(){
        requestCount += 1;
    }

}
