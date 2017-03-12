package com.IVMS.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.IVMS.dao.LineDao;
import com.IVMS.model.Line;
import com.IVMS.service.LineService;

@Service("LineServiceImpl")
public class LineServiceImpl implements LineService {

	@Resource
	private LineDao lineDao;
	
	public List<Line> selectLines() {
		return lineDao.selectLines();
	}

}
