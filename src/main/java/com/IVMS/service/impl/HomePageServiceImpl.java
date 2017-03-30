package com.IVMS.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.IVMS.dao.CheckingFormDao;
import com.IVMS.dao.CheckingToolsDao;
import com.IVMS.model.CheckingFormCustom;
import com.IVMS.model.CheckingTools;
import com.IVMS.service.HomePageService;

@Service("HomePageServiceImpl")
public class HomePageServiceImpl implements HomePageService {
	
	@Resource
	private CheckingFormDao checkingFormDao;
	@Resource
	private CheckingToolsDao checkingToolsDao;

	
	public List<CheckingFormCustom> notPrintCheckingForm(Integer urgentStatus) {
		return checkingFormDao.notPrintCheckingForm(urgentStatus);
	}


	public List<CheckingFormCustom> normalCheckingForm(Integer urgentStatus) {
		return checkingFormDao.normalCheckingForm(urgentStatus);
	}


	public List<CheckingFormCustom> othersCheckingForm(Integer urgentStatus) {
		return checkingFormDao.othersCheckingForm(urgentStatus);
	}


	public List<CheckingFormCustom> historyCheckingForm(Integer requestPageNum, 
			Integer ClaId,Integer Pid, String CFId) {
		int startRow=(requestPageNum-1)*10;
		int numberOfPerPage=20;
		return checkingFormDao.historyCheckingForm(startRow, numberOfPerPage, ClaId, Pid, CFId);
	}


	public int countMyHistoryCheck(Integer ClaId, Integer Pid, String CFId) {
		int count=checkingFormDao.countMyHistoryCheck(ClaId,Pid,CFId);
//		System.out.println(count);
//		int page=(int) Math.ceil(count*1.0/20);
//		System.out.println(page);
		return count;
	}


	public List<CheckingTools> checkingToolsInfo(String CTUseItem, Integer CTStatus,Integer requestPageNum) {
		int startRow=(requestPageNum-1)*10;
		int numberOfPerPage=20;
		return checkingToolsDao.checkingToolsInfo(CTUseItem, CTStatus, startRow, numberOfPerPage);
	}


	public int countCheckingToolsInfo(String CTUseItem, Integer CTStatus) {
		int count=checkingToolsDao.countCheckingToolsInfo(CTUseItem, CTStatus);
//		System.out.println(count);
//		int page=(int) Math.ceil(count*1.0/20);
//		System.out.println(page);
		return count;
	}


	public int updateCfstatuByCfid(String cfid) {
		return checkingFormDao.updateCfstatuByCfid(cfid);
	}

}
