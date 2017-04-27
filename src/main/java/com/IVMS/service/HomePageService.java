package com.IVMS.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.IVMS.model.CheckingForm;
import com.IVMS.model.CheckingFormCustom;
import com.IVMS.model.CheckingTools;

/**
 * 首页service
 * @author as PiscesTong
 *
 */
public interface HomePageService {
	
	List<CheckingFormCustom> notPrintCheckingForm(Integer urgentStatus);
	
	List<CheckingFormCustom> normalCheckingForm(Integer urgentStatus);
	
	List<CheckingFormCustom> othersCheckingForm(Integer urgentStatus);
	
    List<CheckingFormCustom> historyCheckingForm(Integer requestPageNum,
    		Integer ClaId,Integer Pid,String CFId);
    
    int countMyHistoryCheck(Integer ClaId,Integer Pid,String CFId);
    
    List<CheckingTools> checkingToolsInfo(String CTUseItem,Integer CTStatus,String CTUseLine,
    		String CTType,Integer requestPageNum);
    
    int countCheckingToolsInfo(String CTUseItem,Integer CTStatus,String CTUseLine,String CTType);
    
    int updateCfstatuByCfid(String cfid);
    
    int updateCheckingFormByCfid(CheckingForm record);
   
}
