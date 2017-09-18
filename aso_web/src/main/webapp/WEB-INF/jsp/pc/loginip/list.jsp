<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>

<form id="pagerForm" method="post" action="loginip/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	
	<!-- 分页时 带模糊查询的值 -->
	<input type="hidden" name="logIp" value="${param.logIp }"/>
	
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="loginip/list" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>IP地址：</label>
				<input type="text" name="logIp" value="${bean.logIp }"/>
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
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="98%" layoutH="134">
		<thead>
			<tr>
				<th width="40" align="center">序号</th>
				<th width="370" align="center">登录IP</th>
				<th width="100" align="center">登录时间</th>
			</tr>
		</thead>
		<tbody>
			 <c:forEach var="item" items="${pageList.result}" varStatus="row" >
	               <tr>
	                   <td>${(pageList.pageNum-1)*pageList.numPerPage+row.count}</td>
	                   <td>${item.logIp}</td>
	                   <td><ry:formatDate date="${item.loginTime}" ></ry:formatDate></td>
	               </tr>
              </c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<%@include file="/WEB-INF/jsp/inc/page.jsp" %>
</div>
