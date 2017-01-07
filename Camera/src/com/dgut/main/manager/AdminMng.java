package com.dgut.main.manager;

import java.util.List;

import com.dgut.common.page.Pagination;
import com.dgut.common.security.BadCredentialsException;
import com.dgut.common.security.UsernameNotFoundException;
import com.dgut.main.entity.Admin;

public interface AdminMng {
	
	public Admin login(String username, String password, String ip) throws UsernameNotFoundException, BadCredentialsException;
	public Pagination getPage(String username,Integer roleId, String email, 
			 Boolean disabled, Boolean admin, Integer rank,
			int pageNo, int pageSize);
	
	public List getList(String username, String email, Boolean disabled, Boolean admin, Integer rank);

	public List<Admin> getAdminList(Boolean disabled, Integer rank);

	public Admin findById(Integer id);

	public Admin findByUsername(String username);


	public void updateLoginInfo(Integer userId, String ip);


	public void updatePwdRealName(Integer id, String password, String realName);

	public boolean isPasswordValid(Integer id, String password);

	public Admin saveAdmin(String username, String email, String password,
			String ip, int rank,Boolean gender,String realname, Integer roleId	);

	public Admin updateAdmin(Admin bean, String password,Integer roleId);


	public Admin deleteById(Integer id);

	public Admin[] deleteByIds(Integer[] ids);

	public boolean usernameNotExist(String username);
	
	public boolean emailNotExist(String email);
	
	public Integer errorRemaining(String username);
}