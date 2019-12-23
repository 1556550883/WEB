<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>

<table class="table" width="100%" layoutH="128">
	<thead>
		<tr>
			<th align="center">udid型号</th>
			<th align="center">昨天消耗</th>
			<th align="center">今日消耗</th>				
			<th align="center">总剩余</th>
		</tr>
	</thead>
	
	<tbody>
	     <c:forEach var="item" items="${udidModelList}">
			<tr >
				<td>${item.udidType}</td>
                <td>${item.yestDayAmountNum}</td>
                <td>${item.todayAmountNum}</td>
                <td>${item.totalNum}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>