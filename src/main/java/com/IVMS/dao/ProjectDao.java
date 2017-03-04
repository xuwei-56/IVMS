package com.IVMS.dao;

import com.IVMS.model.Project;

public interface ProjectDao {
    int deleteByPrimaryKey(Integer pid);

    int insert(Project record);

    int insertSelective(Project record);

    Project selectByPrimaryKey(Integer pid);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);
}