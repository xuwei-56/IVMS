package com.IVMS.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.IVMS.model.EquipmentCheckTime;

public interface EquipmentCheckTimeDao {
    int deleteByPrimaryKey(Integer ectid);

    int insert(EquipmentCheckTime record);

    int insertEquipmentCheckTime(EquipmentCheckTime equipmentCheckTime);

    EquipmentCheckTime selectEquipmentCheckTimeByEid(Integer eid);

    int updateByPrimaryKeySelective(EquipmentCheckTime record);

    int updateEquipmentCheckTimeByEid(@Param("ectime")Date ectime,@Param("ecnexttime")Date ecnexttime,
    		@Param("eid")Integer eid);
}