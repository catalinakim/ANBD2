package JavaMail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends Authenticator {

	public static void main(String[] args) 
	{
	}//end of main method
	
	protected PasswordAuthentication getPasswordAuthentication() 
	{
		String userName = "progJdoh302@gmail.com";
		String password = "zeroOEO0";
		
		return new PasswordAuthentication(userName, password);
	}

}
