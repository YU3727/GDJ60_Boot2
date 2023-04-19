package com.pooh.base.board;

import java.util.List;

import com.pooh.base.util.Pager;

public interface BoardDAO {
	//기본 공통작업을 모아둠
	
	//전체글 개수 조회
	public Long getTotalCount(Pager pager) throws Exception;
	
	//글전체 조회
	public List<BoardVO> getList(Pager pager) throws Exception;
	
	//글하나 조회
	public BoardVO getDetail(BoardVO boardVO) throws Exception;
	
	//글쓰기
	public int setInsert(BoardVO boardVO) throws Exception;
	
	//글수정
	public int setUpdate(BoardVO boardVO) throws Exception;
	
	//글삭제
	public int setDelete(BoardVO boardVO) throws Exception;
	
	//파일 추가
	public int setFileInsert(BoardFileVO boardFileVO) throws Exception;
	
	//파일 하나 조회
	public BoardFileVO getFileDetail(BoardFileVO boardFileVO) throws Exception;
}
