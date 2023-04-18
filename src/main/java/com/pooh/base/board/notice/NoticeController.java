package com.pooh.base.board.notice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pooh.base.board.BoardVO;
import com.pooh.base.util.Pager;

@Controller
//notice 하위 모든 폴더까지 포함
@RequestMapping("/notice/*")
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
	public ModelAndView getList(@ModelAttribute Pager pager) throws Exception{
		//매개변수는 자동으로 @ModelAttribute annotation이 붙어져서 나가기 때문에, mv.addObject("pager", pager) 한 것과 같다.
		//object의 이름은 매개변수의 첫글자를 소문자로 바꿀 것이 된다.
		ModelAndView mv = new ModelAndView();
		
		List<BoardVO> ar = noticeService.getList(pager);
		
		mv.addObject("list", ar);
		mv.setViewName("board/list");
		return mv;
	}
}
