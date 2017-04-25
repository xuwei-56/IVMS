package com.IVMS.controller;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.IVMS.model.CheckingForm;
import com.IVMS.model.CheckingTools;
import com.IVMS.model.CheckingToolsRecord;
import com.IVMS.model.Classify;
import com.IVMS.model.Equipment;
import com.IVMS.model.EquipmentCheckTime;
import com.IVMS.model.Mail;
import com.IVMS.model.NotifyPersonnelEmail;
import com.IVMS.model.User;
import com.IVMS.model.Warehouse;
import com.IVMS.service.CheckUserService;
import com.IVMS.service.SendCheckUserService;
import com.IVMS.util.CommonUtil;
import com.IVMS.util.EnumUtil;
import com.IVMS.util.MailSender;
import com.IVMS.util.SaveFileUtil;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/user")
public class CheckUserController {

	@Autowired
	CheckUserService checkUserService;
	@Autowired
	SendCheckUserService sendCheckUserService;
	
	@RequestMapping("/addProject")
	@ResponseBody
	public JSONObject addProject(String pName){
		int resultOfAddProject=checkUserService.insertProject(pName);
		if(resultOfAddProject<=0){
			return CommonUtil.constructResponse(0,"插入项目信息失败！",null);
		}else{
			return CommonUtil.constructResponse(EnumUtil.OK, "插入项目信息成功！", null);
		}
	}
	
	@RequestMapping("/deleteProject")
	@ResponseBody
	public JSONObject deleteProject(Integer pId){
		int resultOfDeleteProject=checkUserService.deleteByPid(pId);
		if(resultOfDeleteProject<=0){
			return CommonUtil.constructResponse(0,"删除项目信息失败！",null);
		}else{
			return CommonUtil.constructResponse(EnumUtil.OK, "删除项目信息成功！", null);
		}
	}
		
	@RequestMapping("/addLine")
	@ResponseBody
	public JSONObject addLine(String lName){
		int resultOfAddLine=checkUserService.insertLine(lName);
		int maxLid=checkUserService.selectMaxLid();
		if(resultOfAddLine<=0){
			return CommonUtil.constructResponse(0,"插入生产线信息失败！",null);
		}else{
			return CommonUtil.constructResponse(EnumUtil.OK, "插入生产线信息成功！", null);
		}
	}
	
	@RequestMapping("/deleteLine")
	@ResponseBody
	public JSONObject deleteLine(Integer lid){
		int resultOfDeleteLine=checkUserService.deleteByLid(lid);
		checkUserService.deleteCellByLid(lid);
		if(resultOfDeleteLine<=0){
			return CommonUtil.constructResponse(0,"删除生产线信息失败！",null);
		}else{
			return CommonUtil.constructResponse(EnumUtil.OK, "删除生产线信息成功！", null);
		}
	}
	
	@RequestMapping("/addCheckingClassify")
	@ResponseBody
	public JSONObject addCheckingClassify(Integer claId, String ccName){
		int resultOfAddCheckingClassify=checkUserService.insertCheckingClassify(claId, ccName);
		if(resultOfAddCheckingClassify<=0){
			return CommonUtil.constructResponse(0,"插入检测类型失败！",null);
		}else{
			sendCheckUserService.deleteCheckingClassifyByClaidAndCCname(claId);
			return CommonUtil.constructResponse(EnumUtil.OK, "插入检测类型成功！", null);
		}
	}
	
	@RequestMapping("/deleteCheckingClassify")
	@ResponseBody
	public JSONObject deleteCheckingClassify(Integer ccId){
		int resultOfDeleteCheckingClassify=checkUserService.deleteByCCid(ccId);
		if(resultOfDeleteCheckingClassify<=0){
			return CommonUtil.constructResponse(0,"删除检测类型失败！",null);
		}else{
			return CommonUtil.constructResponse(EnumUtil.OK, "删除检测类型成功！", null);
		}
	}
	
	@RequestMapping("/addClassify")
	@ResponseBody
	public JSONObject addClassify(String cName){
		int resultOfAddClassify=checkUserService.insertClassify(cName);
		int maxClaId=checkUserService.selectMaxClaId();
		checkUserService.insertCheckingClassify(maxClaId,"默认");
		if(resultOfAddClassify<=0){
			return CommonUtil.constructResponse(0,"插入送检类型失败！",null);
		}else{
			return CommonUtil.constructResponse(EnumUtil.OK, "插入送检类型成功！", null);
		}
	}
	
	@RequestMapping("/deleteClassify")
	@ResponseBody
	public JSONObject deleteClassify(Integer claid){
		int resultOfDeleteClassify=checkUserService.deleteByClaid(claid);
		checkUserService.deleteCheckingClassifyByClaid(claid);
		if(resultOfDeleteClassify<=0){
			return CommonUtil.constructResponse(0,"删除送检类型失败！",null);
		}else{
			return CommonUtil.constructResponse(EnumUtil.OK, "删除送检类型成功！", null);
		}
	}
	
	@RequestMapping("/addCell")
	@ResponseBody
	public JSONObject addCell(Integer lid,String cname){
		int resultOfAddCell=checkUserService.insertCell(lid, cname);
		if(resultOfAddCell<=0){
			return CommonUtil.constructResponse(0,"插入单元失败！",null);
		}else{
			return CommonUtil.constructResponse(EnumUtil.OK, "插入单元成功！", null);
		}
	}
	
	@RequestMapping("/deleteCell")
	@ResponseBody
	public JSONObject deleteCell(Integer cid){
		int resultOfDeleteCell=checkUserService.deleteCellByCid(cid);
		if(resultOfDeleteCell<=0){
			return CommonUtil.constructResponse(0,"删除单元失败！",null);
		}else{
			return CommonUtil.constructResponse(EnumUtil.OK, "删除单元成功！", null);
		}
	}
	
