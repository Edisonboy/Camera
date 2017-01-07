package com.dgut.main.manager.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UrlPathHelper;

import com.dgut.common.page.Pagination;
import com.dgut.common.web.RequestUtils;
import com.dgut.common.web.springmvc.MessageResolver;
import com.dgut.main.dao.AdminLogDao;
import com.dgut.main.entity.Admin;
import com.dgut.main.entity.AdminLog;
import com.dgut.main.manager.AdminLogMng;
import com.dgut.main.manager.AdminMng;
import com.dgut.main.web.CmsUtils;

@Service
@Transactional
public class AdminLogMngImpl implements AdminLogMng {
	@Transactional(readOnly = true)
	public Pagination getPage(Integer category, 
			String username, String title, String ip, int pageNo, int pageSize) {
		Pagination page;
		if (StringUtils.isBlank(username)) {
			page = dao.getPage(category,  null, title, ip, pageNo,
					pageSize);
		} else {
			Admin user = adminMng.findByUsername(username);
			if (user != null) {
				page = dao.getPage(category, user.getId(), title, ip,
						pageNo, pageSize);
			} else {
				page = new Pagination(1, pageSize, 0, new ArrayList<AdminLog>(0));
			}
		}
		return page;
	}

	@Transactional(readOnly = true)
	public AdminLog findById(Integer id) {
		AdminLog entity = dao.findById(id);
		return entity;
	}

	public AdminLog save(Integer category, Admin user,
			String url, String ip, Date date, String title, String content) {
		AdminLog log = new AdminLog();
		log.setUser(user);
		log.setCategory(category);
		log.setIp(ip);
		log.setTime(date);
		log.setUrl(url);
		log.setTitle(title);
		log.setContent(content);
		save(log);
		return log;
	}

	public AdminLog loginSuccess(HttpServletRequest request, Admin user,
			String title) {
		String ip = RequestUtils.getIpAddr(request);
		UrlPathHelper helper = new UrlPathHelper();
		String uri = helper.getOriginatingRequestUri(request);
		Date date = new Date();
		AdminLog log = save(AdminLog.LOGIN_SUCCESS,  user, uri, ip, date,
				 title, null);
		return log;
	}

	public AdminLog loginFailure(HttpServletRequest request, String title,
			String content) {
		String ip = RequestUtils.getIpAddr(request);
		UrlPathHelper helper = new UrlPathHelper();
		String uri = helper.getOriginatingRequestUri(request);
		Date date = new Date();
		AdminLog log = save(AdminLog.LOGIN_FAILURE,  null, uri, ip, date,
				 title, content);
		return log;
	}

	public AdminLog operating(HttpServletRequest request, String title,
			String content) {
		Admin user = CmsUtils.getAdmin(request);
		String ip = RequestUtils.getIpAddr(request);
		UrlPathHelper helper = new UrlPathHelper();
		String uri = helper.getOriginatingRequestUri(request);
		Date date = new Date();
		AdminLog log = save(AdminLog.OPERATING,  user, uri, ip, date,
				 title, content);
		return log;
	}

	public AdminLog save(AdminLog bean) {
		dao.save(bean);
		return bean;
	}

	public int deleteBatch(Integer category, Integer days) {
		Date date = null;
		if (days != null && days > 0) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -days);
			date = cal.getTime();
		}
		return dao.deleteBatch(category,  date);
	}

	public AdminLog deleteById(Integer id) {
		AdminLog bean = dao.deleteById(id);
		return bean;
	}

	public AdminLog[] deleteByIds(Integer[] ids) {
		AdminLog[] beans = new AdminLog[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	private AdminMng adminMng;
	private AdminLogDao dao;

	@Autowired
	public void setAdminMng(AdminMng adminMng) {
		this.adminMng = adminMng;
	}
	
	@Autowired
	public void setDao(AdminLogDao dao) {
		this.dao = dao;
	}

}