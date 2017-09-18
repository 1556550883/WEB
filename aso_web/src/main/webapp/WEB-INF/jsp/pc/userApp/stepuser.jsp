<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding parentCode="USER_SCORE_TYPE" bingdingName="channellevel"></ry:binding>
<form id="pagerForm" method="post" action="userApp/getStepUserList">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}">
	<input type="hidden" name="orderDirection" value="${param.orderDirection}">
</form>
	<table class="table" width="100%" layoutH="60">
		<thead>
			<tr>
				<th width="30">
				<input type="checkbox" group="ids" class="checkboxCtrl" >
				</th>
				<th align="center">广告名称</th>
				<th align="center">步骤名称</th>
				<th align="center">IP</th>
				<th align="center">手机序列号</th>
				<th align="center">系统版本号</th>
				<th align="center">手机串号</th>
				<th align="center">mac地址</th>
				<th align="center">记录时间</th>

			</tr>
		</thead>
		<tbody>
		     <c:forEach var="item" items="${pageList.result}">
				<tr >
				  <td align="center">
       				 <input type="checkbox"  id="orderCheckBox" name="ids" value="${item.adverUserStepId}"></td>
       				  <td>${item.adverName}</td>
       				  <td>${item.adverStepName}</td>
	                <td>${item.ip}</td>
					<td>${item.phoneSerialNumber}</td>	               
	                <td>${item.systemVersion}</td>
	                <td>${item.imei}</td>	
	                <td>${item.mac}</td>	               
					<td><ry:formatDate date="${item.createtime}" toFmt="yyyy-MM-dd HH:MM:SS"></ry:formatDate> </td>	                    
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<%@include file="/WEB-INF/jsp/inc/page.jsp" %>
