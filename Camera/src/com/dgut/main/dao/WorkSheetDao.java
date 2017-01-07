package com.dgut.main.dao;

import java.util.List;

import com.dgut.common.page.Pagination;
import com.dgut.main.entity.WorkSheet;

public interface WorkSheetDao extends BaseDao<WorkSheet>{

	public List<WorkSheet> getAll();
	
	public WorkSheet findByWorkSheetNo(String worksheetNo);
	
	public Integer countAll();
	
	public Integer getCount(String worksheetNo);
	
	public Pagination getCurPage(String worksheetNo, int pageNo, int pageSize,String sortOrder);
}
