package com.pooh.base.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.pooh.base.board.BoardVO;
import com.pooh.base.board.notice.NoticeService;
import com.pooh.base.util.Pager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//Controller 내의 모든 메서드에 @ResponseBody가 필요하다면 선언
@RestController
public class RestFulController {
	//RestFul API를 알아보자
	//1. parameter를 url 형식으로 보낸다.
	
	@Autowired
	private NoticeService noticeService;
	
	//RestFulAPI
//	@GetMapping("/rest/list/{page}")
	
//	public ModelAndView getList(@PathVariable Long page, Pager pager) throws Exception{
//		//PathVariable - url에 {}중괄호 내에 입력한 변수명과 Annotation @PathVariable뒤에 입력한 매개변수명이 일치하면 받아줌.
//		//또는 (@PathVariable(name="page") Long p, required = false/true, value="1") 이런식으로 해도 된다.
//		//위의 속성 required = false는 필수값이 아니라는 설정. 값이 없더라고 exception이 발생하지 않고
//		//value="1"은 초기값을 1로 설정해준다는 뜻이다.
//		ModelAndView mv = new ModelAndView();
//		log.error(":: page: {} :::::", page);
//		
//		List<BoardVO> ar = noticeService.getList(pager);
//		
//		mv.addObject("list", ar);
//		mv.setViewName("board/list");
//		return mv;
//	}

	
	//@ResponseBody - Data를 view에 담아서 JSP를 통해 뿌리지 않고, JSON형태로 바로 보내줌
	//JSP에 뿌려줘야하는데 JSP가 없는 경우????
	//JSP가 아닌 HTML에 바로 뿌려주기 위해서 데이터를 JSON 형태로 만들어서 뿌려준다. -> @ResponseBody
	@GetMapping("/rest/list/{page}")
	@ResponseBody
	public List<BoardVO> getList(@PathVariable Long page, Pager pager) throws Exception{
		ModelAndView mv = new ModelAndView();
		log.error(":: page: {} :::::", page);
		
		List<BoardVO> ar = noticeService.getList(pager);
		
		//이렇게 하면(Annotation @ResponseBody를 사용) 리턴하는 데이터를 JSON 형태로 내보낸다.
		return ar;
	}
	
	
	
	//@GetMapping("/rest/{num}/detail")
	@GetMapping("/rest/detail")
	@ResponseBody
	public BoardVO getDetail(BoardVO boardVO) throws Exception{
		boardVO = noticeService.getDetail(boardVO);
		
		return boardVO;
	}
	
	
	
	
}
