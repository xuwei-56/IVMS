package com.IVMS.dao;

import java.util.List;
import java.util.Map;

import com.IVMS.model.NotifyPersonnelEmail;

public interface NotifyPersonnelEmailDao {
    int deleteByPrimaryKey(Integer npeid);

    int insert(NotifyPersonnelEmail record);

    int insertCopySendEmail(NotifyPersonnelEmail email);

    NotifyPersonnelEmail selectByPrimaryKey(Integer npeid);

    int updateNotifyPersonalEmailByCfid(NotifyPersonnelEmail notifyPersonnelEmail);

    int updateByPrimaryKey(NotifyPersonnelEmail record);
    
    int deleteCopyEmailsByCfid(String cfid);
    
    int deleteCopyEmailsByCfidAndStyle(String cfid);
    
    List<NotifyPersonnelEmail> selectNotifyEmailByCfid(String cfid);
    
    List<Map<String,Object>> selectNotifyEmailAndTime();
}