package api;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMail {

	//final String user	  = "ghkim891023@gmail.com"; //발신자 이메일 아이디 입력 
	//final String password = "qubikpy.,"; 			 //발신자 이메일 패스워드 입력

	public static void main(String[] args) {

		String mailProtocol = "smtp";
		String mailHost 	= "smtp.gmail.com";
		String mailPort	    = "587";
		String mailId 		= "ghkim891023@gmail.com"; // 구글계정
		String mailPassword = "qubikpy.,"; 			   // 구글계정 비밀번호 

		String fromName 	= "ghkim";
		String fromEmail 	= "ghkim891023@gmail.com"; // 보내는사람 메일
		String toName 		= "kgh";
		String toEmail 		= "cromgh@naver.com";  // 받는사람 메일
		String mailTitle 	= "메일 테스트 제목3";
		String mailContents = "이메일 인증을 완료하려면, 다음 링크를 클릭하세요. <br>"
						     +"<a href='http://192.168.0.68:8090/anbd2/main/main.jsp'>아나바다 인증하기</a>";
		String debugMode	= "false";
		String authMode		= "true";

		try {
			boolean debug = Boolean.valueOf(debugMode).booleanValue();
			
			//Property에 SMTP 서버 정보를 설정
			Properties mailProps = new Properties();
			mailProps.put("mail.smtp.starttls.enable", "true"); //ssl
			mailProps.setProperty("mail.transport.protocol", mailProtocol); 
			mailProps.put("mail.debug", debugMode);
			mailProps.put("mail.smtp.host", mailHost);
			mailProps.put("mail.smtp.port", mailPort);
			mailProps.put("mail.smtp.connectiontimeout", "5000");
			mailProps.put("mail.smtp.timeout", "5000");  
			mailProps.put("mail.smtp.auth", authMode);

			Session msgSession = null;

			if(authMode.equals("true")) {
		        Authenticator auth = new MyAuthentication(mailId, mailPassword);
				msgSession = Session.getInstance(mailProps, auth);
			} else {
				msgSession = Session.getInstance(mailProps, null); 
			}
			
			msgSession.setDebug(debug);
			
			MimeMessage msg = new MimeMessage(msgSession);
			msg.setFrom(new InternetAddress(fromEmail, fromName));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail, toName));
			msg.setSubject(mailTitle);
			msg.setContent(mailContents, "text/html; charset=euc-kr");

			// 스태틱함수로 직접 보내지 않고 객체를 이용해서 보내고 객체를 닫아준다. 

			Transport t = msgSession.getTransport(mailProtocol);
			try {
				t.connect();
				t.sendMessage(msg, msg.getAllRecipients());
			} finally {
			  t.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

class MyAuthentication extends Authenticator {
    PasswordAuthentication pa;
    public MyAuthentication(String mailId, String mailPass) {
        pa = new PasswordAuthentication(mailId, mailPass);  
    }
    public PasswordAuthentication getPasswordAuthentication() {
        return pa;
    }
}

