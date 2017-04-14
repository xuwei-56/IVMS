package com.IVMS.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.IVMS.model.EquipmentCheckTime;

public interface EquipmentCheckTimeDao {
    int deleteEquipmentCheckTimeByEid(Integer eid);

    int insert(EquipmentCheckTime record);

    int insertEquipmentCheckTime(EquipmentCheckTime equipmentCheckTime);

    EquipmentCheckTime selectEquipmentCheckTimeByEid(Integer eid);
    
    EquipmentCheckTime selectEquipmentCheckTimeByEctid(Integer ectid);

    int updateByPrimaryKeySelective(EquipmentCheckTime record);

    int updateEquipmentCheckTimeByEid(@Param("ectime")Date ectime,@Param("ecnexttime")Date ecnexttime,
    		@Param("eid")Integer eid);
    
    int updateEquipmentLastCheckTime(@Param("ectime")Date ectime,@Param("ecnexttime")Date ecnexttime,
    		@Param("eid")Integer eid);
    
    int selectEctidByEid(Integer eid);
}