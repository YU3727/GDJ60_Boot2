package com.pooh.base.member;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/member/*")
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
	private String adminKey;
	
	
	//Login - get
	@GetMapping("login")
	public ModelAndView getLogin(HttpSession session) throws Exception{
		ModelAndView mv = new ModelAndView();
	
		mv.setViewName("member/login");
		
		//로그인 되어있는 경우, 로그인페이지로 보내지 않고 다른페이지로 보내고 싶을때는 어떻게 할까?
		//로그인 여부를 판별하기 위해 session을 매개변수로 받아와서 작업한다.
		 Object obj = session.getAttribute("SPRING_SECURITY_CONTEXT");
		 if(obj!=null) {
			 mv.setViewName("redirect:/");
		 }
		 
		return mv;
	}
	
	//Spring Security를 사용하면 이걸 더 이상 사용하지 않는다. 내장되어있는 것을 사용함.(service의 loadUserByUsername)
	//Login - post
//	@PostMapping("login")
//	public ModelAndView getLogin(MemberVO memberVO, HttpSession session) throws Exception{
//		//로그인 성공하면 담을 세션도 준비.
//		ModelAndView mv = new ModelAndView();
//		
//		memberVO = memberService.getLogin(memberVO);
//		
//		mv.setViewName("redirect:./login");
//		
//		//id, pw가 일치하지 않아서 조회를 못했다면 memberVO에 null이 들어가있음.
//		if(memberVO != null) {
//			//로그인 성공시 로그인 정보를 session에 보관. 속성명 member
//			session.setAttribute("member", memberVO);
//			mv.setViewName("redirect:../");
//		}
//		
//		return mv;
//	}
	
	
	//Spring Security를 적용 후에는 logout도 Security에서 한다.
	//logout
//	@GetMapping("logout")
//	public ModelAndView getLogout(HttpSession session) throws Exception{
//		ModelAndView mv = new ModelAndView();
//		
//		//logout 시간 DB에 입력하기
//		MemberVO memberVO = (MemberVO)session.getAttribute("member");
//		int result = memberService.setLogoutTime(memberVO);
//		
//		session.invalidate();
//		
//		mv.setViewName("redirect:../");
//		
//		return mv;
//	}
	
	
	//socialLogout할 때 정보를 받아오는 컨트롤러 - 일단 주석처리
//	@GetMapping("socialLogout")
//	public ModelAndView getLogout(HttpSession session) throws Exception{
//		ModelAndView mv = new ModelAndView();
//		
//		mv.setViewName("redirect:../");
//		
//		return mv;
//	}
	
	//join - get
	@GetMapping("join")
	public ModelAndView setJoin(@ModelAttribute MemberVO memberVO) throws Exception{
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("member/join");
		//이것도 가능
		//mv.addObject(new MemberVO());
		return mv;
	}
	
	//join - post
	@PostMapping("join")
	public ModelAndView setJoin(@Valid MemberVO memberVO, BindingResult bindingResult) throws Exception{
		ModelAndView mv = new ModelAndView();
		
		boolean check = memberService.memberCheck(memberVO, bindingResult);
		
		
		//검증
		//if(bindingResult.hasErrors()) {
		if(check) {
			//에러가 있다면
			log.warn("=============== 검증에 실패함 ==================");
			mv.setViewName("member/join");
			return mv;
		}
		
		//insert
		int result = memberService.setJoin(memberVO);
		
		mv.setViewName("redirect:../");
		return mv;
	}
	
	//idDuplicateCheck - id 중복 체크
	@GetMapping("idDuplicateCheck")
	//새로운 응답방식(JSON 형태 - Key:Value 형식이지만 key 없이 value만 올 수도 있다)
	@ResponseBody
	public boolean idDuplicateCheck(MemberVO memberVO) throws Exception{
		//이번에는 modelandview가 아닌 다른것으로 담아 보낸다. 0/1 또는 true/false
		//원래 컨트롤러에서 작업한 내용은 모두 ModelAndView에 담겨서 jsp로 간다.
		//String으로 리턴하면 : WEB-INF/views/'리턴값'.jsp를 찾아감
		//void로 리턴하면 : WEB-INF/views/'입력된 url'.jsp를 찾아감 (여기서는 member/idDuplicateCheck);
		//그럼 int로 하면? : 에러가 발생한다. 허용하지 않음
		//즉, Controller에서 허용하는 리턴타입은 3가지이다. void, String, ModelAndView -> 이를 해결하기위해 @responsebody 사용
		
		//응답으로 나가는건 두가지 방식 뿐이였다.
		//1. fowarding
		//controller에서 d.s.로 mv를 리턴하면, IRVR에서 VIEW 이름을 완성시킨 후 jsp를 찾으러감
		//2. redirect
		//VIEW의 이름에 redirect가 포함되어있으면 IRVR로 보내지 않고 바로 응답으로 보냄.
		//3. @ResponseBody - redirect와 거의 유사
		//Annotation @ResponseBody가 붙어있으면, jsp를 거치지 않고 응답으로 바로 내보낸다.
		// 리턴하는 데이터를 body에 담아서 보낸다. 즉, 리턴하는 데이터를 html이 아닌 JSON 타입으로 바꿔서 내보낸다.
		//즉, Legacy에서 했던 common/result .jsp를 따로 만들어서 했던 작업을 하지 않아도 된다.(java 객체를 JSON으로 변환하는 작업 필요x)
		//@ResponseBody가 붙어있으면 모든 데이터타입을 리턴할 수 있다.
		
		log.info("========================id 중복 체크========================"); //로그 레벨은 info, debug까지 사용, 이보다 상위는 배포할 때 삭제하거나 루트단계를 높혀야한다.
		
		boolean idCheck = memberService.idDuplicateCheck(memberVO);
		log.error("{} <================= idCheck값", idCheck);
		
		return idCheck;
	}
	
	//mypage
	@GetMapping("mypage")
	public void getMyPage() throws Exception{
		//입력 url과 jsp 주소룰 일치시키면 리턴값이 void일 때 url 이름으로 jsp를 찾으러 감
	}
	
	//admin
	@GetMapping("admin")
	public void getAdmin() throws Exception{
		
	}
	
	//**Spring Security가 사용하는 session에는 뭐가 들어있을까?
	@GetMapping("info")
	public void info(HttpSession session) {
		
		String pw = "user01";
		
		MemberVO memberVO = (MemberVO)memberService.loadUserByUsername(pw);
		
		//같은 pw값을 넣었지만 hash값이 다르게 나오고, 같은지 비교하는 결과는 false가 뜬다. 이걸 어떻게 해결하지?
		log.error("::::: {} ::::::", memberVO.getPassword());
		log.error("::::: {} ::::::", passwordEncoder.encode(pw));
		log.error("::::: {} ::::::", memberVO.getPassword().equals(passwordEncoder.encode(pw)));
		
		//이렇게 하면 true가 뜬다.
		//패스워드를 비교하고싶으면, 인코딩 전 패스워드(rawPw)와 인코딩 후 패스워드(encodedPw)를 비교해야한다.
		//비교를 할 때 passwordEncoder클래스 내에 포함된 matchs라는 메서드를 사용해야한다.
		boolean check = passwordEncoder.matches(pw, memberVO.getPassword());
		log.error("::::: {} ::::::", check);
		
		
		
		///////////////////////////////////////////////
		log.error("=============== login info ===============");
		
		//속성이 어떤 이름으로 들어왔는지 알아보고자 할때 이런 방법을 사용.
//		Enumeration<String> names = session.getAttributeNames();
//		//반복문이 몇번 돌아야하는지 모른다. -> for문을 돌릴수 없음, while로
//		while(names.hasMoreElements()) {
//			log.error("====== {} ======", names.nextElement());
//		}
	
//		//위의 작업을 통해 session에 들어가있는 정보의 속성명이 'SPRING_SECURITY_CONTEXT'임을 알게됨.
//		Object obj = session.getAttribute("SPRING_SECURITY_CONTEXT");
//		
//		log.error("======= {} =======", obj);
//		
//		//꺼내본 결과로 SecurityContextImpl 타입이 나옴
//		SecurityContextImpl contextImpl = (SecurityContextImpl)obj;
//		Authentication authentication = contextImpl.getAuthentication();
//		
//		log.error("======= username: {} =======", authentication.getName());
//		log.error("======= details: {} =======", authentication.getDetails());
//		log.error("======= MemberVO: {} =======", authentication.getPrincipal());
//		log.error("======= authorities: {} =======", authentication.getAuthorities());
		
	}
	
	
	//findPassword - get
	@GetMapping("findPassword")
	public ModelAndView getFindPassword() throws Exception{
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("member/findPassword");
		return mv;
	}
	
	
	//findPassword - post
	@PostMapping("findPassword")
	public ModelAndView getFindPassword(MemberVO memberVO) throws Exception{
		ModelAndView mv = new ModelAndView();
		
		int result = memberService.getFindPassword(memberVO);
		
		mv.setViewName("member/findPassword");
		
		//result가 1이면 임시비번발급, 메일발송 성공, 0이면 실패
		if(result>0) {
			mv.setViewName("member/login");
		}
		
		return mv;
	}
	
	
	//연결 끊기
	@GetMapping("delete")
	public String delete() throws Exception{
		//카카오 개발자센터 - 카카오 로그인 - 연결 끊기 부분
		
		//소셜 로그인한 사람이 탈퇴를 원하는지, 사이트에 직접 가입한 로컬 회원이 탈퇴를 원하는지 구분이 필요하다.
		//회원 가입 방법의 구분이 필요함. -> 회원 컬럼에 로컬가입인지 소셜가입인지 체크하는 컬럼이 필요할듯 하다.
		//작업 자체는 서비스에서 해도 됨. 어디서 할거냐는 개발자의 선택
		
		MemberVO memberVO = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		this.kakaoDelete(memberVO);
		
		//이렇게 해놨더니 로그아웃을 안해준다. 어떻게 해야할까?
		//리턴타입을 void에서 String으로 바꾸고, 리턴값을 줘보자.
		return "redirect:./logout";
	}
	
	
	//kakaoDelete
	
	private void kakaoDelete(MemberVO memberVO) throws Exception{
		//RestTemplate 사용
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "KakaoAK "+adminKey);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED); //post
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("target_id_type", "user_id");
		params.add("target_id", memberVO.getAttributes().get("id").toString());
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
		
		//설정한 것들을 restTemplate에 담아 보낸다.
		//uri는 문서참고, return은 사용자 id가 올 것이므로, String.class
		String id = restTemplate.postForObject("https://kapi.kakao.com/v1/user/unlink", request, String.class);
		
		log.error(":: Delete : {} :::::", id);
		
	}
	
	
}
