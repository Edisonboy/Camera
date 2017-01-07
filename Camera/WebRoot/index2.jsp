<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
    <form action="./app/index.do" method="post"> 
    <h1>登录测试</h1>
    	mobile<input type="text" name="mobile"/>
    	pwd<input type="text" name="pwd"/>
    	_t<input type="text" name="_t" value="2016-10-22 00:00:00"/>
    	_s<input type="text" name="_s" value="2016-10-22 00:00:00"/>
    	OPT<input type="text" name="opt" value="2"/>
    	<input type="submit" value="submit"/>
    </form>
    
    <form action="./app/sendPicture.do" method="post" enctype="multipart/form-data">
    <h1>上传图片</h1>
    	<input type="file" name="image"/>
    	<input type="file" name="image"/>
    	文件夹名<input type="text" name="folderName" value="0319007810400611"/>
    	时间<input type="text" name="time" value="2016-11-11 11:11"/>
    	<input type="text" name="token" value="fb265b5acd3548b795c05d3ac5914a2c"/>
    	<input type="submit" value="submit"/>
    </form>
    
    <form action="./app/index.do" method="post">
    <h1>检索户号</h1>
    	OPT<input type="text" name="opt" />
    	userNo<input type="text" name="userNo" value="04006"/>
    	<input type="text" name="token" value="de0d97fdd974405cac50e1c8e5095028"/>
    	_t<input type="text" name="_t" value=""/>
    	_s<input type="text" name="_s" value=""/>
    	<input type="submit" value="submit"/>
    </form>
    
    
    
    
    <form action="./app/index.do" method="post">
    <h1>上传用户的上传记录</h1>
    	OPT<input type="text" name="opt" value="7"/>
    	mobile<input type="text" name="mobile" value="13416955362"/>
    	token<input type="text" name="token" value="ba4416316bd848ed982576d20c17e783"/>
    	_t<input type="text" name="_t" value=""/>
    	_s<input type="text" name="_s" value=""/>
    	<input type="submit" value="submit"/>
    </form>
    
    <form action="./app/index.do" method="post">
    <h1>工作记录具体信息</h1>
    	OPT<input type="text" name="opt" value="8"/>
    	recordNo<input type="text" name="recordNo" value="242"/>
    	token<input type="text" name="token" value="ba4416316bd848ed982576d20c17e783"/>
    	_t<input type="text" name="_t" value=""/>
    	_s<input type="text" name="_s" value=""/>
    	<input type="submit" value="submit"/>
    </form>
    
  </body>
</html>
