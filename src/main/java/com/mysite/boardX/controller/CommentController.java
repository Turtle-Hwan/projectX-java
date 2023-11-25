package com.mysite.boardX.controller;

import com.mysite.boardX.dto.CommentForm;
import com.mysite.boardX.repository.Comment;
import com.mysite.boardX.service.CommentService;
import com.mysite.boardX.repository.Article;
import com.mysite.boardX.service.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class CommentController {
    private final ArticleService articleService;
    private final CommentService commentService;

    @PostMapping("/create/{id}")
    public String createComment(Model model, @PathVariable("id") Integer id,
                               @Valid CommentForm commentForm, BindingResult bindingResult) {
        Article article = this.articleService.getArticle(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("article", article);
            return "article_detail";
        }
        this.commentService.create(article, commentForm.getContent(), commentForm.getUserName(), commentForm.getPassword());
        return String.format("redirect:/article/detail/%s", id);
    }

    //댓글 수정 폼으로 이동
    @GetMapping("/edit/{id}")
    public String commentEdit(Model model, @PathVariable("id") Integer id, CommentForm commentForm) {
        Comment comment = this.commentService.getComment(id);
        model.addAttribute("comment", comment);
        return "comment_edit";
    }


    //댓글을 수정하고 "수정하기 버튼을 누르면" post 방식, comment/edit/1 요청이 들어옴
    @PostMapping("/edit/{id}")
    //@PathVariable("id") Integer id -> /edit/{id} 부분을 처리하기 위한 어노테이션
    //@Valid ArticleForm articleForm -> articleForm의 유효성 검증을 위한 어노테이션 (ex. 길이는 괜찮은지, 비어있지는 않은지..)
    //BindingResult bindingResult -> valid 어노테이션으로 검증이 수행된 결과 객체
    //Model model -> 뷰에 그려주기 위한 객체
    public String commentEdited(@PathVariable("id") Integer id, @Valid CommentForm commentForm, BindingResult bindingResult, Model model) {
        Comment comment = this.commentService.getComment(id); //해당 게시글에 해당하는 entity를 가져오고
        if ((commentForm.getPassword()).equals(comment.getPassword())) { //수정폼에서 내가 작성한 비밀번호와 게시글의 실제 비밀번호가 일치한다면
            this.commentService.edit(id, commentForm.getContent(), commentForm.getUserName()); //수정
            Article article = comment.getArticle();
            model.addAttribute("article", article);
            model.addAttribute("comment", comment);
            return String.format("redirect:/article/detail/%s", article.getId());
        } else { //비밀번호가 다르다면
            return "redirect:/comment/edit/wrongPassword/{id}"; // "/comment/edit/wrongPassword/{id}로 리다이렉트"
        }
    }

    @GetMapping("/edit/wrongPassword/{id}") //비밀번호가 틀렸을 경우
    //Model 객체에 값을 담아두면 템플릿에서 그 값을 사용할 수 있음
    //Model 객체는 따로 내가 생성하지 않더라도 컨트롤러 메서드 매개변수로 지정만 해두면 스프링부트가 자동으로 Model 객체를 생성한다.
    public String articleEditWrongPassword(Model model, @PathVariable("id") Integer id, CommentForm commentForm) {
        Comment comment = this.commentService.getComment(id);
        String wrongPassword = "비밀번호가 틀렸습니다.";
        model.addAttribute("comment", comment);
        model.addAttribute("wrongPassword", wrongPassword);
        return "comment_edit";
    }


    //댓글 삭제 폼으로 이동
    @GetMapping("/delete/{id}")
    public String commentDelete(Model model, @PathVariable("id") Integer id, CommentForm commentForm) {
        Comment comment = this.commentService.getComment(id);
        model.addAttribute("comment", comment);
        return "comment_delete";
    }

    @PostMapping("/delete/{id}")
    public String commentDeleted(@PathVariable("id") Integer id, Model model, CommentForm commentForm) {
        Comment comment = this.commentService.getComment(id);
        if (commentForm.getPassword().equals(comment.getPassword())) {
            Article article = comment.getArticle();
            model.addAttribute("article", article);
            System.out.println("comment id :"+id);
            this.commentService.delete(id);
            return String.format("redirect:/article/detail/%s", article.getId());
        } //비번이 같다면 삭제 진행
        else {
            return "redirect:/comment/delete/wrongPassword/{id}";
        } //비번이 같지 않다면 메세지 출력
    }

    @GetMapping("/delete/wrongPassword/{id}")
    public String commentDeleteWrongPassword(Model model, @PathVariable("id") Integer id, CommentForm  commentForm) {
        String wrongPassword = "비밀번호가 틀렸습니다.";
        Comment comment = this.commentService.getComment(id);
        model.addAttribute("comment", comment);
        model.addAttribute("wrongPassword", wrongPassword);
        return "comment_delete";
    }
}
