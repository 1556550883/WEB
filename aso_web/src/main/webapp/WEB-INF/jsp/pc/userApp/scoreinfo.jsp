<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding parentCode="USER_SCORE_TYPE" bingdingName="channellevel"></ry:binding>
<form id="pagerForm" method="post" action="userApp/getScoreInfoList?userAppNum=${userAppNum}">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}">
	<input type="hidden" name="orderDirection" value="${param.orderDirection}">
	<!-- <input type="hidden" name="userAppNum" value="${bean.userAppNum}">	 -->
</form>
	<table class="table" width="100%" layoutH="60">
		<thead>
			<tr>
				<th width="30">
				<input type="checkbox" group="ids" class="checkboxCtrl" >
				</th>
				<th align="center">用户编号</th>
				<th align="center">获得分数</th>
				<th align="center">得分名称</th>
				<th align="center">创建时间</th>
				<th align="center">操作</th>

			</tr>
		</thead>
		<tbody>
		     <c:forEach var="item" items="${pageList.result}">
				<tr >
				  <td align="center">
       				 <input type="checkbox"  id="orderCheckBox" name="ids" value="${item.userScoreInfoId}"></td>
	                <td>${item.userAppNum}</td>
					<td>${item.score}</td>	               
	                <td>${item.scoreName}</td>	               
					<td><ry:formatDate date="${item.scoreTime}" toFmt="yyyy-MM-dd"></ry:formatDate> </td>
					<td><a title="您确定删除该条积分记录吗？" target="ajaxTodo" href="userApp/delScoreInfo?userAppNum=${userAppNum}&pageNum=${pageList.pageNum}&userScoreInfoNum=${item.userScoreInfoNum}" class="btnDel">删除</a></td>                    
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<%@include file="/WEB-INF/jsp/inc/page.jsp" %>
