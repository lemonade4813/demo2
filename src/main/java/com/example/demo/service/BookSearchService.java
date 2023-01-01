package com.example.demo.service;


import com.example.demo.dto.CountDTO;
import com.example.demo.model.BookSearchEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.persistence.BookSearchRepository;

import java.util.List;

@Service
public class BookSearchService {

    @Autowired
    BookSearchRepository bookSearchRepository;


    public void create(BookSearchEntity entity) {
        System.out.println(bookSearchRepository.save(entity));
        bookSearchRepository.save(entity);

    }

    public List<BookSearchEntity> retrieveMyHistory(String userId) {
        return bookSearchRepository.findByUserId(userId);

    }
    public List<CountDTO> retrieveTop10(){

        System.out.println(bookSearchRepository.findByKeywordCountTop10());
        return bookSearchRepository.findByKeywordCountTop10();


    }

}