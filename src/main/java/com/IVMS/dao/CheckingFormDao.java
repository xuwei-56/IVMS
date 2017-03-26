package com.IVMS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.IVMS.model.CheckingForm;
import com.IVMS.model.CheckingFormCustom;

public interface CheckingFormDao {
	
    int deleteCheckingFormByPrimaryKey(String cfid);

    int insertCheckingForm(CheckingForm checkingForm);

    CheckingForm selectByPrimaryKey(String cfid);
    
    CheckingForm mySendCheckDetails(@Param("isHaveWareHouse")String isHaveWareHouse,
    		@Param("urgentStatus")Integer urgentStatus,@Param("cfid")String cfid);
    
    CheckingForm selectWidAndUrgentStatusByCfid(String cfid);

    int updateByPrimaryKeySelective(CheckingForm record);

    int updateByPrimaryKey(CheckingForm record);
    
    String selectMaxCfid(String cfid);
    
    List<CheckingFormCustom> selectByUserName(@Param("CFMoveP")String CFMoveP,
    		@Param("startRow")Integer startRow,@Param("numberOfPerPage")Integer numberOfPerPage,
    		@Param("ClaId")Integer ClaId,@Param("Pid")Integer Pid,@Param("CFId")String CFId);
    
    List<CheckingFormCustom> historyCheckingForm(@Param("startRow")Integer startRow,
    		@Param("numberOfPerPage")Integer numberOfPerPage,@Param("ClaId")Integer ClaId,
    		@Param("Pid")Integer Pid,@Param("CFId")String CFId);
    
    int countMySendCheck(@Param("CFMoveP")String CFMoveP,@Param("ClaId")Integer ClaId,
    		@Param("Pid")Integer Pid,@Param("CFId")String CFId);
    
    int countMyHistoryCheck(@Param("ClaId")Integer ClaId,@Param("Pid")Integer Pid,
    		@Param("CFId")String CFId);
    
    List<CheckingFormCustom> notPrintCheckingForm(@Param("urgentStatus")Integer urgentStatus);
    
    List<CheckingFormCustom> normalCheckingForm(@Param("urgentStatus")Integer urgentStatus);
    
    List<CheckingFormCustom> othersCheckingForm(@Param("urgentStatus")Integer urgentStatus);
    
    int updateCfstatuByCfid(String cfid);
    
}