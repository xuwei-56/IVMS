package com.IVMS.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
import com.IVMS.util.LdapUtil;

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
	/**
	 * 前端页面路径:登录页
	 */
	@RequestMapping("/login")//默认post和get请求方式都可以
	public void login(HttpServletResponse response, HttpServletRequest request,
			String username,String password) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw=response.getWriter();
		JSONObject jo=new JSONObject();
		User user=LdapUtil.getUserInfo(username,password);
		JSONObject ja=JSONObject.fromObject(user);//把ui对象转换为JSONObject对象
		if(user==null){
			jo.put("userInfo",1);
		}else{
			jo.put("userInfo",ja.toString());
		}
		pw.write(jo.toString());
		pw.flush();
		pw.close();
	}
	@RequestMapping("/getClassify")
	public void getClassify(HttpServletResponse response, HttpServletRequest request) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw=response.getWriter();
		JSONObject jo=new JSONObject();
		List<Classify> classify=classifyservice.selectClassify();
		JSONArray ja=JSONArray.fromObject(classify);
		jo.put("classifyInfo",ja.toString());
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
		JSONArray ja=JSONArray.fromObject(checkingClassify);
		jo.put("checkingClassifyInfo",ja.toString());
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
		JSONArray ja=JSONArray.fromObject(lines);
		jo.put("linesInfo",ja.toString());
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
		JSONArray ja=JSONArray.fromObject(cellNames);
		jo.put("cellNames",ja.toString());
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
		JSONArray ja=JSONArray.fromObject(projects);
		jo.put("projectsInfo",ja.toString());
		pw.write(jo.toString());
		pw.flush();
		pw.close();
	}
}