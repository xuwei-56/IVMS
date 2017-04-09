package com.IVMS.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.IVMS.model.CheckingToolsFile;

public interface CheckingToolsFileDao {
    int deleteCheckingToolsFileByCtid(Integer ctid);
    
    int deleteCheckingToolsFileByCtfid(Integer ctfid);

    int insert(CheckingToolsFile record);
    
    int insertCheckingToolsFile(@Param("ctid")Integer ctid,@Param("ctfname")String ctfname);

    int insertSelective(CheckingToolsFile record);
    
    String selectCTFNameByCTFId(Integer ctfid);
    
    List<Map<String,Object>> selectCTFNameByCTId(Integer ctid);

    List<CheckingToolsFile> selectCtFilesByCtid(Integer ctid);
    
    List<CheckingToolsFile>selectByCtid(Integer ctid);

    int updateByPrimaryKeySelective(CheckingToolsFile record);

    int updateByPrimaryKey(CheckingToolsFile record);
    
    int countCTFNameByCTId(Integer ctid);
}