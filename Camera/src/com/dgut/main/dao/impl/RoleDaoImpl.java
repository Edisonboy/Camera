package com.dgut.main.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dgut.common.hibernate3.HibernateBaseDao;
import com.dgut.main.dao.RoleDao;
import com.dgut.main.entity.Role;

@Repository
public class RoleDaoImpl extends HibernateBaseDao<Role, Integer>
		implements RoleDao {
	@SuppressWarnings("unchecked")
	public List<Role> getList() {
		String hql = "from Role bean order by bean.priority asc";
		return find(hql);
	}

	public Role findById(Integer id) {
		Role entity = get(id);
		return entity;
	}

	public Role save(Role bean) {
		getSession().save(bean);
		return bean;
	}

	public Role deleteById(Integer id) {
		Role entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<Role> getEntityClass() {
		return Role.class;
	}
}