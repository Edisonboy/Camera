package com.dgut.member.dao.impl;


import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.dgut.common.hibernate3.Finder;
import com.dgut.common.hibernate3.HibernateBaseDao;
import com.dgut.common.page.Pagination;
import com.dgut.member.dao.RecordDao;
import com.dgut.member.entity.Record;

@Repository
public class RecordDaoImpl  extends HibernateBaseDao<Record, Integer> implements RecordDao {

	
	public Record save(Record bean) {
		getSession().save(bean);
		return bean;
	}

	public Integer countAll(){
		String hql = "select count(*) from Record";
		Query query = getSession().createQuery(hql);
		return ((Number) query.iterate().next()).intValue();
	}
	
	public Integer getCount(Map<String,Object> bean){
		StringBuilder f = new StringBuilder("select count(*) from Record bean where 1=1");
		if(bean.get("name")!=null){  //上传用户
			f.append(" and bean.member.username like :name");
			//f.setParam("name", "%"+bean.get("name")+"%");
		}
		if(bean.get("userNo")!=null){  //户号
			f.append(" and bean.userNo like :userNo");
			//f.setParam("userNo", "%"+bean.get("userNo")+"%");
		}
		if(bean.get("userName")!=null){
			f.append(" and userNo in (select userNo from InstallationRecord bean where bean.userName like :userName)");
			//f.setParam("userName","%"+bean.get("userName")+"%");
		}
		if(bean.get("start")!=null){
			f.append(" and time>=:start");
			//f.setParam("start", bean.get("start"));
			
		}
		if(bean.get("end")!=null){
			f.append(" and time<=:end");
			//f.setParam("end", bean.get("end"));
		}
		Query q = getSession().createQuery(f.toString());
		return ((Number) q.iterate().next()).intValue();
	}
	
	
	public Pagination getCurPageStu(Map<String,Object> bean, int pageNo, int pageSize, String sortOrder) {
		Finder f = Finder.create("select bean from Record bean where 1=1");
		if(bean.get("name")!=null){  //上传用户
			f.append(" and bean.member.username like :name");
			f.setParam("name", "%"+bean.get("name")+"%");
		}
		if(bean.get("userNo")!=null){  //户号
			f.append(" and bean.userNo like :userNo");
			f.setParam("userNo", "%"+bean.get("userNo")+"%");
		}
		if(bean.get("userName")!=null){
			f.append(" and userNo in (select userNo from InstallationRecord bean where bean.userName like :userName)");
			f.setParam("userName","%"+bean.get("userName")+"%");
		}
		if(bean.get("start")!=null){
			f.append(" and time>=:start");
			f.setParam("start", bean.get("start"));
		}
		if(bean.get("end")!=null){
			f.append(" and time<=:end");
			f.setParam("end", bean.get("end"));
		}
		if(sortOrder.equals("desc")){
			f.append(" order by bean.userNo desc");
		}
		if(sortOrder.equals("asc")){
			f.append(" order by bean.userNo asc");
		}
		return find(f, pageNo, pageSize);
	}
	
	
	public List<String> findUserNo(int user_id) {
		Finder f = Finder.create("select bean.userNo from Record bean where bean.member.id =:id");
		f.setParam("id", user_id);
		return find(f);
	}
	
	
	public List<Record> findListByUserNo(int user_id){
		Finder f = Finder.create("select bean from Record bean where bean.member.id = :id");
		f.setParam("id", user_id);
		return find(f);
	}
	
	public List<Record> getAll(){
		Finder f = Finder.create("select bean from Record bean");
		return find(f);
	}
	
	
	public Record findByUserNo(String userNo){
		return findUniqueByProperty("userNo", userNo);
	}
	
	
	public void delete(Record bean){
		getSession().delete(bean);
	}
	
	
	protected Class<Record> getEntityClass() {
		return Record.class;
	}






	

}
