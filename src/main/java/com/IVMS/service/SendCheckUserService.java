package com.IVMS.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.ldap.LdapContext;

import org.apache.ibatis.annotations.Param;

import javax.naming.NamingException;

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
/**
 * 送检人提交送检单,我的送检，我的检具
 * @author as PiscesTong
 *
 */
public interface SendCheckUserService {

	User getLoginUserInfo(String username,String password);
	
	Set<String> getDepartmentsInfo(String username, String password);
	
	List<User> getUserInfoByDepartment(String username, String password,String department);
		
	List<Cell>selectCellNameByLineId(Integer lid);
	
	List<CheckingClassify> selectCheckClassifyNameByClassifyId(Integer claid);
	
	List<Classify> selectClassify();
	
	List<Line> selectLines();
	
	List<Project>selectProjects();
	
	int insertCopySendEmail(NotifyPersonnelEmail email);
	
	int insertCheckingForm(CheckingForm checkingForm);
	
	int insertUrgentFile(UrgentFile urgentFile);
	
	CheckingForm selectByPrimaryKey(String cfid);
	
	String selectMaxCfid(String cfid);
	
	List<CheckingFormCustom> selectByUserName(String CFMoveP,Integer startRow,Integer ClaId,Integer Pid,String cfid);
	
	int countMySendCheck(String CFMoveP,Integer ClaId,Integer Pid,String cfid);
	
	List<CheckingTools> selectByReceiver(String receiver);
	
	int deleteUrgentFileByCfid(String cfid);
	
	int deleteCheckingFormByPrimaryKey(String cfid);
	
	int deleteCopyEmailsByCfid(String cfid);
	
	List<Warehouse> selectWareHouseByClaid(Integer classifyid);
	
	CheckingForm selectWidAndUrgentStatusByCfid(String cfid);
	
	CheckingForm mySendCheckDetails(String isHaveWareHouse,Integer urgentStatus,String cfid);
	
	List<CheckingToolsFile>selectByCtid(Integer ctid);
	
	Map<String,Object> myCheckingToolsDetails(Integer ctid,Integer isHaveCheckingToolsFile);
	
	int updateWStatusByWidAndClaid(String wid,Integer claid);
	
	int selectClaIdByCheckingTool();
	
	int insertCheckingToolsRecord(String cfid,String cfmovep,Integer cfcomponentid,Date cftime);
	
}
