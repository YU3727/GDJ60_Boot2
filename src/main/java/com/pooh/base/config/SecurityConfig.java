package com.pooh.base.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.pooh.base.member.MemberService;
import com.pooh.base.member.MemberSocialService;
import com.pooh.base.security.UserLoginFailureHandler;
import com.pooh.base.security.UserLogoutHandler;
import com.pooh.base.security.UserLogoutSuccessHandler;
import com.pooh.base.security.UserSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	//Spring에서 Annotation으로 객체를 만든것이 아니므로, autowired를 사용할 수 없다.
	@Autowired
	private UserLogoutSuccessHandler userLogoutSuccessHandler;
	
	@Autowired
	private UserLogoutHandler userLogoutHandler;
	
	@Autowired
	private MemberSocialService memberSocialService;

	@Bean
	//public을 선언하면 default로 바꾸라는 메시지가 출력된다.
	WebSecurityCustomizer webSecurityConfig() {
		//security를 적용하지 말아야할 경로(URL 패턴)들이 있음. 이걸 설정해주는 메서드이다.
		//화살표 함수를 Lamda라고 부름.
		return web -> web.ignoring()
						.antMatchers("/images/**")
						.antMatchers("/js/**")
						.antMatchers("/css/**")
						.antMatchers("/favicon/**")
						
						
						
						
						;
		//더 추가할 수 있으므로 세미콜론은 아래쪽으로 빼둔다.
	}
	
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
		httpSecurity
			//csrf만 활성화 하고 테스트
				.cors()
//				.disable()
				.and()
				.csrf()
				.disable()
				
			.authorizeRequests()
				//url과 권한을 적용하는 부분
				.antMatchers("/").permitAll()
				.antMatchers("/member/join").permitAll()
				//일단은 루트 페이지를 허용한 이후에 제한을 한다. (위까진 허용, 아래부터 제한)
				//Interceptor와 마찬가지로 순서가 중요하다. 위에서부터 아래로 걸러짐
				.antMatchers("/notice/add").hasRole("ADMIN") //interceptor에서 하던걸 해줌. 단, DB에서 역할이름을 ROLE_ADMIN 식으로 입력해둬야함
				.antMatchers("/notice/update").hasRole("ADMIN")
				.antMatchers("/notice/delete").hasRole("ADMIN")
				.antMatchers("/notice/*").permitAll()
				
				.antMatchers("admin/**").hasRole("ADMIN")
				.antMatchers("qna/add").hasAnyRole("ADMIN", "MANAGER", "MEMBER") //회원별 등급이 하나뿐인경우에 이런식으로 써줌. 지금의 경우에는 굳이 쓸 필요없긴하다.
				//.anyRequest().authenticated() //여기에 설정되지 않은 그 외 나머지 url은 로그인 해야 볼 수 있다는 의미
				.anyRequest().permitAll() // 이런식으로도 가능은 함. 일단 다 허용하고 막을부분만 체크하기, test할 때는 이걸 쓰는걸로
				
				.and()
			.formLogin()
				//개발자가 만든 login form을 쓰고싶으면 아래 내용 입력
				//기본 파라미터 이름과 다른 파라미터를 사용하면 입력해줘야함.(username, password가 기본)
				.loginPage("/member/login")
				//.defaultSuccessUrl("/")
				.successHandler(new UserSuccessHandler()) //로그인 성공시 수행할 작업 - 객체를 만드는 또다른 방법(일회용): new 생성자
				//.failureUrl("/member/login")
				.failureHandler(new UserLoginFailureHandler()) //로그인 실패시 수행할 작업
				.permitAll() //login url 허용
				.and()
				
			.logout()
				.logoutUrl("/member/logout")
				//.logoutSuccessUrl("/")
				//.addLogoutHandler(userLogoutHandler)
				.logoutSuccessHandler(userLogoutSuccessHandler) //로그아웃 성공시 수행할 작업
				.invalidateHttpSession(true) //true값을 넣어야 세션을 만료시킴
				.deleteCookies("JSESSIONID") //JSESSIONID라는 이름을 가진 쿠키를 삭제
				.permitAll() //logout url 허용
				.and()
				
				
			.oauth2Login()
				.userInfoEndpoint()
				.userService(memberSocialService) //서비스 객체를 넣어주면 된다.(DefaultOAuth2UserService 객체)
				
			//동시 접속 제한 - 일단 지금은 안되는거같음...
//			.sessionManagement()
//				.maximumSessions(1) //최대 허용 가능한 세션의 수, -1 : 세션 허용 수 무제한
//				.maxSessionsPreventsLogin(false) //false : 이전 사용자의 세션을 만료시킴, true : 새로운 사용자를 인증실패처리
				
				
				
				;
				
		return httpSecurity.build();
			
	}
	
	
	@Bean
	//PasswordEncoder - 평문의 비밀번호를 암호화 하는 클래스
	public PasswordEncoder getPasswordEncoder() {
		//암호화 하는 도구는 여러개가 있을 수 있는데, 아래것을 사용하려 한다.
		return new BCryptPasswordEncoder();
	}
	
	
}
