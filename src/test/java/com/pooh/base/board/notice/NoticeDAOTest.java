package com.pooh.base.board.notice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.pooh.base.board.BoardDAO;
import com.pooh.base.board.BoardFileVO;
import com.pooh.base.board.BoardVO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class NoticeDAOTest { //접근지정자 default라 생략

	@Autowired
	private NoticeDAO noticeDAO;
	
	//@Test
	void setInsertTest() throws Exception{
		for(int i=0; i<120; i++) {
			BoardVO boardVO = new NoticeVO();
			
			boardVO.setTitle("Title Test"+i);
			boardVO.setContents("Contents Test"+i);
			boardVO.setWriter("iu"+i);
			
			int result = noticeDAO.setInsert(boardVO);
			
			if(i%10==0) {
				//10번마다 0.5초 쉬고 재작업
				Thread.sleep(500);
			}
		}
		//assertEquals(1, result);
	}
	
	
	//@Test
	void setUpdateTest() throws Exception{
		BoardVO boardVO = new NoticeVO();
		
		boardVO.setNum(2L);
		boardVO.setTitle("Update Test");
		boardVO.setContents("Update Test");
		boardVO.setWriter("winter");
		
		int result = noticeDAO.setUpdate(boardVO);
		assertEquals(1, result);
	}
	
	
	//@Test
	void setDeleteTest() throws Exception{
		BoardVO boardVO = new BoardVO();
		
		boardVO.setNum(5L);
		
		int result = noticeDAO.setDelete(boardVO);
		assertEquals(1, result);
	}
	
	
	@Test
	void getDetailTest() throws Exception{
		BoardVO boardVO = new BoardVO();
		boardVO.setNum(134L);
		
		boardVO = noticeDAO.getDetail(boardVO);
		if(boardVO != null) {
			log.info("num: {}", boardVO.getNum());
			log.info("title: {}", boardVO.getTitle());
			log.info("contents: {}", boardVO.getContents());
			log.info("writer: {}", boardVO.getContents());
			for(BoardFileVO boardFileVO : boardVO.getBoardFileVOs() ) {
				log.info("fileNum: {}", boardFileVO.getFileNum());
				log.info("num: {}", boardFileVO.getNum());
				log.info("fileName: {}", boardFileVO.getFileName());
			}
		}
		
	}

}
