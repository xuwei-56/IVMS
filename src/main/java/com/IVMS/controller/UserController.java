package com.IVMS.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.IVMS.model.User;
import com.IVMS.util.LdapUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/user")
public class UserController {


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
}