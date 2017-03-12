package com.IVMS.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.IVMS.dao.CheckingClassifyDao;
import com.IVMS.model.CheckingClassify;
import com.IVMS.service.CheckingClassifyService;

@Service("CheckingClassifyServiceImpl")
public class CheckingClassifyServiceImpl implements CheckingClassifyService {

	@Resource
	private CheckingClassifyDao checkingClassifyDao;
	
	public List<CheckingClassify> selectCheckClassifyNameByClassifyId(Integer claid) {
		return checkingClassifyDao.selectCheckClassifyNameByClassifyId(claid);
	}

}
