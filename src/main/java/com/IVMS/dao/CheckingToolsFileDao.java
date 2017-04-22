package com.IVMS.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.IVMS.model.CheckingToolsFile;

public interface CheckingToolsFileDao {
    int deleteCheckingToolsFileByCtid(String ctid);
    
    int deleteCheckingToolsFileByCtfid(Integer ctfid);

    int insert(CheckingToolsFile record);
    
    int insertCheckingToolsFile(@Param("ctid")String ctid,@Param("ctfname")String ctfname);

    int insertSelective(CheckingToolsFile record);
    
    String selectCTFNameByCTFId(Integer ctfid);
    
    List<Map<String,Object>> selectCTFNameByCTId(String ctid);

    List<CheckingToolsFile> selectCtFilesByCtid(String ctid);
    
    List<CheckingToolsFile>selectByCtid(String ctid);

    int updateByPrimaryKeySelective(CheckingToolsFile record);

    int updateByPrimaryKey(CheckingToolsFile record);
    
    int countCTFNameByCTId(String ctid);
}