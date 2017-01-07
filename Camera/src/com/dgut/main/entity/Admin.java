package com.dgut.main.entity;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.dgut.common.hibernate3.PriorityInterface;
import com.dgut.main.entity.base.BaseAdmin;

public class Admin extends BaseAdmin implements PriorityInterface {
	private static final long serialVersionUID = 1L;


	public Integer getRoleId() {
		Role role = getRole();
		if (role == null) {
			return null;
		}
		return role.getId();
	}

	public boolean isSuper() {
		Role r = getRole();
		if (r == null) {
			return false;
		}
		if (r.getSuper()) {
			return true;
		}
		return false;
	}
	public Set<String> getPerms() {
		Role roles = getRole();
		if (roles == null) {
			return null;
		}
		Set<String> allPerms = new HashSet<String>();
		allPerms.addAll(roles.getPerms());
		return allPerms;
	}

	public void init() {
		if(getLoginCount()==null){
			setLoginCount(0);
		}
		if(getErrorCount()==null){
			setErrorCount(0);
		}
		if (getAdmin() == null) {
			setAdmin(false);
		}
		if (getRank() == null) {
			setRank(0);
		}
		if (getDisabled() == null) {
			setDisabled(false);
		}
	}

	public static Integer[] fetchIds(Collection<Admin> users) {
		if (users == null) {
			return null;
		}
		Integer[] ids = new Integer[users.size()];
		int i = 0;
		for (Admin u : users) {
			ids[i++] = u.getId();
		}
		return ids;
	}

	/**
	 * 用于排列顺序。此处优先级为0，则按ID升序排。
	 */
	public Number getPriority() {
		return 0;
	}

	/**
	 * 是否是今天。根据System.currentTimeMillis() / 1000 / 60 / 60 / 24计算。
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isToday(Date date) {
		long day = date.getTime() / 1000 / 60 / 60 / 24;
		long currentDay = System.currentTimeMillis() / 1000 / 60 / 60 / 24;
		return day == currentDay;
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public Admin() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Admin(java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Admin(java.lang.Integer id,
			java.lang.String username,String password, java.util.Date registerTime,
			java.lang.String registerIp, java.lang.Integer loginCount,
			java.lang.Integer rank, java.lang.Boolean admin,
			java.lang.Boolean disabled) {

		super(id,  username,password, registerTime, registerIp, loginCount, rank,
			 admin,  disabled);
	}
	
	/* [CONSTRUCTOR MARKER END] */

}