package com.IVMS.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.IVMS.dao.ClassifyDao;
import com.IVMS.model.Classify;
import com.IVMS.service.ClassifyService;

@Service("ClassifyServiceImpl")
public class ClassifyServiceImpl implements ClassifyService {
	
	@Resource
	private ClassifyDao classifyDao;
	
	public List<Classify> selectClassify() {
		return classifyDao.selectClassify();
	}

}
