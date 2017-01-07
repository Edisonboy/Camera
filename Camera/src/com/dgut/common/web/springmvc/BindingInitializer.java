package com.dgut.common.web.springmvc;

import java.util.Date;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

/**
 * 数据绑定初始化类
 * 
 * @author liufang
 * 
 */
public class BindingInitializer extends ConfigurableWebBindingInitializer {
	/**
	 * 初始化数据绑定
	 */
	public void initBinder(WebDataBinder binder, WebRequest request) {
		super.initBinder(binder, request);
		binder.registerCustomEditor(Date.class, new DateTypeEditor());
	}
}

