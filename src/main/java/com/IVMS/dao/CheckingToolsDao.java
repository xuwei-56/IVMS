package com.IVMS.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.IVMS.model.CheckingTools;

public interface CheckingToolsDao {
    int deleteCheckingToolsByCtidAndCtStatus(Integer ctid);

    int insert(CheckingTools record);

    int insertCheckingTools(CheckingTools checkingTools);

    CheckingTools selectCheckingToolByCtid(Integer ctid);
    
    CheckingTools judgeCtidIsAlreadyExist(Integer ctid);
    
    int selectCheckingToolCycleByCtid(Integer ctrid);
    
    int selectCycleByCtid(Integer ctid);

    int updateCheckingToolByCtid(CheckingTools checkingTools);
    
    int updateCheckingToolStatusByCtidAndCtStatus(@Param("ctstatus")Integer ctstatus,
    		@Param("ctid")Integer ctid);
    
    int updateCheckingToolStatusByCtid(Integer ctid);
    
    int updateCheckingToolTimeAndReceiverByCtid(@Param("ctid")Integer ctid,@Param("ctreceiver")
    String ctreceiver,@Param("ctusetime")Date ctusetime);

    int updateByPrimaryKey(CheckingTools record);
    
    List<CheckingTools> selectByReceiver(String receiver);
    
    Map<String,Object> myCheckingToolsDetails(@Param("ctid")Integer ctid,
    		@Param("isHaveCheckingToolsFile")Integer isHaveCheckingToolsFile);
    
    List<CheckingTools> checkingToolsInfo(@Param("CTUseItem")String CTUseItem,@Param("CTStatus")Integer CTStatus,
    		@Param("startRow")Integer startRow,@Param("numberOfPerPage")Integer numberOfPerPage);
    
    int countCheckingToolsInfo(@Param("CTUseItem")String CTUseItem,@Param("CTStatus")Integer CTStatus);
    
}