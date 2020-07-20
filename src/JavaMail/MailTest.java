package JavaMail;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailTest {

	public static void main(String[] args) 
	{
	}//end of main method
	
	public void sendEmail(String from, String to, String cc, String subject, String content) throws Exception
	{
		Properties props = new Properties();
		//GMAIL smtp 사용
		//이메일 프로토콜 지정 - SMTP
		//SMTP(Simple Mail Transfer Protocol), mail.jar에 있다
		props.put("mail.transport.protocol", "smtp");
		
		//GMAIL SMTP 서비스 주소, 호스트
		props.put("mail.smtp.host", "smtp.gmail.com");
		
		//GMAIL SMTP 서비스 포트, 465/587
		props.put("mail.smtp.port", "465");
		
		//GMAIL 인증 SSL(Secure Socket Layer) 보안 소켓, 인터넷 보안 프로토콜
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		
		//SMTP 인증
		props.put("mail.smtp.auth", "true");
		
		Authenticator auth = new SMTPAuthenticator();
		Session mailSession = Session.getDefaultInstance(props, auth);
		
		Message msg = new MimeMessage(mailSession);
		
		msg.setFrom(new InternetAddress(from));
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
		
		if(!cc.trim().equals(""))
		{
			msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc, false));
		}
		
		msg.setSubject(subject);
		msg.setText(content);
		msg.setSentDate(new Date());
		msg.setContent(content, "text/html; charset=utf-8"); 
		
		Transport.send(msg);
		
	}//end of sendEmail
	

}
