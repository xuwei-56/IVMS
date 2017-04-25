package com.IVMS.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

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
    
    int deleteCopyEmailsByCfidAndEmail(@Param("cfid")String cfid,@Param("npenotifyemail")String npenotifyemail);
    
    List<NotifyPersonnelEmail> selectNotifyEmailByCfid(String cfid);
    
    List<Map<String,Object>> selectNotifyEmailAndTime();
    
    List<NotifyPersonnelEmail> selectStyleByCfidAndNotifyEmail(@Param("cfid")String cfid,@Param("npenotifyemail")String npenotifyemail);
}