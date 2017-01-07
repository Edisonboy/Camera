package com.dgut.main.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dgut.common.page.PageResponse;
import com.dgut.common.page.Pagination;
import com.dgut.main.dto.MemberPhotoDto;
import com.dgut.main.entity.InstallationRecord;
import com.dgut.main.manager.InstallationRecordMng;
import com.dgut.main.manager.impl.InstallationRecordMngImpl;
import com.dgut.member.entity.Member;
import com.dgut.member.entity.MemberPhoto;
import com.dgut.member.entity.Record;
import com.dgut.member.entity.MemberPhoto;
import com.dgut.member.manager.MemberPhotoMng;
import com.dgut.member.manager.RecordMng;
import org.apache.tools.ant.Project;    
import org.apache.tools.ant.taskdefs.Zip;    
import org.apache.tools.ant.types.FileSet; 

@Controller
public class PhotoAct {

	@Autowired
	private MemberPhotoMng memberPhotoMng;
	
	@Autowired
	private RecordMng recordMng;
	
	@Autowired
	private InstallationRecordMng irMng;
	

	
	@RequestMapping("/photoList.do")
	public String add(HttpServletRequest request, ModelMap model){
		return "admin/photoList";
	}
	
	
	@RequestMapping("/getphotoList.do")
	public @ResponseBody PageResponse<MemberPhotoDto> getphotoList(HttpServletRequest request, ModelMap model
			, int pageNumber, int pageSize, String sortOrder, 
			String name, String userNo, String userName, Date start, Date end){
		

		Map<String,Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("userNo", userNo);
		map.put("userName", userName);
		map.put("start", start);
		map.put("end", end);
		
		List<MemberPhotoDto> photoListDto = new ArrayList<MemberPhotoDto>();
		Pagination pagination = recordMng.getCurPageStu(map,pageNumber, pageSize,sortOrder);
		List<Record> recordList = (List<Record>) pagination.getList();
		
		for(Record r : recordList){
			List<InstallationRecord> beanlist = irMng.findByUserNo(r.getUserNo());
			MemberPhotoDto mdto = new MemberPhotoDto();
			mdto.setId(r.getId());
			mdto.setName(r.getMember().getUsername());   //上传用户
			mdto.setUserNo(r.getUserNo());   //户号
			mdto.setUserName(beanlist.get(0).getUserName());  //户名
			mdto.setTime(r.getTime());
			photoListDto.add(mdto);
		}
		PageResponse<MemberPhotoDto> p = new PageResponse<MemberPhotoDto>();
		p.setRecords(photoListDto);
		p.setTotal(photoListDto.size());
		return p;
	}
	
	
	@RequestMapping("/getphoto.do")
	public String getPhoto(HttpServletRequest request, ModelMap model){
		
		//List <MemberPhoto> photoList = new ArrayList<MemberPhoto>();
		String savePath = request.getContextPath();   System.out.println(savePath);
		String userNo = request.getParameter("userNo");
		if(userNo!=null){
			List<MemberPhoto> pl = memberPhotoMng.getPhoto(userNo);
			/*for(MemberPhoto mp : pl){
				String ss = savePath + "/photo/" + userNo + "/" + mp.getPhoto();
				mp.setPhoto(ss);
				photoList.add(mp);
			}*/
			model.addAttribute("photoList",pl);
		}
		return "admin/photo";
	}
	
	
	
	@RequestMapping("/download.do")
	public @ResponseBody Map<String,Object> download(HttpServletRequest request,HttpServletResponse response, ModelMap model,
			String photoId) throws IOException{
		
		Map<String,Object> map = new HashMap<String,Object>();
		String path = request.getServletContext().getRealPath("/")+"photo/";//图片路径
		String userNo = request.getParameter("userNo");   
		
		//下载文件夹
		if(userNo!=null){    
			List<InstallationRecord> irlist = irMng.findByUserNo(userNo);
			String zipPathName = path+userNo+".zip";     
			File zipFile = new File(zipPathName);      
			String srcPathName = path + userNo; //源文件夹路径  
			compressExe(srcPathName,zipFile);
			downloadUtils(request, response, userNo+irlist.get(0).getUserName()+".zip", zipPathName);//户号+户名
			
			map.put("msg", "下载成功!");
		}
		//图片下载
		if(photoId!=null){   
			MemberPhoto mp = memberPhotoMng.findById(Integer.parseInt(photoId));
			if(mp!=null){
				String srcName = path+ File.separator + mp.getUserNo() + File.separator +mp.getPhoto();
				downloadUtils(request, response, mp.getPhoto(), srcName) ;
				map.put("msg", "下载成功!");
			}
		}
		return map;
	}
	
	
	/**
	 * 下载
	 * @param request
	 * @param response
	 * @param fileName 输出文件名
	 * @param srcName  源文件
	 * @throws IOException
	 */
	public void downloadUtils(HttpServletRequest request,HttpServletResponse response,String fileName,String srcName) 
			throws IOException{
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName="+ new String(fileName.getBytes("gbk"),"iso-8859-1"));
		InputStream inputStream = new FileInputStream(new File(srcName));
		OutputStream os = response.getOutputStream();
		byte[] b = new byte[2048];
		int length;
		while ((length = inputStream.read(b)) > 0) {
			os.write(b, 0, length);
		}
		os.close();
		inputStream.close();
	}
	
	
	 /** 
     * 执行压缩操作 
     * @param srcPathName 需要被压缩的文件/文件夹 
     *        zipFile     
     */  
    public void compressExe(String srcPathName,File zipFile) {    
        File srcdir = new File(srcPathName);    
        if (!srcdir.exists()){  
            throw new RuntimeException(srcPathName + "不存在！");    
        }   
        Project prj = new Project();    
        Zip zip = new Zip();    
        zip.setProject(prj);    
        zip.setDestFile(zipFile);    
        FileSet fileSet = new FileSet();    
        fileSet.setProject(prj);    
        fileSet.setDir(srcdir);    
        //fileSet.setIncludes("**/*.java"); //包括哪些文件或文件夹 eg:zip.setIncludes("*.java");    
        //fileSet.setExcludes(...); //排除哪些文件或文件夹    
        zip.addFileset(fileSet);    
        zip.execute();    
    }    
	
    /**
     * 删除图片
     */
    @RequestMapping(value="/delete.do",method=RequestMethod.POST)
    public @ResponseBody Map<String,Object> deletePicture(HttpServletRequest request,@RequestParam String userNo){
    	Map<String,Object> map = new HashMap<String, Object>();
    	try{
    		recordMng.delete(userNo);
    		memberPhotoMng.delete(userNo);
    		map.put("msg", "删除成功!");
    	}catch(Exception e){
    		map.put("msg", "删除失败!");
    	}
    	
    	
    	return map;
    }
    
	
}
