package com.IVMS.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.IVMS.dao.CheckingFormDao;
import com.IVMS.dao.CheckingToolsDao;
import com.IVMS.model.CheckingForm;
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
		int startRow=(requestPageNum-1)*20;
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


	public List<CheckingTools> checkingToolsInfo(String CTUseItem, Integer CTStatus,String CTUseLine,
    		String CTType,Integer requestPageNum) {
		int startRow=(requestPageNum-1)*20;
		int numberOfPerPage=20;
		int endIndex=requestPageNum*20;
		if(CTStatus==null&&CTUseItem.equals("0")&&CTUseLine.equals("0")&&CTType.equals("0")){
			System.out.println("进来了");
			List<CheckingTools>notSure=checkingToolsDao.checkingToolsInfoByCtStatus(6);
			List<CheckingTools>normal=checkingToolsDao.checkingToolsInfoByCtStatus(5);
			List<CheckingTools>fix=checkingToolsDao.checkingToolsInfoByCtStatus(2);
			List<CheckingTools>possession=checkingToolsDao.checkingToolsInfoByCtStatus(1);
			List<CheckingTools>notPossession=checkingToolsDao.checkingToolsInfoByCtStatus(0);
			List<CheckingTools>safe=checkingToolsDao.checkingToolsInfoByCtStatus(3);
			List<CheckingTools>notUse=checkingToolsDao.checkingToolsInfoByCtStatus(4);
			safe.addAll(notUse);
			notPossession.addAll(safe);
			possession.addAll(notPossession);
			fix.addAll(possession);
			normal.addAll(fix);
			notSure.addAll(normal);
			
			List<CheckingTools> indexOfAllResult=null;
			int lengthOfAllResult=notSure.size();//得到集合长度
			if(lengthOfAllResult>startRow){
				if(endIndex<=lengthOfAllResult){
					indexOfAllResult=normal.subList(startRow,endIndex);
				}else{
					indexOfAllResult=normal.subList(startRow,lengthOfAllResult);
				}
				return indexOfAllResult;
			}else{
				return indexOfAllResult;
			}
		}else{
			return checkingToolsDao.checkingToolsInfo(CTUseItem, CTStatus,CTUseLine,CTType,startRow, numberOfPerPage);
		}
	}


	public int countCheckingToolsInfo(String CTUseItem, Integer CTStatus,String CTUseLine,
			String CTType) {
		int count=checkingToolsDao.countCheckingToolsInfo(CTUseItem, CTStatus,CTUseLine,CTType);
//		System.out.println(count);
//		int page=(int) Math.ceil(count*1.0/20);
//		System.out.println(page);
		return count;
	}


	public int updateCfstatuByCfid(String cfid) {
		return checkingFormDao.updateCfstatuByCfid(cfid);
	}


	public int updateCheckingFormByCfid(CheckingForm record) {
		return checkingFormDao.updateCheckingFormByCfid(record);
	}

}
