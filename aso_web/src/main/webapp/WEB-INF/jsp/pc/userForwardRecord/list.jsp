<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding parentCode="USER_SCORE_TYPE" bingdingName="channellevel"></ry:binding>
<form id="pagerForm" method="post" action="userScore/list">
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
	<table class="table" width="100%" layoutH="86">
		<thead>
			<tr>
				<th width="30">
				<input type="checkbox" group="ids" class="checkboxCtrl" >
				</th>
				<th align="center">用户编号</th>
				<th align="center">用户类型</th>
				<th align="center">提现金额</th>
				<th align="center">提现时间</th>
				<th align="center">提现状态</th>
				<th align="center">操作</th>
			</tr>
		</thead>
		<tbody>
		     <c:forEach var="item" items="${pageList.result}">
				<tr >
				  <td align="center">
        <input type="checkbox"  id="orderCheckBox" name="ids" value="${item.userScoreInfoId}"></td>
					<td>${item.userAppNum}</td>	               
	                <td>${item.userType}</td>
	                <td>${item.score}</td>
	                <td>${item.scoreTime}</td>
	                <td>${item.status}</td>
	                <td><a style="cursor: pointer;" onclick="openNav('userAppForwardRecord/verify?userScoreInfoId=${item.userScoreInfoId}','审核','main_index2')"><div style="color: blue">审核</div></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<%@include file="/WEB-INF/jsp/inc/page.jsp" %>
