package com.mysite.sbb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Integer> {

    Article findBySubject(String subject);
    Article findBySubjectAndContent(String subject, String content);

    List<Article> findBySubjectLike(String subject);

    Page<Article> findAll(Pageable pageable);
    //Pageable 객체를 입력으로 받아 Page<Article> 타입 객체를 리턴
}
