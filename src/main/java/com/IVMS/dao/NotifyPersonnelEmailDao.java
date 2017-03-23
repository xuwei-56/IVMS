package com.IVMS.dao;

import com.IVMS.model.NotifyPersonnelEmail;

public interface NotifyPersonnelEmailDao {
    int deleteByPrimaryKey(Integer npeid);

    int insert(NotifyPersonnelEmail record);

    int insertCopySendEmail(NotifyPersonnelEmail email);

    NotifyPersonnelEmail selectByPrimaryKey(Integer npeid);

    int updateByPrimaryKeySelective(NotifyPersonnelEmail record);

    int updateByPrimaryKey(NotifyPersonnelEmail record);
    
    int deleteCopyEmailsByCfid(String cfid);
}