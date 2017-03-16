package com.IVMS.dao;

import com.IVMS.model.CheckingForm;

public interface CheckingFormDao {
    int deleteByPrimaryKey(String cfid);

    int insert(CheckingForm checkingForm);

    int insertSelective(CheckingForm record);

    CheckingForm selectByPrimaryKey(String cfid);

    int updateByPrimaryKeySelective(CheckingForm record);

    int updateByPrimaryKey(CheckingForm record);
}