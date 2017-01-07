package com.dgut.main.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.dgut.common.hibernate3.Finder;
import com.dgut.common.hibernate3.HibernateBaseDao;
import com.dgut.common.page.Pagination;
import com.dgut.main.dao.WorkSheetDao;
import com.dgut.main.entity.WorkSheet;

@Repository
public class WorkSheetDaoImpl extends HibernateBaseDao<WorkSheet,Integer> implements WorkSheetDao {

	
	public void save(WorkSheet bean) {
		getSession().save(bean);
	}

	
	public void delete(WorkSheet bean) {
		getSession().delete(bean);
	}

	
	public void update(WorkSheet bean) {
		getSession().update(bean);
	}

	
	public WorkSheet findById(Serializable id) {
		return (WorkSheet) getSession().get(WorkSheet.class, id);
	}

	
	public WorkSheet findByWorkSheetNo(String worksheetNo){
		return findUniqueByProperty("worksheetNo", worksheetNo);
	}

	
	public List<WorkSheet> getAll(){
		Finder f = Finder.create("select bean from WorkSheet bean");
		return find(f);
	}
	
	
	public Integer countAll() {
		String hql = "select count(*) from WorkSheet";
		Query q = getSession().createQuery(hql);
		return ((Number) q.iterate().next()).intValue();
	}
	
	
	public Integer getCount(String worksheetNo) {
		String hql = "select count(*) from WorkSheet bean where bean.worksheetNo like :worksheetNo";
		Query q = getSession().createQuery(hql).setParameter("worksheetNo", worksheetNo);
		return ((Number) q.iterate().next()).intValue();
	}
	
	
	public Pagination getCurPage(String worksheetNo, int pageNo, int pageSize,String sortOrder){
		Finder f = Finder.create("select bean from WorkSheet bean where 1=1");
		if (StringUtils.isNotBlank(worksheetNo)) {
			f.append(" and bean.worksheetNo=:worksheetNo");
			f.setParam(worksheetNo, worksheetNo);
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
	
	
	protected Class<WorkSheet> getEntityClass() {
		return WorkSheet.class;
	}

}
