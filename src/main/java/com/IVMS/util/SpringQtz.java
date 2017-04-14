package com.IVMS.util;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.IVMS.model.Mail;
import com.IVMS.service.CheckUserService;


/**
 * @description 定时器任务,每隔一分钟执行一次,查找order_state=1的订单,order_creat_time
 * @author gr
 *
 */
@Component
public class SpringQtz {
	@Resource
	private CheckUserService checkUserService;
	/*
    * 测试定时
    * */
	protected void executeSource(){
		System.out.println("测试定时");
		List<Map<String,Object>> noticeEmailAndTime=checkUserService.selectNotifyEmailAndTime();
		System.out.println(noticeEmailAndTime);
		Date currentTime=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String time=sdf.format(currentTime);
        if(noticeEmailAndTime!=null&&!noticeEmailAndTime.isEmpty()){
    		for(Map<String, Object> timeAndEmail:noticeEmailAndTime){
    			Date ctrCheckNextTime=(Date) timeAndEmail.get("NPENotifyTime");
    			System.out.println(ctrCheckNextTime);
    	        sdf=new SimpleDateFormat("yyyyMMdd");
    	        String nextTime=sdf.format(ctrCheckNextTime);
    			if(time.equals(nextTime)){
    				String email=(String) timeAndEmail.get("NPENotifyEmail");
    				System.out.println(email);
    				if(email!=null&&!email.equals("0")){
    					String[]ccs=new String[0];
        				Mail mail=new Mail(email,"公司内部邮件","你的检具或设备下次校验时间已到，请尽快对其进行检测！",ccs);
        			    MailSender.sendMail(mail);
    				}
    			}
    		}
        }
	}
}
