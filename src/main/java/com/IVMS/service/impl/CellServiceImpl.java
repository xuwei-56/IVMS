package com.IVMS.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.IVMS.dao.CellDao;
import com.IVMS.model.Cell;
import com.IVMS.service.CellService;

@Service("CellserviceImpl")
public class CellServiceImpl implements CellService{
	
	@Resource
	private CellDao cellDao;

	public Cell getCell(int CId) {
		// TODO Auto-generated method stub
		return cellDao.selectByPrimaryKey(CId);
	}

}
