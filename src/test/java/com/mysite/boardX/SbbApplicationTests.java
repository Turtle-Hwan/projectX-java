package com.mysite.boardX;

import com.mysite.boardX.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class BoardXApplicationTests {

	@Autowired
	private ArticleService articleService;

	@Test
	void testJpa() {
		//		for (int i = 1; i <= 50; i++) {
//			String subject = String.format("테스트 데이터입니다:[%03d]", i);
//			String content = "test";
//			String userName = "userName_Test";
//			String password = "test";
//			this.articleService.create(subject, content, userName, password);
	}
}
