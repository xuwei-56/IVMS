package com.IVMS.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.naming.ldap.LdapContext;

import org.springframework.stereotype.Service;

import com.IVMS.dao.CellDao;
import com.IVMS.dao.CheckingClassifyDao;
import com.IVMS.dao.CheckingFormDao;
import com.IVMS.dao.CheckingToolsDao;
import com.IVMS.dao.CheckingToolsFileDao;
import com.IVMS.dao.CheckingToolsRecordDao;
import com.IVMS.dao.ClassifyDao;
import com.IVMS.dao.LineDao;
import com.IVMS.dao.NotifyPersonnelEmailDao;
import com.IVMS.dao.ProjectDao;
import com.IVMS.dao.WarehouseDao;
import com.IVMS.model.CheckingTools;
import com.IVMS.model.CheckingToolsRecord;
import com.IVMS.model.NotifyPersonnelEmail;
import com.IVMS.model.Warehouse;
import com.IVMS.service.CheckUserService;
import com.IVMS.util.LdapUtil;

@Service("CheckUserServiceImpl")
public class CheckUserServiceImpl implements CheckUserService{

	@Resource
	private ProjectDao projectDao;
	@Resource
	private LineDao lineDao;
	@Resource
	private CheckingClassifyDao checkingClassifyDao;
	@Resource
	private ClassifyDao classifyDao;
	@Resource
	private CellDao cellDao;
	@Resource
	private CheckingFormDao checkingFormDao;
	@Resource
	private NotifyPersonnelEmailDao notifyPersonnelEmailDao;
	@Resource
	private CheckingToolsDao checkingToolsDao;
	@Resource
	private CheckingToolsFileDao checkingToolsFileDao;
	@Resource
	private CheckingToolsRecordDao checkingToolsRecordDao;
	@Resource
	private WarehouseDao warehouseDao;
	
	public int insertProject(String pName) {
		return projectDao.insertProject(pName);
	}

	public int insertLine(String lName) {
		return lineDao.insertLine(lName);
	}

	public int insertCheckingClassify(Integer claid, String ccname) {
		return checkingClassifyDao.insertCheckingClassify(claid, ccname);
	}

	public int insertClassify(String cname) {
		return classifyDao.insertClassify(cname);
	}

	public int selectMaxClaId() {
		return classifyDao.selectMaxClaId();
	}

	public int deleteByPid(Integer pid) {
		return projectDao.deleteByPid(pid);
	}

	public int deleteByLid(Integer lid) {
		return lineDao.deleteByLid(lid);
	}

	public int deleteByCCid(Integer ccid) {
		return checkingClassifyDao.deleteByCCid(ccid);
	}

	public int deleteByClaid(Integer claid) {
		return classifyDao.deleteByClaid(claid);
	}

	public int deleteCheckingClassifyByClaid(Integer claid) {
		return checkingClassifyDao.deleteCheckingClassifyByClaid(claid);
	}

	public int insertCell(Integer lid, String cname) {
		return cellDao.insertCell(lid, cname);
	}

	public int deleteCellByCid(Integer cid) {
		return cellDao.deleteCellByCid(cid);
	}

	public Integer selectMaxLid() {
		return lineDao.selectMaxLid();
	}

	public int deleteCellByLid(Integer lid) {
		return cellDao.deleteCellByLid(lid);
	}

	public String selectCfRemarkByCfid(String cfid) {
		return checkingFormDao.selectCfRemarkByCfid(cfid);
	}

	public int updateCfStatusToOnCheck(String cfid) {
		return checkingFormDao.updateCfStatusToOnCheck(cfid);
	}

	public List<NotifyPersonnelEmail> selectNotifyEmailByCfid(String cfid) {
		return notifyPersonnelEmailDao.selectNotifyEmailByCfid(cfid);
	}

	public int submitCfReport(Integer cfStatus, String cfRemark, String isHaveReportFile,String cfId) {
		return checkingFormDao.submitCfReport(cfStatus, cfRemark, isHaveReportFile,cfId);
	}

	public int deleteCheckingToolsByCtidAndCtStatus(Integer ctid) {
		return checkingToolsDao.deleteCheckingToolsByCtidAndCtStatus(ctid);
	}

	public int deleteCheckingToolsFileByCtid(Integer ctid) {
		return checkingToolsFileDao.deleteCheckingToolsFileByCtid(ctid);
	}

	public int deleteCheckingToolsFileByCtfid(Integer ctfid) {
		return checkingToolsFileDao.deleteCheckingToolsFileByCtfid(ctfid);
	}

	public int insertCheckingTools(CheckingTools checkingTools) {
		return checkingToolsDao.insertCheckingTools(checkingTools);
	}

