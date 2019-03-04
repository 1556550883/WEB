<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding parentCode="USER_APP_TYPE" bingdingName="userAppType"></ry:binding>
<form id="pagerForm" method="post" action="userApp/apprenticeScoreList">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}">
	<input type="hidden" name="orderDirection" value="${param.orderDirection}">
	<input type="hidden" name="phoneNum" value="${param.phoneNum }">
	<input type="hidden" name="userNum" value="${param.userNum }">
	<input type="hidden" name="userApppType" value="${param.userApppType }">
	<input type="hidden" name="userNick" value="${param.userNick }">
	<input type="hidden" name="loginName" value="${param.loginName }">
	<input type="hidden" name="flag2" value="${param.flag2 }">
</form>

	<table class="table" width="101.7%" layoutH="130">
		<thead>
			<tr>
				<th align="center">用户ID</th>
				<th align="center">徒弟编号</th>
				<th align="center">奖励金额</th>
				<th align="center">时间</th>
				<th align="center">奖励类型</th>
			</tr>
		</thead>
		<tbody>
		     <c:forEach var="item" items="${pageList.result}">
				<tr style="height:50px">
	                <td>${item.userApprenticeId}</td>
	             	<td>${item.apprenticeUserNum}</td>
					<td>${item.score}</td>	
					<td>${item.apprenticeTime}</td>	 
					<td><c:if test="${item.userApprenticeType == 1}">完成任务奖励</c:if>
	             	<c:if test="${item.userApprenticeType == 3}">完成五次任务奖励</c:if>
	             	<c:if test="${item.userApprenticeType == 4}">邀请满足20人奖励</c:if></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<%@include file="/WEB-INF/jsp/inc/page.jsp" %>
