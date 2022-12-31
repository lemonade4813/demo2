package com.example.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


import com.example.demo.model.BookSearchEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookSearchRepository extends JpaRepository<BookSearchEntity, String>
{
    List<BookSearchEntity> findByUserId(String userId);

    @Query("select keyword, date ,count(keyword) from BookSearchEntity group by keyword order by count(keyword) desc limit 10")
    List<BookSearchEntity> findByKeywordCountTop10();

}
