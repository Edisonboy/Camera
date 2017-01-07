package com.dgut.main.manager;

import java.util.List;

import com.dgut.common.page.Pagination;
import com.dgut.main.entity.WorkSheet;


public interface WorkSheetMng extends BaseMng<WorkSheet>{

	public List<WorkSheet> getAll();
	
	public Integer countAll();
	
	public Integer getCount(String worksheetNo);
	
	public Pagination getCurPage(String worksheetNo, int pageNo, int pageSize,String sortOrder);
	
	public WorkSheet findByWorkSheetNo(String worksheetNo);
}
