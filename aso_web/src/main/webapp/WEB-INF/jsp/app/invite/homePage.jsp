<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
	  	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	   
	   <%
    		String path = request.getContextPath();
    		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
		%>
		<base href="<%=basePath%>"/>
		
	   	<link rel="stylesheet" href="css/style.css">
	   	<script src="<%=path %>/js/mobile-detect.js" ></script>
	   	<script src="<%=path %>/js/mobile-login.js" ></script>
	</head>

	<body>
	
	</body>
</html>