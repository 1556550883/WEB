<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<form id="pagerForm" method="post" action="notice/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}">
	<input type="hidden" name="orderDirection" value="${param.orderDirection}">
	<input type="hidden" name="shopName" value="${bean.shopName}" />
</form>
<div class="pageHeader">
  <form rel="pagerForm" onsubmit="return navTabSearch(this);"  action="shopInfo/list" method="post">
    <div class="searchBar">
		<ul class="searchContent ys_wa">
		<li><label class="new_addLabel">商铺名称：</label><input type="text" name="shopName" value="${bean.shopName}" /></li>
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
			<li><a class="add" onclick="openNav('shopInfo/toedit','添加商铺信息','main_index')"><span>添加</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="132">
		<thead>
			<tr>
				<th>序号</th>
				<th>商铺名称</th>
				<th>商铺地址</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		     <c:forEach var="item" items="${pageList.result}" varStatus="row">
				<tr >
					<td>${(pageList.pageNum-1)*pageList.numPerPage+row.count}</td>
					<td>${item.shopName}</td>
	                <td>${item.shopAddress}</td>
					<td>
						<a class="btnEdit" style="cursor: pointer;" onclick="openNav('shopInfo/toedit?id=${item.shopId}','修改商铺信息','main_index')" >修改</a>
						<a target="ajaxTodo" href="shopInfo/delAll?ids=${item.shopId}" title="您确定删除商铺‘${item.shopName}’ 吗？" class="btnDel">删除</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
		<%@include file="/WEB-INF/jsp/inc/page.jsp" %>
</div>