	public int insertCheckingToolsFile(Integer ctid, String ctfname) {
		return checkingToolsFileDao.insertCheckingToolsFile(ctid, ctfname);
	}

	public int updateCheckingToolTimeAndReceiverByCtid(Integer ctid, String ctreceiver, Date ctusetime) {
		return checkingToolsDao.updateCheckingToolTimeAndReceiverByCtid(ctid, ctreceiver, ctusetime);
	}

	public int updateCheckingToolStatusByCtid(Integer ctid) {
		return checkingToolsDao.updateCheckingToolStatusByCtid(ctid);
	}

	public int updateCheckingToolResultByCtrid(CheckingToolsRecord checkingToolsRecord) {
		return checkingToolsRecordDao.updateCheckingToolResultByCtrid(checkingToolsRecord);
	}

	public int updateCheckingToolStatusByCtidAndCtStatus(Integer ctstatus,Integer ctid) {
		return checkingToolsDao.updateCheckingToolStatusByCtidAndCtStatus(ctstatus, ctid);
	}

	public int selectCheckingToolCycleByCtid(Integer ctrid) {
		return checkingToolsDao.selectCheckingToolCycleByCtid(ctrid);
	}

	public int updateAcceptAndAgreeByCtrid(Integer ctrid, Integer ctrcheckresult, Integer ctisagree) {
		return checkingToolsRecordDao.updateAcceptAndAgreeByCtrid(ctrid, ctrcheckresult, ctisagree);
	}

	public int insertCheckingToolRecordByCtUseTime(Integer ctid, String ctrmovecp,Date ctrchecktime, Date ctrchecknexttime) {
		return checkingToolsRecordDao.insertCheckingToolRecordByCtUseTime(ctid,ctrmovecp, ctrchecktime, ctrchecknexttime);
	}

	public int selectCycleByCtid(Integer ctid) {
		return checkingToolsDao.selectCycleByCtid(ctid);
	}

	public CheckingTools selectCheckingToolByCtid(Integer ctid) {
		return checkingToolsDao.selectCheckingToolByCtid(ctid);
	}

	public List<Map<String,Object>> selectEmailAndCheckNextTime() {
		return checkingToolsRecordDao.selectEmailAndCheckNextTime();
	}

	public int insertCheckingToolsRecord(CheckingToolsRecord record) {
		return checkingToolsRecordDao.insertCheckingToolsRecord(record);
	}

	public int updateCfCheckManByCfid(String cfcheckman, String cfid) {
		return checkingFormDao.updateCfCheckManByCfid(cfcheckman, cfid);
	}

	public CheckingTools judgeCtidIsAlreadyExist(Integer ctid) {
		return checkingToolsDao.judgeCtidIsAlreadyExist(ctid);
	}

	public int updateCheckingToolByCtid(CheckingTools checkingTools) {
		return checkingToolsDao.updateCheckingToolByCtid(checkingTools);
	}

	public List<Warehouse> selectWIdByClaid(Integer claid) {
		return warehouseDao.selectWIdByClaid(claid);
	}

	public int deleteWareHouseByWid(String wid) {
		return warehouseDao.deleteWareHouseByWid(wid);
	}

	public List<Warehouse> judgeWidIsAlreadyExist(String wid) {
		return warehouseDao.judgeWidIsAlreadyExist(wid);
	}

	public int insertWareHouse(Warehouse warehouse) {
		return warehouseDao.insertWareHouse(warehouse);
	}

	public String selectCTFNameByCTFId(Integer ctfid) {
		return checkingToolsFileDao.selectCTFNameByCTFId(ctfid);
	}

	public String getEmailByCn(String username, String password, String cn) {
		LdapContext ctx=LdapUtil.getLdapContext(username, password);
		String email=null;
		if(ctx!=null){
			 email=LdapUtil.getEmailByCn(ctx, cn);
		}
		return email;
	}

	public int updateCfStatusToCheckOver(String cfid) {
		return checkingFormDao.updateCfStatusToCheckOver(cfid);
	}

	public int updateCTRCheckNextTimeByCtrNum(Date ctrchecknexttime, String ctrnum) {
		return checkingToolsRecordDao.updateCTRCheckNextTimeByCtrNum(ctrchecknexttime, ctrnum);
	}

	public List<Map<String, Object>> selectCheckingToolRecords(Integer ctid) {
		return checkingToolsRecordDao.selectCheckingToolRecords(ctid);
	}

	public List<Map<String, Object>> selectNotifyEmailAndTime() {
		return notifyPersonnelEmailDao.selectNotifyEmailAndTime();
	}

	public List<Map<String,Object>> selectCTFNameByCTId(Integer ctid) {
		return checkingToolsFileDao.selectCTFNameByCTId(ctid);
	}

}
