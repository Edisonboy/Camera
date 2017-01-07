package com.dgut.common.util;

import java.lang.reflect.Field;

import org.apache.poi.ss.usermodel.Cell;

public class POIUtil {

	private POIUtil(){}  
	
    private static POIUtil poi= null;  
    
    public static POIUtil getInstance(){  
        if(poi==null){  
            synchronized(POIUtil.class){  
                if(poi==null){  
                    poi = new POIUtil();  
                }  
            }  
        }  
        return poi;  
    }  
    
    public String getCellValue(Cell cell){  
    	if(cell!=null){
    		String value = null; 
    		//简单的查检列类型    
    		switch(cell.getCellType())  {    
    		
    		case Cell.CELL_TYPE_STRING://字符串    
    			value = cell.getRichStringCellValue().toString();    
    			break;    
    		case Cell.CELL_TYPE_NUMERIC://数字    
    			double dd = cell.getNumericCellValue();    
    			value = dd+"";    
    			break;    
    		case Cell.CELL_TYPE_BLANK:    
    			value = "";    
    			break;       
    		case Cell.CELL_TYPE_FORMULA:    
    			value = String.valueOf(cell.getCellFormula());    
    			break;    
    		case Cell.CELL_TYPE_BOOLEAN://boolean型值    
    			value = String.valueOf(cell.getBooleanCellValue());    
    			break;    
    		case Cell.CELL_TYPE_ERROR:    
    			value = String.valueOf(cell.getErrorCellValue());    
    			break;    
    		default:    
    			break;    
    		}    
    		return value;    
    	}
    	return null;
    }
    
    //设置实体属性
    public void setObjectValue(Object o,int sc,Cell cell,String name) throws Exception{
    	name = name.substring(0,1).toUpperCase()+name.substring(1);
    	Field field = o.getClass().getDeclaredField(name);
    	field.setAccessible(true);
    	field.set(o, "");
    	
    }
    
    
}
