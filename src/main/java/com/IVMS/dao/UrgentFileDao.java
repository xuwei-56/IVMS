package com.IVMS.dao;

import com.IVMS.model.UrgentFile;

public interface UrgentFileDao {
    int deleteByPrimaryKey(Integer ufid);

    int insert(UrgentFile record);

    int insertUrgentFile(UrgentFile urgentFile);

    UrgentFile selectByPrimaryKey(Integer ufid);

    int updateByPrimaryKeySelective(UrgentFile record);

    int updateByPrimaryKey(UrgentFile record);
}