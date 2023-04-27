package com.pooh.base.member;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberVO implements UserDetails{

	@NotBlank
	private String username;
	@NotBlank
	@Length(min=3, max=20)
	private String password;
	private String passwordCheck;
	@NotBlank
	private String name;
	@Email
	private String email;
	@Past
	private Date birth;
	//boolean으로 선언하면 bit(1)의 값 0과 1이 자동으로 false, true로 전환된다
	//private boolean enabled;
	private Date lastTime;
	
	private List<RoleVO> roleVOs;

	@Override
	//이 메서드를 호출하면 role 이름을 가져간다.(spring이 알아서 가져감)
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//최종리턴은 Collection Type이기만 하면 된다 + generic은 GrantedAuthority를 상속받은 것
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		//역할이 여러개이므로, 반복문으로 꺼내고 꺼낸걸 GrantedAuthority타입으로 authorities 리스트에 넣음
		for(RoleVO roleVO : roleVOs) {
			//GrantedAuthority는 interface, SimpleGrantedAuthority는 구현클래스.
			authorities.add(new SimpleGrantedAuthority(roleVO.getRoleName()));
		}
		return authorities;
	}
	
//	@Override
//	public String getUsername() {
//		username(id) 반환
//		return null;
//	}
	
//	@Override
//	public String getPassword() {
//		password(id) 반환
//		return null;
//	}


	//아래 3가지 메서드 중 하나라도 false라면 로그인이 되지 않음.
	@Override
	public boolean isAccountNonExpired() {
		//계정의 만료 여부
		//true : 만료 안됨
		//false : 만료 됨 -> 로그인 안됨
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		//계정의 잠김 여부(비밀번호 5회 입력 실패 등)
		//true : 잠기지 않음
		//false : 잠김 -> 로그인 안됨
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		//패스워드의 만료 여부(특정 기간 이상 비밀번호의 변경이 없는 경우 등)
		//true : 만료 안됨
		//false : 만료 됨 -> 로그인 안됨
		return true;
	}
	//
	
	
	@Override
	public boolean isEnabled() {
		//계정의 사용 여부
		//true : 계정 활성화
		//false : 계정 비활성화 -> 로그인 안됨
		return true;
	}
	
	
	
}
