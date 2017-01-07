package com.dgut.main.manager;

import javax.servlet.http.HttpServletRequest;

import com.dgut.common.page.Pagination;
import com.dgut.main.entity.Admin;
import com.dgut.main.entity.AdminLog;

public interface AdminLogMng {
	public Pagination getPage(Integer category,
			String username, String title, String ip, int pageNo, int pageSize);

	public AdminLog findById(Integer id);

	public AdminLog operating(HttpServletRequest request, String title,
			String content);

	public AdminLog loginFailure(HttpServletRequest request, String title,
			String content);

	public AdminLog loginSuccess(HttpServletRequest request, Admin user,
			String title);

	public AdminLog save(AdminLog bean);

	public AdminLog deleteById(Integer id);

	public AdminLog[] deleteByIds(Integer[] ids);

	public int deleteBatch(Integer category, Integer days);
}