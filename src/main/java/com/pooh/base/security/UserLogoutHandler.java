package com.pooh.base.security;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.pooh.base.member.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserLogoutHandler implements LogoutHandler{
	//일단 이 클래스는 사용하지 않는걸로 한다.(successHandler 사용)
	
	//로그아웃 할 때 필요한 정보는 두가지(from 카카오 개발자센터)
	
	//properties에 있는 value값을 가져오는 방식.	
	@Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
	private String adminKey;
	
	
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		// TODO Auto-generated method stub
		
		//로그아웃 메서드 호출
		this.simpleLogout();
		
		//redirect 설정
		try {
			response.sendRedirect("/");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void simpleLogout() {
		//redirect의 메서드 형식은 get인데, 카카오 문서에서 요구하는 메서드의 형식은 post이다.
		//그래서 단순한 redirect 방식으로는 해결할 수 없다. -> restTemplate을 사용해보자.
		
		//헤더, 파라미터 등이 필요한지, 어떤게 필요한지는 문서를 보면서 알아내야한다.
		
		//1. 요청준비
		RestTemplate restTemplate = new RestTemplate();
		
		//사용자 정보 준비(SecurityContextHoler가 session.getAttribute("SPRING_SECURITY_CONTEXT")한 것과 같음)
		MemberVO memberVO = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		log.error(":: 회원번호: {} :::::", memberVO.getAttributes().get("id"));
		
		//2. header - 헤더값에 adminKey를 이용해서 로그아웃 할 예정
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "KakaoAK "+adminKey);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		//headers.setBearerAuth(""); //이런것도 사용 가능.
		
		//3. parameter
		//인터페이스 = new 실제구현클래스(); : 인터페이스는 클래스로 만들 수 없기 때문에 구현클래스가 있어야한다.
		//파라미터 name 또한 문서를 보고 key를 그대로 써줘야한다.
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("target_id_type", "user_id");
		params.add("target_id", memberVO.getAttributes().get("id").toString());
		
		//4. header, parameter을 포함한 요청객체 생성
		//단, 생성자 호출시 (파라미터, 헤더)순으로 호출할 것
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
		
		//5. 요청 발생(어떤 메서드 형식으로 어떤 URI를 거칠 것인가)
		//타입의 안정성 때문에 getForEntity를 사용하는걸 권장함.
		//String.class는 리턴을 스트링 클래스로 받겠습니다는 의미.
		String id = restTemplate.getForObject("https://kapi.kakao.com/v1/user/logout", String.class, request);
		
		//왜 에러가 뜰까
		
		//redirectURI는 원래 줘야하는데, 로그아웃 성공하면 UserLogoutSuccessHandler로 갈 것이라 안줘도 될 것 같다.
		
	}
	
	
	
	
}
