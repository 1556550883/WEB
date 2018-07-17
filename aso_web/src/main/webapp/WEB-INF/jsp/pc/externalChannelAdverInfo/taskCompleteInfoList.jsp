<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>

<form id="pagerForm" method="post" action="externalChannelAdverInfo/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}">
	<input type="hidden" name="orderDirection" value="${param.orderDirection}">
</form>

	<div class="panelBar">
		<ul class="toolBar">
		
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="125">
		<thead>
			<tr>
				<th align="center">广告名称</th>
				<th align="center">ip地址</th>
				<th align="center">idfa</th>
				<th align="center">开始时间</th>
				<th align="center">完成时间</th>
			</tr>
		</thead>
		<tbody>
		     <c:forEach var="item" items="${pageList.result}">
				<tr>
	                <td>${item.keywords}</td>
	                <td>${item.ip}</td>
	                <td>${item.idfa}</td>
	                <td>${item.receiveTime}</td>
	                <td>${item.completeTime}</td>
				</tr>
			</c:forEach>  
		</tbody>
	</table>

	<%@include file="/WEB-INF/jsp/inc/page.jsp" %>
