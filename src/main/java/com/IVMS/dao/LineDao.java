package com.IVMS.dao;

import java.util.List;

import com.IVMS.model.Line;

public interface LineDao {
    int deleteByLid(Integer lid);

    int insert(Line record);
    
    int insertLine(String lName);

    int insertSelective(Line record);

    Line selectByPrimaryKey(Integer lid);
    
    String selectLNameByCid(Integer cid);
    
    Integer selectMaxLid();

    int updateByPrimaryKeySelective(Line record);

    int updateByPrimaryKey(Line record);
    
    List<Line> selectLines();
    
}