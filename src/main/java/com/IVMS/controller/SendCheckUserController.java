package com.IVMS.controller;

import java.util.List;
import java.util.Set;

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
import com.IVMS.service.SendCheckUserService;
import com.IVMS.util.CommonUtil;
import com.IVMS.util.EnumUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/user")
public class SendCheckUserController {
	@Autowired
	SendCheckUserService sendCheckUserService;
	
	//获取日志类
	 private static Logger logger= LoggerFactory.getLogger(SendCheckUserController.class);
	 
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
			User user=sendCheckUserService.getLoginUserInfo(username, password);
			if(user==null){
				return CommonUtil.constructResponse(EnumUtil.PASSWORD_ERROR, "账号或密码错误 ", null);
			}else{
				session.setAttribute("username", username);
				session.setAttribute("password", password);
				session.setAttribute("user", user); //存入session保持登录信息
				return CommonUtil.constructResponse(EnumUtil.USER_LOGIN, "登录用户信息", user);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			return CommonUtil.constructExceptionJSON(EnumUtil.UNKOWN_ERROR, "未知错误，请联系管理员", null);
		}
	}
	
	@RequestMapping("/getSessionUser")
	@ResponseBody
	public JSONObject getSessionUser(HttpSession session){
		User user = (User)session.getAttribute("user");
		if (user == null) {
			return CommonUtil.constructResponse(EnumUtil.NOT_LOGIN, "未登录", null);
		} else {
			return CommonUtil.constructResponse(EnumUtil.OK, "返回登录人信息成功", user);
		}
	}
	
	
	@RequestMapping("/getDepartments")
	@ResponseBody
	public JSONObject getDepartments(HttpServletResponse response, HttpServletRequest request) 
			throws Exception {
		HttpSession session=request.getSession();
		String username=(String) session.getAttribute("username");
		String password=(String) session.getAttribute("password");
		Set<String>departmentsInfo=sendCheckUserService.getDepartmentsInfo(username, password);
		if(departmentsInfo==null){
			return CommonUtil.constructResponse(EnumUtil.CAN_NOT_NULL,"没有部门信息", null);
		}else{
			return CommonUtil.constructResponse(EnumUtil.OK, "公司部门信息", departmentsInfo);
		}
	}
	
	@RequestMapping("/getUserInfoByDepartment")
	@ResponseBody
	public JSONObject getUserInfoByDepartment(HttpServletResponse response, HttpServletRequest request,
			String department) 
			throws Exception {
		HttpSession session=request.getSession();
		String username=(String) session.getAttribute("username");
		String password=(String) session.getAttribute("password");
		List<User>userInfoByDepartment=sendCheckUserService.getUserInfoByDepartment(username, password, department);
		if(userInfoByDepartment==null){
			return CommonUtil.constructResponse(EnumUtil.CAN_NOT_NULL,"该部门没有人员信息", null);
		}else{
			return CommonUtil.constructResponse(EnumUtil.OK, "部门人员信息", userInfoByDepartment);
		}
	}
	
	@RequestMapping("/getClassify")
	@ResponseBody
	public JSONObject getClassify(HttpServletResponse response, HttpServletRequest request) throws Exception {
		List<Classify> classify=sendCheckUserService.selectClassify();
		if(classify.isEmpty()){
			return CommonUtil.constructResponse(0,"没有数据！",null);
		}
		else{
			return CommonUtil.constructResponse(1,"送检分类信息",classify);
		}
	}
	
	@RequestMapping("/getCheckingClassify")
	@ResponseBody
	public JSONObject getCheckingClassify(HttpServletResponse response, HttpServletRequest request,
			Integer ClassifyId) throws Exception {
		List<CheckingClassify> checkingClassify=sendCheckUserService.selectCheckClassifyNameByClassifyId(ClassifyId);
		if(checkingClassify.isEmpty()){
			return CommonUtil.constructResponse(0,"没有数据！",null);
		}
		else{
			return CommonUtil.constructResponse(1,"检测分类信息", checkingClassify);
		}
	}
	
	@RequestMapping("/getLines")
	@ResponseBody
	public JSONObject getLines(HttpServletResponse response, HttpServletRequest request) throws Exception {
		List<Line>lines=sendCheckUserService.selectLines();
		if(lines.isEmpty()){
			return CommonUtil.constructResponse(0,"没有数据！",null);
		}
		else{
			return CommonUtil.constructResponse(1,"生产线信息", lines);
		}
	}
	
	@RequestMapping("/getCellNames")
	@ResponseBody
	public JSONObject getCellNames(HttpServletResponse response, HttpServletRequest request,
			Integer LineId) throws Exception {
		List<Cell>cellNames=sendCheckUserService.selectCellNameByLineId(LineId);
		if(cellNames.isEmpty()){
			return CommonUtil.constructResponse(0,"没有数据！",null);
		}
		else{
			return CommonUtil.constructResponse(1,"单元信息", cellNames);
		}
	}
	
	@RequestMapping("/getProjects")
	@ResponseBody
	public JSONObject getProjects(HttpServletResponse response, HttpServletRequest request) throws Exception {
		List<Project>projects=sendCheckUserService.selectProjects();
		if(projects.isEmpty()){
			return CommonUtil.constructResponse(0,"没有数据！",null);
		}
		else{
			return CommonUtil.constructResponse(1,"项目信息", projects);
		}
	}
}