package com.IVMS.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.IVMS.model.CheckingFormCustom;
import com.IVMS.model.CheckingTools;
import com.IVMS.service.HomePageService;
import com.IVMS.util.CommonUtil;
import com.IVMS.util.EnumUtil;
import com.alibaba.fastjson.JSONObject;

@Controller
public class HomePageController {
	
	@Autowired
	HomePageService homePageService;
	
	@RequestMapping("/notPrintCheckingForm")
	@ResponseBody
	public JSONObject notPrintCheckingForm(){
		/**
		 * 先获得紧急的送检单，再获得不紧急的送检单，通过list加上去
		 */
		List<CheckingFormCustom>urgentCheckingForm=homePageService.notPrintCheckingForm(2);
		List<CheckingFormCustom>normalCheckingForm=homePageService.notPrintCheckingForm(1);
		if(urgentCheckingForm.isEmpty()&&normalCheckingForm.isEmpty()){
			return CommonUtil.constructResponse(0,"没有数据！",null);
		}else{
			urgentCheckingForm.addAll(normalCheckingForm);
			return CommonUtil.constructResponse(EnumUtil.OK, "未打印条目列表", urgentCheckingForm);
		}
	}
	
	@RequestMapping("/normalCheckingForm")
	@ResponseBody
	public JSONObject normalCheckingForm(){
		List<CheckingFormCustom>urgentCheckingForm=homePageService.normalCheckingForm(2);
		List<CheckingFormCustom>normalCheckingForm=homePageService.normalCheckingForm(1);
		if(urgentCheckingForm.isEmpty()&&normalCheckingForm.isEmpty()){
			return CommonUtil.constructResponse(0,"没有数据！",null);
		}else{
			urgentCheckingForm.addAll(normalCheckingForm);
			return CommonUtil.constructResponse(EnumUtil.OK, "正常过程送检物料信息", urgentCheckingForm);
		}
	}
	
	@RequestMapping("/othersCheckingForm")
	@ResponseBody
	public JSONObject othersCheckingForm(){
		List<CheckingFormCustom>urgentOthersCheckingForm=homePageService.othersCheckingForm(2);
		List<CheckingFormCustom>normalOthersCheckingForm=homePageService.othersCheckingForm(1);
		if(urgentOthersCheckingForm.isEmpty()&&normalOthersCheckingForm.isEmpty()){
			return CommonUtil.constructResponse(0,"没有数据！",null);
		}else{
			urgentOthersCheckingForm.addAll(normalOthersCheckingForm);
			return CommonUtil.constructResponse(EnumUtil.OK, "其他分类送检物料信息", urgentOthersCheckingForm);
		}
	}
	
	@RequestMapping("/historyCheckingForm")
	@ResponseBody
	public JSONObject historyCheckingForm(Integer requestPageNum,Integer claId,Integer pid,
			String cfid) throws Exception {
		int allPageNum=homePageService.countMyHistoryCheck(claId,pid,cfid);
		List<CheckingFormCustom>historyCheckingForm=homePageService.historyCheckingForm(requestPageNum,
				claId,pid,cfid);
		if(historyCheckingForm.isEmpty()){
			return CommonUtil.constructResponse(0,"没有数据！",null);
		}else{
			return CommonUtil.constructResponse(EnumUtil.OK,String.valueOf(allPageNum), historyCheckingForm);
		}
	}
	
	@RequestMapping("/checkingToolsInfo")
	@ResponseBody
	public JSONObject checkingToolsInfo(Integer requestPageNum,String CTUseItem,Integer CTStatus) throws Exception {
		int allPageNum=homePageService.countCheckingToolsInfo(CTUseItem, CTStatus);
		List<CheckingTools>checkingToolsInfo=homePageService.checkingToolsInfo(CTUseItem, 
				CTStatus, requestPageNum);
		if(checkingToolsInfo.isEmpty()){
			return CommonUtil.constructResponse(0,"没有数据！",null);
		}else{
			return CommonUtil.constructResponse(EnumUtil.OK,String.valueOf(allPageNum), checkingToolsInfo);
		}
	}
}
