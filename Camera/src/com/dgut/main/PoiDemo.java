package com.dgut.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.dgut.common.util.POIUtil;
import com.dgut.main.entity.InstallationRecord;




public class PoiDemo {

	static XSSFRow row;
	
	public static void main(String[] args) throws Exception {
		
		  //List<Entity> elist = new ArrayList<Entity>();
		  
		  FileInputStream fis = new FileInputStream(new File("C:/Users/Lenovo/Desktop/demo.xlsx"));
	      XSSFWorkbook workbook = new XSSFWorkbook(fis);
	      XSSFSheet spreadsheet = workbook.getSheetAt(0);
	      
	      //获取工作单编号   
	      Cell c = spreadsheet.getRow(1).getCell(0);
	      String s = c.getStringCellValue();
	      Pattern p = Pattern.compile("[^0-9]");
	      Matcher m = p.matcher(s);
	      String workNo = m.replaceAll("").trim();
	      System.out.println(workNo);

	      int rowNum = spreadsheet.getLastRowNum();   //总行数
	      
	      for(int i = 3;i<rowNum-1;i++){
	    	  row = spreadsheet.getRow(i);
	    	  if(row!=null){
	    		  
	    		  InstallationRecord bean = new InstallationRecord();
	    		  
	    		  Cell c0 = row.getCell(0);
	    		  String userNo = POIUtil.getInstance().getCellValue(c0);  //户号 
	    		  if(userNo==""||userNo==null){
	    			  userNo = POIUtil.getInstance().getCellValue(spreadsheet.getRow(i-1).getCell(0));
	    		  }
	    		  bean.setUserNo(userNo);      System.out.print(userNo);
	    		  
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
	    		  bean.setTableNo(tableNo);      System.out.print(tableNo);
	    		  
	    		  Cell c7 = row.getCell(7);
	    		  String pedestal = POIUtil.getInstance().getCellValue(c7);
	    		  bean.setPedestal(pedestal);    System.out.print(pedestal);
	    		  
	    		  Cell c8 = row.getCell(8);
	    		  String sealposition = POIUtil.getInstance().getCellValue(c8);
	    		  bean.setSealposition(sealposition);    System.out.print(sealposition);
	    		  
	    		  Cell c9 = row.getCell(9);
	    		  String sealNo = POIUtil.getInstance().getCellValue(c9);
	    		  bean.setSealNo(sealNo);         System.out.print(sealNo);
	    		  
	    		  Cell c10 = row.getCell(10);
	    		  String caseNo = POIUtil.getInstance().getCellValue(c10);
	    		  bean.setCaseNo(caseNo);         System.out.print(caseNo);
	    		  
	    		  Cell c11 = row.getCell(11);
	    		  String staff = POIUtil.getInstance().getCellValue(c11);
	    		  bean.setStaff(staff);           System.out.println(staff);
	    		  
	    		  
	    		  
	    	  }
	    	  
	      }
	}
	
}
