package com.pooh.base.board.qna;

import org.apache.ibatis.annotations.Mapper;

import com.pooh.base.board.BoardDAO;
import com.pooh.base.board.BoardVO;

@Mapper
public interface QnaDAO extends BoardDAO{

	//insert 후 ref값 업데이트 해주기
	public int setRef(BoardVO boardVO) throws Exception;
}
