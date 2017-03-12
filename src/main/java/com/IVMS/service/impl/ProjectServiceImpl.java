package com.IVMS.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.IVMS.dao.ProjectDao;
import com.IVMS.model.Project;
import com.IVMS.service.ProjectService;

@Service("ProjectServiceImpl")
public class ProjectServiceImpl implements ProjectService {
	
	@Resource
	private ProjectDao projectDao;
	
	public List<Project> selectProjects() {
		return projectDao.selectProjects();
	}

}
