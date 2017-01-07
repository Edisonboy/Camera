package com.dgut.app.pck;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface GeneralRestGatewayInterface {

	  public abstract String delegateHandleRequest(HttpServletRequest request,
				HttpServletResponse response,Map<String, String> paramMap, StringBuilder paramStringBuilder)
	    throws RuntimeException;
}
