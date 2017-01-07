package com.dgut.member.entity.base;

import java.io.Serializable;
import java.util.Date;

import com.dgut.member.entity.Member;

public class BaseRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Member member;
	private String userNo;
	private Date time;
	
	
	public BaseRecord() {
		super();
	}
	
	

	public BaseRecord(Integer id, Member member, String userNo, Date time) {
		super();
		this.id = id;
		this.member = member;
		this.userNo = userNo;
		this.time = time;
	}



	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Member getMember() {
		return member;
	}


	public void setMember(Member member) {
		this.member = member;
	}


	public String getUserNo() {
		return userNo;
	}


	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}


	public Date getTime() {
		return time;
	}


	public void setTime(Date time) {
		this.time = time;
	}


	@Override
	public String toString() {
		return "BaseRecord [id=" + id + ", member=" + member + ", userNo=" + userNo + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
}