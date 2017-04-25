package com.IVMS.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.IVMS.model.NotifyPersonnelEmail;
import com.IVMS.service.CheckUserService;
import com.IVMS.service.SendCheckUserService;
import com.IVMS.util.CommonUtil;
import com.IVMS.util.EnumUtil;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/user")
public class EquipmentController {

	@Autowired
	CheckUserService checkUserService;
	@Autowired
	SendCheckUserService sendCheckUserService;
	
	
	@RequestMapping("/getEquipmentNotifyEmail")
	@ResponseBody
	public JSONObject getEquipmentNotifyEmail(Integer eid ){
		List<NotifyPersonnelEmail> emails=checkUserService.selectNotifyEmailByCfid(String.valueOf(eid));
		if(emails.isEmpty()){
			return CommonUtil.constructResponse(0,"此设备没有抄送邮箱！",null);
		}else{
			return CommonUtil.constructResponse(EnumUtil.OK, "设备抄送邮箱！", emails);
		}
	}
	
	@RequestMapping("/addEquipmentNotifyEmail")
	@ResponseBody
	public JSONObject addEquipmentNotifyEmail(Integer eid,String[]copySendEmails){		
		List<NotifyPersonnelEmail> emails=checkUserService.selectNotifyEmailByCfid(String.valueOf(eid));
		Date notifyTime=emails.get(0).getNpenotifytime();//获得定时校验的下一次校验时间
		boolean insertflag=true;
		for(String strEmail:copySendEmails){
			boolean flag=true;
			for(NotifyPersonnelEmail notifyPersonnelEmail:emails){
				if(notifyPersonnelEmail.getNpenotifyemail().equals(strEmail)){//相同则不做处理
					flag=false;
				}
			}
			if(flag==true){//用户加的抄送和数据库的抄送邮箱不一样，往数据库里插入
				NotifyPersonnelEmail notifyPersonnelEmail=new NotifyPersonnelEmail();
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
			return CommonUtil.constructResponse(EnumUtil.OK, "插入抄送邮箱成功！",null);
		}
	}
	
	@RequestMapping("/deleteEquipmentNotifyEmail")
	@ResponseBody
	public JSONObject deleteEquipmentNotifyEmail(Integer eid,String email){
		List<NotifyPersonnelEmail> selectStyleByCfidAndNotifyEmail=checkUserService.
				selectStyleByCfidAndNotifyEmail(String.valueOf(eid), email);
		if(selectStyleByCfidAndNotifyEmail.size()==1){
			if(selectStyleByCfidAndNotifyEmail.get(0).getNpestyle()==2){
				return CommonUtil.constructResponse(0,"此邮箱为设备负责人邮箱，不能删除",null);
			}else{
				Integer resultOfDeleteEquipmentNotifyEmail=checkUserService.
						deleteCopyEmailsByCfidAndEmail(String.valueOf(eid), email);
				if(resultOfDeleteEquipmentNotifyEmail<=0){
					return CommonUtil.constructResponse(0,"删除邮箱失败！",null);
				}
				else{
					return CommonUtil.constructResponse(EnumUtil.OK,"删除邮箱成功！",null);
				}
			}
		}else{
			for(NotifyPersonnelEmail notifyPersonnelEmail:selectStyleByCfidAndNotifyEmail){
				if(notifyPersonnelEmail.getNpestyle()==2){
					return CommonUtil.constructResponse(0,"此邮箱为设备负责人邮箱，不能删除",null);
				}else{
					Integer resultOfDeleteEquipmentNotifyEmail=checkUserService.
							deleteCopyEmailsByCfidAndEmail(String.valueOf(eid), email);
					if(resultOfDeleteEquipmentNotifyEmail<=0){
						return CommonUtil.constructResponse(0,"删除邮箱失败！",null);
					}
					else{
						return CommonUtil.constructResponse(EnumUtil.OK,"删除邮箱成功！",null);
					}
				}
			}
		}
		return null;
	}
}
