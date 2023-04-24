package com.pooh.base.member;

import java.sql.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberVO {

	@NotBlank
	private String userName;
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
	private boolean enabled;
	//boolean으로 선언하면 bit(1)의 값 0과 1이 자동으로 false, true로 전환된다
	private List<RoleVO> roleVOs;
	
}
