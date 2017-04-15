package com.IVMS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.IVMS.model.CheckingClassify;

public interface CheckingClassifyDao {
    int deleteByCCid(Integer ccid);
    
    int deleteCheckingClassifyByClaid(Integer claid);
    
    int deleteCheckingClassifyByClaidAndCCname(Integer claid);

    int insert(CheckingClassify record);
    
    int insertCheckingClassify(@Param("claid")Integer claid,@Param("ccname")String ccname);

    int insertSelective(CheckingClassify record);

    CheckingClassify selectByPrimaryKey(Integer ccid);

    int updateByPrimaryKeySelective(CheckingClassify record);

    int updateByPrimaryKey(CheckingClassify record);
    
    List<CheckingClassify> selectCheckClassifyNameByClassifyId(Integer claid);
}