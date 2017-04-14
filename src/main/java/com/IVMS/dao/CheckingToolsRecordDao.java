package com.IVMS.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.IVMS.model.CheckingToolsRecord;

public interface CheckingToolsRecordDao {
    int deleteByPrimaryKey(Integer ctrid);

    int insertCheckingToolRecordByCtUseTime(@Param("ctid")Integer ctid,@Param("ctrmovecp")String ctrmovecp,
    		@Param("ctrchecktime")Date ctrchecktime,@Param("ctrchecknexttime")Date ctrchecknexttime);

    int insertCheckingToolsRecord(CheckingToolsRecord record);

    CheckingToolsRecord selectCheckingToolRecordByCtrid(Integer ctrid);

    int updateCheckingToolResultByCtrid(CheckingToolsRecord checkingToolsRecord);

    int updateByPrimaryKey(CheckingToolsRecord record);
    
    int updateCTRCheckNextTimeByCtrNum(@Param("ctrchecknexttime")Date ctrchecknexttime,
    		@Param("ctrnum")String ctrnum);
    
    int updateAcceptAndAgreeByCtrid(@Param("ctrid")Integer ctrid,@Param("ctrcheckresult")
    Integer ctrcheckresult,@Param("ctisagree")Integer ctisagree);
    
    int updateCTRCheckNextTimeAndCheckTimeByCtid(@Param("ctrchecktime")Date ctrchecktime,
    		@Param("ctrchecknexttime")Date ctrchecknexttime,@Param("ctid")Integer ctid);
    
    List<Map<String,Object>> selectEmailAndCheckNextTime();
    
    List<Map<String,Object>> selectCheckingToolRecords(Integer ctid);
    
    String selectCfIdByCtid(Integer ctid);
    
    Integer selectMaxCtrIdByCtid(Integer ctid);
}