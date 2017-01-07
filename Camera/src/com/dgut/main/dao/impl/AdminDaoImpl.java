package com.dgut.main.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.dgut.common.hibernate3.Finder;
import com.dgut.common.hibernate3.HibernateBaseDao;
import com.dgut.common.page.Pagination;
import com.dgut.main.dao.AdminDao;
import com.dgut.main.entity.Admin;

@Repository
public class AdminDaoImpl extends HibernateBaseDao<Admin, Integer>
		implements AdminDao {
	
	
	public Pagination getPage(String username,Integer roleId, String email, Boolean disabled, Boolean admin, Integer rank,
			int pageNo, int pageSize) {
		Finder f = Finder.create("select bean from Admin bean  where 1=1");
		if (!StringUtils.isBlank(username)) {
			f.append(" and bean.username like :username or bean.realname like :realname");
			f.setParam("username", "%" + username + "%");
			f.setParam("realname", "%" + username + "%");
		}
		if (!StringUtils.isBlank(email)) {
			f.append(" and bean.email like :email");
			f.setParam("email", "%" + email + "%");
		}
		if (disabled != null) {
			f.append(" and bean.disabled=:disabled");
			f.setParam("disabled", disabled);
		}
		if (admin != null) {
			f.append(" and bean.admin=:admin");
			f.setParam("admin", admin);
		}
		if (rank != null) {
			f.append(" and bean.rank<=:rank");
			f.setParam("rank", rank);
		}
		if (roleId != null) {
			f.append(" and bean.role.id =:role");
			f.setParam("role", roleId);
		}
		f.append(" order by bean.id desc");
		return find(f, pageNo, pageSize);
	}
	
	public List getList(String username, String email,  Boolean disabled, Boolean admin, Integer rank) {
		Finder f = Finder.create("select bean from Admin bean  where 1=1");
		if (!StringUtils.isBlank(username)) {
			f.append(" and bean.username like :username");
			f.setParam("username", "%" + username + "%");
		}
		if (!StringUtils.isBlank(email)) {
			f.append(" and bean.email like :email");
			f.setParam("email", "%" + email + "%");
		}
		if (disabled != null) {
			f.append(" and bean.disabled=:disabled");
			f.setParam("disabled", disabled);
		}
		if (admin != null) {
			f.append(" and bean.admin=:admin");
			f.setParam("admin", admin);
		}
		if (rank != null) {
			f.append(" and bean.rank<=:rank");
			f.setParam("rank", rank);
		}
		f.append(" order by bean.id desc");
		return find(f);
	}

	@SuppressWarnings("unchecked")
	public List<Admin> getAdminList(
			Boolean disabled, Integer rank) {
		Finder f = Finder.create("select bean from Admin");
		f.append(" where 1 = 1 ");
		if (disabled != null) {
			f.append(" and bean.disabled=:disabled");
			f.setParam("disabled", disabled);
		}
		if (rank != null) {
			f.append(" and bean.rank<=:rank");
			f.setParam("rank", rank);
		}
		f.append(" and bean.admin=true");
		f.append(" order by bean.id asc");
		return find(f);
	}

	public Admin findById(Integer id) {
		Admin entity = get(id);
		return entity;
	}

	public Admin findByUsername(String username) {
		return findUniqueByProperty("username", username);
	}

	public int countByUsername(String username) {
		String hql = "select count(*) from Admin bean where bean.username=:username";
		Query query = getSession().createQuery(hql);
		query.setParameter("username", username);
		return ((Number) query.iterate().next()).intValue();
	}

	public int countByEmail(String email) {
		String hql = "select count(*) from Admin bean where bean.email=:email";
		Query query = getSession().createQuery(hql);
		query.setParameter("email", email);
		return ((Number) query.iterate().next()).intValue();
	}

	public Admin save(Admin bean) {
		getSession().save(bean);
		return bean;
	}

	public Admin deleteById(Integer id) {
		Admin entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<Admin> getEntityClass() {
		return Admin.class;
	}
}