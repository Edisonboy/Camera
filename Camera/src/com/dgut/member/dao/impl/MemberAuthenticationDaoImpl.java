package com.dgut.member.dao.impl;

import java.util.Date;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.dgut.common.hibernate3.HibernateBaseDao;
import com.dgut.common.page.Pagination;
import com.dgut.member.dao.MemberAuthenticationDao;
import com.dgut.member.entity.MemberAuthentication;

@Repository
public class MemberAuthenticationDaoImpl extends
		HibernateBaseDao<MemberAuthentication, String> implements MemberAuthenticationDao {
	public int deleteExpire(Date d) {
		String hql = "delete MemberAuthentication bean where bean.updateTime <= :d";
		return getSession().createQuery(hql).setTimestamp("d", d)
				.executeUpdate();
	}

	public MemberAuthentication getByUserId(Long userId) {
		String hql = "from MemberAuthentication bean where bean.uid=?";
		return (MemberAuthentication) findUnique(hql, userId);
	}

	public Pagination getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		Pagination page = findByCriteria(crit, pageNo, pageSize);
		return page;
	}

	public MemberAuthentication findById(String id) {
		MemberAuthentication entity = get(id);
		return entity;
	}

	public MemberAuthentication save(MemberAuthentication bean) {
		getSession().save(bean);
		return bean;
	}

	public MemberAuthentication deleteById(String id) {
		MemberAuthentication entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<MemberAuthentication> getEntityClass() {
		return MemberAuthentication.class;
	}

}
