package com.dgut.app;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dgut.app.pck.Encrypt;
import com.dgut.app.pck.JSONUtils;
import com.dgut.common.file.FileNameUtils;
import com.dgut.common.security.BadCredentialsException;
import com.dgut.common.security.DisabledException;
import com.dgut.common.security.UsernameNotFoundException;
import com.dgut.common.sns.spi.SmsWebApiKit;
import com.dgut.common.util.DateUtils;
import com.dgut.common.util.StrUtils;
import com.dgut.common.web.CookieUtils;
import com.dgut.common.web.RequestUtils;
import com.dgut.main.Constants;
import com.dgut.main.entity.InstallationRecord;
import com.dgut.main.exception.InstallationRecordException;
import com.dgut.main.manager.InstallationRecordMng;
import com.dgut.main.web.CmsUtils;
import com.dgut.member.entity.Member;
import com.dgut.member.entity.MemberAuthentication;
import com.dgut.member.entity.MemberPhoto;
import com.dgut.member.entity.Record;
import com.dgut.member.manager.MemberAuthenticationMng;
import com.dgut.member.manager.MemberLogMng;
import com.dgut.member.manager.MemberMng;
import com.dgut.member.manager.MemberPhotoMng;
import com.dgut.member.manager.RecordMng;
import com.dgut.member.manager.impl.MemberAuthenticationMngImpl;

@Service
public class RequestData {

	/**
	 * 注册 opt = 1
	 * @throws IOException
	 */
	public String regist(HttpServletRequest request,
			HttpServletResponse response, Map<String, String> parameters)
			throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		String mobile = parameters.get("mobile");
		String password = parameters.get("pwd");
		String code = parameters.get("code");
		if (StringUtils.isBlank(password)) {
			jsonMap.put("state", "-3");
			jsonMap.put("msg", "密码不能为空");
			return JSONUtils.printObject(jsonMap);
		}

		password = Encrypt.decrypt3DES(password, Constants.ENCRYPTION_KEY);
		
		Map<String, String> m = RequestData.codeVerify(mobile, code);
		if (m.get("error").equals("-3")) {
			jsonMap.put("state", "-3");
			jsonMap.put("msg", m.get("msg"));
			return JSONUtils.printObject(jsonMap);
		}
		
