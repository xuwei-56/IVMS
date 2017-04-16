package com.IVMS.dao;

import java.util.List;

import com.IVMS.model.Classify;

public interface ClassifyDao {
    int deleteByClaid(Integer claid);

    int insert(Classify record);
    
    int insertClassify(String cname);

    int insertSelective(Classify record);

    Classify selectClassifyNameByClaid(Integer claid);
    
    int selectMaxClaId();
    
    int selectClaIdByCheckingTool();

    int updateByPrimaryKeySelective(Classify record);

    int updateByPrimaryKey(Classify record);
    
    List<Classify> selectClassify();
}