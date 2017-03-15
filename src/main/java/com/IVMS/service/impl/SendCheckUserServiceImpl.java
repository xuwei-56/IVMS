package com.IVMS.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.naming.NamingException;

import org.springframework.stereotype.Service;

import com.IVMS.dao.CellDao;
import com.IVMS.dao.CheckingClassifyDao;
import com.IVMS.dao.ClassifyDao;
import com.IVMS.dao.LineDao;
import com.IVMS.dao.ProjectDao;
import com.IVMS.model.Cell;
import com.IVMS.model.CheckingClassify;
import com.IVMS.model.Classify;
import com.IVMS.model.Line;
import com.IVMS.model.Project;
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

	public User getUserInfo(String username, String password) throws NamingException {
		return LdapUtil.getUserInfo(username,password);
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

}
