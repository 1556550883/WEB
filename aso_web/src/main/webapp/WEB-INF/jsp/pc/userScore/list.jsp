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
				<th align="center">今日分数</th>
				<th align="center">可用分数</th>
				<th align="center">总分数</th>
				<th align="center">今日收徒数量</th>
				<th align="center">收徒总数</th>
				<th align="center">徒弟今日收益</th>
				<th align="center">徒弟总收益</th>
				<th align="center">用户分数类型</th>
			</tr>
		</thead>
		<tbody>
		     <c:forEach var="item" items="${pageList.result}">
				<tr >
				  <td align="center">
        <input type="checkbox"  id="orderCheckBox" name="ids" value="${item.userScoreId}"></td>
					<td>${item.userNum}</td>	               
	                <td>${item.scoreDay}</td>
	                <td>${item.score}</td>
	                <td>${item.scoreSum}</td>
	                <td>${item.apprenticeCountDay}</td>
	                <td>${item.apprenticeCount}</td>
	                <td>${item.apprenticeScoreDay}</td>
	                <td>${item.apprenticeScore}</td>
	                <td><ry:show parentCode="USER_SCORE_TYPE" itemCode="${item.type}"></ry:show></td>               
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<%@include file="/WEB-INF/jsp/inc/page.jsp" %>
