package com.dgut.member.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.member.dao.MemberPhotoDao;
import com.dgut.member.entity.MemberPhoto;
import com.dgut.member.manager.MemberPhotoMng;

@Service
@Transactional
public class MemberPhotoMngImpl implements MemberPhotoMng {

	@Autowired
	private MemberPhotoDao dao;
	
	@Transactional(readOnly=false)
	public MemberPhoto save(MemberPhoto bean) {
		return dao.save(bean);
	}
	

	public List<MemberPhoto> getAll(){
		return dao.getAll();
	}
	

	public List<MemberPhoto> getPhoto(String userNo){
		return dao.getPhoto(userNo);
	}
	
	
	public List<String> getUrls(String userNo){
		return dao.getUrls(userNo);
	}
	
	
	public MemberPhoto findById(int id){
		return dao.findById(id);
	}
	
	@Transactional(readOnly=false)
	public void delete(String userNo){
		List<MemberPhoto> beanlist = dao.getPhoto(userNo);
		if(beanlist.size()>0){
			for(MemberPhoto bean:beanlist){
				dao.delete(bean);
			}
		}else{
			throw new RuntimeException();
		}
	}

}
