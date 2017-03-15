package com.IVMS.test;


import com.IVMS.model.Mail;
import com.IVMS.util.MailSender;

public class MailTest {
	public static void main(String[] args) {
		String[]Ccs={"allstarpeng@126.com","1121"};
		Mail mail=new Mail("792045914@qq.com","哇哇哇","测试邮件",Ccs);
		MailSender.sendMail(mail);
	}
}
