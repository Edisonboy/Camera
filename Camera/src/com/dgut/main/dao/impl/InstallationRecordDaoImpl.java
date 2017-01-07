package com.dgut.main.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;

import com.dgut.common.hibernate3.Finder;
import com.dgut.common.hibernate3.HibernateBaseDao;
import com.dgut.common.page.Pagination;
import com.dgut.main.dao.InstallationRecordDao;
import com.dgut.main.entity.InstallationRecord;

public class InstallationRecordDaoImpl extends HibernateBaseDao<InstallationRecord, Integer> implements InstallationRecordDao {

	
	public void save(InstallationRecord bean) {
		getSession().save(bean);

	}
	
	
	public void delete(InstallationRecord bean){
		getSession().delete(bean);
	}
	
	
	public InstallationRecord findById(Serializable id) {
		return (InstallationRecord) getSession().get(InstallationRecord.class, id);
	}
	
	
	public void update(InstallationRecord bean){
		getSession().update(bean);
	}
	
	
	public List<InstallationRecord> getAll() {
		String hql = "from InstallationRecord bean";
		return find(hql);
	}
	

	public List<InstallationRecord> findUserId(String id) {
		Finder f = Finder.create("select bean from InstallationRecord bean where (userNo like :userId or userName like :userId) and flag = 0");
		f.setParam("userId", "%"+id+"%");
		return find(f);
	}
	
	
	public List<InstallationRecord> findByUserNo(String No) {
		Finder f = Finder.create("select bean from InstallationRecord bean where userNo like :userNo");
		f.setParam("userNo",No);
		return find(f);
	}
	
	
	public List<InstallationRecord> findByWorkSheetNo(String worksheetNo){
		Finder f = Finder.create("select bean from InstallationRecord bean where bean.worksheetNo = :worksheetNo");
		f.setParam("worksheetNo", worksheetNo);
		return find(f);
	}
	
	
	
	
	protected Class<InstallationRecord> getEntityClass() {
		return InstallationRecord.class;
	}


	public InstallationRecord findOne(String id){
		return findUniqueByProperty("userNo", id);
	}

	
	public Integer countAll(){
		String hql = "select count(*) from InstallationRecord";
		Query q = getSession().createQuery(hql);
		return ((Number) q.iterate().next()).intValue();
	}
	
	
	public Integer getCount(String worksheetNo){
		String hql = "select count(*) from InstallationRecord bean where bean.worksheetNo=:worksheetNo";
		Query q = getSession().createQuery(hql).setParameter("worksheetNo", worksheetNo);
		return ((Number) q.iterate().next()).intValue();
	}
	
	
	public Pagination getCurPage(String worksheetNo, int pageNo, int pageSize,String sortOrder){
		Finder f = Finder.create("select bean from InstallationRecord bean where 1=1");
		if (StringUtils.isNotBlank(worksheetNo)) {
			f.append(" and bean.worksheetNo=:worksheetNo");
			f.setParam("worksheetNo", worksheetNo);
		}
		if (StringUtils.isNotBlank(sortOrder)) {
			if(sortOrder.equalsIgnoreCase("asc")){
				f.append(" order by bean.worksheetNo asc");
			}
			if(sortOrder.equalsIgnoreCase("desc")){
				f.append(" order by bean.worksheetNo desc");
			}
		}
		return find(f, pageNo, pageSize);
	}


	

}
