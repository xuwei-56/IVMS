package com.IVMS.dao;

import com.IVMS.model.Classify;

public interface ClassifyDao {
    int deleteByPrimaryKey(Integer claid);

    int insert(Classify record);

    int insertSelective(Classify record);

    Classify selectByPrimaryKey(Integer claid);

    int updateByPrimaryKeySelective(Classify record);

    int updateByPrimaryKey(Classify record);
}