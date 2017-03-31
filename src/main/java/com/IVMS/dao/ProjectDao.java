package com.IVMS.dao;

import java.util.List;

import com.IVMS.model.Project;

public interface ProjectDao {
    int deleteByPid(Integer pid);

    int insert(Project record);
    
    int insertProject(String pName);

    int insertSelective(Project record);

    Project selectByPrimaryKey(Integer pid);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);
    
    List<Project>selectProjects();
}