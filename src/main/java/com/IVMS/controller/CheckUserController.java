package com.IVMS.controller;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.IVMS.service.CheckUserService;
import com.IVMS.util.CommonUtil;
import com.IVMS.util.EnumUtil;
import com.IVMS.util.MailSender;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/user")
public class CheckUserController {

	@Autowired
	CheckUserService checkUserService;
	
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
	public JSONObject updateCfStatusToOnCheck(String cfid){
		/**
		 * 更新检测状态为检测中，发送邮箱（开始检测）
		 */
		Integer resultOfUpdate=checkUserService.updateCfStatusToOnCheck(cfid);
		if(resultOfUpdate<=0){
			return CommonUtil.constructResponse(0,"更新状态失败！",null);
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
//			String[]Ccs={"allstarpeng@126.com"};
			Mail mail=new Mail(receiveEmail,"公司内部邮件","你的送检已开始检测",Ccs);
			MailSender.sendMail(mail);
			return CommonUtil.constructResponse(EnumUtil.OK, "更新状态成功！",null);
		}
	}
	
//	@RequestMapping("/updateCheckingToolStatus")
//	@ResponseBody
//	public JSONObject updateCheckingToolStatus(Integer ctid){
//		/**
//		 * 更新检测状态为检测中，发送邮箱（开始检测）
//		 */
//		Integer resultOfUpdate=checkUserService.updateCfStatusToOnCheck(cfid);
//		if(resultOfUpdate<=0){
//			return CommonUtil.constructResponse(0,"更新状态失败！",null);
//		}else{
//			List<NotifyPersonnelEmail>emails=checkUserService.selectNotifyEmailByCfid(cfid);
//			String[]Ccs=new String[emails.size()-1];
//			int i=-1;
//			String receiveEmail=null;
//			for(NotifyPersonnelEmail notifyPersonnelEmail:emails){
//				String email=notifyPersonnelEmail.getNpenotifyemail();
//				if(i==-1){
//					receiveEmail=email;
//				}
//				if(i!=-1){
//					Ccs[i]=email;
//				}
//				i++;
//			}
////			String[]Ccs={"allstarpeng@126.com"};
//			Mail mail=new Mail(receiveEmail,"公司内部邮件","你的送检已开始检测",Ccs);
//			MailSender.sendMail(mail);
//			return CommonUtil.constructResponse(EnumUtil.OK, "更新状态成功！",null);
//		}
//	}
	
	
	@RequestMapping("/deleteCheckingTool")
	@ResponseBody
	public JSONObject deleteCheckingTool(Integer ctid){
		Integer resultOfDeleteCheckingTool=checkUserService.deleteCheckingToolsByCtidAndCtStatus(ctid);
		if(resultOfDeleteCheckingTool<=0){
			return CommonUtil.constructResponse(0,"删除检具信息失败！",null);
		}else{
			checkUserService.deleteCheckingToolsFileByCtid(ctid);
			return CommonUtil.constructResponse(EnumUtil.OK, "删除检具信息成功！",null);
		}
	}
	
	@RequestMapping("/deleteCheckingToolFile")
	@ResponseBody
	public JSONObject deleteCheckingToolFile(Integer ctfid){
		Integer resultOfDeleteCheckingToolFile=checkUserService.deleteCheckingToolsFileByCtfid(ctfid);
		if(resultOfDeleteCheckingToolFile<=0){
			return CommonUtil.constructResponse(0,"删除检具附件失败！",null);
		}else{
			return CommonUtil.constructResponse(EnumUtil.OK, "删除检具附件成功！",null);
		}
	}
	
	@RequestMapping("/addCheckingToolInfo")
	@ResponseBody
	public JSONObject addCheckingToolInfo(HttpServletRequest request,CheckingTools checkingTools)
			throws Exception{
		Integer resultOfAddCheckingToolInfo=checkUserService.insertCheckingTools(checkingTools);//添加检具基本信息
		if(resultOfAddCheckingToolInfo<=0){
			return CommonUtil.constructResponse(0,"添加检具信息失败！",null);
		}else{
			return CommonUtil.constructResponse(EnumUtil.OK, "添加检具信息成功！",null);
		}
	}
	
