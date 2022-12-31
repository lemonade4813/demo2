package com.example.demo.dto;


import com.example.demo.model.BookSearchEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BookSearchDTO {

    private String id;
    private String keyword;
    private Date date;
    private String userId;

    public BookSearchDTO (BookSearchEntity bookSearchEntity){
        this.id = bookSearchEntity.getId();
        this.userId = bookSearchEntity.getUserId();
        this.date = bookSearchEntity.getDate();
        this.keyword = bookSearchEntity.getKeyword();
    }
}
