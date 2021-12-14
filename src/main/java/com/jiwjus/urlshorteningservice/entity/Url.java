package com.jiwjus.urlshorteningservice.entity;

import lombok.*;

import javax.persistence.*;

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

    @Setter
    @Column
    private String shortPath;

    @Column(nullable = false)
    private int requestCount;


    public void addRequestCount(){
        requestCount += 1;
    }

}
