package com.IVMS.dao;

import com.IVMS.model.Warehouse;

public interface WarehouseDao {
    int deleteByPrimaryKey(String wid);

    int insert(Warehouse record);

    int insertSelective(Warehouse record);

    Warehouse selectByPrimaryKey(String wid);

    int updateByPrimaryKeySelective(Warehouse record);

    int updateByPrimaryKey(Warehouse record);
}