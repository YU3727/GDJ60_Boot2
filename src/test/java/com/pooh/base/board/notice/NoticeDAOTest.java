package com.pooh.base.board.notice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.pooh.base.board.BoardVO;

@SpringBootTest
class NoticeDAOTest { //접근지정자 default라 생략

	@Autowired
	private NoticeDAO noticeDAO;
	
	@Test
	void setInsertTest() throws Exception{
		BoardVO boardVO = new NoticeVO();
		
		boardVO.setTitle("Insert Test");
		boardVO.setContents("Insert Test");
		boardVO.setWriter("winter");
		
		int result = noticeDAO.setInsert(boardVO);
		assertEquals(1, result);
	}

}
