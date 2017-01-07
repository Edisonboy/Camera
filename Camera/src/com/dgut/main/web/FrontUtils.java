package com.dgut.main.web;

import static com.dgut.common.web.Constants.MESSAGE;
import static com.dgut.common.web.Constants.UTF8;
import static com.dgut.common.web.ProcessTimeFilter.START_TIME;
import static com.dgut.common.web.freemarker.DirectiveUtils.PARAM_TPL_SUB;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.LocaleEditor;
import org.springframework.context.MessageSource;

import com.dgut.common.web.RequestUtils;
import com.dgut.common.web.freemarker.DirectiveUtils;
import com.dgut.common.web.springmvc.MessageResolver;
import com.dgut.main.entity.Admin;
import com.sun.org.omg.SendingContext.CodeBasePackage.URLHelper;

import freemarker.core.Environment;
import freemarker.template.AdapterTemplateModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;

/**
 * 前台工具类
 * 
 * 
 */
public class FrontUtils {

	/**
	 * 用户
	 */
	public static final String USER = "user";
	public static final String Title = "工作空间管理系统";
	/**
	 * 为前台模板设置公用数据
	 * 
	 * @param request
	 * @param model
	 */
	public static void adminData(HttpServletRequest request,
			Map<String, Object> map) {
		 Admin user = CmsUtils.getAdmin(request);
		if (user != null) {
			map.put(USER, user);
		}
		map.put("title", Title);
	}


}
