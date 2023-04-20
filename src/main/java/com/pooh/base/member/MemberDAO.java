package com.pooh.base.member;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDAO {

	//공통작업
	//login 하면 로그인 한 사람의 정보를 리턴해주면 됨
	public MemberVO getLogin(MemberVO memberVO) throws Exception;
	
	//join
	public int setJoin(MemberVO memberVO) throws Exception;
	
	//basicRole
	public int setBasicRole(MemberVO memberVO) throws Exception;
	
}
