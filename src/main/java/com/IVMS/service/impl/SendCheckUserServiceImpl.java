package com.IVMS.service.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.naming.ldap.LdapContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.IVMS.dao.CellDao;
import com.IVMS.dao.CheckingClassifyDao;
import com.IVMS.dao.CheckingFormDao;
import com.IVMS.dao.CheckingToolsDao;
import com.IVMS.dao.CheckingToolsFileDao;
import com.IVMS.dao.ClassifyDao;
import com.IVMS.dao.LineDao;
import com.IVMS.dao.NotifyPersonnelEmailDao;
import com.IVMS.dao.ProjectDao;
import com.IVMS.dao.UrgentFileDao;
import com.IVMS.dao.WarehouseDao;
import com.IVMS.model.Cell;
import com.IVMS.model.CheckingClassify;
import com.IVMS.model.CheckingForm;
import com.IVMS.model.CheckingFormCustom;
import com.IVMS.model.CheckingTools;
import com.IVMS.model.CheckingToolsFile;
import com.IVMS.model.Classify;
import com.IVMS.model.Line;
import com.IVMS.model.NotifyPersonnelEmail;
import com.IVMS.model.Project;
import com.IVMS.model.UrgentFile;
import com.IVMS.model.User;
import com.IVMS.model.Warehouse;
import com.IVMS.service.SendCheckUserService;
import com.IVMS.util.LdapUtil;


@Service("SendCheckUserServiceImpl")
public class SendCheckUserServiceImpl implements SendCheckUserService{
	
	private final Logger logger = Logger.getLogger(SendCheckUserServiceImpl.class);
	
	@Resource
	private CellDao cellDao;
	@Resource
	private CheckingClassifyDao checkingClassifyDao;
	@Resource
	private ClassifyDao classifyDao;
	@Resource
	private LineDao lineDao;
	@Resource
	private ProjectDao projectDao;
	@Resource
	private NotifyPersonnelEmailDao notifyPersonnelEmailDao;
	@Resource
	private CheckingFormDao checkingFormDao;
	@Resource
	private UrgentFileDao urgentFileDao;
	@Resource
	private CheckingToolsDao checkingToolsDao;
	@Resource
	private WarehouseDao warehouseDao;
	@Resource
	private CheckingToolsFileDao checkingToolsFileDao;

	
	public User getLoginUserInfo(String username, String password) {
		LdapContext ctx=LdapUtil.getLdapContext(username, password);
    	User user=null;
    	if(ctx!=null){
    		user=LdapUtil.getLoginUserInfo(ctx,username);
    	}
    	return user;
	}
	
	public Set<String> getDepartmentsInfo(String username, String password) {
		LdapContext ctx=LdapUtil.getLdapContext(username, password);
		Set<String>departmentsInfo=null;
		if(ctx!=null){
			 departmentsInfo=LdapUtil.getDepartmentsInfo(ctx);
		}
		return departmentsInfo;
	}
	
	public List<User> getUserInfoByDepartment(String username, String password,String department) {
		LdapContext ctx=LdapUtil.getLdapContext(username, password);
		List<User>userInfoByDepartment=null;
		if(ctx!=null){
			userInfoByDepartment=LdapUtil.getUserInfoByDepartment(ctx, department);
		}
		return userInfoByDepartment;
	}
	
	public List<Cell> selectCellNameByLineId(Integer lid) {
		return cellDao.selectCellNameByLineId(lid);
	}

	public List<CheckingClassify> selectCheckClassifyNameByClassifyId(Integer claid) {
		return checkingClassifyDao.selectCheckClassifyNameByClassifyId(claid);
	}

	public List<Classify> selectClassify() {
		return classifyDao.selectClassify();
	}

	public List<Line> selectLines() {
		return lineDao.selectLines();
	}

	public List<Project> selectProjects() {
		return projectDao.selectProjects();
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public int insertCopySendEmail(NotifyPersonnelEmail email) {
		try {
			return notifyPersonnelEmailDao.insertCopySendEmail(email);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public int insertCheckingForm(CheckingForm checkingForm) {
		try {
			return checkingFormDao.insertCheckingForm(checkingForm);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public int insertUrgentFile(UrgentFile urgentFile) {
		try {
			return urgentFileDao.insertUrgentFile(urgentFile);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}

	public CheckingForm selectByPrimaryKey(String cfid){
		return checkingFormDao.selectByPrimaryKey(cfid);
	}

	public String selectMaxCfid(String cfid){
		return checkingFormDao.selectMaxCfid(cfid);
	}

	public List<CheckingFormCustom> selectByUserName(String CFMoveP,Integer requestPageNum,
			Integer ClaId,Integer Pid,String cfid) {
		int startRow=(requestPageNum-1)*10;
		int numberOfPerPage=20;
		return checkingFormDao.selectByUserName(CFMoveP,startRow,numberOfPerPage,ClaId,Pid,cfid);
	}

	public int countMySendCheck(String CFMoveP,Integer ClaId,Integer Pid,String cfid) {
		int count=checkingFormDao.countMySendCheck(CFMoveP,ClaId,Pid,cfid);
		System.out.println(count);
		int page=(int) Math.ceil(count*1.0/20);
		System.out.println(page);
		return page;
	}

	public List<CheckingTools> selectByReceiver(String receiver) {
		return checkingToolsDao.selectByReceiver(receiver);
	}

	public int deleteUrgentFileByCfid(String cfid) {
		return urgentFileDao.deleteUrgentFileByCfid(cfid);
	}

	public int deleteCheckingFormByPrimaryKey(String cfid) {
		return checkingFormDao.deleteCheckingFormByPrimaryKey(cfid);
	}

	public int deleteCopyEmailsByCfid(String cfid) {
		return notifyPersonnelEmailDao.deleteCopyEmailsByCfid(cfid);
	}

	public List<Warehouse> selectWareHouseByClaid(Integer classifyid) {
		return warehouseDao.selectWareHouseByClaid(classifyid);
	}

	public CheckingForm selectWidAndUrgentStatusByCfid(String cfid) {
		return checkingFormDao.selectWidAndUrgentStatusByCfid(cfid);
	}

	public List<CheckingForm> mySendCheckDetails(String isHaveWareHouse, Integer urgentStatus, String cfid) {
		return checkingFormDao.mySendCheckDetails(isHaveWareHouse, urgentStatus, cfid);
	}

	public List<CheckingToolsFile> selectByCtid(Integer ctid) {
		return checkingToolsFileDao.selectByCtid(ctid);
	}

	public List<CheckingTools> myCheckingToolsDetails(Integer ctid, Integer isHaveCheckingToolsFile) {
		return checkingToolsDao.myCheckingToolsDetails(ctid, isHaveCheckingToolsFile);
	}
	
}
