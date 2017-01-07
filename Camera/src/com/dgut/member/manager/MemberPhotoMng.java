package com.dgut.member.manager;

import java.util.List;

import com.dgut.member.entity.MemberPhoto;

public interface MemberPhotoMng {

	
	public MemberPhoto save(MemberPhoto bean);
	
	public List<MemberPhoto> getAll();
	
	public List<MemberPhoto> getPhoto(String userNo);
	
	public MemberPhoto findById(int id);
	
	public void delete(String userNo);
	
	public List<String> getUrls(String userNo);
	
}
