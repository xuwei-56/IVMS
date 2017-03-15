package com.IVMS.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	/**
	 * 前端页面路径:登录页
	 */
	@RequestMapping("/login")//默认post和get请求方式都可以
	@ResponseBody
	public JSONObject login(HttpServletResponse response, HttpServletRequest request,
			String username,String password) throws Exception {
		HttpSession session=request.getSession();
		session.setAttribute("username", username);
		session.setAttribute("password", password);
		User user=sendCheckUserService.getLoginUserInfo(username, password);
		if(user==null){
			return CommonUtil.constructResponse(-1, "账号或密码错误 ", null);
		}else{
			return CommonUtil.constructResponse(1, "登录用户信息", user);
		}
	}
	
	@RequestMapping("/getDepartments")//默认post和get请求方式都可以
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
	
	@RequestMapping("/getUserInfoByDepartment")//默认post和get请求方式都可以
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