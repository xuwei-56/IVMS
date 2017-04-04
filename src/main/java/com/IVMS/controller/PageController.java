package com.IVMS.controller;

import java.util.Random;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.IVMS.util.CommonUtil;
import com.IVMS.util.EnumUtil;
import com.alibaba.fastjson.JSONObject;

@Controller
public class PageController {


	/**
	 * 前端页面路径:登录页
	 */
	@RequestMapping("/")
	public String login() {
		return "index";
	}
	/**
	 * 前端页面路径:登录页
	 */
	@RequestMapping("/login")
	public String login1() {
		return "login";
	}
	/**
	 * 前端页面路径:主页
	 */
	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	/**
	 * 前端页面路径:普通用户登陆首页
	 */
	@RequestMapping("/user_index")
	public String userIndex(){
		return "user/user_index";
	}
	/**
	 * 前端页面路径:普通用户展示
	 */
	@RequestMapping("/user_list")
	public String userList(){
		return "user/user_list";
	}
	
	/**
	 * 前端页面路径:检具详情展示
	 */
	@RequestMapping("/checktoolDetail")
	public String checktoolDetail(){
		return "checktoolDetail";
	}
	/**
	 * 前端页面路径:检测人员登录
	 */
	@RequestMapping("/admin_index")
	public String adminIndex(){
		return "admin_index";
	}
	/**
	 * 前端页面路径:检测人员检具管理
	 */
	@RequestMapping("/admin_tools")
	public String adminTools(){
		return "admin_tools";
	}
	/**
	 * 前端页面路径:检测人员分类管理
	 */
	@RequestMapping("/admin_utils")
	public String adminUtils(){
		return "admin_utils";
	}
	/**
	 * 获取图片验证码
	 * @return 图片流
	 */
	@RequestMapping(value="code/getAuthCode")
	@ResponseBody
	public JSONObject getPictureVerificationCode(HttpServletResponse response, HttpSession session,String type)throws Exception{
		if (type == null){
			return CommonUtil.constructResponse(EnumUtil.ERROR, "非法请求", null);
		}
		/*AuthCodeUtil authCodeUtil = AuthCodeUtil.Instance();
		String pictureVerificationCode = authCodeUtil.getString();
		ByteArrayInputStream image = authCodeUtil.getImage();
		session.setAttribute("verifyCode", pictureVerificationCode);
		OutputStream stream = response.getOutputStream();
		byte[] data = new byte[image.available()];
		image.read(data);
		stream.write(data);
		stream.flush();
		stream.close();*/
		
		// 生成随机类      
		Random random = new Random();
		// 取随机产生的认证码(6位数字)      
		String sRand="";      
		for (int i=0;i<4;i++){      
			String rand=String.valueOf(random.nextInt(10));      
			sRand+=rand;
		}
		session.setAttribute("verifyCode", sRand);
		
		return CommonUtil.constructResponse(EnumUtil.OK, "请求验证码成功", sRand);
	}

	/**
	 * 图片验证码验证接口
	 */
	@RequestMapping("code/checkAuthcode")
	@ResponseBody
	public JSONObject checkAuthcode(HttpSession session,String type,String code){
		if (type == null || "".equals(type) || code==null || "".equals(code))
			return CommonUtil.constructResponse(0,"参数错误",null);
		Object sessionCode = session.getAttribute(type);
		if (code.equals(sessionCode)){
			session.removeAttribute(type);
			return CommonUtil.constructResponse(1,null,null);
		}else {
			return CommonUtil.constructResponse(0,"验证码错误",null);
		}

	}
	
	
	
	/*
	 * 返回下载页面
	 */
	@RequestMapping("download")
	public String download(){
		return "download";
	}

}