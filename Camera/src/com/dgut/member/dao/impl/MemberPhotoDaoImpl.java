package com.dgut.member.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dgut.common.hibernate3.Finder;
import com.dgut.common.hibernate3.HibernateBaseDao;
import com.dgut.member.dao.MemberPhotoDao;
import com.dgut.member.entity.MemberPhoto;

@Repository
public class MemberPhotoDaoImpl  extends HibernateBaseDao<MemberPhoto, Integer> implements MemberPhotoDao {

	
	public MemberPhoto save(MemberPhoto bean) {
		getSession().save(bean);
		return bean;
	}


	public List<MemberPhoto> getAll(){
		Finder f = Finder.create("select bean from MemberPhoto bean");
		return find(f);
	}
	
	
	public List<MemberPhoto> getPhoto(String userNo){
		Finder f = Finder.create("select bean from MemberPhoto bean where bean.userNo =:userNo");
		f.setParam("userNo", userNo);
		return find(f);
	}
	
	
	public List<String> getUrls(String userNo){
		Finder f = Finder.create("select bean.url from MemberPhoto bean where bean.userNo =:userNo");
		f.setParam("userNo", userNo);
		return find(f);
	}
	
	public MemberPhoto findById(int id){
		return findUniqueByProperty("id", id);
	}
	
	
	public void delete(MemberPhoto bean){
		getSession().delete(bean);
	}
	
	
	protected Class<MemberPhoto> getEntityClass() {
		return MemberPhoto.class;
	}

}
