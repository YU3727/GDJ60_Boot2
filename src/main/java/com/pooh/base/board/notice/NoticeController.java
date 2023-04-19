package com.pooh.base.board.notice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.pooh.base.board.BoardVO;
import com.pooh.base.util.Pager;

import lombok.extern.slf4j.Slf4j;

@Controller
//notice 하위 모든 폴더까지 포함
@RequestMapping("/notice/*")
@Slf4j //log 기록을 남겨주는 Annotation
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	//각 메서드가 실행되기 전에 @ModelAttribute부터 실행한다.
	@ModelAttribute("board")
	public String getBoard() {
		return "notice";
	}

	
	//list
	@GetMapping("list")
//	@RequestMapping(value="list", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView getList(@ModelAttribute Pager pager) throws Exception{
		//매개변수는 자동으로 @ModelAttribute annotation이 붙어져서 나가기 때문에, mv.addObject("pager", pager) 한 것과 같다.
		//object의 이름은 매개변수의 첫글자를 소문자로 바꿀 것이 된다.
		
		//파라미터 확인 등을 하고싶을때는 system.out이라는 프린트문 대신에 로그기록을 사용하도록 한다.
		log.info("kind : {}", pager.getKind());
		log.info("search: {}", pager.getSearch());
		
		ModelAndView mv = new ModelAndView();
		
		//파라미터 확인용 - 이제 이거 대신에 로그를 사용하면 된다.
//		System.out.println(pager.getKind());
//		System.out.println(pager.getSearch());
		
		List<BoardVO> ar = noticeService.getList(pager);
		
		mv.addObject("list", ar);
		mv.setViewName("board/list");
		return mv;
	}
	
	
	//add
	@GetMapping("add")
	public ModelAndView setInsert() throws Exception{
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("board/add");
		return mv;
	}
	
	//add
	@PostMapping("add")
	public ModelAndView setInsert(NoticeVO noticeVO) throws Exception{
		ModelAndView mv = new ModelAndView();
		
		int result = noticeService.setInsert(noticeVO);
		
		mv.setViewName("redirect:./list");
		return mv;
	}
}
