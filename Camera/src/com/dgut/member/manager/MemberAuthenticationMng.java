package com.dgut.member.manager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dgut.common.page.Pagination;
import com.dgut.common.security.BadCredentialsException;
import com.dgut.common.security.UsernameNotFoundException;
import com.dgut.common.web.session.SessionProvider;
import com.dgut.member.entity.MemberAuthentication;

public interface MemberAuthenticationMng {
	/**
	 * 认证信息session key
	 */
	public static final String Member_AUTH_KEY = "member_auth_key";

	public Integer retrieveUserIdFromSession(SessionProvider session,
			HttpServletRequest request);
	
	public Integer retrieveUserIdFromToken(String token,
			HttpServletRequest request);

	public void storeAuthIdToSession(SessionProvider session,
			HttpServletRequest request, HttpServletResponse response,
			String authId);

	/**
	 * 通过认证ID，获得认证信息。本方法会检查认证是否过期。
	 * 
	 * @param authId
	 *            认证ID
	 * @return 返回MemberAuthentication对象。如果authId不存在或已经过期，则返回null。
	 */
	public MemberAuthentication retrieve(String authId);

	/**
	 * 登录
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param ip
	 *            登录IP
	 * @return 认证信息
	 * @throws UsernameNotFoundException
	 *             用户名没有找到
	 * @throws BadCredentialsException
	 *             错误的认证信息，比如密码错误
	 */
	public MemberAuthentication login(String username, String password, String ip,
			HttpServletRequest request, HttpServletResponse response,
			SessionProvider session) throws UsernameNotFoundException,
			BadCredentialsException;
	
	
	/**
	 * 获得认证分页信息
	 * 
	 * @param pageNo
	 *            当前页数
	 * @param pageSize
	 *            页数
	 * @return
	 */
	public Pagination getPage(int pageNo, int pageSize);

	/**
	 * 根据认证ID查找认证信息
	 * 
	 * @param id
	 *            认证ID
	 * @return
	 */
	public MemberAuthentication findById(String id);

	/**
	 * 保存认证信息
	 * 
	 * @param bean
	 * @return
	 */
	public MemberAuthentication save(MemberAuthentication bean);

	/**
	 * 删除认证信息
	 * 
	 * @param id
	 * @return
	 */
	public MemberAuthentication deleteById(String id);

	/**
	 * 删除认证信息
	 * 
	 * @param ids
	 * @return
	 */
	public MemberAuthentication[] deleteByIds(String[] ids);
}
