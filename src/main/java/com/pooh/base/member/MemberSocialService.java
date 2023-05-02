package com.pooh.base.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberSocialService extends DefaultOAuth2UserService{
	
	
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		//소셜 로그인에 성공하면 로그인 정보와 함께 토큰을 발급한다.
		//로그인에 성공하면 Spring에서 이 메서드를 호출해서 MemberVO(OAuth2User)를 리턴, 정보를 받아간다.
		//즉, 이 메서드가 실행되는 건 kakao에서 로그인이 성공했다는 의미이다.
		
		//아래 내용이 찍혔다는건 kakao 서버를 다녀왔다는 뜻이다.
		log.error(":: token : {} :::::", userRequest.getAccessToken());
		
		//아래 registration에 로그인 한 사용자의 정보가 담겨져있음.
		ClientRegistration registration = userRequest.getClientRegistration();
		
		log.error(":: registrationID : {} :::::", registration.getRegistrationId());
		log.error(":: scope : {} :::::", registration.getScopes());
		log.error(":: clientName : {} :::::", registration.getClientName());
		log.error(":: clientID : {} :::::", registration.getClientId()); //이게 실제 받아온 토큰같은거
		
		
		//담겨져 있는 사용자 정보를 꺼내보자
		OAuth2User user = super.loadUser(userRequest);
		
		log.error(":: name : {} :::::", user.getName()); //kakao에서 받아오는 userID. 숫자 10자리
		
		
		//아래 메서드 테스트로 실행
		//this.socialJoinCheck(userRequest);
		
		//우리가 만든 memberVO 리턴(실제 필요한 것)
		return this.socialJoinCheck(userRequest);
	}
	
	
	
	//이 안에서만 사용할 메서드 준비
	private OAuth2User socialJoinCheck(OAuth2UserRequest userRequest) {
		//userRequset에 담긴 내용으로 DB에서 회원정보(role) 조회, 일치하는 사람이 없으면 DB에 회원 추가
		//이 메서드의 존재의의는 kakao에서 받은 정보를 토대로 우리 서버에서 사용할 MemberVO로 변경해주는 작업을 수행하기 위함이다.
		
		OAuth2User user = super.loadUser(userRequest);
		Map<String, Object> map = user.getAttributes();
		
		//map(user)에 무슨 key가 있는지 모르기 때문에 아래의 작업을 수행
		//map에서 key를 꺼내서 set형태로 바꿔줌, set에는 반복문이 없기 때문에 열거형으로 내용을 꺼내준다.
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			log.error("Key ::::: {}", key);
			log.error("Value ::: {}", map.get(key));
		}
		
		//위에서 받아온 map의 정보에 value값 또한 map이라 한번 더 map을 써서 value의 map 또한 key로 꺼내오려 한다.
		HashMap<String, Object> m = (HashMap<String, Object>)map.get("properties");
		log.error(":: nickname : {} :::::", m.get("nickname"));
		
		
		//해당 정보는 kakao 서버에서 보내온 정보. -> 그래서 여기에는 권한정보가 없다.
		//원래는 권한 등 우리 웹브라우저에 필요한 정보들을 담아서 MemberVO를 완성시켜서 리턴해줘야 하는게 맞다.
		//최종적으로는 MemberVO를 만들어 내보내는게 목적.
		
		//원래는 DB에서 조회해오기가 있어야함.
		MemberVO memberVO = new MemberVO();
		memberVO.setAttributes(map);
		memberVO.setUsername(m.get("nickname").toString());
		
		List<RoleVO> roleVOs = new ArrayList<>();
		RoleVO roleVO = new RoleVO();
		roleVO.setRoleName("ROLE_MEMBER");
		roleVOs.add(roleVO);
		
		memberVO.setRoleVOs(roleVOs);
		
		memberVO.setEnabled(true);
		
		
		return memberVO;
	}

}
