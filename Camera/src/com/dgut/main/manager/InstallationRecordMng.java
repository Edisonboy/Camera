package com.dgut.main.manager;

import java.util.List;

import com.dgut.common.page.Pagination;
import com.dgut.main.entity.InstallationRecord;

public interface InstallationRecordMng extends BaseMng<InstallationRecord>{

	
	public List<InstallationRecord> findUserId(String id);
	
	public List<InstallationRecord> getAll();
	
	public InstallationRecord findOne(String id);
	
	public List<InstallationRecord> findByUserNo(String No);
	
	public List<InstallationRecord> findByWorkSheetNo(String worksheetNo);
	
	public Integer countAll();
	
	public Integer getCount(String worksheetNo);
	
	public Pagination getCurPage(String worksheetNo, int pageNo, int pageSize,String sortOrder);
	
	public void deleteList(List<InstallationRecord> beanlist);

	public List<InstallationRecord> findList(List<String> list);
}
