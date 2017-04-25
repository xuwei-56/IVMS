package com.IVMS.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.ldap.LdapContext;

import org.apache.ibatis.annotations.Param;

import com.IVMS.model.Cell;
import com.IVMS.model.CheckingForm;
import com.IVMS.model.CheckingTools;
import com.IVMS.model.CheckingToolsFile;
import com.IVMS.model.CheckingToolsRecord;
import com.IVMS.model.Equipment;
import com.IVMS.model.EquipmentCheckTime;
import com.IVMS.model.NotifyPersonnelEmail;
import com.IVMS.model.Warehouse;
import com.IVMS.util.LdapUtil;

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
	
    int deleteCheckingToolsByCtidAndCtStatus(String ctid);
    
    int deleteCheckingToolsFileByCtid(String ctid);
    
    int deleteCheckingToolsFileByCtfid(Integer ctfid);
    
    int insertCheckingTools(CheckingTools checkingTools);
    
    int insertCheckingToolsFile(String ctid,String ctfname);
    
    int updateCheckingToolTimeAndReceiverByCtid(String ctid,String ctreceiver,Date ctusetime,
    		String ctuseitem,String ctuseline,String ctusestation);
    
    int updateCheckingToolStatusByCtid(String ctid);
    
    int updateCheckingToolResultByCtrid(CheckingToolsRecord checkingToolsRecord);
    
    int updateCheckingToolStatusByCtidAndCtStatus(Integer ctstatus,String ctid);
    
    int selectCheckingToolCycleByCtid(Integer ctrid);
    
    int updateAcceptAndAgreeByCtrid(Integer ctrid,Integer ctrcheckresult,Integer ctisagree);
    
    int insertCheckingToolRecordByCtUseTime(String ctid,String ctrmovecp,Date ctrchecktime,Date ctrchecknexttime);
    
    int selectCycleByCtid(String ctid);
    
    CheckingTools selectCheckingToolByCtid(String ctid);
	
    List<Map<String,Object>> selectEmailAndCheckNextTime();
    
    int insertCheckingToolsRecord(CheckingToolsRecord record);
    
    int updateCfCheckManByCfid(String cfcheckman,String cfid);
    
    CheckingTools judgeCtidIsAlreadyExist(String ctid);
    
    int updateCheckingToolByCtid(CheckingTools checkingTools);
    
    List<Warehouse> selectWIdByClaid(Integer claid);
    
    int deleteWareHouseByWid(String wid);
    
    List<Warehouse> judgeWidIsAlreadyExist(String wid);
    
    int insertWareHouse(Warehouse warehouse);
    
    String selectCTFNameByCTFId(Integer ctfid);
    
	String getEmailByCn(String username, String password,String cn);
	
	int updateCfStatusToCheckOver(String cfid);
	 
	int updateCTRCheckNextTimeByCtrNum(Date ctrchecknexttime,String ctrnum);
	
	List<Map<String,Object>> selectCheckingToolRecords(String ctid);
	
	List<Map<String,Object>> selectNotifyEmailAndTime();
	
	List<Map<String,Object>> selectCTFNameByCTId(String ctid);
	
	int insertEquipment(Equipment equipment);
	
	int updateEquipment(Equipment equipment);
	
	Equipment selectEquipmentByEid(Integer eid);
	
	EquipmentCheckTime selectEquipmentCheckTimeByEid(Integer eid);
	
	int insertEquipmentCheckTime(EquipmentCheckTime equipmentCheckTime);
	
	List<Map<String,Object>> selectEquipmentDetailInfo(Integer cid,Integer lid,String eworker,String ename,
			Integer requestPageNum);
	
    int countEquipmentDetailInfo(Integer cid,String eworker,String ename);
    
    int updateEquipmentCheckTimeByEid(Date ectime,Date ecnexttime,Integer eid);
    
    int updateEquipmentLastCheckTime(Date ectime,Date ecnexttime,Integer eid);
    
    List<Map<String,Object>> myEquipment(String eworker);
    
    int updateCheckingToolReceiverByCtid(String ctreceiver,String ctid);
    
    int deleteEquipmentByEid(Integer eid);
    
    int deleteEquipmentCheckTimeByEid(Integer eid);
    
    int countCTFNameByCTId(String ctid);
    
    String selectCfIdByCtid(String ctid);
    
    List<CheckingToolsFile> selectCtFilesByCtid(String ctid);
    
    Integer selectMaxCtrIdByCtid(String ctid);
    
    CheckingToolsRecord selectCheckingToolRecordByCtrid(Integer ctrid);
    
    int updateCTRCheckNextTimeAndCheckTimeByCtid(Date ctrchecktime,Date ctrchecknexttime,String ctid);
    
    int updateNotifyPersonalEmailByCfid(NotifyPersonnelEmail notifyPersonnelEmail);
    
    Integer selectMaxEid();
    
    int selectEctidByEid(Integer eid);
    
    EquipmentCheckTime selectEquipmentCheckTimeByEctid(Integer ectid);
    
    int deleteCopyEmailsByCfidAndStyle(String cfid);
    
    Cell selectCNameByCid(Integer cid);
    
    String selectLNameByCid(Integer cid);
    
    int deleteCopyEmailsByCfidAndEmail(String cfid,String npenotifyemail);
    
    List<NotifyPersonnelEmail> selectStyleByCfidAndNotifyEmail(String cfid,String npenotifyemail);
}
