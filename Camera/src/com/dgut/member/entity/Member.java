package com.dgut.member.entity;

import java.util.Collection;
import java.util.Date;
import com.dgut.common.hibernate3.PriorityInterface;
import com.dgut.member.entity.base.BaseMember;

public class Member extends BaseMember implements PriorityInterface{
	private static final long serialVersionUID = 1L;




	public void init() {
		if(getLoginCount()==null){
			setLoginCount(0);
		}
		if(getErrorCount()==null){
			setErrorCount(0);
		}
		if (getDisabled() == null) {
			setDisabled(false);
		}
	}

	public static Integer[] fetchIds(Collection<Member> users) {
		if (users == null) {
			return null;
		}
		Integer[] ids = new Integer[users.size()];
		int i = 0;
		for (Member u : users) {
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
	public Member() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Member(java.lang.Integer id) {
		super(id);
	}

	/* [CONSTRUCTOR MARKER END] */

}
