package com.dgut.member.entity.base;

import java.io.Serializable;

import com.dgut.main.entity.InstallationRecord;

public class BaseMemeberPhoto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String userNo;
	private String photo;
	private String maxUrl;
	private String minUrl;
	
	public BaseMemeberPhoto() {
		super();
	}


	public BaseMemeberPhoto(Integer id, String userNo, String photo, String maxUrl, String minUrl) {
		super();
		this.id = id;
		this.userNo = userNo;
		this.photo = photo;
		this.maxUrl = maxUrl;
		this.minUrl = minUrl;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}


	public String getMaxUrl() {
		return maxUrl;
	}


	public void setMaxUrl(String maxUrl) {
		this.maxUrl = maxUrl;
	}


	public String getMinUrl() {
		return minUrl;
	}


	public void setMinUrl(String minUrl) {
		this.minUrl = minUrl;
	}


	@Override
	public String toString() {
		return "BaseMemeberPhoto [id=" + id + ", userNo=" + userNo + ", photo=" + photo + ", maxUrl=" + maxUrl
				+ ", minUrl=" + minUrl + "]";
	}

	
}