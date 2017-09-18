<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String reqPath= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
%>
	<div class="pageFormContent nowrap" layoutH="57">
	<dl style="width:520px">
		<dt style="">您的回调地址：</dt>
		<dd style="width: 200px">
		<input type="text" style="width: 300px"  name="basePath" value="<%=basePath %>/app/activation/${channalNum}" />	
		</dd>
			</dl>
	
	</div>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
