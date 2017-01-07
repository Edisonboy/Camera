package com.dgut.member.dao;

import java.util.List;
import java.util.Map;

import com.dgut.common.page.Pagination;
import com.dgut.member.entity.Record;

public interface RecordDao {
	
	public Record save(Record bean);
	
	public List<String> findUserNo(int user_id);
	
	public List<Record> getAll();
	
	public Record findByUserNo(String userNo);
	
	public void delete(Record bean);
	
	public Integer countAll();
	
	public Pagination getCurPageStu(Map<String,Object> bean, int pageNo, int pageSize, String sortOrder);
	
	public Integer getCount(Map<String,Object> bean);
	
	public List<Record> findListByUserNo(int user_id);
	
}
