package com.IVMS.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.IVMS.dao.CellDao;
import com.IVMS.model.Cell;
import com.IVMS.service.CellService;

@Service("CellserviceImpl")
public class CellServiceImpl implements CellService{
	
	@Resource
	private CellDao cellDao;
	
	public List<Cell> selectCellNameByLineId(Integer lid) {
		return cellDao.selectCellNameByLineId(lid);
	}


}
