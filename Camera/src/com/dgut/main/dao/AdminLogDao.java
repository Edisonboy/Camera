package com.dgut.main.dao;

import java.util.Date;

import com.dgut.common.page.Pagination;
import com.dgut.main.entity.AdminLog;

public interface AdminLogDao {
	public Pagination getPage(Integer category,  Integer userId,
			String title, String ip, int pageNo, int pageSize);

	public AdminLog findById(Integer id);

	public AdminLog save(AdminLog bean);

	public AdminLog deleteById(Integer id);

	public int deleteBatch(Integer category,  Date before);
}