	@RequestMapping("/getCfRemark")
	@ResponseBody
	public JSONObject getCfRemark(String cfid){
		String remark=checkUserService.selectCfRemarkByCfid(cfid);
		if(remark==null){
			return CommonUtil.constructResponse(0,"没有数据！",null);
		}else{
			return CommonUtil.constructResponse(EnumUtil.OK, "送检单备注信息！", remark);
		}
	}
		
	
	@RequestMapping("/updateCfStatusToOnCheck")
	@ResponseBody
	public JSONObject updateCfStatusToOnCheck(HttpSession session,String cfid,String SCFComponentId){
		/**
		 * 更新检测状态为检测中，发送邮箱（开始检测）
		 */
		long l=System.currentTimeMillis();
		String chekingToolsEmail=null;
		Integer resultOfUpdate=checkUserService.updateCfStatusToOnCheck(cfid);
		if(resultOfUpdate<=0){
			return CommonUtil.constructResponse(0,"更新状态失败！",null);
		}else{
			User user=(User) session.getAttribute("user");
			String userName=user.getCn();//拿到检测人	
			checkUserService.updateCfCheckManByCfid(userName, cfid);//更新送检单的检测人
			CheckingForm checkingForm=sendCheckUserService.selectByPrimaryKey(cfid);
			Integer ClaId=checkingForm.getClaid();
			Integer claId=sendCheckUserService.selectClaIdByCheckingTool();//得到检具送检类型的主键
			Date sendCheckTime=checkingForm.getCftime();//获得送检时间
			Date currentTime=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String time=sdf.format(currentTime);
	        String cftime=sdf.format(sendCheckTime);
			if(ClaId==claId){//检具送检
				CheckingTools checkingTools=checkUserService.selectCheckingToolByCtid(SCFComponentId);
				String ctreceiver=checkingTools.getCtreceiver();
				System.out.println("ctreceiver:"+ctreceiver);
				String username=(String) session.getAttribute("username");
				String password=(String) session.getAttribute("password");
				chekingToolsEmail=checkUserService.getEmailByCn(username, password,ctreceiver);//得到领用人邮箱
				if(chekingToolsEmail!=null&&!chekingToolsEmail.isEmpty()&&!chekingToolsEmail.equals("0")){
					String mailContent="你的检具送检已开始检测！"+"\r\n\r\n送检单号："+cfid+"\r\n检具编号："+SCFComponentId+"\r\n送检时间："+cftime+"\r\n检测人:"+userName+
							"\r\n检测时间："+time;
					/**
					 * 给送检单邮箱抄送人发送邮箱
					 */
					List<NotifyPersonnelEmail>emails=checkUserService.selectNotifyEmailByCfid(cfid);
					if(!emails.isEmpty()){
						String[]Ccs=new String[emails.size()];
						int i=0;
						for(NotifyPersonnelEmail notifyPersonnelEmail:emails){
							String email=notifyPersonnelEmail.getNpenotifyemail();
						    Ccs[i]=email;
							i++;
						}
						if(chekingToolsEmail!=null){
							Mail mail=new Mail(chekingToolsEmail,"公司内部邮件",mailContent,Ccs);
							long ll=System.currentTimeMillis();
							System.out.println((double)(ll-l));
							MailSender.sendMail(mail);
							long lll=System.currentTimeMillis();
							System.out.println((double)(lll-ll));
						}
					}
				}
			}else{
				/**
				 * 给送检单邮箱抄送人发送邮箱
				 */
				Classify classify=sendCheckUserService.selectClassifyNameByClaid(ClaId);
				String classifyName=classify.getCname();//得到送检类型
				List<NotifyPersonnelEmail>emails=checkUserService.selectNotifyEmailByCfid(cfid);
				if(!emails.isEmpty()){
					String[]Ccs=new String[emails.size()-1];
					int i=-1;
					String receiveEmail=null;
					for(NotifyPersonnelEmail notifyPersonnelEmail:emails){
						String email=notifyPersonnelEmail.getNpenotifyemail();
						if(i==-1){
							receiveEmail=email;
						}
						if(i!=-1){
							Ccs[i]=email;
						}
						i++;
					}
					if(receiveEmail!=null){
//							String[]Ccs={"allstarpeng@126.com"};
						String mailContent="你的送检已开始检测！"+"\r\n\r\n送检单号："+cfid+"\r\n送检类型："+classifyName+"\r\n零件编号："+SCFComponentId+"\r\n送检时间："+cftime+"\r\n检测人:"+userName+
								"\r\n检测时间："+time;
						Mail mail=new Mail(receiveEmail,"公司内部邮件",mailContent,Ccs);
						MailSender.sendMail(mail);
					}
				}
			}
				return CommonUtil.constructResponse(EnumUtil.OK, "更新状态成功！",null);	
		}
	}
	
	
	@RequestMapping("/deleteCheckingTool")
	@ResponseBody
	public JSONObject deleteCheckingTool(String ctid,HttpServletRequest request){
		Integer resultOfDeleteCheckingTool=checkUserService.deleteCheckingToolsByCtidAndCtStatus(ctid);
		if(resultOfDeleteCheckingTool<=0){
			return CommonUtil.constructResponse(0,"删除检具信息失败！",null);
		}else{
			/**
			 * 真正删除检具附件
			 */
			List<Map<String,Object>> checkingToolFile=checkUserService.selectCTFNameByCTId(ctid);
			for(Map<String, Object> file:checkingToolFile){
				String ctfName=(String) file.get("CTFName");
				System.out.println(ctfName);
				String root=request.getSession().getServletContext().getRealPath("/checkingtoolfile/");
				root=root.replace("\\","/");//  \\对\转义，把\换成/
				File fileOfCheckingTool=new File(root+ctfName);
				fileOfCheckingTool.delete();//删除检具附件
			}
			checkUserService.deleteCheckingToolsFileByCtid(ctid);
			return CommonUtil.constructResponse(EnumUtil.OK, "删除检具信息成功！",null);
		}
	}
	
	@RequestMapping("/deleteCheckingToolFile")
	@ResponseBody
	public JSONObject deleteCheckingToolFile(Integer ctfid,HttpServletRequest request){
		String path=checkUserService.selectCTFNameByCTFId(ctfid);
		System.out.println("ctfname:"+path);
		String root=request.getSession().getServletContext().getRealPath("/checkingtoolfile/");
		root=root.replace("\\","/");//  \\对\转义，把\换成/
		File checkingToolFile=new File(root+path);
		System.out.println("checkingToolFile:"+checkingToolFile);
		checkingToolFile.delete();//删除检具附件
		Integer resultOfDeleteCheckingToolFile=checkUserService.deleteCheckingToolsFileByCtfid(ctfid);
		if(resultOfDeleteCheckingToolFile<=0){
			return CommonUtil.constructResponse(0,"删除检具附件失败！",null);
		}else{
			return CommonUtil.constructResponse(EnumUtil.OK, "删除检具附件成功！",null);
		}
	}
	
