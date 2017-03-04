package com.IVMS.dao;

import com.IVMS.model.EquipmentCheckTime;

public interface EquipmentCheckTimeDao {
    int deleteByPrimaryKey(Integer ectid);

    int insert(EquipmentCheckTime record);

    int insertSelective(EquipmentCheckTime record);

    EquipmentCheckTime selectByPrimaryKey(Integer ectid);

    int updateByPrimaryKeySelective(EquipmentCheckTime record);

    int updateByPrimaryKey(EquipmentCheckTime record);
}