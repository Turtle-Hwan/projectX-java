package com.mysite.boardX.controller;

import com.mysite.boardX.dto.CommentForm;
import com.mysite.boardX.dto.ArticleForm;
import com.mysite.boardX.repository.Article;
import com.mysite.boardX.repository.ArticleRepository;
import com.mysite.boardX.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RequestMapping("/article")
@RequiredArgsConstructor
@Controller
public class ArticleController {
    private final ArticleService articleService;
    private final ArticleRepository articleRepository;

    @GetMapping("/list")
    /*@RequestParam(value = "page", defaultValue = "0") int page
    http://localhost:8082/article/list?page=0 처럼 get 방식으로 요청된 URL에서 page값을 가져오기 위함
    * URL에 페이지 파라미터 page가 전달되지 않을 경우 디폴트 값으로 0이 되도록 (페이징 첫 번호 = 0) */
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Article> paging = this.articleService.getList(page);
        //질문 목록을 조회하는 articleService의 getList 메서드를 조회
        //정수 타입의 페이지 번호를 입력받아, 해당 페이지의 질문 목록을 리턴하는 메서드
        model.addAttribute("paging", paging);
        //템플릿에 Page<Article> 객체인 paging을 전달
        return "article_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, CommentForm commentForm) {
        Article article = this.articleService.getArticle(id);
        model.addAttribute("article", article);
        return "article_detail";
    }

    @GetMapping("/create")
    public String articleCreate(ArticleForm articleForm) {
        return "article_form";
    }

    @PostMapping("/create")
    public String articleCreate(@Valid ArticleForm articleForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "article_form";
        }
        this.articleService.create(articleForm.getSubject(), articleForm.getContent(), articleForm.getUserName(), articleForm.getPassword());
        return "redirect:/article/list"; // 질문 저장후 질문목록으로 이동
    }

    @GetMapping("/edit/{id}")
    public String articleEdit(Model model, @PathVariable("id") Integer id, ArticleForm articleForm) {
        Article article = this.articleService.getArticle(id);
        model.addAttribute("article", article);
        return "article_edit";
    }

    //1번 게시글을 수정하고 "수정하기 버튼을 누르면" post 방식, article/edit/1 요청이 들어옴
    @PostMapping("/edit/{id}")
    //@PathVariable("id") Integer id -> {id} 부분을 처리하기 위한 어노테이션
    //@Valid ArticleForm articleForm -> articleForm의 유효성 검증을 위한 어노테이션 (ex. 길이는 괜찮은지, 비어있지는 않은지..)
    //BindingResult bindingResult -> valid 어노테이션으로 검증이 수행된 결과 객체
    //Model model -> 뷰에 그려주기 위한 객체
    public String articleEdited(@PathVariable("id") Integer id, @Valid ArticleForm articleForm, BindingResult bindingResult, Model model) {
        Article q = articleService.getArticle(id); //해당 게시글에 해당하는 entity를 가져오고
        if ((articleForm.getPassword()).equals(q.getPassword())) { //수정폼에서 내가 작성한 비밀번호와 게시글의 실제 비밀번호가 일치한다면
            this.articleService.edit(id, articleForm.getSubject(), articleForm.getContent(), articleForm.getUserName()); //수정
            return "redirect:/article/list"; // 질문 저장후 질문목록으로 이동
        } else { //비밀번호가 다르다면
            return "redirect:/article/edit/wrongPassword/{id}"; // "/article/edit/wrongPassword/{id}로 리다이렉트"
        }
    }

    @GetMapping("/edit/wrongPassword/{id}") //비밀번호가 틀렸을 경우
    //Model 객체에 값을 담아두면 템플릿에서 그 값을 사용할 수 있음
    //Model 객체는 따로 내가 생성하지 않더라도 컨트롤러 메서드 매개변수로 지정만 해두면 스프링부트가 자동으로 Model 객체를 생성한다.
    public String articleEditWrongPassword(Model model, @PathVariable("id") Integer id, ArticleForm articleForm) {
        Article article = this.articleService.getArticle(id);
        String wrongPassword = "비밀번호가 틀렸습니다.";
        model.addAttribute("article", article);
        model.addAttribute("wrongPassword", wrongPassword);
        return "article_edit";
    }

    @GetMapping("/delete/{id}") //삭제창을 띄우기 위한
    public String articleDelete(Model model, @PathVariable("id") Integer id, ArticleForm articleForm) {
        Article article = this.articleService.getArticle(id);
        model.addAttribute("article", article);
        return "article_delete";
    }

    @PostMapping("/delete/{id}") //삭제 메서드
    public String articleDeleted(@PathVariable("id") Integer id, @Valid ArticleForm articleForm, BindingResult bindingResult) {
        Article q = articleService.getArticle(id);
        if ((articleForm.getPassword()).equals(q.getPassword())) { //화면에 입력한 비번이랑 찐 비번이랑 같으면
            this.articleService.delete(id); //서비스의 delete 함수 실행
            return "redirect:/article/list"; // 질문 저장후 질문목록으로 이동
        }else {
            return "redirect:/article/delete/wrongPassword/{id}"; // 질문 저장후 질문목록으로 이동
        }

    }

    @GetMapping("/delete/wrongPassword/{id}")
    public String articleDeleteWrongPassword(Model model, @PathVariable("id") Integer id, ArticleForm articleForm) {
        Article article = this.articleService.getArticle(id);
        String wrongPassword = "비밀번호가 틀렸습니다.";
        model.addAttribute("article", article);
        model.addAttribute("wrongPassword", wrongPassword);
        return "article_delete";
    }
}