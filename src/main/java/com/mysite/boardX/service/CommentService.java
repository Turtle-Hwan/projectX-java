package com.mysite.boardX.service;

import com.mysite.boardX.DataNotFoundException;
import com.mysite.boardX.repository.Comment;
import com.mysite.boardX.repository.Article;
import com.mysite.boardX.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public void create(Article article, String content, String userName, String password) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreateDate(LocalDateTime.now());
        comment.setArticle(article);
        comment.setUserName(userName);
        comment.setPassword(password);
        this.commentRepository.save(comment);
    }

    public void edit(int id,String content, String userName) {
        Comment a = this.getComment(id);
        a.setContent(content);
        a.setUserName(userName);
        commentRepository.save(a);
    }
    public Comment getComment(Integer id) {
        Optional<Comment> comment = this.commentRepository.findById(id);
        if (comment.isPresent()) {
            return comment.get();
        } else {
            throw new DataNotFoundException("comment not found");
        }
    }

    public void delete(Integer id) {
        Comment a = this.getComment(id);
        commentRepository.delete(a);
    }
}
