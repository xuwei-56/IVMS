package com.IVMS.controller;

import java.io.File;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.IVMS.model.Cell;
import com.IVMS.model.CheckingClassify;
import com.IVMS.model.CheckingForm;
import com.IVMS.model.Classify;
import com.IVMS.model.Line;
import com.IVMS.model.NotifyPersonnelEmail;
import com.IVMS.model.Project;
import com.IVMS.model.UrgentFile;
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
	 private static Integer lastTime=0;
	 private static Integer startNumber=1;
	 
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
		try{
			HttpSession session=request.getSession();
			String username=(String) session.getAttribute("username");
			String password=(String) session.getAttribute("password");
			Set<String>departmentsInfo=sendCheckUserService.getDepartmentsInfo(username, password);
			if(departmentsInfo==null){
				return CommonUtil.constructResponse(EnumUtil.CAN_NOT_NULL,"没有部门信息", null);
			}else{
				return CommonUtil.constructResponse(EnumUtil.OK, "公司部门信息", departmentsInfo);
			}
		}catch(Exception e){
			logger.info(e.getMessage());
			return CommonUtil.constructExceptionJSON(EnumUtil.UNKOWN_ERROR, "未知错误，请联系管理员", null);
		}
	}
	
	@RequestMapping("/getUserInfoByDepartment")
	@ResponseBody
	public JSONObject getUserInfoByDepartment(HttpServletResponse response, HttpServletRequest request,
			String department) 
			throws Exception {
		try{
			HttpSession session=request.getSession();
			String username=(String) session.getAttribute("username");
			String password=(String) session.getAttribute("password");
			List<User>userInfoByDepartment=sendCheckUserService.getUserInfoByDepartment(username, password, department);
			if(userInfoByDepartment==null){
				return CommonUtil.constructResponse(EnumUtil.CAN_NOT_NULL,"该部门没有人员信息", null);
			}else{
				return CommonUtil.constructResponse(EnumUtil.OK, "部门人员信息", userInfoByDepartment);
			}
		}catch(Exception e){
			logger.info(e.getMessage());
			return CommonUtil.constructExceptionJSON(EnumUtil.UNKOWN_ERROR, "未知错误，请联系管理员", null);
		}
	}
	
	@RequestMapping("/getClassify")
	@ResponseBody
	public JSONObject getClassify(HttpServletResponse response, HttpServletRequest request) throws Exception {
		try{
			List<Classify> classify=sendCheckUserService.selectClassify();
			if(classify.isEmpty()){
				return CommonUtil.constructResponse(0,"没有数据！",null);
			}
			else{
				return CommonUtil.constructResponse(1,"送检分类信息",classify);
			}
		}catch(Exception e){
			logger.info(e.getMessage());
			return CommonUtil.constructExceptionJSON(EnumUtil.UNKOWN_ERROR, "未知错误，请联系管理员", null);
		}
	}
	
	@RequestMapping("/getCheckingClassify")
	@ResponseBody
	public JSONObject getCheckingClassify(HttpServletResponse response, HttpServletRequest request,
			Integer ClassifyId) throws Exception {
		try{
			List<CheckingClassify> checkingClassify=sendCheckUserService.selectCheckClassifyNameByClassifyId(ClassifyId);
			if(checkingClassify.isEmpty()){
				return CommonUtil.constructResponse(0,"没有数据！",null);
			}
			else{
				return CommonUtil.constructResponse(1,"检测分类信息", checkingClassify);
			}
		}catch(Exception e){
			logger.info(e.getMessage());
			return CommonUtil.constructExceptionJSON(EnumUtil.UNKOWN_ERROR, "未知错误，请联系管理员", null);
		}
	}
	
	@RequestMapping("/getLines")
	@ResponseBody
	public JSONObject getLines(HttpServletResponse response, HttpServletRequest request) throws Exception {
		try{
			List<Line>lines=sendCheckUserService.selectLines();
			if(lines.isEmpty()){
				return CommonUtil.constructResponse(0,"没有数据！",null);
			}
			else{
				return CommonUtil.constructResponse(1,"生产线信息", lines);
			}
		}catch(Exception e){
			logger.info(e.getMessage());
			return CommonUtil.constructExceptionJSON(EnumUtil.UNKOWN_ERROR, "未知错误，请联系管理员", null);
		}
	}
	
	@RequestMapping("/getCellNames")
	@ResponseBody
	public JSONObject getCellNames(HttpServletResponse response, HttpServletRequest request,
			Integer LineId) throws Exception {
		try{
			List<Cell>cellNames=sendCheckUserService.selectCellNameByLineId(LineId);
			if(cellNames.isEmpty()){
				return CommonUtil.constructResponse(0,"没有数据！",null);
			}
			else{
				return CommonUtil.constructResponse(1,"单元信息", cellNames);
			}
		}catch(Exception e){
			logger.info(e.getMessage());
			return CommonUtil.constructExceptionJSON(EnumUtil.UNKOWN_ERROR, "未知错误，请联系管理员", null);
		}
	}
	
	@RequestMapping("/getProjects")
	@ResponseBody
	public JSONObject getProjects(HttpServletResponse response, HttpServletRequest request) throws Exception {
		try{
			List<Project>projects=sendCheckUserService.selectProjects();
			if(projects.isEmpty()){
				return CommonUtil.constructResponse(0,"没有数据！",null);
			}
			else{
				return CommonUtil.constructResponse(1,"项目信息", projects);
			}
		}catch(Exception e){
			logger.info(e.getMessage());
			return CommonUtil.constructExceptionJSON(EnumUtil.UNKOWN_ERROR, "未知错误，请联系管理员", null);
		}
	}
	
	@RequestMapping("/addSendCheckInfo")
	@ResponseBody
	public JSONObject addSendCheckInfo(HttpServletResponse response, HttpServletRequest request,
			CheckingForm checkingForm,String[]copySendEmails,@RequestParam(value = "urgentfile", 
			required = false) MultipartFile urgentfile) 
			throws Exception {
		try{
			/**
			 * 根据时间推移生成不同检验单编号(时间每过一天尾号又从001开始)
			 */
		    NumberFormat nf = NumberFormat.getInstance();
	        //设置是否使用分组
	        nf.setGroupingUsed(false);
	        //设置最大整数位数
	        nf.setMaximumIntegerDigits(3);
	        //设置最小整数位数    
	        nf.setMinimumIntegerDigits(3);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
	        String time=sdf.format(new Date());
	        Integer currentTime=Integer.parseInt(time);
	        StringBuilder checkFormId=new StringBuilder();
	        Integer urgentstatu=checkingForm.getCfurgentstatus();
	        if(urgentstatu==0){//正常送检，不加急
	        	checkFormId=checkFormId.append(time).append("01");
	        }else{
	        	checkFormId=checkFormId.append(time).append("02");
	        }
	        if(currentTime>lastTime){
	        	startNumber=1;
	        	checkFormId.append(nf.format(startNumber));
	        	lastTime=currentTime;
	        	System.out.println(lastTime);
	        }else{
	        	checkFormId.append(nf.format(startNumber));
	        }
	    	startNumber++;
	        System.out.println(checkFormId);
	        /**
	         * 把数据添加进数据库
	         */
	        NotifyPersonnelEmail personnelEmail=null;
	        String sendCheckId=checkFormId.toString();//得到送检单号
	        boolean flag=true;
	        if(copySendEmails!=null){//有抄送邮箱就添加进数据库，没有就不加
	        	for(String email:copySendEmails){
	            	personnelEmail=new NotifyPersonnelEmail();
	            	personnelEmail.setCfid(sendCheckId);
	            	personnelEmail.setNpenotifyemail(email);
	            	int result=sendCheckUserService.insertCopySendEmail(personnelEmail);
	            	if(result<=0){
	            		flag=false;
	            	}
	            }
	        }
	        /**
	         * 把文件传输到指定路径，并设置对象参数，添加到数据库
	         */
	        String root =request.getServletContext().getRealPath("/urgentfile/");
	        String filename = urgentfile.getOriginalFilename();  
	        UrgentFile urgentFile=null;
	        if(filename!=null&&!filename.isEmpty()){
	        	int index = filename.lastIndexOf("\\");
	    		if(index != -1) {
	    			filename = filename.substring(index+1);
	    		}
	    		sdf=new SimpleDateFormat("yyyy-MM-dd HH时mm分ss秒");
	            time=sdf.format(new Date());
	            System.out.println(time);
	            filename=time.substring(11,17)+"_"+filename;
	            File dirFile = new File(root,time.substring(0,4) + "/" +time.substring(5,7)+ "/" +time.substring(8,10));
	            File destFile = new File(dirFile,filename);
	            System.out.println(destFile);//文件路径
	            if(!destFile.exists()){
	            	destFile.mkdirs();
	            }  
	            try {  
	            	urgentfile.transferTo(destFile);  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }
	            String path=destFile.toString();//文件路径
				System.out.println("path:"+path);
				String strOfPath=path.replace("\\","/");//  \\对\转义，把\换成/
				String itemName=request.getContextPath();
				int index1=strOfPath.indexOf(itemName);
				path=strOfPath.substring(index1);
				System.out.println("文件路径："+path);
				urgentFile=new UrgentFile();
				urgentFile.setCfid(sendCheckId);
				urgentFile.setUfname(path);
				int result=sendCheckUserService.insertUrgentFile(urgentFile);
				if(result<0){
					flag=false;
				}
	        }
	        checkingForm.setCfid(sendCheckId);
	        checkingForm.setCftime(new Date());
	        int result=sendCheckUserService.insert(checkingForm);
	        if(result<=0){
	        	flag=false;
	        }
	        if(flag==false){
	        	return CommonUtil.constructResponse(EnumUtil.SYSTEM_ERROR,"提交信息失败！",null);
	        }else{
	        	return CommonUtil.constructResponse(EnumUtil.OK,"提交信息成功！",null);
	        }
		}catch(Exception e){
			logger.info(e.getMessage());
			return CommonUtil.constructExceptionJSON(EnumUtil.UNKOWN_ERROR, "未知错误，请联系管理员", null);
		}
	}
}