package com.dgut.member.dao.impl;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.dgut.common.hibernate3.Finder;
import com.dgut.common.hibernate3.HibernateBaseDao;
import com.dgut.common.page.Pagination;
import com.dgut.member.dao.MemberDao;
import com.dgut.member.entity.Member;

@Repository
public class MemberDaoImpl extends HibernateBaseDao<Member, Integer> implements
		MemberDao {


	public Pagination getPage(String username, String realname,
			int pageNo, int pageSize) {
		Finder f = Finder.create("select bean from Member bean  where 1=1");
		if (!StringUtils.isBlank(username)) {
			f.append(" and bean.username like :username ");
			f.setParam("username", "%" + username + "%");
			
		}
		if(!StringUtils.isBlank(realname)){
			f.append(" and bean.realname like :realname");
			f.setParam("realname", "%" + realname + "%");
		}
		f.append(" order by bean.id asc");
		return find(f, pageNo, pageSize);
	}

	public List getList(String username, Boolean disabled) {
		Finder f = Finder.create("select bean from Member bean  where 1=1");
		if (!StringUtils.isBlank(username)) {
			f.append(" and bean.username like :username");
			f.setParam("username", "%" + username + "%");
		}
		if (disabled != null) {
			f.append(" and bean.disabled=:disabled");
			f.setParam("disabled", disabled);
		}
		f.append(" order by bean.id desc");
		return find(f);
	}

	@SuppressWarnings("unchecked")
	public List<Member> getMemberList(Boolean disabled, Integer rank) {
		Finder f = Finder.create("select bean from Member");
		f.append(" where 1 = 1 ");
		if (disabled != null) {
			f.append(" and bean.disabled=:disabled");
			f.setParam("disabled", disabled);
		}
		if (rank != null) {
			f.append(" and bean.rank<=:rank");
			f.setParam("rank", rank);
		}
		f.append(" and bean.Member=true");
		f.append(" order by bean.id asc");
		return find(f);
	}

	public Member findById(Integer id) {
		Member entity = get(id);
		return entity;
	}

	public Member findByUsername(String username) {
		//Finder f = Finder.create("select bean from Member bean where bean.username=:username");
		//f.setParam("username", username);
		//return (Member) find(f);
		
		return findUniqueByProperty("username", username);
	}

	public int countByUsername(String username) {
		String hql = "select count(*) from Member bean where bean.username=:username";
		Query query = getSession().createQuery(hql);
		query.setParameter("username", username);
		return ((Number) query.iterate().next()).intValue();
	}


	public Member save(Member bean) {
		getSession().save(bean);
		return bean;
	}

	public Member deleteById(Integer id) {
		Member entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<Member> getEntityClass() {
		return Member.class;
	}

}
