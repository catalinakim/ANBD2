package JavaMail;

import java.security.Key;

import javax.mail.MessagingException;

public class PostMan 
{
	public static void main(String[] args) 
	{
		String from = "progJdoh302@gmail.com";
		String to = "jdoh302@naver.com";
		String cc = to;
		String subject = "html �������� �޾���?";
		String content = "��ũ�� �� �״� ���⸦ Ŭ���ؼ� ���������� �Ϸ��غ���";
		content += "<a href=\"http://192.168.0.65:8090/anbd2/main/main.jsp\">�������� �Ϸ��ϱ�</a>";
		
		if(from.trim().equals(""))
		{
			System.out.println("������ ����� �Է����� �ʾҽ��ϴ�.");
		}
		else if(to.trim().equals(""))
		{
			System.out.println("�޴� ����� �Է����� �ʾҽ��ϴ�.");
		}
		else 
		{
			MailTest mt = new MailTest();
			try 
			{
				mt.sendEmail(from, to, cc, subject, content);
				System.out.println("���� ���� ����");
			}
			catch (MessagingException e) 
			{
				System.out.println("���� ���� ����\n���п��� : ");
				System.out.println(e.getMessage());
				
			}
			catch (Exception e) 
			{
				System.out.println("���� ���� ����");
			}
		}
	}//end of main method
	
	
}//end of PostMan
