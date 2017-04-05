package com.IVMS.controller;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.IVMS.model.CheckingTools;
import com.IVMS.model.CheckingToolsRecord;
import com.IVMS.model.Mail;
import com.IVMS.model.NotifyPersonnelEmail;
import com.IVMS.model.User;
import com.IVMS.model.Warehouse;
import com.IVMS.service.CheckUserService;
import com.IVMS.service.SendCheckUserService;
import com.IVMS.util.CommonUtil;
import com.IVMS.util.EnumUtil;
import com.IVMS.util.LdapUtil;
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
		checkUserService.insertCell(maxLid, "默认");
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
	public JSONObject updateCfStatusToOnCheck(HttpSession session,String cfid,String SCFComponentId,
			Integer ClaId){
		/**
		 * 更新检测状态为检测中，发送邮箱（开始检测）
		 */
		String chekingToolsEmail=null;
		Integer resultOfUpdate=checkUserService.updateCfStatusToOnCheck(cfid);
		if(resultOfUpdate<=0){
			return CommonUtil.constructResponse(0,"更新状态失败！",null);
		}else{
			User user=(User) session.getAttribute("user");
			String userName=user.getCn();//拿到检测人
			checkUserService.updateCfCheckManByCfid(userName, cfid);//更新送检单的检测人
			Integer claId=sendCheckUserService.selectClaIdByCheckingTool();//得到检具送检类型的主键
			if(ClaId==claId){
				CheckingTools checkingTools=checkUserService.selectCheckingToolByCtid(
						Integer.parseInt(SCFComponentId));
				String ctreceiver=checkingTools.getCtreceiver();
				String username=(String) session.getAttribute("username");
				String password=(String) session.getAttribute("password");
				chekingToolsEmail=checkUserService.getEmailByCn(username, password,ctreceiver);//得到领用人邮箱
				if(chekingToolsEmail!=null&&!chekingToolsEmail.isEmpty()){
					Mail mail=new Mail(chekingToolsEmail,"公司内部邮件","你的送检已开始检测",null);
					MailSender.sendMail(mail);
				}
			}else{
				List<NotifyPersonnelEmail>emails=checkUserService.selectNotifyEmailByCfid(cfid);
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
//				String[]Ccs={"allstarpeng@126.com"};
				Mail mail=new Mail(receiveEmail,"公司内部邮件","你的送检已开始检测",Ccs);
				MailSender.sendMail(mail);
			}
			return CommonUtil.constructResponse(EnumUtil.OK, "更新状态成功！",null);
		}
	}
	
	
	@RequestMapping("/deleteCheckingTool")
	@ResponseBody
	public JSONObject deleteCheckingTool(Integer ctid,HttpServletRequest request){
		Integer resultOfDeleteCheckingTool=checkUserService.deleteCheckingToolsByCtidAndCtStatus(ctid);
		if(resultOfDeleteCheckingTool<=0){
			return CommonUtil.constructResponse(0,"删除检具信息失败！",null);
		}else{
			checkUserService.deleteCheckingToolsFileByCtid(ctid);
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
			return CommonUtil.constructResponse(EnumUtil.OK, "删除检具信息成功！",null);
		}
	}
	
	@RequestMapping("/deleteCheckingToolFile")
	@ResponseBody
	public JSONObject deleteCheckingToolFile(Integer ctfid,HttpServletRequest request){
		Integer resultOfDeleteCheckingToolFile=checkUserService.deleteCheckingToolsFileByCtfid(ctfid);
		if(resultOfDeleteCheckingToolFile<=0){
			return CommonUtil.constructResponse(0,"删除检具附件失败！",null);
		}else{
			String path=checkUserService.selectCTFNameByCTFId(ctfid);
			String root=request.getSession().getServletContext().getRealPath("/checkingtoolfile/");
			root=root.replace("\\","/");//  \\对\转义，把\换成/
			File checkingToolFile=new File(root+path);
			checkingToolFile.delete();//删除检具附件
			return CommonUtil.constructResponse(EnumUtil.OK, "删除检具附件成功！",null);
		}
	}
	
	@RequestMapping("/addCheckingToolInfo")
	@ResponseBody
	public JSONObject addCheckingToolInfo(HttpServletRequest request,CheckingTools checkingTools,
			@RequestParam(value = "checkingToolFiles", required = false) MultipartFile[] checkingToolFiles)
			throws Exception{
		Integer ctid=checkingTools.getCtid();
		CheckingTools checkingtools=checkUserService.judgeCtidIsAlreadyExist(ctid);//判断检具是否已经存在
		if(checkingtools==null){//添加的检具不存在
			Integer resultOfAddCheckingToolInfo=checkUserService.insertCheckingTools(checkingTools);//添加检具基本信息
			if(resultOfAddCheckingToolInfo<=0){
				return CommonUtil.constructResponse(0,"添加检具信息失败！",null);
			}else{
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
		String cfid=checkingToolsRecord.getCtrnum();//送检单号
		Integer ctid=checkingToolsRecord.getCtid();//检具编号
		checkingToolsRecord.setCtrchecktime(new Date());//设置检具校验时间
		String emailInfo=null,email=null;
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
		}else{
			/*
			 * 检具正常或维修，录入下次校验时间,检具正常时修改送检单状态，检具维修时不修改送检单状态
			 */
			if(ctStatus==5){//正常
				Integer checkingToolCycle=checkUserService.selectCycleByCtid(ctid);//拿到检具校验周期
				Calendar calendar=Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.add(Calendar.MONTH,checkingToolCycle);
				Date nextCheckTime=calendar.getTime();
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
				notifyPersonnelEmail.setCfid(cfid);
				notifyPersonnelEmail.setNpenotifytime(nextCheckTime);
				notifyPersonnelEmail.setNpestyle(1);
				sendCheckUserService.insertCopySendEmail(notifyPersonnelEmail);
				checkUserService.updateCfStatusToCheckOver(cfid);
				emailInfo="正常";
			}else{
				emailInfo="维修中";
			}
		}
		checkingToolsRecord.setCtrremark(ctStatus);	
		Integer resultOfAddCheckingToolInfo=checkUserService.
				insertCheckingToolsRecord(checkingToolsRecord);//添加检具记录结果
		if(resultOfAddCheckingToolInfo<=0){
			return CommonUtil.constructResponse(0,"添加检具检测记录失败！",null);
		}else{
			//更新检具状态
			checkUserService.updateCheckingToolStatusByCtidAndCtStatus(ctStatus, ctid);
			/**
			 * 发送邮箱
			 */
			if(email!=null&&!email.isEmpty()){
				Mail mail=new Mail(email,"公司内部邮件","你的检具送检已检测完成,检测状态："+emailInfo,null);
				MailSender.sendMail(mail);
			}
			return CommonUtil.constructResponse(EnumUtil.OK, "添加检具检测记录成功！",null);
		}
	}
	
	@RequestMapping("/addCheckingToolReceiver")
	@ResponseBody
	public JSONObject addCheckingToolReceiver(HttpSession session,Integer ctid, String ctreceiver)
			throws Exception{
		Integer resultOfAddCheckingToolReceiver=checkUserService.updateCheckingToolTimeAndReceiverByCtid
				(ctid,ctreceiver, new Date());
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
			calendar.add(Calendar.MONTH,cycle);
			Date nextCheckTime=calendar.getTime();//得到检具下次检验时间
			String username=(String) session.getAttribute("username");
			String password=(String) session.getAttribute("password");
			String email=checkUserService.getEmailByCn(username, password,ctreceiver);//得到领用人邮箱
			NotifyPersonnelEmail notifyPersonnelEmail=new NotifyPersonnelEmail();
			if(email!=null){
				notifyPersonnelEmail.setNpenotifyemail(email);
			}
			notifyPersonnelEmail.setNpenotifytime(nextCheckTime);
			notifyPersonnelEmail.setNpestyle(1);
			sendCheckUserService.insertCopySendEmail(notifyPersonnelEmail);
			return CommonUtil.constructResponse(EnumUtil.OK, "更新检具领用人成功！",null);
		}
	}
	
	@RequestMapping("/updateAgreeAndAccept")
	@ResponseBody
	public JSONObject updateAgreeAndAccept(Integer ctrid, Integer ctracceptresult, Integer ctisagree)
			throws Exception{
		Integer resultOfUpdateAgreeAndAccept=checkUserService.updateAcceptAndAgreeByCtrid
				(ctrid, ctracceptresult, ctisagree);
		if(resultOfUpdateAgreeAndAccept<=0){
			return CommonUtil.constructResponse(0,"更新接收和确认信息失败！",null);
		}else{
			return CommonUtil.constructResponse(EnumUtil.OK, "更新接收和确认信息成功！",null);
		}
	}
	
	@RequestMapping("/updateCheckingToolStatus")
	@ResponseBody
	public JSONObject updateCheckingToolStatus(Integer ctid,HttpSession session,Integer ctStatus,
			String cfId) throws Exception{
		String email=null,emailInfo=null;
		if(ctStatus==5){//正常
			Integer cycle=checkUserService.selectCycleByCtid(ctid);
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.MONTH,cycle);
			Date nextCheckTime=calendar.getTime();//得到检具下次检验时间
			checkUserService.updateCTRCheckNextTimeByCtrNum(nextCheckTime, cfId);//更新检具下次校验时间
			/**
			 * 拿到检具领用人邮箱
			 */
			CheckingTools checkingTools=checkUserService.selectCheckingToolByCtid(ctid);
			String ctreceiver=checkingTools.getCtreceiver();
			String username=(String) session.getAttribute("username");
			String password=(String) session.getAttribute("password");
			email=checkUserService.getEmailByCn(username, password,ctreceiver);//得到领用人邮箱
			NotifyPersonnelEmail notifyPersonnelEmail=new NotifyPersonnelEmail();
			if(email!=null){
				notifyPersonnelEmail.setNpenotifyemail(email);
			}
			notifyPersonnelEmail.setCfid(cfId);
			notifyPersonnelEmail.setNpenotifytime(nextCheckTime);
			notifyPersonnelEmail.setNpestyle(1);
			sendCheckUserService.insertCopySendEmail(notifyPersonnelEmail);
		}else{
			if(ctStatus==3){
				emailInfo="封存";
			}else{
				emailInfo="报废";
			}
		}
		Integer resultOfUpdateCheckingToolStatus=checkUserService.
				updateCheckingToolStatusByCtidAndCtStatus(ctStatus, ctid);
		checkUserService.updateCfStatusToCheckOver(cfId);//修改送检单状态
		if(resultOfUpdateCheckingToolStatus<=0){
			return CommonUtil.constructResponse(0,"更新检具状态失败！",null);
		}else{
			if(email!=null&&!email.isEmpty()){
				Mail mail=new Mail(email,"公司内部邮件","你的检具送检经过维修后,检测状态为："+emailInfo,null);
				MailSender.sendMail(mail);
			}
			return CommonUtil.constructResponse(EnumUtil.OK, "更新检具状态成功！",null);
		}
	}
	
	@RequestMapping("/updateCheckingTool")
	@ResponseBody
	public JSONObject updateCheckingTool(CheckingTools checkingTools)
			throws Exception{
		Integer resultOfUpdateCheckingTool=checkUserService.updateCheckingToolByCtid(checkingTools);
		if(resultOfUpdateCheckingTool<=0){
			return CommonUtil.constructResponse(0,"更新检具信息失败！",null);
		}else{
			return CommonUtil.constructResponse(EnumUtil.OK, "更新检具信息成功！",null);
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
			Map<String,String> letters=new HashMap<String,String>();
			for(Warehouse warehouse:Wids){
				String wid=warehouse.getWid();
				wid=wid.substring(0,2);
				letters.put(wid, "wid");
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
				int hCode = filename.hashCode();
				String hex = Integer.toHexString(hCode);
				if(!hex.equals("0")){
					File dirFile = new File(root, hex.charAt(0) + "/" + hex.charAt(1));
					dirFile.mkdirs();
					SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
  	 	            String time=sdf.format(new Date());
  	 	            filename=time+"_"+filename;
					File destFile = new File(dirFile, filename);
					cfReportFile.transferTo(destFile);
					path="/"+hex.charAt(0) + "/" + hex.charAt(1)+"/"+filename;//数据库存的路径
				}
			}
		}
		
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
					Mail mail=new Mail(receiveEmail,"公司内部邮件","你的送检已检测完成",Ccs);
					MailSender.sendMail(mail);
				}
				return CommonUtil.constructResponse(EnumUtil.OK, "更新检测结果成功！",null);
			}
	}
}
