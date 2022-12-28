package com.example.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;


@Slf4j
@RestController
public class ApiController {

    @CrossOrigin(originPatterns = "http://localhost:3000")
    @GetMapping("/kakao/{keyword}")
    public String callApi(@PathVariable String keyword) throws JsonProcessingException {


        ByteBuffer buffer = StandardCharsets.UTF_8.encode(keyword);
        String encode = StandardCharsets.UTF_8.decode(buffer).toString();
        System.out.println(encode);

        URI uri = UriComponentsBuilder
                .fromUriString("https://dapi.kakao.com/v3/search")
                .path("/book")
                .queryParam("query", encode)
                .encode()
                .build()
                .toUri();


        RestTemplate restTemplate = new RestTemplate();

        // 아래는 헤더를 넣기 위함
        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("Authorization", "KakaoAK c27902098d864347d63934caeb3bd42c")
                .build();

        ResponseEntity<String> result = restTemplate.exchange(req, String.class);

        log.info("uri : {} " , uri);
        log.info("result : {}", result.getBody());

        return result.getBody();



    }
}







