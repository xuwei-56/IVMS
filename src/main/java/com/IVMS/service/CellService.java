package com.IVMS.service;

import java.util.List;

import com.IVMS.model.Cell;

public interface CellService {

	List<Cell>selectCellNameByLineId(Integer lid);
	
}
