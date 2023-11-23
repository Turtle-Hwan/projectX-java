package com.mysite.sbb;

import com.mysite.sbb.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private ArticleService articleService;

	@Test
	void testJpa() {
//		 (int i = 1; i <= 300; i++) {
//			String subject = String.format("테스트 데이터입니다:[%03d]", i);
//			String content = "내용무";
//			this.articleService.create(subject, content);
//		}for
	}
}
