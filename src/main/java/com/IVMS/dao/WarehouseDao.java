package com.IVMS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.IVMS.model.Warehouse;

public interface WarehouseDao {
    int deleteWareHouseByWid(String wid);

    int insert(Warehouse record);

    int insertWareHouse(Warehouse warehouse);
    
    List<Warehouse> judgeWidIsAlreadyExist(String wid);
    
    List<Warehouse> selectWIdByClaid(Integer claid);
    
    List<Warehouse> selectWareHouseByClaid(Integer classifyid);

    int updateByPrimaryKeySelective(Warehouse record);

    int updateByPrimaryKey(Warehouse record);
    
    int updateWStatusByWidAndClaid(@Param("wid")String wid,@Param("claid")Integer claid);
    
    
}