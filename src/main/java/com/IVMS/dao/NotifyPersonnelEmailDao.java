package com.IVMS.dao;

import java.util.List;

import com.IVMS.model.NotifyPersonnelEmail;

public interface NotifyPersonnelEmailDao {
    int deleteByPrimaryKey(Integer npeid);

    int insert(NotifyPersonnelEmail record);

    int insertCopySendEmail(NotifyPersonnelEmail email);

    NotifyPersonnelEmail selectByPrimaryKey(Integer npeid);

    int updateByPrimaryKeySelective(NotifyPersonnelEmail record);

    int updateByPrimaryKey(NotifyPersonnelEmail record);
    
    int deleteCopyEmailsByCfid(String cfid);
    
    List<NotifyPersonnelEmail> selectNotifyEmailByCfid(String cfid);
}