	@RequestMapping("/judgeCtid")
	@ResponseBody
	public JSONObject judgeCtid(String ctid,HttpServletRequest request){
		CheckingTools checkingtools=checkUserService.judgeCtidIsAlreadyExist(ctid);//判断检具是否已经存在
		if(checkingtools==null){
			return CommonUtil.constructResponse(EnumUtil.OK,"此检具可以添加",null);
		}else{
			return CommonUtil.constructResponse(0,"检具已存在，不能重复添加！",null);
		}
	}
	
	@RequestMapping("/addCheckingToolInfo")
	@ResponseBody
	public JSONObject addCheckingToolInfo(HttpServletRequest request,CheckingTools checkingTools,
			@RequestParam(value = "checkingToolFiles", required = false) MultipartFile[] checkingToolFiles)
			throws Exception{
		String ctid=checkingTools.getCtid();
		CheckingTools checkingtools=checkUserService.judgeCtidIsAlreadyExist(ctid);//判断检具是否已经存在
		if(checkingtools==null){//添加的检具不存在
			Integer resultOfAddCheckingToolInfo=checkUserService.insertCheckingTools(checkingTools);//添加检具基本信息
			if(resultOfAddCheckingToolInfo<=0){
				return CommonUtil.constructResponse(0,"添加检具信息失败！",null);
			}else{
				NotifyPersonnelEmail personnelEmail=new NotifyPersonnelEmail();
            	personnelEmail.setCfid(ctid);
            	personnelEmail.setNpestyle(1);
            	sendCheckUserService.insertCopySendEmail(personnelEmail);
				/**
				 * 保存检具附件并把路径添加到数据库
				 */
				if(checkingToolFiles!=null&&checkingToolFiles.length>0){  
		            for(int i = 0;i<checkingToolFiles.length;i++){  
		                MultipartFile file = checkingToolFiles[i];  
		                SaveFileUtil saveFileUtil=new SaveFileUtil();
		                String filePath=saveFileUtil.saveFile(file, request);
		                checkUserService.insertCheckingToolsFile(ctid, filePath);
		            }  
		        }  
				return CommonUtil.constructResponse(EnumUtil.OK, "添加检具信息成功！",null);
			}
		}else{
			return CommonUtil.constructResponse(0, "检具已存在，不能重复添加！",null);
		}
	}
	
	@RequestMapping("/addCheckingToolResult")
	@ResponseBody
	public JSONObject addCheckingToolResult(HttpServletRequest request,HttpSession session,
			CheckingToolsRecord checkingToolsRecord,Integer ctStatus)throws Exception{	
//		User user=(User) session.getAttribute("user");
//		String userName=user.getCn();//拿到检测人
		String userName="唐天鹏";
		String cfid=checkingToolsRecord.getCtrnum();//送检单号
		CheckingForm checkingForm=sendCheckUserService.selectByPrimaryKey(cfid);
		Integer ClaId=checkingForm.getClaid();
		Date sendCheckTime=checkingForm.getCftime();
		String ctid=checkingToolsRecord.getCtid();//检具编号
		checkingToolsRecord.setCtrchecktime(new Date());//设置检具校验时间
		Date currentTime=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=sdf.format(currentTime);//得到检具校验时间
        String cftime=sdf.format(sendCheckTime);//得到检具送检时间
		String emailInfo=null,email=null,toolNextCheckInfo=null;
		if(ctStatus==3||ctStatus==4){
			/**
			 * 检具封存或报废，没有下次校验时间,修改送检单状态
			 */
			checkUserService.updateCfStatusToCheckOver(cfid);
			if(ctStatus==3){
				emailInfo="封存";
			}else{
				emailInfo="报废";
			}
			toolNextCheckInfo="";
			checkUserService.updateCheckingToolStatusByCtidAndCtStatus(6, ctid);//确认签字
		}else{
			/*
			 * 检具正常或维修，录入下次校验时间,检具正常时修改送检单状态，检具维修时不修改送检单状态
			 */
			if(ctStatus==5){//正常
				Integer checkingToolCycle=checkUserService.selectCycleByCtid(ctid);//拿到检具校验周期
				Calendar calendar=Calendar.getInstance();
				calendar.setTime(new Date());
				if(checkingToolCycle==1){
					calendar.add(Calendar.MONTH,3);
				}else if(checkingToolCycle==2){
					calendar.add(Calendar.MONTH,6);
				}else if(checkingToolCycle==3){
					calendar.add(Calendar.MONTH,12);
				}
				Date nextCheckTime=calendar.getTime();
				sdf=new SimpleDateFormat("yyyy-MM-dd");
				String nextCheckTimeFormat=sdf.format(nextCheckTime);
				checkingToolsRecord.setCtrchecknexttime(nextCheckTime);//获得检具下次校验时间
				CheckingTools checkingTools=checkUserService.selectCheckingToolByCtid(ctid);
				String ctreceiver=checkingTools.getCtreceiver();
				String username=(String) session.getAttribute("username");
				String password=(String) session.getAttribute("password");
				email=checkUserService.getEmailByCn(username, password,ctreceiver);//得到领用人邮箱
				NotifyPersonnelEmail notifyPersonnelEmail=new NotifyPersonnelEmail();
				if(email!=null){
					notifyPersonnelEmail.setNpenotifyemail(email);
				}
				notifyPersonnelEmail.setCfid(ctid);
				notifyPersonnelEmail.setNpenotifytime(nextCheckTime);
				notifyPersonnelEmail.setNpestyle(1);
				checkUserService.updateNotifyPersonalEmailByCfid(notifyPersonnelEmail);
				checkUserService.updateCfStatusToCheckOver(cfid);
				emailInfo="正常";
				checkUserService.updateCheckingToolStatusByCtidAndCtStatus(6, ctid);//确认签字
				toolNextCheckInfo="\r\n检具下次校验时间："+nextCheckTimeFormat;
			}else{
				checkUserService.updateCfStatusToCheckOver(cfid);
				emailInfo="维修中";
				checkUserService.updateCheckingToolStatusByCtidAndCtStatus(ctStatus, ctid);//维修
				toolNextCheckInfo="";
			}
		}
		checkingToolsRecord.setCtrremark(ctStatus);	
		Integer resultOfAddCheckingToolInfo=checkUserService.
				insertCheckingToolsRecord(checkingToolsRecord);//添加检具记录结果
		if(resultOfAddCheckingToolInfo<=0){
			return CommonUtil.constructResponse(0,"添加检具检测记录失败！",null);
		}else{
			/**
			 * 发送邮箱
			 */
			if(email!=null&&!email.isEmpty()){
				String emailContent="你的检具送检已检测完成！"+"\r\n\r\n送检单号："+cfid+"\r\n检具编号："+ctid+"\r\n送检时间："+cftime+"\r\n检测人:"+userName+
								"\r\n录入检具校验结果时间："+time+"\r\n检测状态："+emailInfo+toolNextCheckInfo;
				List<NotifyPersonnelEmail>emails=checkUserService.selectNotifyEmailByCfid(cfid);
				if(!emails.isEmpty()){
					String[]Ccs=new String[emails.size()];
					int i=0;
					for(NotifyPersonnelEmail notifyPersonnelEmail:emails){
						String notifyEmail=notifyPersonnelEmail.getNpenotifyemail();
							Ccs[i]=notifyEmail;
							i++;
					}
					Mail mail=new Mail(email,"公司内部邮件",emailContent,Ccs);
					MailSender.sendMail(mail);
				}
			}
			return CommonUtil.constructResponse(EnumUtil.OK, "添加检具检测记录成功！",null);
		}
	}
	
