package com.mysite.boardX.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mysite.boardX.repository.Article;
import com.mysite.boardX.repository.ArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import com.mysite.boardX.DataNotFoundException;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public List<Article> getList() {
        return this.articleRepository.findAll();
    }

    public Article getArticle(Integer id) {
        Optional<Article> article = this.articleRepository.findById(id);
        if (article.isPresent()) {
            return article.get();
        } else {
            throw new DataNotFoundException("article not found");
        }
    }

    public void create(String subject, String content, String userName, String password) {
        Article q = new Article();
        q.setSubject(subject);
        q.setContent(content);
        q.setUserName(userName);
        q.setPassword(password);
        q.setCreateDate(LocalDateTime.now());
        this.articleRepository.save(q);
    }

    public Page<Article> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10,Sort.by(sorts));
        return this.articleRepository.findAll(pageable);
    }

    public void edit(int id, String subject, String content, String userName) {
        Article q = this.getArticle(id);
        q.setSubject(subject);
        q.setContent(content);
        q.setUserName(userName);
        articleRepository.save(q);
    }

    public void delete(Integer id) {
        Article q = this.getArticle(id);
        articleRepository.delete(q);
    }
}
