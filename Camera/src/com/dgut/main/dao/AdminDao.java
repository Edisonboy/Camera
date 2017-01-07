package com.dgut.main.dao;

import java.util.List;

import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.main.entity.Admin;

/**
 * 用户DAO接口
 * 
 * 
 */
public interface AdminDao{
	public Pagination getPage(String username,Integer roleId, String email,  Boolean disabled, Boolean admin, Integer rank,
			int pageNo, int pageSize);
	
	public List getList(String username, String email, Boolean disabled, Boolean admin, Integer rank);

	public List<Admin> getAdminList(Boolean disabled, Integer rank);

	public Admin findById(Integer id);

	public Admin findByUsername(String username);

	public int countByUsername(String username);
	

	public int countByEmail(String email);

	public Admin save(Admin bean);

	public Admin updateByUpdater(Updater<Admin> updater);

	public Admin deleteById(Integer id);
}