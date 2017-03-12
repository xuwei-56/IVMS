package com.IVMS.dao;

import java.util.List;

import com.IVMS.model.Cell;

public interface CellDao {
    int deleteByPrimaryKey(Integer cid);

    int insert(Cell record);

    int insertSelective(Cell record);

    Cell selectByPrimaryKey(Integer cid);

    int updateByPrimaryKeySelective(Cell record);

    int updateByPrimaryKey(Cell record);
    
    List<Cell>selectCellNameByLineId(Integer lid);
}