package com.dgut.member.dao;

import java.util.List;

import com.dgut.member.entity.MemberPhoto;

public interface MemberPhotoDao {
	
	public MemberPhoto save(MemberPhoto mp);

	public List<MemberPhoto> getAll();
	
	public List<MemberPhoto> getPhoto(String userNo);
	
	public MemberPhoto findById(int id);
	
	public void delete(MemberPhoto bean);
	
	public List<String> getUrls(String userNo);
	
}
