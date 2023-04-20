package com.pooh.base.board.qna;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.pooh.base.board.BoardVO;
import com.pooh.base.board.notice.NoticeVO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class QnaDAOTest {
	
	@Autowired
	private QnaDAO qnaDAO;
	
	
	//@Test
	void setInsertTest() throws Exception{	
		//qna row 여러개 입력
		for(int i=0; i<120; i++) {
			BoardVO boardVO = new NoticeVO();
			
			boardVO.setTitle("Title Test"+i);
			boardVO.setContents("Contents Test"+i);
			boardVO.setWriter("winter"+i);
			
			int result = qnaDAO.setInsert(boardVO);
			result = qnaDAO.setRef(boardVO);
			
			if(i%10==0) {
				//10번마다 0.5초 쉬고 재작업
				Thread.sleep(500);
			}
		}
//		assertEquals(1, result);
	}
	
	
	@Test
	void setRefTest() throws Exception{
		for(Long i=0L; i<120L; i++) {
			BoardVO boardVO = new BoardVO();
			boardVO.setNum(i);
			
			int result = qnaDAO.setRef(boardVO);
			
			if(i%10==0) {
				//10번마다 0.5초 쉬고 재작업
				Thread.sleep(500);
			}
		}
	}

}
