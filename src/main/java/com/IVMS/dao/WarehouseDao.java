package com.IVMS.dao;

import java.util.List;

import com.IVMS.model.Warehouse;

public interface WarehouseDao {
    int deleteByPrimaryKey(String wid);

    int insert(Warehouse record);

    int insertSelective(Warehouse record);

    Warehouse selectByPrimaryKey(String wid);
    
    List<Warehouse> selectWareHouseByClaid(Integer classifyid);

    int updateByPrimaryKeySelective(Warehouse record);

    int updateByPrimaryKey(Warehouse record);
}