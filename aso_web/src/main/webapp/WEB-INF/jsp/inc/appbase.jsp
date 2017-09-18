<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<base href="<%=basePath%>" />
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<link href="weixin/css/style.css" rel="stylesheet" type="text/css">
<link href="weixin/css/index.css" rel="stylesheet" type="text/css">
<style type="text/css">
    body {
        padding-bottom: 40px;
    }
</style>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ry" uri="http://www.ruanyun.com/taglib/ry" %>
<%@taglib prefix="dj" uri="/WEB-INF/defined-jstl.tld" %>