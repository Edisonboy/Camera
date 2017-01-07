package com.dgut.member.manager.impl;

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
import com.dgut.main.web.CmsUtils;
import com.dgut.member.dao.MemberLogDao;
import com.dgut.member.entity.Member;
import com.dgut.member.entity.MemberLog;
import com.dgut.member.manager.MemberLogMng;
import com.dgut.member.manager.MemberMng;


@Service
@Transactional
public class MemberLogMngImpl implements MemberLogMng {
	@Transactional(readOnly = true)
	public Pagination getPage(Integer category, 
			String username, String title, String ip, int pageNo, int pageSize) {
		Pagination page;
		if (StringUtils.isBlank(username)) {
			page = dao.getPage(category,  null, title, ip, pageNo,
					pageSize);
		} else {
			Member user = MemberMng.findByUsername(username);
			if (user != null) {
				page = dao.getPage(category, user.getId(), title, ip,
						pageNo, pageSize);
			} else {
				page = new Pagination(1, pageSize, 0, new ArrayList<MemberLog>(0));
			}
		}
		return page;
	}

	@Transactional(readOnly = true)
	public MemberLog findById(Integer id) {
		MemberLog entity = dao.findById(id);
		return entity;
	}

	public MemberLog save(Integer category, Member user,
			String url, String ip, Date date, String title, String content) {
		MemberLog log = new MemberLog();
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

	public MemberLog loginSuccess(HttpServletRequest request, Member user,
			String title) {
		String ip = RequestUtils.getIpAddr(request);
		UrlPathHelper helper = new UrlPathHelper();
		String uri = helper.getOriginatingRequestUri(request);
		Date date = new Date();
		MemberLog log = save(MemberLog.LOGIN_SUCCESS,  user, uri, ip, date,
				 title, null);
		return log;
	}

	public MemberLog loginFailure(HttpServletRequest request, String title,
			String content) {
		String ip = RequestUtils.getIpAddr(request);
		UrlPathHelper helper = new UrlPathHelper();
		String uri = helper.getOriginatingRequestUri(request);
		Date date = new Date();
		MemberLog log = save(MemberLog.LOGIN_FAILURE,  null, uri, ip, date,
				 title, content);
		return log;
	}

	public MemberLog operating(HttpServletRequest request, String title,
			String content) {
		Member user = CmsUtils.getMember(request);
		String ip = RequestUtils.getIpAddr(request);
		UrlPathHelper helper = new UrlPathHelper();
		String uri = helper.getOriginatingRequestUri(request);
		Date date = new Date();
		MemberLog log = save(MemberLog.OPERATING,  user, uri, ip, date,
				 title, content);
		return log;
	}

	public MemberLog save(MemberLog bean) {
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

	public MemberLog deleteById(Integer id) {
		MemberLog bean = dao.deleteById(id);
		return bean;
	}

	public MemberLog[] deleteByIds(Integer[] ids) {
		MemberLog[] beans = new MemberLog[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	private MemberMng MemberMng;
	private MemberLogDao dao;

	@Autowired
	public void setMemberMng(MemberMng MemberMng) {
		this.MemberMng = MemberMng;
	}
	
	@Autowired
	public void setDao(MemberLogDao dao) {
		this.dao = dao;
	}

}
