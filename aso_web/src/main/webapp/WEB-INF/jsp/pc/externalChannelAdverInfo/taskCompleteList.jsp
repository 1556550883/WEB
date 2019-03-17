<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>

<form id="pagerForm" method="post" action="externalChannelAdverInfo/list">
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
	<table class="table" width="100%" layoutH="125">
		<thead>
			<tr>
				<th align="center">广告名称</th>
				<th align="center">今日完成数量</th>
				<th align="center">完成详情</th>
			</tr>
		</thead>
		<tbody>
		     <c:forEach var="item" items="${pageList.result}">
				<tr>
	                <td>${item.keywords}</td>
	                <td>${item.num}</td>
	                <td>
						<a style="cursor: pointer;" onclick="openNav('externalChannelAdverInfo/adverCompleteInfo?adverId=${item.adverId}','广告完成情况','main_index2')"><div style="color: blue">广告完成情况</div></a>
					</td>
				</tr>
			</c:forEach>  
		</tbody>
	</table>

	<%@include file="/WEB-INF/jsp/inc/page.jsp" %>
