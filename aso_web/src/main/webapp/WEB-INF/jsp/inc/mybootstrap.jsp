<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>中共安徽省委讲师团移动互联网管理系统</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ry" uri="http://www.ruanyun.com/taglib/ry" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="dj" uri="/WEB-INF/defined-jstl.tld" %>
<!-- 个人修改的css-->
<link href="bootstrap/mycss/bootstrap.css" rel="stylesheet" type="text/css" />
<link href="bootstrap/mycss/bootstrap-theme.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="reception/css/style.css" />
<script src="dwz/js/jquery-1.7.2.js" type="text/javascript" charset="UTF-8"></script>
<script src="bootstrap/js/bootstrap.js" type="text/javascript" charset="UTF-8"></script>
<script type="text/javascript" >
	var basePath = document.getElementsByTagName("base")[0].getAttribute("href");
</script>
<style>
	label{margin:10px;}
	#search{
		display:none;
		width: 80%;
		margin:0 auto;
		text-align:center;
	}
	#search .name{
		width:150px;
		height:20px;
		display:inline;
	}
	#search .type{
		width:150px;
		display:inline;
	}
	.top2_text a{
		font-size: 36px;
		color: #fff;
		line-height: 52px;
	}
	.top2_text a:hover{
		font-size: 36px;
		color: #fff;
		line-height: 52px;
		text-decoration: none;
	}
</style>