<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding parentCode="USER_SCORE_TYPE" bingdingName="channellevel"></ry:binding>

<form id="pagerForm" method="post" action="adverEffectiveInfo/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}">
	<input type="hidden" name="orderDirection" value="${param.orderDirection}">
</form>

<div class="pageHeader">
	<form rel="pagerForm"  onsubmit="return navTabSearch(this);" action="adverEffectiveInfo/list" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>广告名称：<input type="hidden" name="adverId" value="${bean.adverId}"/></td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
	<div class="panelBar">
		<ul class="toolBar">
		
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="125">
		<thead>
			<tr>
				<th align="center">广告名称</th>
				<th align="center">广告价格</th>
				<th align="center">用户ID</th>
				<th align="center">用户名</th>
				<th align="center">ip</th>
				<th align="center">idfa</th>
				<th align="center">apple id</th>
				<th align="center">领取时间</th>
				<th align="center">完成时间</th>
				<th align="center">支付时间</th>
			</tr>
		</thead>
		<tbody>
		     <c:forEach var="item" items="${pageList.result}">
				<tr>
	                <td>${item.adverName}</td>
	                <td>${item.adverPrice}</td>
	                <td>${item.userAppId}</td>
	                <td>${item.loginName}</td>  
	                <td>${item.ip}</td>  
	                <td>${item.idfa}</td> 
	                <td>${item.appleId}</td> 
	                <td>${item.receiveTime}</td>  
	                <td>${item.completeTime}</td>
	                <td>${item.payTime}</td>
				</tr>
			</c:forEach>  
		</tbody>
	</table>

	<%@include file="/WEB-INF/jsp/inc/page.jsp" %>
