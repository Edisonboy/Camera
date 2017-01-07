package com.dgut.member.manager.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.common.page.Pagination;
import com.dgut.common.security.BadCredentialsException;
import com.dgut.common.security.UsernameNotFoundException;
import com.dgut.common.web.CookieUtils;
import com.dgut.common.web.session.SessionProvider;
import com.dgut.member.dao.MemberAuthenticationDao;
import com.dgut.member.entity.Member;
import com.dgut.member.entity.MemberAuthentication;
import com.dgut.member.manager.MemberAuthenticationMng;
import com.dgut.member.manager.MemberMng;

@Service
@Transactional
public class MemberAuthenticationMngImpl implements MemberAuthenticationMng {
	private Logger log = LoggerFactory
			.getLogger(MemberAuthenticationMngImpl.class);

	

	// 过期时间 2天内不登录就要重新登录
	public static int timeout = 60 * 60 *24 *2 * 1000; // 2天

	// 刷新间隔时间   3天清除一次过期的
	private int interval = 3 * 24 * 60 * 60 * 1000; // 3天

//	private int timeout =  10 * 1000; // 10秒过期
//
//	// 间隔时间   3天清除一次过期的
//	private int interval = 20 * 1000; // 清除超过20秒的
	// 刷新时间。
	private long refreshTime = getNextRefreshTime(System.currentTimeMillis(),
			this.interval);
	
	public MemberAuthentication login(String username, String password,
			String ip, HttpServletRequest request,
			HttpServletResponse response, SessionProvider session)
			throws UsernameNotFoundException, BadCredentialsException {
		Member user = memberMng.login(username, password, ip);
		MemberAuthentication auth = new MemberAuthentication();
		auth.setUid(user.getId());
		auth.setUsername(user.getUsername());
		auth.setLoginIp(ip);
		save(auth);
		session.setAttribute(request, response, Member_AUTH_KEY, auth.getId());
		
		String token = (String) session.getAttribute(request, MemberAuthenticationMng.Member_AUTH_KEY);
		CookieUtils.addCookie(request, response, "token", token, MemberAuthenticationMngImpl.timeout/1000, null);
		
		return auth;
	}

	public MemberAuthentication retrieve(String authId) {
		long current = System.currentTimeMillis();
		// 是否刷新数据库
		if (refreshTime < current) {
			refreshTime = getNextRefreshTime(current, interval);
			int count = dao.deleteExpire(new Date(current - timeout));
			log.info("refresh MemberAuthentication, delete count: {}", count);
		}
		MemberAuthentication auth = findById(authId);
		if (auth != null && auth.getUpdateTime().getTime() + timeout > current) {
			auth.setUpdateTime(new Timestamp(current));
			return auth;
		} else {
			return null;
		}
	}

	//适合短连接 session20分钟后过期
	public Integer retrieveUserIdFromSession(SessionProvider session,
			HttpServletRequest request) {
		String authId = (String) session.getAttribute(request, Member_AUTH_KEY);
		if (authId == null) {
			return null;
		}
		MemberAuthentication auth = retrieve(authId);
		if (auth == null) {
			return null;
		}
		return auth.getUid();
	}
	//适合长连接 
	public Integer retrieveUserIdFromToken(String token,
			HttpServletRequest request) {
		
		if (token == null) {
			return null;
		}
		MemberAuthentication auth = retrieve(token);
		if (auth == null) {
			return null;
		}
		return auth.getUid();
	}

	public void storeAuthIdToSession(SessionProvider session,
			HttpServletRequest request, HttpServletResponse response,
			String authId) {
		session.setAttribute(request, response, Member_AUTH_KEY, authId);
	}

	@Transactional(readOnly = true)
	public Pagination getPage(int pageNo, int pageSize) {
		Pagination page = dao.getPage(pageNo, pageSize);
		return page;
	}

	@Transactional(readOnly = true)
	public MemberAuthentication findById(String id) {
		MemberAuthentication entity = dao.findById(id);
		return entity;
	}

	public MemberAuthentication save(MemberAuthentication bean) {
		bean.setId(StringUtils.remove(UUID.randomUUID().toString(), '-'));
		bean.init();
		dao.save(bean);
		return bean;
	}

	public MemberAuthentication deleteById(String id) {
		MemberAuthentication bean = dao.deleteById(id);
		return bean;
	}

	public MemberAuthentication[] deleteByIds(String[] ids) {
		MemberAuthentication[] beans = new MemberAuthentication[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}


	private MemberMng memberMng;
	private MemberAuthenticationDao dao;

	@Autowired
	public void setDao(MemberAuthenticationDao dao) {
		this.dao = dao;
	}

	@Autowired
	public void setMemberMng(MemberMng memberMng) {
		this.memberMng = memberMng;
	}

	/**
	 * 设置认证过期时间。默认30分钟。
	 * 
	 * @param timeout
	 *            单位分钟
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout * 60 * 1000;
	}

	/**
	 * 设置刷新数据库时间。默认4小时。
	 * 
	 * @param interval
	 *            单位分钟
	 */
	public void setInterval(int interval) {
		this.interval = interval * 60 * 1000;
		this.refreshTime = getNextRefreshTime(System.currentTimeMillis(),
				this.interval);
	}

	/**
	 * 获得下一个刷新时间。
	 * 
	 * 
	 * 
	 * @param current
	 * @param interval
	 * @return 随机间隔时间
	 */
	private long getNextRefreshTime(long current, int interval) {
		return current + interval;
		// 为了防止多个应用同时刷新，间隔时间=interval+RandomUtils.nextInt(interval/4);
		// return current + interval + RandomUtils.nextInt(interval / 4);
	}

}
