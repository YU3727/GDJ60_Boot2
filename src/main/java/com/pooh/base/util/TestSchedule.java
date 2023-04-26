package com.pooh.base.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pooh.base.board.notice.NoticeDAO;
import com.pooh.base.board.notice.NoticeVO;
import com.pooh.base.member.MemberDAO;
import com.pooh.base.member.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TestSchedule {
	
	@Autowired
	private MemberDAO memberDAO;
	@Autowired
	private NoticeDAO noticeDAO;
	@Autowired
	private MailManager mailManager;
	@Autowired
	private JavaMailSender javaMailSender;

	//@scheduled()의 괄호안에 언제 반복할건지 주기를 적어줌.
	//자정 = @Scheduled(cron = "0 0 0 * * *")
	//cron은 (cron = "초 분 시 일 월 요일")에 입력된 값 마다 반복하겠다는 의미
	//@Scheduled(cron = "20 */43 * * * *")
	public void test() throws Exception{
		log.error("============ 반복중 ============");
//		List<MemberVO> memberList = memberDAO.getMemberList();
		List<MemberVO> memberList = memberDAO.getBirthdayMember();
		
		//1. 매 월일시분 10초 주기로 멤버리스트 가져와서 로그출력하기
//		for(MemberVO memberVO : memberList) {
//			 //자정마다 체크, 마지막으로 로그인한지 3일이 지나면 enabled 값을 0으로 업데이트 하기.(테스트할때는 자정마다가 아니라 확인 가능하게끔)
//			 Long timeDiff = memberDAO.getTimeDiff(memberVO);
//			 if(timeDiff >= 3) {
//				 memberDAO.setEnabledValue0(memberVO);
//			 }else {
//				 memberDAO.setEnabledValue1(memberVO);
//			 }
//			 log.error("========== {}, {} ========", memberVO.getUserName(), timeDiff);
//			 
//		 }
//		 
//		 //2. 자정마다, 생일인 사람을 찾아서 공지사항에 글 쓰기.(월, 일만 비교)
//		 //1) 생일인 사람 찾기
//		memberList = memberDAO.getBirthdayMember();
//		 
//		 //2) 공지사항에 글 등록하기
//		 NoticeVO noticeVO = new NoticeVO();
//		 
//		 StringBuffer sb = new StringBuffer();
//		 
//		 sb.append("오늘의 생일자는 ");
//		 for(MemberVO memberVO: memberList) {
//			 sb.append(memberVO.getUserName());
//			 sb.append("님 ");
//		 } 
//		 sb.append("입니다. 축하합니다");
//		 
//		 noticeVO.setTitle("오늘의 생일자입니다");
//		 noticeVO.setWriter("ADMIN");
//		 noticeVO.setContents(sb.toString());
//		 
//		 noticeDAO.setInsert(noticeVO); 
//		
//		
//		//3. 생일자한테 email 전송하기
//		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//		String[] receiveList = new String[memberList.size()];
//		
//		//보낼 이메일 주소 찾기
//		for(int i=0; i<receiveList.length; i++) {
//			receiveList[i] = memberList.get(i).getEmail();
//		}
//		
//		simpleMailMessage.setTo(receiveList);
//		
//		//메일 제목설정
//		simpleMailMessage.setSubject("생일을 축하합니다");
//		
//		//메일 내용설정
//		simpleMailMessage.setText("생일을 축하합니다 - test 환경 발송");
//		
//		//메일 전송
//		javaMailSender.send(simpleMailMessage);
//		log.error("============{}============", simpleMailMessage);
		
		for(MemberVO memberVO : memberList) {
			mailManager.send(memberVO.getEmail(), "생일축하", "<h1>생일을 축하합니다.>");
			}
	}
}