	@RequestMapping("/addCheckingToolReceiver")
	@ResponseBody
	public JSONObject addCheckingToolReceiver(HttpSession session,String ctid,String ctreceiver,
			String ctuseitem,String ctuseline,String ctusestation)
			throws Exception{
		Integer resultOfAddCheckingToolReceiver=checkUserService.updateCheckingToolTimeAndReceiverByCtid
				(ctid,ctreceiver, new Date(),ctuseitem,ctuseline,ctusestation);
		if(resultOfAddCheckingToolReceiver<=0){
			return CommonUtil.constructResponse(0,"更新检具领用人失败！",null);
		}else{
			checkUserService.updateCheckingToolStatusByCtid(ctid);//更改检具状态为已领用
			/**
			 *  在notifypersonnelemail表中加入通知邮箱，通知时间和通知类型
			 */
			Integer cycle=checkUserService.selectCycleByCtid(ctid);
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(new Date());
			if(cycle==1){
				calendar.add(Calendar.MONTH,3);
			}else if(cycle==2){
				calendar.add(Calendar.MONTH,6);
			}else if(cycle==3){
				calendar.add(Calendar.MONTH,12);
			}
			Date nextCheckTime=calendar.getTime();//得到检具下次检验时间
			CheckingToolsRecord record=new CheckingToolsRecord();
			record.setCtid(ctid);
			record.setCtrchecktime(new Date());
			record.setCtrchecknexttime(nextCheckTime);
			checkUserService.insertCheckingToolsRecord(record);
			String username=(String) session.getAttribute("username");
			String password=(String) session.getAttribute("password");
			String email=checkUserService.getEmailByCn(username, password,ctreceiver);//得到领用人邮箱
			
			NotifyPersonnelEmail notifyPersonnelEmail=new NotifyPersonnelEmail();
			if(email!=null){
				notifyPersonnelEmail.setNpenotifyemail(email);
			}
			notifyPersonnelEmail.setNpenotifytime(nextCheckTime);
			notifyPersonnelEmail.setNpestyle(1);
			notifyPersonnelEmail.setCfid(ctid);
			checkUserService.updateNotifyPersonalEmailByCfid(notifyPersonnelEmail);
			return CommonUtil.constructResponse(EnumUtil.OK, "更新检具领用人成功！",null);
		}
	}
	
	@RequestMapping("/updateAgreeAndAccept")
	@ResponseBody
	public JSONObject updateAgreeAndAccept(String ctid, Integer ctracceptresult, Integer ctisagree)
			throws Exception{
		Integer ctrid=checkUserService.selectMaxCtrIdByCtid(ctid);
		Integer resultOfUpdateAgreeAndAccept=checkUserService.updateAcceptAndAgreeByCtrid
				(ctrid, ctracceptresult, ctisagree);
		if(resultOfUpdateAgreeAndAccept<=0){
			return CommonUtil.constructResponse(0,"更新接收和确认信息失败！",null);
		}else{
			CheckingToolsRecord checkingToolsRecord=checkUserService.
					selectCheckingToolRecordByCtrid(ctrid);
			Integer status=checkingToolsRecord.getCtrremark();
			if(status==2){//维修
				checkUserService.updateCheckingToolStatusByCtidAndCtStatus(5, ctid);//更改状态为正常
			}else{
				checkUserService.updateCheckingToolStatusByCtidAndCtStatus(status, ctid);
			}
			return CommonUtil.constructResponse(EnumUtil.OK, "更新接收和确认信息成功！",null);
		}
	}
	
