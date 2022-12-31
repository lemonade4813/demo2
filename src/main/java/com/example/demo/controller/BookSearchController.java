package com.example.demo.controller;

import com.example.demo.dto.BookSearchDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.service.BookSearchService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.model.BookSearchEntity;

@Slf4j
@RestController
@Service


public class BookSearchController {

    @Autowired
    private BookSearchService bookSearchService;


    @CrossOrigin(originPatterns = "http://localhost:3000")
    @GetMapping("/kakao")
    public String callApi(@RequestParam("keyword") String keyword, @RequestParam("page") String page
                          ,@RequestParam("size") String size, @RequestParam("userId") String userId)
             {

        try {
            ByteBuffer buffer = StandardCharsets.UTF_8.encode(keyword);
            String encode = StandardCharsets.UTF_8.decode(buffer).toString();
            System.out.println(encode);

            URI uri = UriComponentsBuilder
                    .fromUriString("https://dapi.kakao.com/v3/search")
                    .path("/book")
                    .queryParam("query", encode)
                    .queryParam("page", page)
                    .queryParam("size", size)
                    .encode()
                    .build()
                    .toUri();

            System.out.println(uri);


            RestTemplate restTemplate = new RestTemplate();

            // 아래는 헤더를 넣기 위함
            RequestEntity<Void> req = RequestEntity
                    .get(uri)
                    .header("Authorization", "KakaoAK c27902098d864347d63934caeb3bd42c")
                    .build();

            ResponseEntity<String> result = restTemplate.exchange(req, String.class);

            log.info("uri : {} ", uri);
            log.info("result : {}", result.getBody());


            BookSearchEntity entity = BookSearchEntity.builder().keyword(keyword).userId(userId).date(new Date()).id(null).build();

            bookSearchService.create(entity);

            return result.getBody();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    @GetMapping("/top10")
    public ResponseEntity<?> retrieveTop10List(){

    try {

        List<BookSearchEntity> entities = bookSearchService.retrieveTop10();

        List<BookSearchDTO> keywordTop10 = entities.stream().map(BookSearchDTO::new).collect(Collectors.toList());

        ResponseDTO<BookSearchDTO> response = ResponseDTO.<BookSearchDTO>builder().data(keywordTop10).build();

        return ResponseEntity.ok().body(response);

    }catch(Exception e){

        String error = e.getMessage();

        ResponseDTO<BookSearchDTO> response = ResponseDTO.<BookSearchDTO>builder().error(error).build();

        return ResponseEntity.badRequest().body(response);

    }

    }

    @GetMapping("/retrieveMyList")

    public ResponseEntity<?> retrieveMyList(@RequestParam String userId){

        try {
            List<BookSearchEntity> entities = bookSearchService.retrieveMyHistory(userId);

            List<BookSearchDTO> myHistory = entities.stream().map(BookSearchDTO::new).collect(Collectors.toList());

            ResponseDTO<BookSearchDTO> response = ResponseDTO.<BookSearchDTO>builder().data(myHistory).build();

            return ResponseEntity.ok().body(response);

        }catch(Exception e){

            String error = e.getMessage();

            ResponseDTO<BookSearchDTO> response = ResponseDTO.<BookSearchDTO>builder().error(error).build();

            return ResponseEntity.badRequest().body(response);

        }
   }

}


