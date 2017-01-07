package com.dgut.main.manager.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.common.page.Pagination;
import com.dgut.main.dao.WorkSheetDao;
import com.dgut.main.entity.WorkSheet;
import com.dgut.main.manager.WorkSheetMng;

@Service
@Transactional
public class WorkSheetMngImpl implements WorkSheetMng {

	@Autowired
	private WorkSheetDao dao;
	
	@Transactional(readOnly=false)
	public void save(WorkSheet bean) {
		dao.save(bean);
	}

	@Transactional(readOnly=false)
	public void delete(WorkSheet bean) {
		dao.delete(bean);
	}

	@Transactional(readOnly=false)
	public void update(WorkSheet bean) {
		dao.update(bean);
	}

	public WorkSheet findById(Serializable id) {
		return dao.findById(id);
	}
	
	public WorkSheet findByWorkSheetNo(String worksheetNo){
		return dao.findByWorkSheetNo(worksheetNo);
	}
	
	public List<WorkSheet> getAll(){
		return dao.getAll();
	}
	
	
	public Integer countAll(){
		return dao.countAll();
	}
	
	
	public Integer getCount(String worksheetNo){
		return dao.getCount(worksheetNo);
	}
	
	
	public Pagination getCurPage(String worksheetNo, int pageNo, int pageSize,String sortOrder){
		return dao.getCurPage(worksheetNo, pageNo, pageSize, sortOrder);
	}

}