	@RequestMapping("/updateCheckingToolStatus")
	@ResponseBody
	public JSONObject updateCheckingToolStatus(String ctid,HttpSession session,Integer ctStatus) throws Exception{
		User user=(User) session.getAttribute("user");
		String userName=user.getCn();//拿到检测人
		String cfId=checkUserService.selectCfIdByCtid(ctid);//拿到最新订单号
		CheckingForm checkingForm=sendCheckUserService.selectByPrimaryKey(cfId);
		Integer ClaId=checkingForm.getClaid();
		Date sendCheckTime=checkingForm.getCftime();//得到送检时间
		Date currentTime=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=sdf.format(currentTime);//得到检具校验时间
        String cftime=sdf.format(sendCheckTime);//得到检具送检时间
        
		String email=null,emailInfo=null,toolNextCheckInfo=null;
		if(ctStatus==5){//正常
			Integer cycle=checkUserService.selectCycleByCtid(ctid);
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(new Date());
			if(cycle==1){
				calendar.add(Calendar.MONTH,3);
			}else if(cycle==2){
				calendar.add(Calendar.MONTH,6);
			}else if(cycle==3){
				calendar.add(Calendar.MONTH,12);
			}
			Date nextCheckTime=calendar.getTime();//得到检具下次检验时间
			sdf=new SimpleDateFormat("yyyy-MM-dd");
			String nextCheckTimeFormat=sdf.format(nextCheckTime);
			checkUserService.updateCTRCheckNextTimeByCtrNum(nextCheckTime, cfId);//更新检具下次校验时间
			/**
			 * 拿到检具领用人邮箱
			 */
			CheckingTools checkingTools=checkUserService.selectCheckingToolByCtid(ctid);
			String ctreceiver=checkingTools.getCtreceiver();//检具领用人
			String username=(String) session.getAttribute("username");
			String password=(String) session.getAttribute("password");
			email=checkUserService.getEmailByCn(username, password,ctreceiver);//得到领用人邮箱
			NotifyPersonnelEmail notifyPersonnelEmail=new NotifyPersonnelEmail();
			if(email!=null){
				notifyPersonnelEmail.setNpenotifyemail(email);
			}
			/**
			 * 更新定时发送邮箱的邮箱和时间
			 */
			notifyPersonnelEmail.setCfid(ctid);
			notifyPersonnelEmail.setNpenotifytime(nextCheckTime);
			notifyPersonnelEmail.setNpestyle(1);
			checkUserService.updateNotifyPersonalEmailByCfid(notifyPersonnelEmail);
			checkUserService.updateCheckingToolStatusByCtidAndCtStatus(6, ctid);//确认签字
			toolNextCheckInfo="\r\n检具下次校验时间："+nextCheckTimeFormat;
		}else{
			if(ctStatus==3){
				emailInfo="封存";
				checkUserService.updateCheckingToolStatusByCtidAndCtStatus(6, ctid);//确认签字
			}else{
				emailInfo="报废";
				checkUserService.updateCheckingToolStatusByCtidAndCtStatus(6, ctid);//确认签字
			}
			toolNextCheckInfo="";
		}
		Integer resultOfUpdateCheckingToolStatus=checkUserService.
				updateCheckingToolStatusByCtidAndCtStatus(ctStatus, ctid);
		checkUserService.updateCfStatusToCheckOver(cfId);//修改送检单状态
		if(resultOfUpdateCheckingToolStatus<=0){
			return CommonUtil.constructResponse(0,"更新检具状态失败！",null);
		}else{
			if(email!=null&&!email.isEmpty()){
				String mailContent="你的检具送检经过维修后,检测状态为："+emailInfo+"！\r\n\r\n送检单号："+cfId+"\r\n检具编号："+ctid+"\r\n送检时间："+cftime+"\r\n检测人:"+userName+
					"\r\n检具修改为正常状态的时间："+time+toolNextCheckInfo;
				List<NotifyPersonnelEmail>emails=checkUserService.selectNotifyEmailByCfid(cfId);
				if(!emails.isEmpty()){
					String[]Ccs=new String[emails.size()];
					int i=0;
					for(NotifyPersonnelEmail notifyPersonnelEmail:emails){
						String notifyEmail=notifyPersonnelEmail.getNpenotifyemail();
							Ccs[i]=notifyEmail;
							i++;
					}
					Mail mail=new Mail(email,"公司内部邮件",mailContent,Ccs);
					MailSender.sendMail(mail);
				}
			}
			return CommonUtil.constructResponse(EnumUtil.OK, "更新检具状态成功！",null);
		}
	}
	
	@RequestMapping("/updateCheckingTool")
	@ResponseBody
	public JSONObject updateCheckingTool(CheckingTools checkingTools,HttpServletRequest request,
			@RequestParam("checkingToolFiles") MultipartFile[] checkingToolFiles)
			throws Exception{
		String ctid=checkingTools.getCtid();
		Integer ctrid=checkUserService.selectMaxCtrIdByCtid(ctid);
		Integer resultOfUpdateCheckingTool=checkUserService.updateCheckingToolByCtid(checkingTools);
		if(resultOfUpdateCheckingTool<=0){
			return CommonUtil.constructResponse(0,"更新检具信息失败！",null);
		}else{
			CheckingToolsRecord checkingToolsRecord=new CheckingToolsRecord();
			checkingToolsRecord=checkUserService.selectCheckingToolRecordByCtrid(ctrid);
			if(checkingToolsRecord!=null){
				Date checkTime=checkingToolsRecord.getCtrchecktime();
				Integer cycle=checkingTools.getCtcheckcycle();
				Calendar calendar=Calendar.getInstance();
				calendar.setTime(checkTime);
				if(cycle==1){
					calendar.add(Calendar.MONTH,3);
				}else if(cycle==2){
					calendar.add(Calendar.MONTH,6);
				}else if(cycle==3){
					calendar.add(Calendar.MONTH,12);
				}
				Date nextCheckTime=calendar.getTime();//得到检具下次检验时间
				checkingToolsRecord.setCtrid(ctrid);
				checkingToolsRecord.setCtrchecknexttime(nextCheckTime);
				checkUserService.updateCheckingToolResultByCtrid(checkingToolsRecord);
				/**
				 * 更新定时发送邮箱
				 */
				NotifyPersonnelEmail notifyPersonnelEmail=new NotifyPersonnelEmail();
				notifyPersonnelEmail.setNpenotifytime(nextCheckTime);
				notifyPersonnelEmail.setNpestyle(1);
				notifyPersonnelEmail.setCfid(ctid);
				checkUserService.updateNotifyPersonalEmailByCfid(notifyPersonnelEmail);
			}
			/**
			 * 保存检具附件并把路径添加到数据库
			 */
			if(checkingToolFiles!=null&&checkingToolFiles.length>0){  
	            for(int i = 0;i<checkingToolFiles.length;i++){  
	                MultipartFile file = checkingToolFiles[i];  
	                SaveFileUtil saveFileUtil=new SaveFileUtil();
	                String filePath=saveFileUtil.saveFile(file, request);
	                checkUserService.insertCheckingToolsFile(ctid, filePath);
	            }  
	        }  
			return CommonUtil.constructResponse(EnumUtil.OK, "更新检具信息成功！",null);
		}
	}
	
	@RequestMapping("/getCheckingToolReceiver")
	@ResponseBody
	public JSONObject getCheckingToolReceiver(String ctid)
			throws Exception{
		CheckingTools checkingTools=checkUserService.selectCheckingToolByCtid(ctid);
		if(checkingTools==null){
			return CommonUtil.constructResponse(0,"没有数据",null);
		}else{
			return CommonUtil.constructResponse(EnumUtil.OK,"检具信息",checkingTools);
		}
	}
	
