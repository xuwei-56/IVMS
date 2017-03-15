package com.IVMS.service;

import java.util.List;
import java.util.Set;

import javax.naming.ldap.LdapContext;

import com.IVMS.model.Cell;
import com.IVMS.model.CheckingClassify;
import com.IVMS.model.Classify;
import com.IVMS.model.Line;
import com.IVMS.model.Project;
import com.IVMS.model.User;
/**
 * 送检人提交送检单
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
}
