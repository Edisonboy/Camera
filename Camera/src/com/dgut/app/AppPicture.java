package com.dgut.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.dgut.app.pck.GeneralRestGatewayInterface;
import com.dgut.app.pck.JSONUtils;
import com.dgut.common.file.FileNameUtils;
import com.dgut.common.image.AverageImageScale;
import com.dgut.common.image.ImageScaleImpl;
import com.dgut.common.image.ImageUtils;
import com.dgut.common.util.DateUtils;
import com.dgut.main.entity.InstallationRecord;
import com.dgut.main.manager.InstallationRecordMng;
import com.dgut.main.web.CmsUtils;
import com.dgut.member.entity.Member;
import com.dgut.member.entity.MemberPhoto;
import com.dgut.member.entity.Record;
import com.dgut.member.entity.MemberPhoto;
import com.dgut.member.manager.MemberMng;
import com.dgut.member.manager.MemberPhotoMng;
import com.dgut.member.manager.RecordMng;


@Controller
public class AppPicture {
	private static final Logger log = LoggerFactory
			.getLogger(AppPicture.class);
	

	
	
	@RequestMapping(value = "/sendPicture.do", method = RequestMethod.POST)
	public void sendPicture(HttpServletRequest request, HttpServletResponse response,ModelMap model,
			@RequestParam("image") MultipartFile []file,@RequestParam String time) 
					throws IOException, FileUploadException, ParseException {
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
	    
		String folderName = request.getParameter("folderName");
		if(StringUtils.isEmpty(folderName)){
			jsonMap.put("msg", "文件夹名称不能为空");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(JSONUtils.printObject(jsonMap));
			out.flush();
			out.close();
		}
		Member m = CmsUtils.getMember(request);
	    Date date = DateUtils.format2.parse(time);
	    
		if(file!=null){
			for(int i=0;i<file.length;i++){
				String fileName = file[i].getOriginalFilename();  //上传文件名称
				String ext = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
				String destName = FileNameUtils.genFileName(ext);  
				File maxFile = toFile(request, folderName, destName,true);
				FileUtils.writeByteArrayToFile(maxFile, file[i].getBytes());
				
				File minFile = toFile(request, folderName, destName,false);
				//FileUtils.writeByteArrayToFile(minFile, file[i].getBytes());
				AverageImageScale.resizeFix(mulTofile(file[i]) , minFile, 160, 160);
				
				//member-userNo关联
				Record bean = recordMng.findByUserNo(folderName);
				if(bean==null){
					Record record = new Record();
					record.setMember(m);
					record.setUserNo(folderName);
					record.setTime(date);
					recordMng.save(record);
				}
				
				//userNo-photo关联
			    MemberPhoto mp = new MemberPhoto();
			    mp.setUserNo(folderName);
			    mp.setPhoto(destName);
			    mp.setMaxUrl(databasePath(request,folderName,destName,true));
			    mp.setMinUrl(databasePath(request,folderName,destName,false));
			    memberPhotoMng.save(mp);
			    
			    //设置用户安装记录---flag=true
			    List<InstallationRecord> irList = irMng.findUserId(folderName);
			    if(irList.size()>0){
			    	for(InstallationRecord ir : irList){
			    		ir.setDate(date);
			    		ir.setFlag(true);
			    		irMng.save(ir);
			    	}
			    }
			    jsonMap.put("state", "0");
			    jsonMap.put("msg", "上传成功");
			}
		}else{
			jsonMap.put("state", "-1");
			jsonMap.put("msg", "上传失败");
		}
		
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(JSONUtils.printObject(jsonMap));
		out.flush();
		out.close();
		
	}
	
	
	//图片保存系统盘的路径
	public static String filePath(HttpServletRequest request){
		return request.getServletContext().getRealPath("/");
	}
	
	
	//数据库url路径
	public static String databasePath(HttpServletRequest request,String folderName,String destName,boolean flag){
		if(flag){
			return request.getContextPath()+"/photo/"+folderName+"/"+destName;   //大图
		}else{
			return request.getContextPath()+"/photo/"+folderName+"/min/"+destName;   //小图
		}
	}
	
	
	//文件夹路径
	public static File toFile(HttpServletRequest request,String folderName,String destName,boolean flag){
		if(flag){
			File maxFile = new File(filePath(request)+"/photo/"+folderName,destName);  //大图
			return maxFile;
		}else{
			File minFile = new File(filePath(request)+"/photo/"+folderName+"/min/",destName);  //小图
			return minFile;
		}
	}
	
	public static File mulTofile(MultipartFile file){
		CommonsMultipartFile cf = (CommonsMultipartFile) file;
		DiskFileItem fi = (DiskFileItem) cf.getFileItem();
		return fi.getStoreLocation();
	}
	
	
	@Autowired
	private MemberPhotoMng memberPhotoMng;
	
	@Autowired
	private RecordMng recordMng;
	
	@Autowired
	private InstallationRecordMng irMng;
	
	@Autowired
	private MemberMng memberMng;
}

