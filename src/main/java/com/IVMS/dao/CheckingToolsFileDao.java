package com.IVMS.dao;

import java.util.List;

import com.IVMS.model.CheckingToolsFile;

public interface CheckingToolsFileDao {
    int deleteByPrimaryKey(Integer ctfid);

    int insert(CheckingToolsFile record);

    int insertSelective(CheckingToolsFile record);

    CheckingToolsFile selectByPrimaryKey(Integer ctfid);
    
    List<CheckingToolsFile>selectByCtid(Integer ctid);

    int updateByPrimaryKeySelective(CheckingToolsFile record);

    int updateByPrimaryKey(CheckingToolsFile record);
}