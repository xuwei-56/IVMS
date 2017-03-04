package com.IVMS.dao;

import com.IVMS.model.Equipment;

public interface EquipmentDao {
    int deleteByPrimaryKey(Integer eid);

    int insert(Equipment record);

    int insertSelective(Equipment record);

    Equipment selectByPrimaryKey(Integer eid);

    int updateByPrimaryKeySelective(Equipment record);

    int updateByPrimaryKey(Equipment record);
}