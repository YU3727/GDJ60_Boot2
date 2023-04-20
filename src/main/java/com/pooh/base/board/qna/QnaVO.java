package com.pooh.base.board.qna;

import com.pooh.base.board.BoardVO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnaVO extends BoardVO{
	
	private Long ref;
	private Long step;
	private Long depth;

}
