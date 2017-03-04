package com.IVMS.dao;

import com.IVMS.model.CheckingClassify;

public interface CheckingClassifyDao {
    int deleteByPrimaryKey(Integer ccid);

    int insert(CheckingClassify record);

    int insertSelective(CheckingClassify record);

    CheckingClassify selectByPrimaryKey(Integer ccid);

    int updateByPrimaryKeySelective(CheckingClassify record);

    int updateByPrimaryKey(CheckingClassify record);
}