	@RequestMapping("/updateCheckingToolReceiver")
	@ResponseBody
	public JSONObject updateCheckingToolReceiver(String ctid,String ctreceiver)
			throws Exception{
		Integer resultOfUpdateCheckingToolReceiver=checkUserService.updateCheckingToolReceiverByCtid(ctreceiver, ctid);
		if(resultOfUpdateCheckingToolReceiver<=0){
			return CommonUtil.constructResponse(0,"更新检具领用人失败！",null);
		}else{
			return CommonUtil.constructResponse(EnumUtil.OK,"更新检具领用人成功！",null);
		}
		
	}
	
	@RequestMapping("/getWareHouseByClaid")
	@ResponseBody
	public JSONObject getWareHouseByClaid(Integer claId)
			throws Exception{
		List<Warehouse> Wids=checkUserService.selectWIdByClaid(claId);
		if(Wids.isEmpty()){
			return CommonUtil.constructResponse(0,"该送检类型没有库位！",null);
		}else{
			List<Warehouse> letters=new ArrayList<Warehouse>();
			String lastWid=null;
			for(Warehouse warehouse:Wids){
				Warehouse wh=new Warehouse();
				String wid=warehouse.getWid();
				wid=wid.substring(0,2);
				if(!wid.equals(lastWid)){
					wh.setWid(wid);
					letters.add(wh);
				}
				lastWid=wid;
			}
			return CommonUtil.constructResponse(EnumUtil.OK,"库位信息！",letters);
		}
	}
	
	@RequestMapping("/deleteWareHouse")
	@ResponseBody
	public JSONObject deleteWareHouse(String wid)
			throws Exception{
		Integer resultOfDeleteWareHouse=checkUserService.deleteWareHouseByWid(wid);
		if(resultOfDeleteWareHouse<=0){
			return CommonUtil.constructResponse(0,"删除库位失败！",null);
		}else{
			return CommonUtil.constructResponse(EnumUtil.OK, "删除库位成功！",null);
		}
	}
	
	@RequestMapping("/addWareHouse")
	@ResponseBody
	public JSONObject addWareHouse(Warehouse warehouse)
			throws Exception{
		String wid=warehouse.getWid();
		List<Warehouse>warehouses=checkUserService.judgeWidIsAlreadyExist(wid);
		int number=1,resultOfInsertWareHouse=0;
		if(!warehouses.isEmpty()){
			return CommonUtil.constructResponse(0,"库位已存在！",null);
		}else{
			while(number<4){
				String wid1=wid+number;
				warehouse.setWid(wid1);
				resultOfInsertWareHouse=checkUserService.insertWareHouse(warehouse);
				number++;
			}
			if(resultOfInsertWareHouse<=0){
				return CommonUtil.constructResponse(0,"添加库位失败！",null);
			}else{
				return CommonUtil.constructResponse(EnumUtil.OK, "添加库位成功！",null);
			}
		}
	}
	
