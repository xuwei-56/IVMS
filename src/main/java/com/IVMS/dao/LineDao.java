package com.IVMS.dao;

import java.util.List;

import com.IVMS.model.Line;

public interface LineDao {
    int deleteByPrimaryKey(Integer lid);

    int insert(Line record);

    int insertSelective(Line record);

    Line selectByPrimaryKey(Integer lid);

    int updateByPrimaryKeySelective(Line record);

    int updateByPrimaryKey(Line record);
    
    List<Line> selectLines();
    
}