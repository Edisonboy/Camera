package com.dgut.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.dgut.app.pck.GeneralRestGateway;
import com.dgut.app.pck.GeneralRestGatewayInterface;
import com.dgut.app.pck.JSONUtils;
import com.dgut.common.web.CookieUtils;
import com.dgut.common.web.session.SessionProvider;
import com.dgut.main.Constants;
import com.dgut.main.web.CmsUtils;
import com.dgut.member.manager.MemberAuthenticationMng;
import com.dgut.member.manager.impl.MemberAuthenticationMngImpl;


@Controller
public class AppController implements GeneralRestGatewayInterface {
	private static final Logger log = LoggerFactory
			.getLogger(AppController.class);

	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public void getIndex(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("state", "-1");
		jsonMap.put("msg", "请使用post请求");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(JSONUtils.printObject(jsonMap));
		out.flush();
		out.close();
	}

	@RequestMapping(value = "/index.do", method = RequestMethod.POST)
	public void index(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws IOException {
		StringBuilder errorDescription = new StringBuilder();
		int code = GeneralRestGateway.handle(Constants.APP_ENCRYPTION_KEY,
				3000, this, request, response, errorDescription);
		if (code < 0) {
			log.error("app请求失败:" + errorDescription.toString());
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put("state", "-1");
			jsonMap.put("msg", errorDescription.toString());
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(JSONUtils.printObject(jsonMap));
			out.flush();
			out.close();
		}
		
		
	}

	@Override
	public String delegateHandleRequest(HttpServletRequest request,
			HttpServletResponse response, Map<String, String> parameters,
			StringBuilder paramStringBuilder) throws RuntimeException {

		String result = null;
		Integer OPT = Integer.valueOf(parameters.get("opt"));
		
		//需要登录
		if (OPT != AppConstants.APP_REGISTER && OPT != AppConstants.APP_Login&& OPT != AppConstants.APP_Citys
				&& OPT!=AppConstants.APP_ForgetPassword) {
			if (CmsUtils.getMember(request) == null) {
				Map<String, Object> jsonMap = new HashMap<String, Object>();
				jsonMap.put("state", -2);
				jsonMap.put("msg", "太久未登录，请重新登录");
				try {
					return JSONUtils.printObject(jsonMap);
				} catch (IOException e) {
				}
			}
			
		}

		switch (OPT) {
		case AppConstants.APP_REGISTER:
			try {
				result = requestData.regist(request, response, parameters);
			} catch (IOException e) {
				log.error("注册时：" + e.getMessage());
			}
			break;
		case AppConstants.APP_Login:
			try {
				result = requestData.login(request, response, parameters);
			} catch (IOException e) {
				log.error("登录时：" + e.getMessage());
			}
			break;
		case AppConstants.APP_ForgetPassword:
			try {
				result = requestData.forgetPwd(request, response, parameters);
			} catch (IOException e) {
				log.error("忘记密码时：" + e.getMessage());
			}
			break;
		case AppConstants.APP_USERID:
			try{
				result = requestData.retrieveUserId(request, response, parameters);
			}catch(IOException e){
				log.error("户号检索错误时：" + e.getMessage());
			}
			break;
		case AppConstants.APP_RECORD:
			try{
				result = requestData.retrieveRecord(request, response, parameters);
			}catch(IOException e){
				log.error("上传记录信息错误时：" + e.getMessage());
			}
			break;
		case AppConstants.APP_IRECORD:
			try{
				result = requestData.retrieveIRecord(request, response, parameters);
			}catch(IOException e){
				log.error("上传记录具体信息错误时：" + e.getMessage());
			}
			break;

		default:
			break;
		}
		return result;
	}
	

	@Autowired
	private RequestData requestData;
	

}
