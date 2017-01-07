package com.dgut.main.entity.base;

import java.io.Serializable;
import java.util.Date;

public class BaseInstallationRecord implements Serializable {

	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	
	private Integer recordNo;
	private String userNo;
	private String userName;
	private String address;
	private String cflag;
	private String meterNo;
	private String type;
	private String tableNo;
	private String pedestal;
	private String sealposition;
	private String sealNo;
	private String caseNo;
	private String staff;
	private Date date;
	private String signature;
	private boolean flag;
	private String worksheetNo;
	
	
	public BaseInstallationRecord() {
		super();
	}


	public Integer getRecordNo() {
		return recordNo;
	}


	public void setRecordNo(Integer recordNo) {
		this.recordNo = recordNo;
	}


	public String getUserNo() {
		return userNo;
	}


	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getCflag() {
		return cflag;
	}


	public void setCflag(String cflag) {
		this.cflag = cflag;
	}


	public String getMeterNo() {
		return meterNo;
	}


	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getTableNo() {
		return tableNo;
	}


	public void setTableNo(String tableNo) {
		this.tableNo = tableNo;
	}


	public String getPedestal() {
		return pedestal;
	}


	public void setPedestal(String pedestal) {
		this.pedestal = pedestal;
	}


	public String getSealposition() {
		return sealposition;
	}


	public void setSealposition(String sealposition) {
		this.sealposition = sealposition;
	}


	public String getSealNo() {
		return sealNo;
	}


	public void setSealNo(String sealNo) {
		this.sealNo = sealNo;
	}


	public String getCaseNo() {
		return caseNo;
	}


	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}


	public String getStaff() {
		return staff;
	}


	public void setStaff(String staff) {
		this.staff = staff;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public String getSignature() {
		return signature;
	}


	public void setSignature(String signature) {
		this.signature = signature;
	}

	
	public boolean isFlag() {
		return flag;
	}


	public void setFlag(boolean flag) {
		this.flag = flag;
	}


	public String getWorksheetNo() {
		return worksheetNo;
	}


	public void setWorksheetNo(String worksheetNo) {
		this.worksheetNo = worksheetNo;
	}


	
	
	
	
	

}
