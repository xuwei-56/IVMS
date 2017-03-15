﻿package com.IVMS.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.IVMS.model.Cell;
import com.IVMS.model.CheckingClassify;
import com.IVMS.model.Classify;
import com.IVMS.model.Line;
import com.IVMS.model.Project;
import com.IVMS.model.User;
import com.IVMS.service.CellService;
import com.IVMS.service.CheckingClassifyService;
import com.IVMS.service.ClassifyService;
import com.IVMS.service.LineService;
import com.IVMS.service.ProjectService;
import com.IVMS.util.CommonUtil;
import com.IVMS.util.LdapUtil;
import com.IVMS.util.EnumUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	ClassifyService classifyservice;
	@Autowired
	CheckingClassifyService checkingClassifyService;
	@Autowired
	LineService lineService;
	@Autowired
	CellService cellService;
	@Autowired
	ProjectService projectService;
	
	//获取日志类
	 private static Logger logger= LoggerFactory.getLogger(UserController.class);
	/**
	 * 前端页面路径:登录页
	 */
	@RequestMapping("/login")//默认post和get请求方式都可以
	@ResponseBody
	public JSONObject login(HttpSession session, String username,String password, String verifyCode) throws Exception {
		
		try {
			if(username == null || password == null||verifyCode ==null){
				logger.info("用户名密码验证码不能为空");
				return CommonUtil.constructResponse(EnumUtil.CAN_NOT_NULL, "请输入有效信息", null);
			}
			if(!verifyCode.equals(session.getAttribute("verifyCode"))){
				return CommonUtil.constructResponse(0, "验证码错误", null);
			}
			logger.info("开始验证用户 "+ username +" 是否存在");
			User user=LdapUtil.getUserInfo(username,password);
			if(user==null){
				return CommonUtil.constructResponse(EnumUtil.PASSWORD_ERROR, "账号或密码错误!", null);
			}else{
				session.setAttribute("user", user); //存入session保持登录信息
				return CommonUtil.constructResponse(EnumUtil.OK, "登录用户信息", user);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			return CommonUtil.constructExceptionJSON(EnumUtil.UNKOWN_ERROR, "未知错误，请联系管理员", null);
		}
	}
	@RequestMapping("/getClassify")
	public void getClassify(HttpServletResponse response, HttpServletRequest request) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw=response.getWriter();
		JSONObject jo=new JSONObject();
		List<Classify> classify=classifyservice.selectClassify();
		if(classify.isEmpty()){
			jo=CommonUtil.constructResponse(0,"没有数据！",null);
		}
		else{
			JSONArray ja=JSONArray.fromObject(classify);
			jo=CommonUtil.constructResponse(1,"送检分类信息", ja);
		}
		pw.write(jo.toString());
		pw.flush();
		pw.close();
	}
	@RequestMapping("/getCheckingClassify")
	public void getCheckingClassify(HttpServletResponse response, HttpServletRequest request,Integer ClassifyId) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw=response.getWriter();
		JSONObject jo=new JSONObject();
		List<CheckingClassify> checkingClassify=checkingClassifyService.selectCheckClassifyNameByClassifyId(ClassifyId);
		if(checkingClassify.isEmpty()){
			jo=CommonUtil.constructResponse(0,"没有数据！",null);
		}
		else{
			JSONArray ja=JSONArray.fromObject(checkingClassify);
			jo=CommonUtil.constructResponse(1,"检测分类信息", ja);
		}
		pw.write(jo.toString());
		pw.flush();
		pw.close();
	}
	@RequestMapping("/getLines")
	public void getLines(HttpServletResponse response, HttpServletRequest request) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw=response.getWriter();
		JSONObject jo=new JSONObject();
		List<Line>lines=lineService.selectLines();
		if(lines.isEmpty()){
			jo=CommonUtil.constructResponse(0,"没有数据！",null);
		}
		else{
			JSONArray ja=JSONArray.fromObject(lines);
			jo=CommonUtil.constructResponse(1,"生产线信息", ja);
		}
		pw.write(jo.toString());
		pw.flush();
		pw.close();
	}
	@RequestMapping("/getCellNames")
	public void getCellNames(HttpServletResponse response, HttpServletRequest request,
			Integer LineId) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw=response.getWriter();
		JSONObject jo=new JSONObject();
		List<Cell>cellNames=cellService.selectCellNameByLineId(LineId);
		if(cellNames.isEmpty()){
			jo=CommonUtil.constructResponse(0,"没有数据！",null);
		}
		else{
			JSONArray ja=JSONArray.fromObject(cellNames);
			jo=CommonUtil.constructResponse(1,"单元信息", ja);
		}
		pw.write(jo.toString());
		pw.flush();
		pw.close();
	}
	@RequestMapping("/getProjects")
	public void getProjects(HttpServletResponse response, HttpServletRequest request) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw=response.getWriter();
		JSONObject jo=new JSONObject();
		List<Project>projects=projectService.selectProjects();
		if(projects.isEmpty()){
			jo=CommonUtil.constructResponse(0,"没有数据！",null);
		}
		else{
			JSONArray ja=JSONArray.fromObject(projects);
			jo=CommonUtil.constructResponse(1,"项目信息", ja);
		}
		pw.write(jo.toString());
		pw.flush();
		pw.close();
	}
}