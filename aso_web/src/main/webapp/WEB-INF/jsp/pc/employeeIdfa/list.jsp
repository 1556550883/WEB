<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<form id="pagerForm" method="post" action="channelInfo/employeeIdfaStatistics?userAppId=${userAppId}">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}">
	<input type="hidden" name="orderDirection" value="${param.orderDirection}">
</form>

	<div class="pageHeader">
		<span style="color:red">总数量：${total}</span>
	</div>
	
	
	<div class="panelBar">
		<ul class="toolBar">
			<!-- <li><a id="export" class="icon" href="javascript:;"><span>导出</span></a></li> -->
			
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="128">
		<thead>
			<tr class="header">
				<th align="center" width="100px;">广告ID</th>
				<th align="center" width="250px;">广告名称</th>
				<th align="center" width="80px;">广告价格</th>
				<th align="center" width="300px;">idfa</th>
				<th align="center" width="100px;">ip</th>
				<th align="center" width="250px;">地区</th>
				<th align="center" width="200px;">手机型号</th>
				<th align="center" width="100px;">手机系统版本</th>
				<th align="center" width="150px;">领取时间</th>
				<th align="center" width="150px;">打开时间</th>
				<th align="center" width="150px;">完成时间</th>
				<th align="center" width="100px;">任务状态</th>
			</tr>
		</thead>
		<tbody>
		     <c:forEach var="item" items="${pageList.result}">
				<tr class="body">
	                <td style="text-align:left;">${item.adid}</td>	               
	                <td style="text-align:left;">${item.adverName}</td>
	                <td style="text-align:left;">${item.adverPrice}</td>
	                <td style="text-align:left;">${item.idfa}</td>
	                <td style="text-align:left;">${item.ip}</td>
	                <td style="text-align:left;">${item.ipLocaltion}</td>
	                <td style="text-align:left;">${item.phoneModel}</td>
	                <td style="text-align:left;">${item.phoneVersion}</td>
	                <td style="text-align:left;">${item.receiveTime}</td>
					<td style="text-align:left;">${item.openAppTime}</td>
					<td style="text-align:left;">${item.completeTime}</td>
					<td style="text-align:left;">
					<c:if test="${item.status == '1'}">领取任务</c:if>
					<c:if test="${item.status == '1.5'}">打开任务</c:if>
					<c:if test="${item.status == '1.6'}">过期任务</c:if>
					<c:if test="${item.status == '2.1'}">过期任务</c:if>
					<c:if test="${item.status == '2'}">完成任务</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

<%@include file="/WEB-INF/jsp/inc/page.jsp" %>

