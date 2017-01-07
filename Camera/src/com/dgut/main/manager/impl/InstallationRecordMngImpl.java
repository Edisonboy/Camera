package com.dgut.main.manager.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.dgut.common.page.Pagination;
import com.dgut.main.dao.InstallationRecordDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.main.entity.InstallationRecord;
import com.dgut.main.exception.InstallationRecordException;
import com.dgut.main.manager.InstallationRecordMng;

@Service
@Transactional
public class InstallationRecordMngImpl implements InstallationRecordMng {

	@Autowired
	private InstallationRecordDao dao;
	
	
	@Transactional(readOnly=false)
	public void save(InstallationRecord bean) {
		dao.save(bean);
	}
	
	
	@Transactional(readOnly=false)
	public void delete(InstallationRecord bean) {
		dao.delete(bean);
	}
	
	
	@Transactional(readOnly=false)
	public void deleteList(List<InstallationRecord> beanlist) {
		for(InstallationRecord bean : beanlist){
			dao.delete(bean);
		}
	}


	@Transactional(readOnly=false)
	public void update(InstallationRecord bean) {
		dao.update(bean);
	}


	public InstallationRecord findById(Serializable id) {
		return dao.findById(id);
	}
	
	
	public List<InstallationRecord> findUserId(String id){
		return dao.findUserId(id);
	}
	
	
	public List<InstallationRecord> findByUserNo(String No){
		return dao.findByUserNo(No);
	}
	
	
	public List<InstallationRecord> getAll(){
		return dao.getAll();
	}
	
	
	public InstallationRecord findOne(String id){
		return dao.findOne(id);
	}

	/**
	 * 根据上传用户获取对应的记录
	 * @param list
	 * @return
	 */
	public List<InstallationRecord> findList(List<String> list){
		List<InstallationRecord> beanlist = new ArrayList<InstallationRecord>();
		for(String userNo : list){   
			List<InstallationRecord> bean = dao.findByUserNo(userNo);
			if(bean.size()>0){
				beanlist.add(bean.get(0));
			}else{
				throw new InstallationRecordException("bean not found");
			}
		}
		return beanlist;
	}
	
	
	public List<InstallationRecord> findByWorkSheetNo(String worksheetNo){
		return dao.findByWorkSheetNo(worksheetNo);
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
