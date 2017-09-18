<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding parentCode="USER_SCORE_TYPE" bingdingName="channellevel"></ry:binding>
<form id="pagerForm" method="post" action="channelAdverStepUser/list">
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
				<th align="center">广告编号</th>
				<th align="center">广告步骤编号</th>
				<th align="center">是否完成</th>
				<th align="center">ip地址</th>
				<th align="center">手机序列号</th>
				<th align="center">系统版本号</th>
				<th align="center">苹果账号</th>
			</tr>
		</thead>
		<tbody>
		     <c:forEach var="item" items="${pageList.result}">
				<tr >
				  <td align="center">
        <input type="checkbox"  id="orderCheckBox" name="ids" value="${item.adverUserStepId}"></td>
					<td>${item.adverNum}</td>	               
	                <td>${item.adverStepNum}</td>
	                <td><c:if test="${item.adverUserStepStatus==1}" >完成</c:if>
	      			<c:if test="${item.adverUserStepStatus==-1}" >未完成</c:if></td>
	                <td>${item.ip}</td>					
	      			<td>${item.phoneSerialNumber}</td>
	      			<td>${item.systemVersion}</td>	
	      			<td>${item.flag1}</td>	
              
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<%@include file="/WEB-INF/jsp/inc/page.jsp" %>
