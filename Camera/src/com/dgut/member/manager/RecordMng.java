package com.dgut.member.manager;


import java.util.List;
import java.util.Map;

import com.dgut.common.page.Pagination;
import com.dgut.member.entity.Record;

public interface RecordMng {

	
	public Record save(Record bean);
	
	public List<Record> getAll();
	
	public List<String> findUserNo(int user_id);
	
	public Record findByUserNo(String userNo);
	
	public void delete(String userNo);
	
	public Integer countAll();
	
	public Pagination getCurPageStu(Map<String,Object> bean, int pageNo, int pageSize, String sortOrder);
	
	public Integer getCount(Map<String,Object> bean);
	
	public List<Record> findListByUserNo(int user_id);
}
