package com.dgut.main.web;

import static com.dgut.common.web.Constants.MESSAGE;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import com.dgut.common.web.session.SessionProvider;
import com.dgut.common.web.springmvc.MessageResolver;
import com.dgut.main.entity.Admin;
import com.dgut.main.manager.AdminMng;
import com.dgut.main.manager.AuthenticationMng;
/**
 * 上下文信息拦截器
 * 
 * 包括登录信息、权限信息
 * 
 */

public class AdminContextInterceptor extends HandlerInterceptorAdapter {
	private static final Logger log = Logger
			.getLogger(AdminContextInterceptor.class);
	public static final String PERMISSION_MODEL = "_permission_key";


public static final String PROCESS_URL = "processUrl";
public static final String RETURN_URL = "returnUrl";

//	HTTPServletResponse. SC_BAD_REQUEST：对应的响应状态代码为400。
//
//	HTTPServletResponse. SC_FOUND：对应的响应状态代码为302。
//
//	HTTPServletResponse. SC_METHOD_NOT_ALLOWED：对应的响应状态代码为405。
//
//	HTTPServletResponse. SC_NON_AUTHORITATIVE_INFORMATION：对应的响应状态代码为203。
//
//	HTTPServletResponse. SC_FORBIDDEN：对应的响应状态代码为403。
//
//	HTTPServletResponse. SC_NOT_FOUND：对应的响应状态代码为404。
//
//	HTTPServletResponse. SC_OK：对应的响应状态代码为200。


	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 获得用户
		Admin user = null;
		if (adminId != null) {
			// 指定管理员（开发状态）
			user = cmsUserMng.findById(adminId);
			if (user == null) {
				throw new IllegalStateException("User ID=" + adminId
						+ " not found!");
			}
		} else {
			// 正常状态
			Integer userId = authMng
					.retrieveUserIdFromSession(session, request);
			if (userId != null) {
				user = cmsUserMng.findById(userId);
			}
		}
		// 此时用户可以为null
		CmsUtils.setAdmin(request, user);
		// User加入线程变量
		AdminThreadVariable.setUser(user);

		String uri = getURI(request);
		// 不在验证的范围内
		if (exclude(uri)) {
			return true;
		}
		// 用户为null跳转到登陆页面
		if (user == null) {
			response.sendRedirect(getLoginUrl(request));
			return false;
		}
		// 用户不是管理员，提示无权限。
		if (!user.getAdmin()) {
			request.setAttribute(MESSAGE, MessageResolver.getMessage(request,
					"login.notAdmin"));
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return false;
		}
		// 没有访问权限，提示无权限。
		if (auth && !user.isSuper()
				&& !permistionPass(uri, user.getPerms(), false)) {
			request.setAttribute(MESSAGE, MessageResolver.getMessage(request,
					"login.notPermission"));
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler, ModelAndView mav)
			throws Exception {
		Admin user = CmsUtils.getAdmin(request);
		// 不控制权限时perm为null，PermistionDirective标签将以此作为依据不处理权限问题。
		if (auth && user != null && !user.isSuper() && mav != null
				&& mav.getModelMap() != null && mav.getViewName() != null
				&& !mav.getViewName().startsWith("redirect:")) {
			mav.getModelMap().addAttribute(PERMISSION_MODEL, user.getPerms());
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// Sevlet容器有可能使用线程池，所以必须手动清空线程变量。
		AdminThreadVariable.removeUser();
	}

	private String getLoginUrl(HttpServletRequest request) {
		StringBuilder buff = new StringBuilder();
		if (loginUrl.startsWith("/")) {
			String ctx = request.getContextPath();
			if (!StringUtils.isBlank(ctx)) {
				buff.append(ctx);
			}
		}
		buff.append(loginUrl).append("?");
		buff.append(RETURN_URL).append("=").append(returnUrl);
		if (!StringUtils.isBlank(processUrl)) {
			buff.append("&").append(PROCESS_URL).append("=").append(
					getProcessUrl(request));
		}
		return buff.toString();
	}

	private String getProcessUrl(HttpServletRequest request) {
		StringBuilder buff = new StringBuilder();
		if (loginUrl.startsWith("/")) {
			String ctx = request.getContextPath();
			if (!StringUtils.isBlank(ctx)) {
				buff.append(ctx);
			}
		}
		buff.append(processUrl);
		return buff.toString();
	}
//
//
//	private CmsSite getByCookie(HttpServletRequest request) {
//		Cookie cookie = CookieUtils.getCookie(request, SITE_COOKIE);
//		if (cookie != null) {
//			String v = cookie.getValue();
//			if (!StringUtils.isBlank(v)) {
//				try {
//					Integer siteId = Integer.parseInt(v);
//					return cmsSiteMng.findById(siteId);
//				} catch (NumberFormatException e) {
//					log.warn("cookie site id format exception", e);
//				}
//			}
//		}
//		return null;
//	}
//
	private boolean exclude(String uri) {
		if (excludeUrls != null) {//	/login.do,/logout.do
			
			for (String exc : excludeUrls) {
				if (exc.equals(uri)) {
					return true;
				}
			}
		}
		return false;
	}
//
	private boolean permistionPass(String uri, Set<String> perms,
			boolean viewOnly) {
		String u = null;
		int i;
		for (String perm : perms) {
			if (uri.startsWith(perm)) {
				// 只读管理员
				if (viewOnly) {
					// 获得最后一个 '/' 的URI地址。
					i = uri.lastIndexOf("/");
					if (i == -1) {
						throw new RuntimeException("uri must start width '/':"
								+ uri);
					}
					u = uri.substring(i + 1);
					// 操作型地址被禁止
					if (u.startsWith("o_")) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * 获得第三个路径分隔符的位置
	 * 
	 * @param request
	 * @throws IllegalStateException
	 *             访问路径错误，没有三(四)个'/'
	 */
	private static String getURI(HttpServletRequest request)
			throws IllegalStateException {
		UrlPathHelper helper = new UrlPathHelper();
		String uri = helper.getOriginatingRequestUri(request);///cms/jeeadmin/jeecms/login.do
		String ctxPath = helper.getOriginatingContextPath(request);//cms
		int start = 0, i = 0, count = 1;
		if (!StringUtils.isBlank(ctxPath)) {
			count++;
		}
		while (i < count && start != -1) {
			start = uri.indexOf('/', start + 1);
			i++;
		}
		if (start <= 0) {
			throw new IllegalStateException(
					"admin access path not like '/jeeadmin/jspgou/...' pattern: "
							+ uri);
		}
		return uri.substring(start);
	}

	private SessionProvider session;
	private AuthenticationMng authMng;
	private AdminMng cmsUserMng;
	private Integer adminId;
	private boolean auth = true;
	private String[] excludeUrls;
	private String loginUrl;
	private String processUrl;
	private String returnUrl;

	@Autowired
	public void setSession(SessionProvider session) {
		this.session = session;
	}


	@Autowired
	public void setCmsUserMng(AdminMng cmsUserMng) {
		this.cmsUserMng = cmsUserMng;
	}

	@Autowired
	public void setAuthMng(AuthenticationMng authMng) {
		this.authMng = authMng;
	}

	public void setAuth(boolean auth) {
		this.auth = auth;
	}

	public void setExcludeUrls(String[] excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public void setProcessUrl(String processUrl) {
		this.processUrl = processUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
}