package com.dgut.main.manager.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.common.security.BadCredentialsException;
import com.dgut.common.security.UsernameNotFoundException;
import com.dgut.common.security.encoder.PwdEncoder;
import com.dgut.core.entity.Config.ConfigLogin;
import com.dgut.core.manager.ConfigMng;
import com.dgut.main.dao.AdminDao;
import com.dgut.main.entity.Admin;
import com.dgut.main.manager.AdminMng;
import com.dgut.main.manager.RoleMng;

@Service
@Transactional
public class AdminMngImpl implements AdminMng {
	@Transactional(readOnly = true)
	public Pagination getPage(String username,Integer roleId, String email,
			Boolean disabled, Boolean admin, Integer rank,
			int pageNo, int pageSize) {
		Pagination page = dao.getPage(username,roleId, email, disabled, admin, rank, pageNo, pageSize);
		return page;
	}
	
	@Transactional(readOnly = true)
	public List getList(String username, String email,  Boolean disabled, Boolean admin, Integer rank) {
		List list = dao.getList(username, email,
				disabled, admin, rank);
		return list;
	}

	@Transactional(readOnly = true)
	public List<Admin> getAdminList(
			Boolean disabled, Integer rank) {
		return dao.getAdminList( disabled, rank);
	}

	@Transactional(readOnly = true)
	public Admin findById(Integer id) {
		Admin entity = dao.findById(id);
		return entity;
	}

	@Transactional(readOnly = true)
	public Admin findByUsername(String username) {
		Admin entity = dao.findByUsername(username);
		return entity;
	}


	public void updateLoginInfo(Integer userId, String ip) {
		Date now = new Timestamp(System.currentTimeMillis());
		Admin user = findById(userId);
		if (user != null) {
			user.setLoginCount(user.getLoginCount() + 1);
			user.setLastLoginIp(ip);
			user.setLastLoginTime(now);
		}
	}


	public boolean isPasswordValid(Integer id, String password) {
		 Admin user = findById(id);
		return pwdEncoder.isPasswordValid(user.getPassword(), password);
	}

	public void updatePwdRealName(Integer id, String password, String realName) {
		Admin user = findById(id);
		user.setRealname(realName);
		if (!StringUtils.isBlank(password)) {
			user.setPassword(pwdEncoder.encodePassword(password));
		}
	}

	public Admin saveAdmin(String username, String email, String password,
			String ip,  int rank,Boolean gender,String realname, Integer roleId) {
		Date now = new Timestamp(System.currentTimeMillis());
		Admin user = new Admin();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(pwdEncoder.encodePassword(password));
		user.setRegisterIp(ip);
		user.setRegisterTime(now);
		user.setLastLoginIp(ip);
		user.setLastLoginTime(now);
		//不强制验证邮箱直接激活
//		user.setActivation(true);
		user.init();
		user.setAdmin(true);
		user.setRank(rank);
		if (gender == null) {
			user.setGender(true);
		}else{
			user.setGender(gender);
		}
		user.setRealname(realname);		
		user.init();
		if (roleId != null) {
			user.setRole(cmsRoleMng.findById(roleId));
		}
		dao.save(user);
		
		return user;
	}


	public Admin updateAdmin(Admin bean, String password,Integer roleId) {
		Updater<Admin> updater = new Updater<Admin>(bean);
//		updater.include("email");
		Admin user = dao.updateByUpdater(updater);
		// 更新角色
		user.setRole(cmsRoleMng.findById(roleId));
		if (!StringUtils.isBlank(password)) {
			user.setPassword(pwdEncoder.encodePassword(password));
		}
		return user;
	}

	public Admin getByUsername(String username) {
		return dao.findByUsername(username);
	}
	public Admin login(String username, String password, String ip)
			throws UsernameNotFoundException, BadCredentialsException {
		Admin user = getByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("username not found: "
					+ username);
		}
		if (!pwdEncoder.isPasswordValid(user.getPassword(), password)) {
			updateLoginError(user.getId(), ip);
			throw new BadCredentialsException("password invalid");
		}
		updateLoginSuccess(user.getId(), ip);
		return user;
	}
	public void updateLoginSuccess(Integer userId, String ip) {
		Admin user = findById(userId);
		Date now = new Timestamp(System.currentTimeMillis());

		user.setLoginCount(user.getLoginCount() + 1);
		user.setLastLoginIp(ip);
		user.setLastLoginTime(now);

		user.setErrorCount(0);
		user.setErrorTime(null);
		user.setErrorIp(null);
	}
	
	public void updateLoginError(Integer userId, String ip) {
		Admin user = findById(userId);
		Date now = new Timestamp(System.currentTimeMillis());
		ConfigLogin configLogin = configMng.getConfigLogin();
		int errorInterval = configLogin.getErrorInterval();
		Date errorTime = user.getErrorTime();

		user.setErrorIp(ip);
		if (errorTime == null
				|| errorTime.getTime() + errorInterval * 60 * 1000 < now
						.getTime()) {
			user.setErrorTime(now);
			user.setErrorCount(1);
		} else {
			user.setErrorCount(user.getErrorCount() + 1);
		}
	}

	
	public Admin deleteById(Integer id) {
		Admin bean = dao.deleteById(id);
		return bean;
	}

	public Admin[] deleteByIds(Integer[] ids) {
		Admin[] beans = new Admin[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	public boolean usernameNotExist(String username) {
		return dao.countByUsername(username) <= 0;
	}
	

	public boolean emailNotExist(String email) {
		return dao.countByEmail(email) <= 0;
	}
	
	public Integer errorRemaining(String username) {
		if (StringUtils.isBlank(username)) {
			return null;
		}
		Admin user = getByUsername(username);
		if (user == null) {
			return null;
		}
		long now = System.currentTimeMillis();
		ConfigLogin configLogin = configMng.getConfigLogin();
		int maxErrorTimes = configLogin.getErrorTimes();
		int maxErrorInterval = configLogin.getErrorInterval() * 60 * 1000;
		Integer errorCount = user.getErrorCount();
		Date errorTime = user.getErrorTime();
		if (errorCount <= 0 || errorTime == null
				|| errorTime.getTime() + maxErrorInterval < now) {
			return maxErrorTimes;
		}
		return maxErrorTimes - errorCount;
	}

	private RoleMng cmsRoleMng;
//	private UnifiedUserMng unifiedUserMng;
	private AdminDao dao;


	@Autowired
	public void setCmsRoleMng(RoleMng cmsRoleMng) {
		this.cmsRoleMng = cmsRoleMng;
	}

//	@Autowired
//	public void setUnifiedUserMng(UnifiedUserMng unifiedUserMng) {
//		this.unifiedUserMng = unifiedUserMng;
//	}


	@Autowired
	public void setDao(AdminDao dao) {
		this.dao = dao;
	}
	private ConfigMng configMng;

	private PwdEncoder pwdEncoder;
	
	@Autowired
	public void setPwdEncoder(PwdEncoder pwdEncoder) {
		this.pwdEncoder = pwdEncoder;
	}
	@Autowired
	public void setConfigMng(ConfigMng configMng) {
		this.configMng = configMng;
	}

}