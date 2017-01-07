package com.dgut.main.action;

import static com.dgut.common.page.SimplePage.cpn;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dgut.common.file.FileNameUtils;
import com.dgut.common.page.PageResponse;
import com.dgut.common.page.Pagination;
import com.dgut.common.upload.FileRepository;
import com.dgut.common.util.POIUtil;
import com.dgut.common.web.CookieUtils;
import com.dgut.main.entity.InstallationRecord;
import com.dgut.main.entity.WorkSheet;
import com.dgut.main.manager.InstallationRecordMng;
import com.dgut.main.manager.WorkSheetMng;



@Controller
public class DataImport{
	
	//public static int pageSize = 10;
	//public static int pageNo = 1;
	public static String sortOrder = null;
	
	@Autowired
	private InstallationRecordMng irMng;
	
	@Autowired 
	private WorkSheetMng workSheetMng;
	
	static XSSFRow row;
	
	@RequestMapping("/dataImport.do")
	public String dataImport(HttpServletRequest request, ModelMap model){
		
		return "admin/dataImport";
	}

	@RequestMapping("/dataTobase.do")
	public @ResponseBody Map<String,Object> dataTobase(HttpServletRequest request,
			@RequestParam("file") MultipartFile []file) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		if(file!=null){
			for(int i =0;i<file.length;i++){
				String fileName = file[i].getOriginalFilename();  //上传文件名
				String ext = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());  //后缀
				if(ext.equalsIgnoreCase("xlsx")||ext.equalsIgnoreCase("xls")){
					String destName = FileNameUtils.genFileName(ext);
					String savepath= request.getServletContext().getRealPath("/")+"dataImport";    System.out.println(savepath);
					File destFile = new File(savepath,destName);
					FileUtils.writeByteArrayToFile(destFile, file[i].getBytes());
					
					dataTransform(destFile);
					map.put("msg", "提交成功！");
				}else{
					map.put("msg", "提交文件格式错误！");
				}
			}
		}else{
			map.put("msg", "提交失败！");
		}
		return map;
	}
	
	
	/**
	 * 获取工作单编号列表
	 */
	@RequestMapping(value="/getDataList.do")
	public @ResponseBody PageResponse<WorkSheet> getDataList(HttpServletRequest request, String worksheetNo,
			int pageNumber, int pageSize,String sortOrder)throws Exception{
		
		PageResponse<WorkSheet> page = new PageResponse<WorkSheet>();
		Integer total = 0;
		Pagination pagination = workSheetMng.getCurPage(null, cpn(pageNumber), pageSize, sortOrder);
		if(StringUtils.isBlank(worksheetNo)){
			total = workSheetMng.countAll();
		}else{
			total = workSheetMng.getCount(worksheetNo);
		}
		page.setRecords((List)pagination.getList());	
		page.setTotal(total);
		
		return page;
	}
	
	
	
	/**
	 * 获取记录数据
	 * @param file
	 * @throws Exception
	 */
	@RequestMapping(value="/getData.do")
	public String getData(HttpServletRequest request, ModelMap map, 
			@RequestParam String worksheetNo,Integer pageNo,String sortOrder) 
			throws Exception {
		System.out.println(worksheetNo);
		List<InstallationRecord> irList = irMng.findByWorkSheetNo(worksheetNo);
		map.addAttribute("irList",irList);
		Pagination page = irMng.getCurPage(worksheetNo, cpn(pageNo), CookieUtils.getPageSize(request), sortOrder);
		List<InstallationRecord> test = (List<InstallationRecord>) page.getList();
		map.addAttribute("worksheetNo", worksheetNo);
		map.addAttribute("pagination",page);
		return "admin/dataDetial";
	}
	
	
	/**
	 * 删除工作单所有记录
	 * @param file
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteData.do",method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> delete(HttpServletRequest request, @RequestParam String worksheetNo){
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		if(StringUtils.isNotBlank(worksheetNo)){
			
			WorkSheet bean = workSheetMng.findByWorkSheetNo(worksheetNo);
			if(bean!=null){
				workSheetMng.delete(bean);
				List<InstallationRecord> beanlist = irMng.findByWorkSheetNo(worksheetNo);
				if(beanlist.size()>0){
					irMng.deleteList(beanlist);
				}
				map.put("msg", "删除成功");
			}else{
				map.put("msg", "该单号不存在");
			}
		}
		return map;
	}
	
	
	
	
	public void dataTransform(File file) throws Exception {
		
		  List<InstallationRecord> irlist = new ArrayList<InstallationRecord>();
		  
		  FileInputStream fis = new FileInputStream(file);
	      XSSFWorkbook workbook = new XSSFWorkbook(fis);
	      XSSFSheet spreadsheet = workbook.getSheetAt(0);
	      
	      //获取工作单编号   
	      Cell c = spreadsheet.getRow(1).getCell(0);
	      String s = c.getStringCellValue();
	      Pattern p = Pattern.compile("[^0-9]");
	      Matcher m = p.matcher(s);
	      String worksheetNo = m.replaceAll("").trim();
	      System.out.println(worksheetNo);

	      int rowNum = spreadsheet.getLastRowNum();   //总行数
	      
	      for(int i = 3;i<rowNum-1;i++){
	    	  row = spreadsheet.getRow(i);
	    	  if(row!=null){
	    		  
	    		  InstallationRecord bean = new InstallationRecord();
	    		  bean.setWorksheetNo(worksheetNo);
	    		  
	    		  Cell c0 = row.getCell(0);
	    		  String userNo = POIUtil.getInstance().getCellValue(c0);  //户号 
	    		  if(userNo==""||userNo==null){
	    			  userNo = POIUtil.getInstance().getCellValue(spreadsheet.getRow(i-1).getCell(0));
	    		  }
	    		  bean.setUserNo(userNo);      
	    		  System.out.print(userNo);
	    		  
	    		  Cell c1 = row.getCell(1);
	    		  String userName = POIUtil.getInstance().getCellValue(c1);
	    		  if(userName==""||userName==null){
	    			  userName = POIUtil.getInstance().getCellValue(spreadsheet.getRow(i-1).getCell(1));
	    		  }
	    		  bean.setUserName(userName);   System.out.print(userName);
	    		  
	    		  Cell c2 = row.getCell(2);
	    		  String address = POIUtil.getInstance().getCellValue(c2);
	    		  if(address==null||address==""){
	    			  address = POIUtil.getInstance().getCellValue(spreadsheet.getRow(i-1).getCell(2));
	    		  }
	    		  bean.setAddress(address);      System.out.print(address);
	    		  
	    		  Cell c3 = row.getCell(3);
	    		  String flag = POIUtil.getInstance().getCellValue(c3);
	    		  bean.setCflag(flag);            System.out.print(flag);
	    		  
	    		  Cell c4 = row.getCell(4);
	    		  String meterNo = POIUtil.getInstance().getCellValue(c4);
	    		  bean.setMeterNo(meterNo);      System.out.print(meterNo);
	    		  
	    		  Cell c5 = row.getCell(5);
	    		  String type = POIUtil.getInstance().getCellValue(c5);
	    		  bean.setType(type);            System.out.print(type);
	    		  
	    		  
	    		  Cell c6 = row.getCell(6);
	    		  String tableNo = POIUtil.getInstance().getCellValue(c6);
	    		  if(tableNo!=null||tableNo!=""){bean.setTableNo(tableNo);}
	    		  System.out.print(tableNo);
	    		  
	    		  
	    		  Cell c7 = row.getCell(7);
	    		  String pedestal = POIUtil.getInstance().getCellValue(c7);
	    		  if(pedestal!=""||pedestal!=null){bean.setPedestal(pedestal);}
	    		  System.out.print(pedestal);
	    		  
	    		  
	    		  Cell c8 = row.getCell(8);
	    		  String sealposition = POIUtil.getInstance().getCellValue(c8);
	    		  if(sealposition!=""||sealposition!=null){bean.setSealposition(sealposition);}
	    		  System.out.print("封印位置："+sealposition);
	    		  
	    		  
	    		  Cell c9 = row.getCell(9);
	    		  String sealNo = POIUtil.getInstance().getCellValue(c9);
	    		  if(sealNo!=""||sealNo!=null){bean.setSealNo(sealNo); }
	    		  System.out.print(sealNo);
	    		  
	    		  
	    		  Cell c10 = row.getCell(10);
	    		  String caseNo = POIUtil.getInstance().getCellValue(c10);
	    		  if(caseNo!=""||caseNo!=null){bean.setCaseNo(caseNo);}
	    		  System.out.print(caseNo);
	    		  
	    		  
	    		  Cell c11 = row.getCell(11);
	    		  String staff = POIUtil.getInstance().getCellValue(c11);
	    		  if(staff!=""||staff!=null){bean.setStaff(staff);}
	    		  System.out.println(staff);
	    		  
	    		  irlist.add(bean);
	    	  }
	      }
	      for(InstallationRecord bean : irlist){
	    	  try {
				irMng.save(bean);
			} catch (Exception e) {
				e.printStackTrace();
			}
	      }
	      WorkSheet wbean = new WorkSheet();
	      wbean.setWorksheetNo(worksheetNo);
	      workSheetMng.save(wbean);
	}
	
	
}
