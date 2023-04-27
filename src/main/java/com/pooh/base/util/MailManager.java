package com.pooh.base.util;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailManager {
	//메일을 보내는 클래스
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("spring.mail.username")
	private String sender;
	
	public void send(String to, String sub, String con) throws Exception{
		//매개변수를 따로 보내기보다는 emailVO같은거 만들어서 한번에 보내주면 될거같음
		
		//아래 코드보다 조금 더 정교하게 보낼 수 있는 메일.(HTML 태그 또한 전송 가능)
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		mimeMessage.setFrom(sender);
		mimeMessage.addRecipient(RecipientType.TO, new InternetAddress(to));
		mimeMessage.setSubject(sub);
		mimeMessage.setText(con, "UTF-8", "html");
		
		javaMailSender.send(mimeMessage);
		
		
		//HTML태그를 무시하고 그대로 text로 보냄.
//		SimpleMailMessage mailMessage = new SimpleMailMessage();
//		mailMessage.setFrom(sender);
//		mailMessage.setTo(to);
//		mailMessage.setSubject(sub);
//		mailMessage.setText(con);
//		
//		javaMailSender.send(mailMessage);
	}
}
