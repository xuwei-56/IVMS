package com.IVMS.dao;

import com.IVMS.model.CheckingTools;

public interface CheckingToolsDao {
    int deleteByPrimaryKey(Integer ctid);

    int insert(CheckingTools record);

    int insertSelective(CheckingTools record);

    CheckingTools selectByPrimaryKey(Integer ctid);

    int updateByPrimaryKeySelective(CheckingTools record);

    int updateByPrimaryKey(CheckingTools record);
}