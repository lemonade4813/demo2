package com.example.demo.persistence;

import com.example.demo.dto.CountDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


import com.example.demo.model.BookSearchEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookSearchRepository extends JpaRepository<BookSearchEntity, String>
{
    List<BookSearchEntity> findByUserId(String userId);

    @Query(nativeQuery = true, value = "select count(b.keyword) as cnt, b.keyword from Book_Search b group by b.keyword order by cnt desc limit 10")
    List<CountDTO> findByKeywordCountTop10();

}
