package com.pooh.base.member;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/member/*")
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	
	//Login - get
	@GetMapping("login")
	public ModelAndView getLogin() throws Exception{
		ModelAndView mv = new ModelAndView();
	
		mv.setViewName("member/login");
		return mv;
	}
	
	//Login - post
	@PostMapping("login")
	public ModelAndView getLogin(MemberVO memberVO, HttpSession session) throws Exception{
		//로그인 성공하면 담을 세션도 준비.
		ModelAndView mv = new ModelAndView();
		
		memberVO = memberService.getrLogin(memberVO);
		
		mv.setViewName("redirect:./login");
		
		//id, pw가 일치하지 않아서 조회를 못했다면 memberVO에 null이 들어가있음.
		if(memberVO != null) {
			//로그인 성공시 로그인 정보를 session에 보관. 속성명 member
			session.setAttribute("member", memberVO);
			mv.setViewName("redirect:../");
		}
		
		return mv;
	}
		
	//logout
	@GetMapping("logout")
	public ModelAndView getLogout(HttpSession session) throws Exception{
		ModelAndView mv = new ModelAndView();
		
		session.invalidate();
		mv.setViewName("redirect:../");
		
		return mv;
	}
	
	//join - get
	@GetMapping("join")
	public ModelAndView setJoin() throws Exception{
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("member/join");
		return mv;
	}
	
	//join - post
	@PostMapping("join")
	public ModelAndView setJoin(MemberVO memberVO) throws Exception{
		ModelAndView mv = new ModelAndView();
		
		int result = memberService.setJoin(memberVO);
		
		String message = "회원가입에 실패했습니다.";
		if(result>0) {
			message="축하드립니다\n회원가입에 성공했습니다.";
		}
		
//		mv.addObject("result", message);
//		mv.addObject("url", "./login");
//		mv.setViewName("common/result");
		mv.setViewName("redirect:../");
		return mv;
	}
	
	
	
}
