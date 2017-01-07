package com.dgut.main.entity.base;

import java.io.Serializable;

public class BaseWorkSheet implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String worksheetNo;
	
	public BaseWorkSheet() {
		super();
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getWorksheetNo() {
		return worksheetNo;
	}
	public void setWorksheetNo(String worksheetNo) {
		this.worksheetNo = worksheetNo;
	}
	
	

}
