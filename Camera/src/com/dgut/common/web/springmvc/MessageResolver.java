package com.dgut.common.web.springmvc;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * 国际化信息帮助类
 * 
 * @author liufang
 * 
 */
public final class MessageResolver {
	/**
	 * 获得国际化信息
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param code
	 *            国际化代码
	 * @param args
	 *            替换参数
	 * @return
	 * @see org.springframework.context.MessageSource#getMessage(String,
	 *      Object[], Locale)
	 */
	public static String getMessage(HttpServletRequest request, String code,
			Object... args) {
		WebApplicationContext messageSource = RequestContextUtils
				.getWebApplicationContext(request);
		if (messageSource == null) {
			throw new IllegalStateException("WebApplicationContext not found!");
		}
		
		//拦截请求是那种语言只
		//需要在初次访问系统时，在地址拦上添加"?locale=en"即可访问英文网站。
		//注意，访问系统的第一个页面时添加即可，后绪可不再添加。


		LocaleResolver localeResolver = RequestContextUtils
				.getLocaleResolver(request);
		Locale locale;
		if (localeResolver != null) {
			locale = localeResolver.resolveLocale(request);
		} else {
			locale = request.getLocale();
		}
		return messageSource.getMessage(code, args, locale);
	}
}
