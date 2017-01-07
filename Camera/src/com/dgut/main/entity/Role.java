package com.dgut.main.entity;

import java.util.Collection;

import com.dgut.main.entity.base.BaseRole;

public class Role extends BaseRole {
	private static final long serialVersionUID = 1L;

	public static Integer[] fetchIds(Collection<Role> roles) {
		if (roles == null) {
			return null;
		}
		Integer[] ids = new Integer[roles.size()];
		int i = 0;
		for (Role r : roles) {
			ids[i++] = r.getId();
		}
		return ids;
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public Role () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Role (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Role (
		java.lang.Integer id,
		java.lang.String name,
		java.lang.Integer priority,
		java.lang.Boolean m_super) {

		super (
			id,
			name,
			priority,
			m_super);
	}

	/* [CONSTRUCTOR MARKER END] */

}