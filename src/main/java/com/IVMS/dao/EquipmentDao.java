package com.IVMS.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.IVMS.model.Equipment;

public interface EquipmentDao {
    int deleteEquipmentByEid(Integer eid);

    int insert(Equipment record);

    int insertEquipment(Equipment equipment);

    Equipment selectEquipmentByEid(Integer eid);
    
    List<Map<String,Object>> selectEquipmentDetailInfo(@Param("cid")Integer cid,
    		@Param("eworker")String eworker,@Param("ename")String ename,@Param("startRow")Integer startRow, 
    		@Param("numberOfPerPage")Integer numberOfPerPage);
    
    int countEquipmentDetailInfo(@Param("cid")Integer cid,@Param("eworker")String eworker,
    		@Param("ename")String ename);

    int updateEquipment(Equipment equipment);

    int updateByPrimaryKey(Equipment record);
    
    List<Map<String,Object>> myEquipment(String eworker);
    
    Integer selectMaxEid();
}