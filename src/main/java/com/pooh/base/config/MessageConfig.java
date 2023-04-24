package com.pooh.base.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class MessageConfig implements WebMvcConfigurer{
	//message를 사용하기 위한 설정파일(interceptor)
	
	
	//객체 생성의 두가지 방법 : Annotation, xml
	//소스 코드를 열어볼 수 없는 경우 : xml(라이브러리 등) // 열어볼 수 있는 경우 : Annotation
	//라이브러리를 사용하는 경우, 객체를 만들고싶으면 @bean을 사용하면 된다. (-> Legacy에서 xml에 <bean></bean> 태그를 쓴것과 같음)

	//bean의 이름은 리턴타입의 첫글자를 소문자로 바꾼 것이 된다.
	//어딘가에서 쓰려면 @autowired 해서 쓰면 된다.(자세한 내용은 강사님 notion - SpringBoot - DI IOC 참고)
	@Bean
	public LocaleResolver localeResolver() {
		//메서드명은 꼭 리턴타입의 첫글자를 소문자로 바꾼 같은 이름으로 해야한다.
		//LocaleResolver를 쓸때는 web.servlet 하위의 파일을 import 해줘야한다.
		
		//session이나 cookie를 사용하기
		//1.session - server에 저장된다.
		SessionLocaleResolver sessionLocaleResolver =  new SessionLocaleResolver();
		sessionLocaleResolver.setDefaultLocale(Locale.KOREA);
		
		//2.cookie - 사용자의 web browser에 저장됨
		CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
		cookieLocaleResolver.setDefaultLocale(Locale.KOREAN);
		cookieLocaleResolver.setCookieName("lang");
		
		//객체를 만들면 Spring pool에 담아둔다. + session/cookie 둘 중 아무거나 쓰면 된다.
		return sessionLocaleResolver;
	}
	
	
	@Bean
	public LocaleChangeInterceptor getLocaleChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		
		//언어를 구분할 parameter의 이름을 설정해줌
		localeChangeInterceptor.setParamName("lang_opt");
		
		
		return localeChangeInterceptor;
	}
}
