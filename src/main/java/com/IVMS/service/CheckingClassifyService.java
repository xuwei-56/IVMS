package com.IVMS.service;

import java.util.List;

import com.IVMS.model.CheckingClassify;

public interface CheckingClassifyService {

	List<CheckingClassify> selectCheckClassifyNameByClassifyId(Integer claid);
}
