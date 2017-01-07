package com.dgut.main.exception;

public class InstallationRecordException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public InstallationRecordException() {}
	public InstallationRecordException(String msg) {
		super(msg);
	}
}
