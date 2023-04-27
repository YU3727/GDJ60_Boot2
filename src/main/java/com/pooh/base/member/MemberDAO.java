package com.pooh.base.member;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDAO {

	//공통작업
	//login 하면 로그인 한 사람의 정보를 리턴해주면 됨
	public MemberVO getLogin(MemberVO memberVO) throws Exception;
	
	//join
	public int setJoin(MemberVO memberVO) throws Exception;
	
	//basicRole
	//public int setBasicRole(MemberVO memberVO) throws Exception;
	public int setBasicRole(Map<String, Object> map) throws Exception;
	
	//idDuplicateCheck
	public MemberVO idDuplicateCheck(MemberVO memberVO) throws Exception;
	
	//아래는 Schedule 수업때 사용한 메서드.
	//schedule test용 getMemberList
	public List<MemberVO> getMemberList() throws Exception;
	
	//로그아웃 시 마지막 로그아웃 시간을 저장하기
	public int setLogoutTime(MemberVO memberVO) throws Exception;
	
	//로그인 한지 3일이 지났는지 확인
	public Long getTimeDiff(MemberVO memberVO) throws Exception;
	
	//3일이 지났으면 enabled 값을 0으로 바꾸기
	public int setEnabledValue0(MemberVO memberVO) throws Exception;
	
	//3일이 안지났으면 enabled 값을 1로 바꾸기
	public int setEnabledValue1(MemberVO memberVO) throws Exception;

	//생일인 사람 찾기
	public List<MemberVO> getBirthdayMember() throws Exception;	
	
	//생일인 사람 글쓰기
	public int setBirthNotice(String names) throws Exception;
	
	//ID, EMAIL 일치하는 사람 조회
	public MemberVO getFindPassword(MemberVO memberVO) throws Exception;
	
	//임시 비밀번호 업데이트
	public int setTempPassword(MemberVO memberVO) throws Exception;
}
