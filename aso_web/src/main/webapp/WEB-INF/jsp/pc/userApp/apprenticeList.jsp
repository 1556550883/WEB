<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding parentCode="USER_APP_TYPE" bingdingName="userAppType"></ry:binding>
<form id="pagerForm" method="post" action="userApp/apprenticeList">
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
				<th align="center">手机号码</th>
				<th align="center">手机号码等级</th>
				<th align="center">机型版本</th>
				<th align="center">登陆名</th>
				<th align="center">微信账号</th>
				<th align="center">微信头像</th>
				<th align="center">真实姓名</th>
				<th align="center">支付宝账号</th>
				<th align="center">余额</th>
				<th align="center">总收益</th>
				<th align="center">用户等级</th>
				<th align="center">徒弟列表</th>
			</tr>
		</thead>
		<tbody>
		     <c:forEach var="item" items="${pageList.result}">
				<tr style="height:50px">
	                <td>${item.userAppId}</td>
	             	<td>${item.phoneNum}</td>
	             	<td><c:if test="${item.isEffective == 0}">正常</c:if>
	             	<c:if test="${item.isEffective == 1}">黑名单</c:if>
	             	<c:if test="${item.isEffective == 2}">可疑</c:if>
	             	<c:if test="${item.isEffective == 3}">未检测</c:if></td>
					<td>${item.phoneModel}</td>	
					<td>${item.loginName}</td>	 
					<td>${item.weixin}</td>
					<td style="background:url(${item.flag5});background-repeat:no-repeat; background-size:100% 100%;-moz-background-size:100% 100%;}"></td> 
	                <td>${item.userNick}</td>
	                <td>${item.zhifubao}</td>
	             	<td>${item.userScore.score}</td>
					<td>${item.userScore.scoreSum}</td>  
	                <td>${item.level }</td>
	                <td>
						<a style="cursor: pointer;" onclick="add('userApp/apprenticeList?userAppId=${item.userAppId}','徒弟列表',1500,1000,'main_')"><span style="color:blue">徒弟列表</span></a>     	
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<%@include file="/WEB-INF/jsp/inc/page.jsp" %>
