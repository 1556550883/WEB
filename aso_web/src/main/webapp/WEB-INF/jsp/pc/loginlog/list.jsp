<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>

<form id="pagerForm" method="post" action="loginlog/getlist">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	
	<!-- 分页时 带模糊查询的值 -->
	<input type="hidden" name="loginName" value="${param.loginName }"/>
	<input type="hidden" name="loginTime" value="${param.loginTime}" />
	<input type="hidden" name="endDate" value="${param.endDate}" />
	
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="loginlog/getlist" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>登录名称：</label>
				<input type="text" name="loginName" value="${tloginlog.loginName }"/>
			</li>
			<li>
				<label>开始时间：</label>
				<input type="text" name="loginTime"  id="loginlog_startDate" maxId="loginlog_endDate" class="date" value="<fmt:formatDate value="${tloginlog.loginTime}" pattern="yyyy-MM-dd"/>" dateFmt="yyyy-MM-dd" readonly="readonly" />
			</li>
			<li>
				<label>结束时间：</label>
				<input type="text" name="endDate" id="loginlog_endDate" minId="loginlog_startDate" class="date" value="<fmt:formatDate value="${tloginlog.endDate}" pattern="yyyy-MM-dd"/>" dateFmt="yyyy-MM-dd" readonly="readonly" />
			</li>
		</ul>
		
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
	</div>
	<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" onclick="add('user/toUserEdit','添加信息',900,500,'main_')"><span>添加</span></a></li>
				
			<li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids" postType="string" href="user/batchDelete" class="delete"><span>批量删除</span></a></li>
<!--			<li class="line">line</li>-->
<!--			<li><a class="icon" href="user/exportExcel" target="dwzExport" targetType="navTab" title="您确定导出这些记录吗?"><span>导出EXCEL</span></a></li>-->
		</ul>
	</div>
</div>
<div class="pageContent">
	<!--<div class="panelBar">
		<ul class="toolBar">
			<li class="line">line</li>
		</ul>
	</div>-->
	<table class="table" width="101.8%" layoutH="138">
		<thead>
			<tr>
				<th width="40" align="center">序号</th>
				<th width="370" align="center">登录名称</th>
				<th width="370" align="center">登录IP</th>
				<th width="100" align="center">登录时间</th>
			</tr>
		</thead>
		<tbody>
			 <c:forEach var="item" items="${pageList.result}" varStatus="row" >
	               <tr>
	                   <td>${(pageList.pageNum-1)*pageList.numPerPage+row.count}</td>
	                   <td class="center">${item.loginName}</td>
	                   <td>${item.logIp}</td>
	                   <td><ry:formatDate date="${item.loginTime}" ></ry:formatDate></td>
	               </tr>
              </c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<%@include file="/WEB-INF/jsp/inc/page.jsp" %>
</div>
