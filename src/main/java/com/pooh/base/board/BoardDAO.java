package com.pooh.base.board;

import java.util.List;

public interface BoardDAO {
	//기본 공통작업을 모아둠
	
//	//전체글 개수 조회
//	public Long getTotalCount() throws Exception;
//	
//	//글전체 조회
//	public List<BoardVO> getList() throws Exception;
//	
//	//글하나 조회
//	public BoardVO getDetail(BoardVO boardVO) throws Exception;
//	
	//글쓰기
	public int setInsert(BoardVO boardVO) throws Exception;
//	
//	//글수정
//	public int setUpdate(BoardVO boardVO) throws Exception;
//	
//	//글삭제
//	public int setDelete(BoardVO boardVO) throws Exception;
	
}
