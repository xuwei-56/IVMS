package com.IVMS.controller;

import com.IVMS.util.AuthCodeUtil;
import com.IVMS.util.CommonUtil;
import com.IVMS.util.EnumUtil;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.util.Random;

@Controller
public class PageController {


	/**
	 * 前端页面路径:登录页
	 */
	@RequestMapping("/")
	public String login() {
		System.out.println("hhhhh");
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
	 * 前端页面路径:数据展示页
	 */
	@RequestMapping("/data")
	public String data(){
		return "data";
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