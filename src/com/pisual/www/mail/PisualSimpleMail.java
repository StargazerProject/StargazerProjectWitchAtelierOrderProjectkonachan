package com.pisual.www.mail;

/**Pisual 标准通知模型**/
public class PisualSimpleMail {
	public PisualSimpleMail(String title,String content) {
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.163.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("pisual@163.com");
		mailInfo.setPassword("");
		mailInfo.setFromAddress("pisual@163.com");
		mailInfo.setToAddress("pisual@163.com");
		mailInfo.setSubject(title);
		mailInfo.setContent(content);
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendHtmlMail(mailInfo);
	}
}
