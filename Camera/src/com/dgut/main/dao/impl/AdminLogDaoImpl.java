package com.dgut.main.dao.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.dgut.common.hibernate3.Finder;
import com.dgut.common.hibernate3.HibernateBaseDao;
import com.dgut.common.page.Pagination;
import com.dgut.main.dao.AdminLogDao;
import com.dgut.main.entity.AdminLog;

@Repository
public class AdminLogDaoImpl extends HibernateBaseDao<AdminLog, Integer> implements
		AdminLogDao {
	public Pagination getPage(Integer category,  Integer userId,
			String title, String ip, int pageNo, int pageSize) {
		Finder f = Finder.create("from AdminLog bean where 1=1");
		if (category != null) {
			f.append(" and bean.category=:category");
			f.setParam("category", category);
		}
		if (userId != null) {
			f.append(" and bean.user.id=:userId");
			f.setParam("userId", userId);
		}
		if (StringUtils.isNotBlank(title)) {
			f.append(" and bean.title like :title");
			f.setParam("title", "%" + title + "%");
		}
		if (StringUtils.isNotBlank(ip)) {
			f.append(" and bean.ip like :ip");
			f.setParam("ip", ip + "%");
		}
		f.append(" order by bean.id desc");
		return find(f, pageNo, pageSize);
	}

	public AdminLog findById(Integer id) {
		AdminLog entity = get(id);
		return entity;
	}

	public AdminLog save(AdminLog bean) {
		getSession().save(bean);
		return bean;
	}

	public AdminLog deleteById(Integer id) {
		AdminLog entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	public int deleteBatch(Integer category,  Date before) {
		Finder f = Finder.create("delete AdminLog bean where 1=1");
		if (category != null) {
			f.append(" and bean.category=:category");
			f.setParam("category", category);
		}
		if (before != null) {
			f.append(" and bean.time<=:before");
			f.setParam("before", before);
		}
		Query q = f.createQuery(getSession());
		return q.executeUpdate();
	}

	@Override
	protected Class<AdminLog> getEntityClass() {
		return AdminLog.class;
	}
}