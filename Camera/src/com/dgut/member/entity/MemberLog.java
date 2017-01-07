package com.dgut.member.entity;

import com.dgut.member.entity.base.BaseMemberLog;


public class MemberLog extends BaseMemberLog {
	public static final int LOGIN_SUCCESS = 1;
	public static final int LOGIN_FAILURE = 2;
	public static final int OPERATING = 3;

	/* [CONSTRUCTOR MARKER BEGIN] */
	public MemberLog () {
		super();
	}
	

	/**
	 * Constructor for primary key
	 */
	public MemberLog (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public MemberLog (
		java.lang.Integer id,
		java.lang.Integer category,
		java.util.Date time) {

		super (
			id,
			category,
			time);
	}

	/* [CONSTRUCTOR MARKER END] */
}