	@RequestMapping("/addCheckingToolResult")
	@ResponseBody
	public JSONObject addCheckingToolResult(HttpServletRequest request,CheckingToolsRecord 
			checkingToolsRecord,Integer ctStatus)throws Exception{
		checkingToolsRecord.setCtrchecktime(new Date());
		Integer checkingToolCycle=checkUserService.selectCheckingToolCycleByCtid
				(checkingToolsRecord.getCtrid());
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH,checkingToolCycle);
		Date nextCheckTime=calendar.getTime();
		checkingToolsRecord.setCtrchecknexttime(nextCheckTime);
		Integer resultOfAddCheckingToolInfo=checkUserService.
				updateCheckingToolResultByCtrid(checkingToolsRecord);//更新检具记录结果
		if(resultOfAddCheckingToolInfo<=0){
			return CommonUtil.constructResponse(0,"添加检具检测记录失败！",null);
		}else{
			//更新检具状态
			checkUserService.updateCheckingToolStatusByCtrId(ctStatus, checkingToolsRecord.getCtrid());
			return CommonUtil.constructResponse(EnumUtil.OK, "添加检具检测记录成功！",null);
		}
	}
	
	@RequestMapping("/addCheckingToolReceiver")
	@ResponseBody
	public JSONObject addCheckingToolReceiver(Integer ctid, String ctreceiver)
			throws Exception{
		Integer resultOfAddCheckingToolReceiver=checkUserService.updateCheckingToolTimeAndReceiverByCtid
				(ctid,ctreceiver, new Date());
		if(resultOfAddCheckingToolReceiver<=0){
			return CommonUtil.constructResponse(0,"更新检具领用人失败！",null);
		}else{
			checkUserService.updateCheckingToolStatusByCtid(ctid);//更改检具状态为已领用
			/**
			 *  在checkingtoolrecord表中加入ctid和校验时间以及下次校验时间
			 */
			Integer cycle=checkUserService.selectCycleByCtid(ctid);
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.MONTH,cycle);
			Date nextCheckTime=calendar.getTime();
			CheckingTools checkingTool=checkUserService.selectCheckingToolByCtid(ctid);
			String receiver=checkingTool.getCtreceiver();//得到检具领用人
			checkUserService.insertCheckingToolRecordByCtUseTime(ctid,receiver,new Date(), nextCheckTime);
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
	
	@RequestMapping("/addCheckingToolFile")
	@ResponseBody
	public JSONObject addCheckingToolFile(HttpServletRequest request,Integer ctid,
			@RequestParam(value = "checkingToolFiles", required = false) MultipartFile checkingToolFiles)
			throws Exception{
		String path=null;
		Integer resultOfAddCheckingToolFile=null;
		if(checkingToolFiles!=null){
			String filename = checkingToolFiles.getOriginalFilename();//获取上传的文件名称
			if(filename!=null && !filename.isEmpty()){
				String root=request.getSession().getServletContext().getRealPath("/checkingtoolfile/");
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
					checkingToolFiles.transferTo(destFile);
					path="/"+hex.charAt(0) + "/" + hex.charAt(1)+"/"+filename;//数据库存的路径
					resultOfAddCheckingToolFile=checkUserService.insertCheckingToolsFile(ctid, path);
				}
			}
		}
		if(resultOfAddCheckingToolFile==null&&path==null){
			return CommonUtil.constructResponse(0,"添加检具附件失败！",null);
		}else{
			return CommonUtil.constructResponse(EnumUtil.OK, "添加检具附件成功！",null);
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
				return CommonUtil.constructResponse(EnumUtil.OK, "更新检测结果成功！",null);
			}
	}
}
