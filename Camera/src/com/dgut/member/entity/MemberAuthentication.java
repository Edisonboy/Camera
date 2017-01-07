package com.dgut.member.entity;

import java.sql.Timestamp;
import java.util.Date;

import com.dgut.main.entity.base.BaseAuthentication;

public class MemberAuthentication extends BaseAuthentication {
	private static final long serialVersionUID = 1L;

	public void init() {
		Date now = new Timestamp(System.currentTimeMillis());
		setLoginTime(now);
		setUpdateTime(now);
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public MemberAuthentication () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public MemberAuthentication (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public MemberAuthentication (
		java.lang.String id,
		java.lang.Integer uid,
		java.lang.String username,
		java.util.Date loginTime,
		java.lang.String loginIp,
		java.util.Date updateTime) {

		super (
			id,
			uid,
			username,
			loginTime,
			loginIp,
			updateTime);
	}

	/* [CONSTRUCTOR MARKER END] */

}
