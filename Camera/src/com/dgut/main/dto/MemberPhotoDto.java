package com.dgut.main.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.dgut.member.entity.MemberPhoto;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Lenovo
 *
 */
public class MemberPhotoDto {

	private Integer id;
	private String name;
	private String userNo;
	private String userName;
	private String photo;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm",locale="zh",timezone="GMT+8")
	private Date time;
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getUserNo() {
		return userNo;
	}


	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPhoto() {
		return photo;
	}


	public void setPhoto(String photo) {
		this.photo = photo;
	}


	public Date getTime() {
		return time;
	}


	public void setTime(Date time) {
		this.time = time;
	}


	public static void MemberPhotoToDto(MemberPhoto mp, MemberPhotoDto mpDto,String userName){
		BeanUtils.copyProperties(mp, mpDto);
		/*
		if(mp.getMember()!=null){
			mpDto.setName(mp.getMember().getUsername());   //上传用户
			
		}
		mpDto.setUserNo(mp.getFileName()); //户号
		mpDto.setUserName(userName);       //户名
		*/
	}
}
