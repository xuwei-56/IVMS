package com.IVMS.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.IVMS.model.CheckingForm;
import com.IVMS.model.CheckingTools;
import com.IVMS.model.CheckingToolsRecord;
import com.IVMS.model.NotifyPersonnelEmail;

/**
 * 检测人service
 * @author as PiscesTong
 *
 */
public interface CheckUserService {
	
	int insertProject(String pName);
	
	int insertLine(String lName);
	
	int insertCheckingClassify(Integer claid,String ccname);
	
	int insertClassify(String cname);
	
    int insertCell(Integer lid,String cname);
	
	int selectMaxClaId();
	
	int deleteByPid(Integer pid);
	
	int deleteByLid(Integer lid);
	
	int deleteByCCid(Integer ccid);
	
	int deleteByClaid(Integer claid);
	
	int deleteCheckingClassifyByClaid(Integer claid);
	
	int deleteCellByCid(Integer cid);
	
	Integer selectMaxLid();
	
	int deleteCellByLid(Integer lid);
	
	String selectCfRemarkByCfid(String cfid);
	
	int updateCfStatusToOnCheck(String cfid);
	
	List<NotifyPersonnelEmail> selectNotifyEmailByCfid(String cfid);
	
	int submitCfReport(Integer cfStatus,String cfRemark,String isHaveReportFile,String cfId);
	
    int deleteCheckingToolsByCtidAndCtStatus(Integer ctid);
    
    int deleteCheckingToolsFileByCtid(Integer ctid);
    
    int deleteCheckingToolsFileByCtfid(Integer ctfid);
    
    int insertCheckingTools(CheckingTools checkingTools);
    
    int insertCheckingToolsFile(Integer ctid,String ctfname);
    
    int updateCheckingToolTimeAndReceiverByCtid(Integer ctid,String ctreceiver,Date ctusetime);
    
    int updateCheckingToolStatusByCtid(Integer ctid);
    
    int updateCheckingToolResultByCtrid(CheckingToolsRecord checkingToolsRecord);
    
    int updateCheckingToolStatusByCtrId(Integer ctstatus,Integer ctrid);
    
    int selectCheckingToolCycleByCtid(Integer ctrid);
    
    int updateAcceptAndAgreeByCtrid(Integer ctrid,Integer ctrcheckresult,Integer ctisagree);
    
    int insertCheckingToolRecordByCtUseTime(Integer ctid,String ctrmovecp,Date ctrchecktime,Date ctrchecknexttime);
    
    int selectCycleByCtid(Integer ctid);
    
    CheckingTools selectCheckingToolByCtid(Integer ctid);
	
    List<Map<String,Object>> selectEmailAndCheckNextTime();
}
