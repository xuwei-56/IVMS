package com.IVMS.dao;

import com.IVMS.model.CheckingToolsRecord;

public interface CheckingToolsRecordDao {
    int deleteByPrimaryKey(Integer ctrid);

    int insert(CheckingToolsRecord record);

    int insertSelective(CheckingToolsRecord record);

    CheckingToolsRecord selectByPrimaryKey(Integer ctrid);

    int updateByPrimaryKeySelective(CheckingToolsRecord record);

    int updateByPrimaryKey(CheckingToolsRecord record);
}