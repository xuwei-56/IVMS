package com.IVMS.dao;

import com.IVMS.model.NotifyPersonnelEmail;

public interface NotifyPersonnelEmailDao {
    int deleteByPrimaryKey(Integer npeid);

    int insert(NotifyPersonnelEmail record);

    int insertSelective(NotifyPersonnelEmail record);

    NotifyPersonnelEmail selectByPrimaryKey(Integer npeid);

    int updateByPrimaryKeySelective(NotifyPersonnelEmail record);

    int updateByPrimaryKey(NotifyPersonnelEmail record);
}