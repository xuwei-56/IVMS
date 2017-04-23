package com.IVMS.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.IVMS.model.CheckingForm;
import com.IVMS.model.CheckingFormCustom;

public interface CheckingFormDao {
	
    int deleteCheckingFormByPrimaryKey(String cfid);
    
    int insertCheckingToolsRecord(@Param("cfid")String cfid,@Param("cfmovep")String cfmovep,
    		@Param("cfcomponentid")Integer cfcomponentid,@Param("cftime")Date cftime );

    int insertCheckingForm(CheckingForm checkingForm);

    CheckingForm selectByPrimaryKey(String cfid);
    
    String selectCfRemarkByCfid(String cfid);
    
    CheckingForm mySendCheckDetails(@Param("cfid")String cfid);
    
    CheckingForm selectWidAndUrgentStatusByCfid(String cfid);

    int updateCheckingFormByCfid(CheckingForm record);

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
    
    int updateCfStatusToOnCheck(String cfid);
    
    int updateCfStatusToCheckOver(String cfid);
    
    int updateCfCheckManByCfid(@Param("cfcheckman")String cfcheckman,@Param("cfid")String cfid);
    
    int submitCfReport(@Param("cfStatus")Integer cfStatus,@Param("cfRemark")String cfRemark,
    		@Param("isHaveReportFile")String isHaveReportFile,@Param("cfId")String cfId);
}