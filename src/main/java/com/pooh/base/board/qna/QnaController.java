package com.pooh.base.board.qna;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.pooh.base.board.BoardFileVO;
import com.pooh.base.board.BoardVO;
import com.pooh.base.util.Pager;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/qna/*")
public class QnaController {

	@Autowired
	private QnaService qnaService;
	
	//각 메서드가 실행되기 전 게시판 구분용으로 값을 담아줌
	@ModelAttribute("board")
	public String getBoard() {
		return "qna";
	}
	
	
	//list
	@GetMapping("list")
	public ModelAndView getList(Pager pager) throws Exception{
		ModelAndView mv = new ModelAndView();
		
		List<BoardVO> ar =  qnaService.getList(pager);
		
		mv.addObject("list", ar);
		mv.setViewName("board/list");
		return mv;
	}
	
	
	//detail
	@GetMapping("detail")
	public ModelAndView getDetail(QnaVO qnaVO) throws Exception{
		ModelAndView mv = new ModelAndView();
		
		BoardVO boardVO = qnaService.getDetail(qnaVO);
		
		mv.addObject("boardVO", boardVO);
		mv.setViewName("board/detail");
		return mv;
	}
	
	
	//fileDown
	@GetMapping("fileDown")
	public ModelAndView getFileDown(BoardFileVO boardFileVO) throws Exception{
		ModelAndView mv = new ModelAndView();
		
		boardFileVO = qnaService.getFileDetail(boardFileVO);
		
		mv.addObject("boardFileVO", boardFileVO);
		mv.setViewName("fileManager");
		return mv;
	}
	
	//add-get
	@GetMapping("add")
	public ModelAndView setInsert(BoardVO boardVO) throws Exception{
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("board/add");
		return mv;
	}
	
	
	//add-post
	@PostMapping("add")
	public ModelAndView setInsert(BoardVO boardVO, MultipartFile[] boardFiles) throws Exception{
		ModelAndView mv = new ModelAndView();
		
		int result = qnaService.setInsert(boardVO, boardFiles);
		
		mv.setViewName("redirect:./list");
		return mv;
	}
	
	
}
