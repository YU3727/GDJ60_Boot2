package com.pooh.base.board.notice;

import org.apache.ibatis.annotations.Mapper;

import com.pooh.base.board.BoardDAO;

@Mapper
public interface NoticeDAO extends BoardDAO{
	//Mapper라고 선언해두면 자동으로 찾는다.
	
	//namespace는 package + 해당 클래스명
	//id는 메서드명
	//return은 선언된 데이터 형태를 사용
	
}
