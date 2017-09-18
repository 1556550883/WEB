<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding parentCode="USERSEX,ORG_TYPE" bingdingName="usersexs,orgTypeList"></ry:binding>
<form id="pagerForm" method="post" action="statistics/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
	
	<!-- 分页时 带模糊查询的值 -->
	<input type="hidden" name="createdate" value="${param.createdate}" />
	<input type="hidden" name="endDate" value="${param.endDate}" />
</form>
<div class="pageContent">
 	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="statistics/list" method="post">
	<div class="searchBar">
		
		<table class="searchContent">
			<tr>
				<td>
				日期：<input type="text" name="createDate" class="date textInput readonly"  readonly="true" value="<fmt:formatDate value="${info.createDate}" pattern="yyyy-MM-dd"/>"/>  
				</td>				
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
	</div>
	<table class="table" width="100%" layoutH="85">
		<thead>
			<tr>
									
				<th width="180" align="center">每日注册用户</th>
			
			</tr>
		</thead>
		<tbody>		   
			<tr target="sid_user" rel="1">
					
				<td>${dayuserNum}</td>						
			</tr>
		
		</tbody>
	</table>
	<!-- 分页 -->
	<%@include file="/WEB-INF/jsp/inc/page.jsp" %>
</div>
