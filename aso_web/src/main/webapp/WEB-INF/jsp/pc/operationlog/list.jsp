<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>

<form id="pagerForm" method="post" action="operationLog/getlist">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	
	<!-- 分页时 带模糊查询的值 -->
	<input type="hidden" name="operationUserName" value="${param.operationUserName}"/>
	<input type="hidden" name="createdate" value="${param.createdate}" />
	<input type="hidden" name="endDate" value="${param.endDate}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="operationLog/getlist" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>操作人：</label>
				<input type="text" name="operationUserName" value="${toperationlog.operationUserName }"/>
			</li>
			<li>
				<label>开始时间：</label>
				<input type="text" name="createdate"  id="oplog_startDate" maxId="oplog_endDate" class="date" value="<fmt:formatDate value="${toperationlog.createdate}" pattern="yyyy-MM-dd"/>" dateFmt="yyyy-MM-dd" readonly="readonly" />
			</li>
			<li>
				<label>结束时间：</label>
				<input type="text" name="endDate" id="oplog_endDate" minId="oplog_startDate" class="date" value="<fmt:formatDate value="${toperationlog.endDate}" pattern="yyyy-MM-dd"/>" dateFmt="yyyy-MM-dd" readonly="readonly" />
			</li>
		</ul>
		
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="101.8%" layoutH="138">
		<thead>
			<tr>
				<th width="40" align="center">序号</th>
				<th width="220" align="center">操作名称</th>
				<th width="450" align="center">操作内容</th>
				<th width="200" align="center">操作人</th>
				<th width="100" align="center">时间</th>
			</tr>
		</thead>
		<tbody>
			 <c:forEach var="item" items="${pageList.result}" varStatus="row" >
	               <tr>
	                   <td>${(pageList.pageNum-1)*pageList.numPerPage+row.count}</td>
	                   <td class="center">${item.operationName}</td>
	                   <td>${item.operationContent}</td>
	                   <td>${item.operationUserName}</td>
	                   <td><ry:formatDate date="${item.createdate}" ></ry:formatDate></td>
	               </tr>
              </c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<%@include file="/WEB-INF/jsp/inc/page.jsp" %>
</div>
