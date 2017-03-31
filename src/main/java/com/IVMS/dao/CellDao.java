package com.IVMS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.IVMS.model.Cell;

public interface CellDao {
    int deleteCellByCid(Integer cid);
    
    int deleteCellByLid(Integer lid);

    int insert(Cell record);

    int insertSelective(Cell record);
    
    int insertCell(@Param("lid")Integer lid,@Param("cname")String cname);

    Cell selectByPrimaryKey(Integer cid);

    int updateByPrimaryKeySelective(Cell record);

    int updateByPrimaryKey(Cell record);
    
    List<Cell>selectCellNameByLineId(Integer lid);
    
}