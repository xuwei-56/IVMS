package com.IVMS.util;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.IVMS.model.Cell;
import com.IVMS.model.CheckingTools;
import com.IVMS.model.CheckingToolsRecord;
import com.IVMS.model.Equipment;
import com.IVMS.model.EquipmentCheckTime;
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
    						CheckingTools checkingtool=checkUserService.selectCheckingToolByCtid(cfid);
    						Integer ctrid=checkUserService.selectMaxCtrIdByCtid(cfid);
    						CheckingToolsRecord checkingToolsRecord=checkUserService.selectCheckingToolRecordByCtrid(ctrid);
    						Date lastctrchecktime=checkingToolsRecord.getCtrchecktime();//检具上次校验时间
    						sdf=new SimpleDateFormat("yyyy-MM-dd");
    		    	        String lastCheckingToolchecktime=sdf.format(lastctrchecktime);
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
    						String emailInfo="你的检具下次校验时间已到，请尽快送检！"+"\r\n\r\n检具编号："+cfid+"\r\n检具名称："+
    								ctName+"\r\n领用工线："+ctUseLine+"\r\n领用工位："+ctUseStation+"\r\n使用项目："+
    								ctUseItem+"\r\n校验周期："+cycleInfo+"\r\n检具上次校验时间："+lastCheckingToolchecktime;
    						System.out.println("emailInfo:"+emailInfo);
    						Mail mail=new Mail(email,"公司内部邮件",emailInfo,ccs);
    					    MailSender.sendMail(mail);
    					}else if(style==2||style==3){//设备
    						Equipment equipment=checkUserService.selectEquipmentByEid(Integer.valueOf(cfid));
    						String ename=equipment.getEname();
    						Integer cid=equipment.getCid();
    						String lName=checkUserService.selectLNameByCid(cid);
    						Cell cell=checkUserService.selectCNameByCid(cid);
    						String cName=cell.getCname();//装配线名称
    						Integer cycle=equipment.getEcheckcycle();
    						String eWorker=equipment.getEworker();
    						Integer ectid=checkUserService.selectEctidByEid(Integer.valueOf(cfid));//得到最新ectid
    						EquipmentCheckTime equipmentCheckTime=checkUserService.selectEquipmentCheckTimeByEctid(ectid);
    						Date lastCheckTime=equipmentCheckTime.getEctime();//设备上次校验时间
    						sdf=new SimpleDateFormat("yyyy-MM-dd");
    		    	        String lastEquipmentCheckTime=sdf.format(lastCheckTime);
    						String emailInfo="你的设备下次校验时间已到，请尽快对其进行检测！"+"\r\n\r\n设备编号："+cfid+"\r\n设备名称："+
    						ename+"\r\n生产线："+lName+"\r\n装配线："+cName+"\r\n检查周期："+cycle+"天"+"\r\n设备负责人："+eWorker
    						+"\r\n设备上次校验时间："+lastEquipmentCheckTime;
    						Mail mail=new Mail(email,"公司内部邮件",emailInfo,ccs);
    					    MailSender.sendMail(mail);
    					}
    				}
    			}
    		}
        }
	}
}
