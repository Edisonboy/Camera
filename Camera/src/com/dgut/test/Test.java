package com.dgut.test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dgut.app.pck.JSONUtils;
import com.dgut.common.page.Pagination;
import com.dgut.common.security.BadCredentialsException;
import com.dgut.common.security.UsernameNotFoundException;
import com.dgut.main.entity.InstallationRecord;
import com.dgut.main.entity.WorkSheet;
import com.dgut.main.manager.InstallationRecordMng;
import com.dgut.main.manager.WorkSheetMng;
import com.dgut.member.entity.Member;
import com.dgut.member.entity.MemberPhoto;
import com.dgut.member.entity.Record;
import com.dgut.member.entity.MemberPhoto;
import com.dgut.member.manager.MemberMng;
import com.dgut.member.manager.MemberPhotoMng;
import com.dgut.member.manager.RecordMng;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:WebRoot/WEB-INF/config/test/*.xml"})
public class Test {

	
	@Autowired
	private MemberMng memberMng;
	
	@Autowired
	private MemberPhotoMng memberPhotoMng;
	
	@Autowired
	private InstallationRecordMng irMng;
	
	@Autowired
	private RecordMng recordMng;
	
	@Autowired
	private WorkSheetMng workSheetMng;
	
	 @org.junit.Test
	 public void testD() throws UsernameNotFoundException, BadCredentialsException {
		InstallationRecord i = new InstallationRecord();
		i.setAddress("aaaaaa");
		irMng.save(i);
	 }
	 
	 
	 @org.junit.Test
	 public void testPhoto(){
		Member m = memberMng.findById(1);
		MemberPhoto mp = new MemberPhoto();
		mp.setPhoto("bb");
		memberPhotoMng.save(mp);
	 }
	 
	 @org.junit.Test
	 public void test2(){
		 
		 
		 InstallationRecord userList = irMng.findById(246123123);
//		 for(InstallationRecord bean : userList){
			 System.out.println(userList.getUserName());
//		 }
		
	 }
	 
	 @org.junit.Test
	 public void test3(){
		 List<MemberPhoto> photoList = memberPhotoMng.getAll();
		 for(MemberPhoto p : photoList){
			 System.out.println(p.getPhoto());
		 }
	 }
	 
	 @org.junit.Test
	 public void test4() throws IllegalArgumentException, IllegalAccessException, IOException{
		InstallationRecord bean = irMng.findById(242);
		Map<String, Object> jsonMap = new HashMap<String, Object>();

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
		List<String> list = memberPhotoMng.getUrls(bean.getUserNo());
		List<String> photolist = new ArrayList<String>();   HttpServletRequest request = null;
		String savePath = request.getContextPath();
		for(String s : list){
			String ss = savePath + "/photo/" + bean.getUserNo() + "/" + s;
			photolist.add(ss);
		}
		jsonMap.put("date", map);
		jsonMap.put("photoUrl", photolist);
		System.out.println(JSONUtils.printObject(jsonMap));
	 }
	 
	
}
