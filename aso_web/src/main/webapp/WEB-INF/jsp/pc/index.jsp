<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=7" />
	<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
	<title>赚钱网站</title>
	<%@include file="/WEB-INF/jsp/inc/base-dwz.jsp" %>
	
</head>

<body scroll="no">
    <!-- 整个index主页显示区域 -->
	<div id="layout">
		<!-- 头部 -->
		<%@include file="/WEB-INF/jsp/inc/header.jsp" %>
		<!-- 左边菜单栏 -->
		<%@include file="/WEB-INF/jsp/inc/left.jsp" %>
		
		<!-- main区域 -->
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
						    <li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon">我的主页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
				    <li><a href="javascript:;">我的主页</a></li>
				</ul>
				<!-- 数据显示面板 -->
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox">
					    <!-- 面板显示区域的头部 -->
						<div class="accountInfo">
						 
						<div class="pageFormContent" layoutH="80" style="margin-right:230px">
							
						 
						</div>
						
					 
					</div>
					
				</div>
			</div>
		</div>
	
	</div>
	
	<!-- footer底部信息 -->
	<div id="footer">Copyright &copy; 2015 所有人 <!-- Tel：0551-52897073 --></div>
	

</body>
<script >
	function startCheckOut(url){
		window.open(url,'','width=170px,location=no,status=no,top=0,left=0');
	}

	function startCheckOut2(url){
		window.open(url,'','width=800px,location=no,status=no,top=0,left=0');
	}
</script>
</html>