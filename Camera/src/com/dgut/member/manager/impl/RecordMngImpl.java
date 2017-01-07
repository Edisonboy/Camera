package com.dgut.member.manager.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.common.page.Pagination;
import com.dgut.member.dao.RecordDao;
import com.dgut.member.entity.Record;
import com.dgut.member.manager.RecordMng;

@Service
@Transactional
public class RecordMngImpl implements RecordMng {

	@Autowired
	private RecordDao dao;
	
	@Transactional(readOnly=false)
	public Record save(Record bean) {
		return dao.save(bean);
	}
	
	public List<Record> getAll(){
		return dao.getAll();
	}

	public List<String> findUserNo(int user_id){
		return dao.findUserNo(user_id);
	}
	
	public List<Record> findListByUserNo(int user_id){
		return dao.findListByUserNo(user_id);
	}
	
	
	public Record findByUserNo(String userNo){
		return dao.findByUserNo(userNo);
	}
	
	@Transactional(readOnly=false)
	public void delete(String userNo){
		Record bean = dao.findByUserNo(userNo);
		if(bean!=null){
			dao.delete(bean);
		}else{
			throw new RuntimeException();
		}
	}
	
	public Integer countAll(){
		return dao.countAll();
	}
	
	public Pagination getCurPageStu(Map<String,Object> map, int pageNo, int pageSize, String sortOrder){
		return dao.getCurPageStu(map,pageNo,pageSize,sortOrder);
	}
	
	public Integer getCount(Map<String,Object> bean){
		return dao.getCount(bean);
	}

}
