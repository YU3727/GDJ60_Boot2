package com.pooh.base.board;

import java.sql.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BoardVO {
	//@Data의 경우 상속받아 사용하면 에러가 발생할 여지가 있다. 기본생성자를 만들기 때문일까

	private Long num;
	private String title;
	private String contents;
	private String writer;
	private Date regDate;
	private Long hit;
	
	private List<BoardFileVO> boardFileVOs;
}
