package com.dgut.common.page;

import java.io.Serializable;
import java.util.List;

public class PageResponse<T> implements Serializable {

	 /**
	 * 前台交互分页类
	 */
	private static final long serialVersionUID = 1L;
		private Integer total;  //总条数  
	    private List<T> records; //当前页显示数据  
	  
	    public Integer getTotal() {  
	        return total;  
	    }  
	  
	    public void setTotal(Integer total) {  
	        this.total = total;  
	    }  
	  
	    public List<T> getRecords() {  
	        return records;  
	    }  
	  
	    public void setRecords(List<T> records) {  
	        this.records = records;  
	    }  
	
}
