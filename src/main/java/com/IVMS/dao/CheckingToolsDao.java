package com.IVMS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.IVMS.model.CheckingTools;

public interface CheckingToolsDao {
    int deleteByPrimaryKey(Integer ctid);

    int insert(CheckingTools record);

    int insertSelective(CheckingTools record);

    CheckingTools selectByPrimaryKey(Integer ctid);

    int updateByPrimaryKeySelective(CheckingTools record);

    int updateByPrimaryKey(CheckingTools record);
    
    List<CheckingTools> selectByReceiver(String receiver);
    
    CheckingTools myCheckingToolsDetails(@Param("ctid")Integer ctid,
    		@Param("isHaveCheckingToolsFile")Integer isHaveCheckingToolsFile);
    
    List<CheckingTools> checkingToolsInfo(@Param("CTUseItem")String CTUseItem,@Param("CTStatus")Integer CTStatus,
    		@Param("startRow")Integer startRow,@Param("numberOfPerPage")Integer numberOfPerPage);
    
    int countCheckingToolsInfo(@Param("CTUseItem")String CTUseItem,@Param("CTStatus")Integer CTStatus);
    
}