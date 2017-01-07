package com.dgut.main.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dgut.common.page.Pagination;
import com.dgut.main.entity.InstallationRecord;

public interface InstallationRecordDao extends BaseDao<InstallationRecord>{

	
	public List<InstallationRecord> findUserId(String id);
	
	public List<InstallationRecord> getAll();
	
	public InstallationRecord findOne(String id);
	
	public List<InstallationRecord> findByUserNo(String No);
	
	public List<InstallationRecord> findByWorkSheetNo(String worksheetNo);
	
	public Integer countAll();
	
	public Integer getCount(String worksheetNo);
	
	public Pagination getCurPage(String worksheetNo, int pageNo, int pageSize,String sortOrder);
	
}
