package com.pooh.base.board.notice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.pooh.base.board.BoardFileVO;
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
	
	
	//detail
	@GetMapping("detail")
	public ModelAndView getDetail(NoticeVO noticeVO) throws Exception{
		ModelAndView mv = new ModelAndView();
		BoardVO boardVO = noticeService.getDetail(noticeVO);
		
		mv.addObject("boardVO", boardVO);
		mv.setViewName("board/detail");
		return mv;
	}
	
	//fileDown
	@GetMapping("fileDown")
	public ModelAndView getFileDown(BoardFileVO boardFileVO) throws Exception{
		ModelAndView mv = new ModelAndView();
		
		boardFileVO = noticeService.getFileDetail(boardFileVO);
		
		//FileManager의 boardFileVO boardFileVO = (BoardFileVO)model.get("boardFileVO"); 속성명을 그대로 써야한다.
		mv.addObject("boardFileVO", boardFileVO);
		//view의 이름이 중요하다.
		//customView를 사용하기 위해서는, 해당 클래스가 abstractView를 상속 받은 상태에서만 가능하다.
		//Legacy에서는 order라는 순서에 따라 customView와 jsp를 찾는 순서가 갈렸지만, boot에서는 알아서 CV를 먼저 찾고 jsp를 찾으러감
		mv.setViewName("fileManager");
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
	public ModelAndView setInsert(NoticeVO noticeVO, MultipartFile[] boardFiles) throws Exception{
		//jsp에서 입력받는 파라미터 이름과 매개변수 이름을 같게 해야 데이터를 받는다.
		ModelAndView mv = new ModelAndView();
		
		for(MultipartFile multipartFile : boardFiles) {
			log.info("OriginalName: {}, size: {}", multipartFile.getOriginalFilename(), multipartFile.getSize());
		}
		
		int result = noticeService.setInsert(noticeVO, boardFiles);
		
		mv.setViewName("redirect:./list");
		return mv;
	}
	

}