	@RequestMapping("/addEquipment")
	@ResponseBody
	public JSONObject addEquipMent(HttpSession session,Equipment equipment,
			@DateTimeFormat(pattern="yyyy-MM-dd") Date date,String[]copySendEmails)
			throws Exception{
		User user = (User)session.getAttribute("user");
		if(user.getPager().equals("1")){//设备负责人
			int resultOfAddEquipment=checkUserService.insertEquipment(equipment);
			if(resultOfAddEquipment<=0){
				return CommonUtil.constructResponse(0,"添加设备信息失败！",null);
			}else{
				Integer equipmentCycle=equipment.getEcheckcycle();
				String worker=equipment.getEworker();//得到负责人
				Calendar calendar=Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.DATE,equipmentCycle);
				Date nextCheckTime=calendar.getTime();//得到设备下次检验时间
				EquipmentCheckTime equipmentCheckTime=new EquipmentCheckTime();
				//得到eid
				Integer eid=checkUserService.selectMaxEid();
				equipmentCheckTime.setEid(eid);
				equipmentCheckTime.setEcnexttime(nextCheckTime);
				equipmentCheckTime.setEctime(date);
				checkUserService.insertEquipmentCheckTime(equipmentCheckTime);
				/**
				 * 添加负责人通知邮箱
				 */
				String username=(String) session.getAttribute("username");
				String password=(String) session.getAttribute("password");
				String email=checkUserService.getEmailByCn(username, password,worker);//得到领用人邮箱
				NotifyPersonnelEmail notifyPersonnelEmail=new NotifyPersonnelEmail();
				if(email!=null){
					notifyPersonnelEmail.setNpenotifyemail(email);
				}
				notifyPersonnelEmail.setNpenotifytime(nextCheckTime);
				notifyPersonnelEmail.setNpestyle(2);
				notifyPersonnelEmail.setCfid(String.valueOf(eid));
				sendCheckUserService.insertCopySendEmail(notifyPersonnelEmail);
				/**
				 * 添加设备抄送邮箱
				 */
				List<NotifyPersonnelEmail> emails=checkUserService.selectNotifyEmailByCfid(String.valueOf(eid));
				Date notifyTime=nextCheckTime;//获得定时校验的下一次校验时间
				boolean insertflag=true;
				for(String strEmail:copySendEmails){
					boolean flag=true;
					for(NotifyPersonnelEmail notifyEmail:emails){
						if(notifyEmail.getNpenotifyemail().equals(strEmail)){//相同则不做处理
							flag=false;
						}
					}
					if(flag==true){//用户加的抄送和数据库的抄送邮箱不一样，往数据库里插入
						notifyPersonnelEmail=new NotifyPersonnelEmail();
						notifyPersonnelEmail.setCfid(String.valueOf(eid));
						notifyPersonnelEmail.setNpenotifyemail(strEmail);
						notifyPersonnelEmail.setNpestyle(3);
						notifyPersonnelEmail.setNpenotifytime(notifyTime);
						int resultOfInsertNotifyEmail=sendCheckUserService.insertCopySendEmail(notifyPersonnelEmail);
						if(resultOfInsertNotifyEmail<=0){
							insertflag=false;
						}
					}
				}
				if(insertflag=false){
					return CommonUtil.constructResponse(0,"插入抄送邮箱失败！",null);
				}else{
					return CommonUtil.constructResponse(EnumUtil.OK, "添加设备信息成功！", null);
				}
			}
		}else{
			return CommonUtil.constructResponse(0,"你不是设备总负责人，不能对设备进行添加操作！",null);
		}
	}
	
	@RequestMapping("/updateEquipment")
	@ResponseBody
	public JSONObject updateEquipment(HttpSession session,Equipment equipment,
			@DateTimeFormat(pattern="yyyy-MM-dd") Date date,String[]copySendEmails)
			throws Exception{
		Integer eid=equipment.getEid();
		Integer cycle=equipment.getEcheckcycle();//天数
		String worker=equipment.getEworker();//负责人
		Integer ectid=checkUserService.selectEctidByEid(eid);//得到最新ectid
		User user = (User)session.getAttribute("user");
		if(user.getPager().equals("1")){//设备负责人
			int resultOfUpdateEquipment=checkUserService.updateEquipment(equipment);
			if(resultOfUpdateEquipment<=0){
				return CommonUtil.constructResponse(0,"更新设备信息失败！",null);
			}else{
				EquipmentCheckTime equipmentCheckTime=checkUserService.selectEquipmentCheckTimeByEctid(ectid);
				Date lastCheckTime=equipmentCheckTime.getEctime();//上次设备校验时间
				Calendar calendar=Calendar.getInstance();
				calendar.setTime(lastCheckTime);
				calendar.add(Calendar.DATE,cycle);
				Date nextCheckTime=calendar.getTime();//得到设备下次检验时间
				/**
				 * 修改下一次校验时间，修改定时发送邮箱的邮箱和时间
				 */
				if(date!=null){
					calendar=Calendar.getInstance();
					calendar.setTime(date);//上一次校验时间
					calendar.add(Calendar.DATE,cycle);
					nextCheckTime=calendar.getTime();//得到设备下次检验时间
				}
				//根据修改的周期更改最新校验时间
				checkUserService.updateEquipmentLastCheckTime(lastCheckTime, nextCheckTime, eid);
				String username=(String) session.getAttribute("username");
				String password=(String) session.getAttribute("password");
				String email=checkUserService.getEmailByCn(username, password,worker);//得到领用人邮箱
				NotifyPersonnelEmail notifyPersonnelEmail=new NotifyPersonnelEmail();
				if(email!=null){
					notifyPersonnelEmail.setNpenotifyemail(email);
				}
				notifyPersonnelEmail.setNpenotifytime(nextCheckTime);
				notifyPersonnelEmail.setNpestyle(2);
				notifyPersonnelEmail.setCfid(String.valueOf(eid));
				checkUserService.updateNotifyPersonalEmailByCfid(notifyPersonnelEmail);
				notifyPersonnelEmail.setNpestyle(3);
				notifyPersonnelEmail.setNpenotifyemail(null);
				checkUserService.updateNotifyPersonalEmailByCfid(notifyPersonnelEmail);
				/**插入设备抄送邮箱
				 * 
				 */
				List<NotifyPersonnelEmail> emails=checkUserService.selectNotifyEmailByCfid(String.valueOf(eid));
				Date notifyTime=nextCheckTime;//获得定时校验的下一次校验时间
				boolean insertflag=true;
				for(String strEmail:copySendEmails){
					boolean flag=true;
					for(NotifyPersonnelEmail notifyEmail:emails){
						if(notifyEmail.getNpenotifyemail().equals(strEmail)){//相同则不做处理
							flag=false;
						}
					}
					if(flag==true){//用户加的抄送和数据库的抄送邮箱不一样，往数据库里插入
						notifyPersonnelEmail=new NotifyPersonnelEmail();
						notifyPersonnelEmail.setCfid(String.valueOf(eid));
						notifyPersonnelEmail.setNpenotifyemail(strEmail);
						notifyPersonnelEmail.setNpestyle(3);
						notifyPersonnelEmail.setNpenotifytime(notifyTime);
						int resultOfInsertNotifyEmail=sendCheckUserService.insertCopySendEmail(notifyPersonnelEmail);
						if(resultOfInsertNotifyEmail<=0){
							insertflag=false;
						}
					}
				}
				if(insertflag=false){
					return CommonUtil.constructResponse(0,"插入抄送邮箱失败！",null);
				}else{
					return CommonUtil.constructResponse(EnumUtil.OK, "更新设备信息成功！", null);
				}	
			}
		}else{
			return CommonUtil.constructResponse(0,"你不是设备总负责人，不能对设备进行更改操作！",null);
		}
	}
	
	@RequestMapping("/deleteEquipment")
	@ResponseBody
	public JSONObject deleteEquipment(Integer eid,HttpSession session)
			throws Exception{
		User user = (User)session.getAttribute("user");
		if(user.getPager().equals("1")){//设备负责人
			int resultOfDeleteEquipment=checkUserService.deleteEquipmentByEid(eid);
			if(resultOfDeleteEquipment<=0){
				return CommonUtil.constructResponse(0,"删除设备信息失败！",null);
			}else{
				checkUserService.deleteEquipmentCheckTimeByEid(eid);
				checkUserService.deleteCopyEmailsByCfidAndStyle(String.valueOf(eid));
				return CommonUtil.constructResponse(EnumUtil.OK, "删除设备信息成功！", null);
			}	
		}else{
			return CommonUtil.constructResponse(0,"你不是设备总负责人，不能对设备进行删除操作！",null);
		}
	}
	
	@RequestMapping("/equipmentInfo")
	@ResponseBody
	public JSONObject equipmentInfo(Integer cid,Integer lid,String eworker,String ename,Integer requestPageNum)
			throws Exception{
		List<Map<String,Object>>
		equipmentInfo=checkUserService.selectEquipmentDetailInfo
				(cid,lid,eworker, ename, requestPageNum);
		if(equipmentInfo.isEmpty()){
			return CommonUtil.constructResponse(0,"没有数据！",null);
		}else{
			Integer countEquipmentInfo=checkUserService.countEquipmentDetailInfo(cid, eworker, 
					ename);
			return CommonUtil.constructResponse(EnumUtil.OK,String.valueOf(countEquipmentInfo),equipmentInfo);
		}
	}
	
	@RequestMapping("/addEquipmentTime")
	@ResponseBody
	public JSONObject addEquipmentTime(HttpSession session,Integer eid,
			@DateTimeFormat(pattern="yyyy-MM-dd") Date ectime) throws Exception{
		Equipment equipment=checkUserService.selectEquipmentByEid(eid);
		Integer equipmentCycle=equipment.getEcheckcycle();
		String worker=equipment.getEworker();//得到设备负责人
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(ectime);
		calendar.add(Calendar.DATE,equipmentCycle);
		Date nextCheckTime=calendar.getTime();//得到设备下次检验时间
		EquipmentCheckTime equipmentCheckTime=new EquipmentCheckTime();
		equipmentCheckTime.setEid(eid);
		equipmentCheckTime.setEcnexttime(nextCheckTime);
		equipmentCheckTime.setEctime(ectime);
		User user = (User)session.getAttribute("user");
		if(user.getPager().equals("1")||user.getCn().equals(worker)){//设备负责人
			Integer resultOfInsertEquipmentCheckTime=checkUserService.
					insertEquipmentCheckTime(equipmentCheckTime);
			if(resultOfInsertEquipmentCheckTime<=0){
				return CommonUtil.constructResponse(0,"添加设备校验时间失败！",null);
			}else{
				String username=(String) session.getAttribute("username");
				String password=(String) session.getAttribute("password");
				String email=checkUserService.getEmailByCn(username, password,worker);//得到领用人邮箱
				NotifyPersonnelEmail notifyPersonnelEmail=new NotifyPersonnelEmail();
				if(email!=null){
					notifyPersonnelEmail.setNpenotifyemail(email);
				}
				notifyPersonnelEmail.setNpenotifytime(nextCheckTime);
				notifyPersonnelEmail.setNpestyle(2);
				notifyPersonnelEmail.setCfid(String.valueOf(eid));
				checkUserService.updateNotifyPersonalEmailByCfid(notifyPersonnelEmail);
				notifyPersonnelEmail.setNpestyle(3);
				notifyPersonnelEmail.setNpenotifyemail(null);
				checkUserService.updateNotifyPersonalEmailByCfid(notifyPersonnelEmail);
				return CommonUtil.constructResponse(EnumUtil.OK, "添加设备校验时间成功！",null);
			}
		}else{
			return CommonUtil.constructResponse(0, "您不是设备负责人，不能确认检测！",null);
		}
	}
	
	@RequestMapping("/myEquipment")
	@ResponseBody
	public JSONObject myEquipment(HttpSession session)
			throws Exception{
		User user=(User) session.getAttribute("user");
		String userName=user.getCn();
		List<Map<String,Object>> myEquipment=checkUserService.myEquipment(userName);
		if(myEquipment.isEmpty()){
			return CommonUtil.constructResponse(0,"没有数据！",null);
		}else{
			return CommonUtil.constructResponse(EnumUtil.OK, "我的设备信息",myEquipment);
		}
	}
	
	@RequestMapping("/submitCheckResult")
	@ResponseBody
	public JSONObject submitCheckResult(HttpServletRequest request,Integer cfStatus,
			String cfRemark,String cfId,@RequestParam(value = "cfReportFile", required = false) 
	        MultipartFile cfReportFile) throws Exception{
		/**
		 * 更新检测状态，发送邮箱(检测完)
		 */
		String path=null;
		if(cfReportFile!=null){
			String filename = cfReportFile.getOriginalFilename();//获取上传的文件名称
			if(filename!=null && !filename.isEmpty()){
				String root=request.getSession().getServletContext().getRealPath("/cfreportfile/");
				int index = filename.lastIndexOf("\\");
				if(index != -1) {
					filename = filename.substring(index+1);
				}
//				int hCode = filename.hashCode();
//				String hex = Integer.toHexString(hCode);
//				if(!hex.equals("0")){
//					File dirFile = new File(root, hex.charAt(0) + "/" + hex.charAt(1));
//					dirFile.mkdirs();
//					SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
//  	 	            String time=sdf.format(new Date());
  	 	            filename=cfId+"_"+filename;
					File destFile = new File(root, filename);
					destFile.mkdirs();
					cfReportFile.transferTo(destFile);
					path=filename;//数据库存的路径
//				}
			}
		}
		long ll=System.currentTimeMillis();
			int resultOfUpdateCfReport=checkUserService.submitCfReport(cfStatus, cfRemark, path,cfId);
			if(resultOfUpdateCfReport<=0){
				return CommonUtil.constructResponse(0,"更新检测结果失败！",null);
			}else{
				List<NotifyPersonnelEmail>emails=checkUserService.selectNotifyEmailByCfid(cfId);
				if(emails!=null&&!emails.isEmpty()){
					String[]Ccs=new String[emails.size()-1];
					int i=-1;
					String receiveEmail=null;
					for(NotifyPersonnelEmail notifyPersonnelEmail:emails){
						String email=notifyPersonnelEmail.getNpenotifyemail();
						if(i==-1){
							receiveEmail=email;
						}
						if(i!=-1){
							Ccs[i]=email;
						}
						i++;
					}
					CheckingForm checkingForm=sendCheckUserService.selectByPrimaryKey(cfId);
					Date sendCheckTime=checkingForm.getCftime();//得到送检时间
					String cfComponentId=checkingForm.getCfcomponentid();
					String userName=checkingForm.getCfcheckman();
					Integer claid=checkingForm.getClaid();//得到送检类型
					Classify classify=sendCheckUserService.selectClassifyNameByClaid(claid);
					String classifyName=classify.getCname();//得到送检类型名称
					Date currentTime=new Date();
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String time=sdf.format(currentTime);
			        String cftime=sdf.format(sendCheckTime);
			        String emailInfo=null;
			        if(cfStatus==3){
			        	emailInfo="通过";
			        }else if(cfStatus==4){
			        	emailInfo="部分通过";
			        }else{
			        	emailInfo="未通过";
			        }
					String mailContent="你的送检已送检完成！"+"\r\n\r\n送检单号："+cfId+"\r\n送检类型："+classifyName+"\r\n零件编号："+cfComponentId+"\r\n送检时间："+cftime+"\r\n检测状态："+emailInfo+"\r\n备注信息："+cfRemark+"\r\n检测人:"+userName+
							"\r\n检测结果录入时间："+time;
					Mail mail=new Mail(receiveEmail,"公司内部邮件",mailContent,Ccs);
					MailSender.sendMail(mail);
				}
				long l=System.currentTimeMillis();
				System.out.println((double)(l-ll)/1000);
				return CommonUtil.constructResponse(EnumUtil.OK, "更新检测结果成功！",null);
			}
	}
}
