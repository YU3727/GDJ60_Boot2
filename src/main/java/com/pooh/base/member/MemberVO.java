package com.pooh.base.member;

import java.sql.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberVO {

	private String userName;
	private String passWord;
	private String name;
	private String email;
	private Date birth;
	private boolean enabled;
	//boolean으로 선언하면 bit(1)의 값 0과 1이 자동으로 false, true로 전환된다
	private List<RoleVO> roleVOs;
	
}
