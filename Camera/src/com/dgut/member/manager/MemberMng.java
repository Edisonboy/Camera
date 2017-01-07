package com.dgut.member.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dgut.common.page.Pagination;
import com.dgut.common.security.BadCredentialsException;
import com.dgut.common.security.UsernameNotFoundException;
import com.dgut.member.entity.Member;

public interface MemberMng {

	public Member login(String username, String password, String ip) throws UsernameNotFoundException, BadCredentialsException;
	
	public Pagination getPage(String username, String realname,int pageNo, int pageSize);
	
	public List getList(String username,  Boolean disabled);

	public Member findById(Integer id);

	public Member findByUsername(String username);


	public void updateLoginInfo(Integer userId, String ip);


	public void updatePwdRealName(Integer id, String password, String realName);

	public boolean isPasswordValid(Integer id, String password);

	public Member saveMember(String username,  String password,
			String ip,Boolean gender,String realname);
	
	public void create(Member bean,HttpServletRequest request);

	public Member updateMember(Member bean, String password);


	public Member deleteById(Integer id);

	public Member[] deleteByIds(Integer[] ids);
	public boolean usernameNotExist(String username);
	public Integer errorRemaining(String username);
}
