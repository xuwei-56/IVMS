package com.IVMS.util;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.IVMS.model.Cell;
import com.IVMS.model.CheckingTools;
import com.IVMS.model.Equipment;
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
    				String cfid=(String) timeAndEmail.get("CFId");
    				Integer style=(Integer) timeAndEmail.get("NPEStyle");
    				System.out.println("emai:"+email+",cfid"+cfid);
    				if(email!=null&&!email.equals("0")){
    					String[]ccs=new String[0];
    					if(style==1){//检具
    						CheckingTools checkingtool=checkUserService.selectCheckingToolByCtid(Integer.valueOf(cfid));
    						String ctName=checkingtool.getCtname();
    						String ctUseLine=checkingtool.getCtuseline();//领用工线
    						String ctUseStation=checkingtool.getCtusestation();//领用工位
    						String ctUseItem=checkingtool.getCtuseitem();//使用项目
    						Integer cycle=checkingtool.getCtcheckcycle();//校验周期
    						String cycleInfo=null;
    						if(cycle==1){
    							cycleInfo="三个月";
    						}else if(cycle==2){
    							cycleInfo="半年";
    						}else{
    							cycleInfo="一年";
    						}
    						String emailInfo="你的检具下次校验时间已到，请尽快送检！"+"   检具编号："+cfid+"，检具名称："+
    								ctName+"，领用工线："+ctUseLine+"，领用工位："+ctUseStation+"，使用项目："+
    								ctUseItem+"，校验周期："+cycleInfo+"。";
    						System.out.println("emailInfo:"+emailInfo);
    						Mail mail=new Mail(email,"公司内部邮件",emailInfo,ccs);
    					    MailSender.sendMail(mail);
    					}else if(style==2){//设备
    						Equipment equipment=checkUserService.selectEquipmentByEid(Integer.valueOf(cfid));
    						String ename=equipment.getEname();
    						Integer cid=equipment.getCid();
    						Cell cell=checkUserService.selectCNameByCid(cid);
    						String cName=cell.getCname();//装配线名称
    						Integer cycle=equipment.getEcheckcycle();
    						String emailInfo="你设备下次校验时间已到，请尽快对其进行检测！"+"   设备编号："+cfid+"，设备名称："+ename+"，装配线："+cName+"，检查周期："+cycle+"天。";
    						Mail mail=new Mail(email,"公司内部邮件",emailInfo,ccs);
    					    MailSender.sendMail(mail);
    					}
    				}
    			}
    		}
        }
	}
}