		boolean notExist = memberMng.usernameNotExist(mobile);
		if(notExist==false){
			jsonMap.put("state", "-3");
			jsonMap.put("msg", "该手机号码已注册");
			return JSONUtils.printObject(jsonMap);
		}
		String ip = RequestUtils.getIpAddr(request);
		Member member = memberMng.saveMember(mobile, password, ip, null	, null);
		jsonMap.put("state", "0");
		jsonMap.put("msg", "注册成功");
		jsonMap.put("phone", member.getUsername());
		jsonMap.put("realname", member.getRealname());
		jsonMap.put("uid", Encrypt.encrypt3DES(member.getId() + "",
				Constants.ENCRYPTION_KEY));
		return JSONUtils.printObject(jsonMap);
	}
	/**
	 * 手机验证码验证
	 * @return
	 */
	public static Map<String, String> codeVerify(String mobile,String code)
	{
		Map<String, String> jsonMap = new HashMap<String, String>();
		if (!StrUtils.isMobileNum(mobile)) {
			jsonMap.put("state", "-3");
			jsonMap.put("msg", "手机号码错误");
			return jsonMap;
		}
		if (StringUtils.isBlank(code)) {
			jsonMap.put("state", "-3");
			jsonMap.put("msg", "验证码不能为空");
			return jsonMap;
		}
		try {
			String c = SmsWebApiKit.checkcode(mobile, "86", code);
			if (c.equals("200")) {
				jsonMap.put("state", "0");
				jsonMap.put("msg", "验证成功");
			}else if(c.equals("468")){
				jsonMap.put("state", "-3");
				jsonMap.put("msg", "验证码错误");
			}else if(c.equals("467")){
				jsonMap.put("state", "-3");
				jsonMap.put("msg", "请求校验验证码频繁");
			}else {
				jsonMap.put("state", "-3");
				jsonMap.put("msg", "code:"+c);
			}
		} catch (Exception e) {
			jsonMap.put("state", "-3");
			jsonMap.put("msg", "请求失败"+e.getLocalizedMessage());
		}
		
		return jsonMap;
	}
	/**
	 * 用户登录(opt=2) 
	 * 登录成功后返回的token时长是2天，也就是2天内没操作需要重新登录
	 * 如果在2天内的时候登录的话，token时长重新计算
	 * 
	 * @param mobile
	 *            用户名
	 * @param pwd
	 *            密码
	 * @throws IOException
	 */
	public String login(HttpServletRequest request,
			HttpServletResponse response, Map<String, String> parameters)
			throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String mobile = parameters.get("mobile");
		String password = parameters.get("pwd");

		if (StringUtils.isBlank(mobile)) {
			jsonMap.put("state", "-1");
			jsonMap.put("msg", "请输入手机号码");
			return JSONUtils.printObject(jsonMap);
		}
		if(!StrUtils.isMobileNum(mobile)){
			jsonMap.put("state", "-2");
			jsonMap.put("msg", "请输入正确的手机号码");
			return JSONUtils.printObject(jsonMap);
		}

		if (StringUtils.isBlank(password)) {
			jsonMap.put("state", "-3");
			jsonMap.put("msg", "请输入密码");
			return JSONUtils.printObject(jsonMap);
		}

		password = Encrypt.decrypt3DES(password, Constants.ENCRYPTION_KEY);
		System.out.println(password);
		String ip = RequestUtils.getIpAddr(request);
		Member member;
		MemberAuthentication auth;
		try {
			auth = memberAuthenMng
					.login(mobile, password, ip, request, response, session);
			memberMng.updateLoginInfo(auth.getUid(), ip);
			member = memberMng.findById(auth.getUid());
			if (member.getDisabled()) {
				// 如果已经禁用，则退出登录。
				memberAuthenMng.deleteById(auth.getId());
				session.logout(request, response);
				throw new DisabledException("user disabled");
			}
			memberLogMng.loginSuccess(request, member, "登录成功");
		} catch (UsernameNotFoundException e) {
			jsonMap.put("state", "-4");
			jsonMap.put("msg", "用户名不存在");
			return JSONUtils.printObject(jsonMap);
		} catch (BadCredentialsException e) {
			jsonMap.put("state", "-5");
			jsonMap.put("msg", "用户名或密码错误");
			return JSONUtils.printObject(jsonMap);
		} catch (DisabledException e) {
			jsonMap.put("state", "-6");
			jsonMap.put("msg", "账户被禁用，请联系管理员");
			return JSONUtils.printObject(jsonMap);
		}
		jsonMap.put("state", "0");
		jsonMap.put("msg", "登录成功");
		jsonMap.put("mobile", member.getUsername());
		jsonMap.put("realname", member.getRealname());
		jsonMap.put("uid", Encrypt.encrypt3DES(member.getId() + "",
				Constants.ENCRYPTION_KEY));
		jsonMap.put("loginCount", member.getLoginCount());
		jsonMap.put("lastLoginIp", member.getLastLoginIp());
		jsonMap.put("lastLoginTime",
				DateUtils.dateToString(member.getLastLoginTime()));
		jsonMap.put("token", auth.getId());
		jsonMap.put("expires_in", MemberAuthenticationMngImpl.timeout/1000);
		return JSONUtils.printObject(jsonMap);
	}
	/**
	 * 忘记密码 opt = 3
	 */
	public String forgetPwd(HttpServletRequest request,
			HttpServletResponse response, Map<String, String> parameters)
			throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		String mobile = parameters.get("mobile");
		String password = parameters.get("pwd");
		String code = parameters.get("code");
		if (StringUtils.isBlank(password)) {
			jsonMap.put("error", "-3");
			jsonMap.put("msg", "密码不能为空");
			return JSONUtils.printObject(jsonMap);
		}

		password = Encrypt.decrypt3DES(password, Constants.ENCRYPTION_KEY);
		
		Map<String, String> m = RequestData.codeVerify(mobile, code);
		if (m.get("error").equals("-3")) {
			jsonMap.put("error", "-3");
			jsonMap.put("msg", m.get("msg"));
			return JSONUtils.printObject(jsonMap);
		}
		
		Member member = memberMng.findByUsername(mobile);
		if(member==null){
			jsonMap.put("error", "-3");
			jsonMap.put("msg", "手机号码未注册");
			return JSONUtils.printObject(jsonMap);
		}
		member = memberMng.updateMember(member, password);
		jsonMap.put("error", "0");
		jsonMap.put("msg", "更新密码成功");
		return JSONUtils.printObject(jsonMap);
	}
	
	
	/**
	 * 检索userId opt = 6
	 */
	public String retrieveUserId(HttpServletRequest request,
			HttpServletResponse response, Map<String, String> parameters)
			throws IOException {
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String userNo = parameters.get("userNo");
		
		List<InstallationRecord> userList = irMng.findUserId(userNo);
		
		//过滤重复元素
		filterRepeat(userList);
/*		for(int i=0 ;i<userList.size()-1 ;i ++)  {       
			for(int j  = userList.size() - 1 ;j > i;j -- )  {       
	           if  (userList.get(j).getUserNo().equals(userList.get(i).getUserNo()))  {       
	        	   userList.remove(j);       
	            }        
	        }        
	    } */
		
		for(InstallationRecord  s : userList){
			System.out.println(s.getUserNo());
		}
		if(userList.size()>0){
			Map<String,Object> map = new HashMap<String,Object>();
			Map[] mapl = new Map[userList.size()];
			
			int i = 0;
			for(InstallationRecord s : userList){
				mapl[i] = new HashMap(); 
				mapl[i].put("id",s.getUserNo());
				mapl[i].put("name",s.getUserName());
				mapl[i].put("address", s.getAddress());
				mapl[i].put("worksheet", s.getWorksheetNo());
				i++;
			}
			jsonMap.put("data", mapl);
			jsonMap.put("state", "0");
			jsonMap.put("msg", "检索成功");
		}else{
			jsonMap.put("state", "-6");
			jsonMap.put("msg", "无此户号");
		}
		return JSONUtils.printObject(jsonMap);
	}
	
	
	/**
	 * 获取用户对应的用户信息  opt = 7
	 */
	public String retrieveRecord(HttpServletRequest request,
			HttpServletResponse response, Map<String, String> parameters)
			throws IOException,InstallationRecordException{
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		String mobile = parameters.get("mobile");
		if(StringUtils.isBlank(mobile)){
			jsonMap.put("state", "0");
			jsonMap.put("msg", "当前用户不存在");
			return JSONUtils.printObject(jsonMap);
		}
		Member member = memberMng.findByUsername(mobile);
		List<Record> list = recordMng.findListByUserNo(member.getId());
		if(list.size()<=0){
			jsonMap.put("state", "0");
			jsonMap.put("msg", "当前用户无上传记录");
			return JSONUtils.printObject(jsonMap);
		}
		//Map<String,Object> recordMap = new HashMap<String, Object>();
		List<String> ulist = new ArrayList<String>();
		try {
			for(Record record : list){
				//recordMap.put(record.getUserNo(), record.getTime());
				ulist.add(record.getUserNo());
			}
		} catch (InstallationRecordException e) {
			jsonMap.put("state", "-7");
			jsonMap.put("msg", "上传记录信息异常");
			return JSONUtils.printObject(jsonMap);
		}
		List<InstallationRecord> irlist = irMng.findList(ulist);
		filterRepeat(irlist);
		if(irlist.size()>0){
			Map<String,Object> map = new HashMap<String,Object>();
			Map[] mapl = new Map[irlist.size()];
			int i = 0;
			for(InstallationRecord s : irlist){
				mapl[i] = new HashMap(); 
				mapl[i].put("recordNo", s.getRecordNo());
				mapl[i].put("id",s.getUserNo());
				mapl[i].put("name",s.getUserName());
				mapl[i].put("address", s.getAddress());
				mapl[i].put("worksheet", s.getWorksheetNo());
				mapl[i].put("time", DateUtils.format2.format(s.getDate()));
				i++;
			}
			jsonMap.put("data", mapl);
			jsonMap.put("state", "0");
			jsonMap.put("msg", "获取成功");
		}else{
			jsonMap.put("state", "-7");
			jsonMap.put("msg", "上传记录信息错误");
		}
		return JSONUtils.printObject(jsonMap);
	}
	
	
	/**
	 * 获取工作记录具体信息 opt = 8
	 * @throws Exception 
	 */
	public String retrieveIRecord(HttpServletRequest request,
			HttpServletResponse response, Map<String, String> parameters)
					throws IOException{
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String recordNo = parameters.get("recordNo");
		if(StringUtils.isBlank(recordNo)){
			jsonMap.put("state", "-8");
			jsonMap.put("msg", "工作记录Id不能为空");
			return JSONUtils.printObject(jsonMap);
		}System.out.println(recordNo);
		InstallationRecord bean = irMng.findById(Integer.parseInt(recordNo));
	
		if(bean!=null){
			
			Map<String, Object> mapl = null;
			try {
				mapl = toMap(bean);
			} catch (IllegalArgumentException e) {
				jsonMap.put("state", "-8");
				jsonMap.put("msg", "信息错误");
			} catch (IllegalAccessException e) {
				jsonMap.put("state", "-8");
				jsonMap.put("msg", "信息错误");
			}
			//List<String> photolist = mpMng.getUrls(bean.getUserNo());
			List<MemberPhoto> beanlist = mpMng.getPhoto(bean.getUserNo());
			List<String> maxUrl = new ArrayList<String>();
			List<String> minUrl = new ArrayList<String>();
			for(MemberPhoto mm : beanlist){
				maxUrl.add(mm.getMaxUrl());
				minUrl.add(mm.getMinUrl());
			}
			
			jsonMap.put("data", mapl);
			jsonMap.put("maxUrl", maxUrl);
			jsonMap.put("minUrl", minUrl);
			jsonMap.put("state", "0");
			jsonMap.put("msg", "获取成功");
		}else{
			jsonMap.put("state", "0");
			jsonMap.put("msg", "无此户号");
		}
		return JSONUtils.printObject(jsonMap);
	}
	
	
	
	//对象-> Map
	public Map<String,Object> toMap(Object bean) throws IllegalArgumentException, IllegalAccessException {
		Map<String,Object> map = new HashMap<String,Object>();
		Field[] declaredFileds = bean.getClass().getSuperclass().getDeclaredFields();
		for(Field field:declaredFileds){ 
			field.setAccessible(true);
			System.out.println(field.get(bean));
			if(field.get(bean)!=null){
				map.put(field.getName(), field.get(bean).toString());
			}else{
				map.put(field.getName(), "");
			}
		}
		return map;
	}
	
	
	
	public void filterRepeat(List<InstallationRecord> list){
		for(int i=0 ;i<list.size()-1 ;i ++)  {       
			for(int j  = list.size() - 1 ;j > i;j -- )  {       
	           if  (list.get(j).getUserNo().equals(list.get(i).getUserNo()))  {       
	        	   list.remove(j);       
	            }        
	        }        
	    } 
	}

	
	@Autowired
	private MemberAuthenticationMng memberAuthenMng;

	@Autowired
	private com.dgut.common.web.session.SessionProvider session;
	
	@Autowired
	private MemberMng memberMng;
	
	@Autowired
	private MemberLogMng memberLogMng;
	
	@Autowired
	private InstallationRecordMng irMng;
	
	@Autowired
	private RecordMng recordMng;
	
	@Autowired
	private MemberPhotoMng mpMng;

}
