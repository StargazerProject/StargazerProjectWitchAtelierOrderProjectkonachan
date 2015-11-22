package com.pisual.www.cells.yande.util;

import com.pisual.www.mail.MailSenderInfo;
import com.pisual.www.mail.SimpleMailSender;

public class PisualMail {
	/**邮件发送服务**/
	public static void sendMail(String subject,String content)
	{
		 MailSenderInfo mailInfo = new MailSenderInfo();   
	     mailInfo.setMailServerHost("smtp.163.com");   
	     mailInfo.setMailServerPort("25");   
	     mailInfo.setValidate(true);   
	     mailInfo.setUserName("pisual@163.com");
	     mailInfo.setPassword(""); 
	     mailInfo.setFromAddress("pisual@163.com");   
	     mailInfo.setToAddress("pisual@163.com");   
	     mailInfo.setSubject(subject);   
	     mailInfo.setContent(content);   
	     SimpleMailSender sms = new SimpleMailSender();  
	     sms.sendTextMail(mailInfo);
	}
}
