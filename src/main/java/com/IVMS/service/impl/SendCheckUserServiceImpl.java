package com.IVMS.service.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.naming.ldap.LdapContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.IVMS.dao.CellDao;
import com.IVMS.dao.CheckingClassifyDao;
import com.IVMS.dao.CheckingFormDao;
import com.IVMS.dao.ClassifyDao;
import com.IVMS.dao.LineDao;
import com.IVMS.dao.NotifyPersonnelEmailDao;
import com.IVMS.dao.ProjectDao;
import com.IVMS.dao.UrgentFileDao;
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
import com.IVMS.util.LdapUtil;

@Service("SendCheckUserServiceImpl")
public class SendCheckUserServiceImpl implements SendCheckUserService{
	
	@Resource
	private CellDao cellDao;
	@Resource
	private CheckingClassifyDao checkingClassifyDao;
	@Resource
	private ClassifyDao classifyDao;
	@Resource
	private LineDao lineDao;
	@Resource
	private ProjectDao projectDao;
	@Resource
	private NotifyPersonnelEmailDao notifyPersonnelEmailDao;
	@Resource
	private CheckingFormDao checkingFormDao;
	@Resource
	private UrgentFileDao urgentFileDao;
	
	public User getLoginUserInfo(String username, String password) {
		LdapContext ctx=LdapUtil.getLdapContext(username, password);
    	User user=null;
    	if(ctx!=null){
    		user=LdapUtil.getLoginUserInfo(ctx,username);
    	}
    	return user;
	}
	
	public Set<String> getDepartmentsInfo(String username, String password) {
		LdapContext ctx=LdapUtil.getLdapContext(username, password);
		Set<String>departmentsInfo=null;
		if(ctx!=null){
			 departmentsInfo=LdapUtil.getDepartmentsInfo(ctx);
		}
		return departmentsInfo;
	}
	
	public List<User> getUserInfoByDepartment(String username, String password,String department) {
		LdapContext ctx=LdapUtil.getLdapContext(username, password);
		List<User>userInfoByDepartment=null;
		if(ctx!=null){
			userInfoByDepartment=LdapUtil.getUserInfoByDepartment(ctx, department);
		}
		return userInfoByDepartment;
	}
	
	public List<Cell> selectCellNameByLineId(Integer lid) {
		return cellDao.selectCellNameByLineId(lid);
	}

	public List<CheckingClassify> selectCheckClassifyNameByClassifyId(Integer claid) {
		return checkingClassifyDao.selectCheckClassifyNameByClassifyId(claid);
	}

	public List<Classify> selectClassify() {
		return classifyDao.selectClassify();
	}

	public List<Line> selectLines() {
		return lineDao.selectLines();
	}

	public List<Project> selectProjects() {
		return projectDao.selectProjects();
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public int insertCopySendEmail(NotifyPersonnelEmail email) {
		try {
			return notifyPersonnelEmailDao.insertCopySendEmail(email);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public int insert(CheckingForm checkingForm) {
		try {
			return checkingFormDao.insertSelective(checkingForm);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public int insertUrgentFile(UrgentFile urgentFile) {
		try {
			return urgentFileDao.insertUrgentFile(urgentFile);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}
	
}
