package com.IVMS.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.IVMS.model.CheckingTools;

public interface CheckingToolsDao {
    int deleteCheckingToolsByCtidAndCtStatus(String ctid);

    int insert(CheckingTools record);

    int insertCheckingTools(CheckingTools checkingTools);

    CheckingTools selectCheckingToolByCtid(String ctid);
    
    CheckingTools judgeCtidIsAlreadyExist(String ctid);
    
    int selectCheckingToolCycleByCtid(Integer ctrid);
    
    int selectCycleByCtid(String ctid);

    int updateCheckingToolByCtid(CheckingTools checkingTools);
    
    int updateCheckingToolStatusByCtidAndCtStatus(@Param("ctstatus")Integer ctstatus,
    		@Param("ctid")String ctid);
    
    int updateCheckingToolStatusByCtid(String ctid);
    
    int updateCheckingToolTimeAndReceiverByCtid(@Param("ctid")String ctid,@Param("ctreceiver")
    String ctreceiver,@Param("ctusetime")Date ctusetime,@Param("ctuseitem")String ctuseitem,
    @Param("ctuseline")String ctuseline,@Param("ctusestation")String ctusestation);

    int updateByPrimaryKey(CheckingTools record);
    
    List<CheckingTools> selectByReceiver(String receiver);
    
    Map<String,Object> myCheckingToolsDetails(@Param("ctid")String ctid);
    
    List<CheckingTools>checkingToolsInfoByCtStatus(Integer CTStatus);
    
    List<CheckingTools> checkingToolsInfo(@Param("CTUseItem")String CTUseItem,@Param("CTStatus")Integer CTStatus,
    		@Param("CTUseLine")String CTUseLine,@Param("CTType")String CTType,
    		@Param("startRow")Integer startRow,@Param("numberOfPerPage")Integer numberOfPerPage);
    
    int countCheckingToolsInfo(@Param("CTUseItem")String CTUseItem,@Param("CTStatus")Integer CTStatus,
    		@Param("CTUseLine")String CTUseLine,@Param("CTType")String CTType);
    
    int updateCheckingToolReceiverByCtid(@Param("ctreceiver")String ctreceiver,
    		@Param("ctid")String ctid);
    
}