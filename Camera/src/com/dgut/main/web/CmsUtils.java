package com.dgut.main.web;

import javax.servlet.http.HttpServletRequest;

import com.dgut.main.entity.Admin;
import com.dgut.member.entity.Member;

/**
 * 
 * 比如获得会员信息,获得后台站点信息
 */
public class CmsUtils {
	/**
	 * 管理员KEY
	 */
	public static final String Admin_KEY = "_admin_user_key";

	/**
	 * 会员KEY
	 */
	public static final String Member_KEY = "_member_user_key";
	/**
	 * 获得会员
	 * 
	 * @param request
	 * @return
	 */
	public static Member getMember(HttpServletRequest request) {
		return (Member) request.getAttribute(Member_KEY);
	}
	
	/**
	 * 获得管理员
	 * 
	 * @param request
	 * @return
	 */
	public static Admin getAdmin(HttpServletRequest request) {
		return (Admin) request.getAttribute(Admin_KEY);
	}
	
	
	/**
	 * 获得管理员ID
	 * 
	 * @param request
	 * @return
	 */
	public static Integer getAdminId(HttpServletRequest request) {
		Admin user = getAdmin(request);
		if (user != null) {
			return user.getId();
		} else {
			return null;
		}
	}
	
	/**
	 * 获得会员ID
	 * 
	 * @param request
	 * @return
	 */
	public static Integer getMemberId(HttpServletRequest request) {
		Member user = getMember(request);
		if (user != null) {
			return user.getId();
		} else {
			return null;
		}
	}

	/**
	 * 设置管理员
	 * 
	 * @param request
	 * @param user
	 */
	public static void setAdmin(HttpServletRequest request, Admin user) {
		request.setAttribute(Admin_KEY, user);
	}
	
	/**
	 * 设置会员
	 * 
	 * @param request
	 * @param user
	 */
	public static void setMember(HttpServletRequest request, Member user) {
		request.setAttribute(Member_KEY, user);
	}

}
