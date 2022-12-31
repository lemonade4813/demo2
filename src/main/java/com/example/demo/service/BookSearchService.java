package com.example.demo.service;


import com.example.demo.model.BookSearchEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.persistence.BookSearchRepository;

import java.util.List;

@Service
public class BookSearchService {

    @Autowired
    BookSearchRepository bookSearchRepository;



    public void create(BookSearchEntity entity){
        bookSearchRepository.save(entity);
    }

    public List<BookSearchEntity> retrieveMyHistory(String userId){
        return bookSearchRepository.findByUserId(userId);
    }
    public List<BookSearchEntity> retrieveTop10(){
        return bookSearchRepository.findByKeywordCountTop10();}
    }

