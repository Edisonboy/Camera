package com.dgut.main.entity;

import com.dgut.main.entity.base.BaseAdminLog;

public class AdminLog extends BaseAdminLog {
	private static final long serialVersionUID = 1L;
	public static final int LOGIN_SUCCESS = 1;
	public static final int LOGIN_FAILURE = 2;
	public static final int OPERATING = 3;

	/* [CONSTRUCTOR MARKER BEGIN] */
	public AdminLog () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public AdminLog (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public AdminLog (
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