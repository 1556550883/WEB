<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding parentCode="" bingdingName=""></ry:binding>

<form id="pagerForm" method="post" action="externalInfo/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}">
	<input type="hidden" name="orderDirection" value="${param.orderDirection}">
</form>

<div class="panelBar">
	<ul class="toolBar">
		<li><a class="add" onclick="openNav('externalInfo/toEdit?type=1','添加信息','main_index')"><span>添加</span></a></li>			
		<li><a class="delete" title="确定要启用吗？" href="externalInfo/updateIsEnable?isEnable=1"  target="selectedTodo" postType="string" rel="ids"><span>启用</span></a></li>
		<li><a class="delete" title="确定要停用吗？" href="externalInfo/updateIsEnable?isEnable=0"  target="selectedTodo" postType="string" rel="ids"><span>停用</span></a></li>
		<li class="line">line</li>
	</ul>
</div>

<table class="table" width="100%" layoutH="128">
	<thead>
		<tr>
			<th width="30">
			<input type="checkbox" group="ids" class="checkboxCtrl" >
			</th>
			<th align="center">外放渠道名称</th>
			<th align="center">外放渠道编号</th>
			<th align="center">外放渠道标识</th>
			<th align="center">是否启用</th>
			<th align="center">外放渠道描述</th>
			<th align="center">创建时间</th>			
			<th align="center">管理</th>	
		</tr>
	</thead>
	
	<tbody>
	     <c:forEach var="item" items="${pageList.result}">
			<tr >
			  	<td align="center"><input type="checkbox"  id="orderCheckBox" name="ids" value="${item.externalChannelId}"></td>
				<td>${item.externalChannelName}</td>
                <td>${item.externalChannelNum}</td>
                <td>${item.externalChannelKey}</td>
                 <td>
	                <c:if test="${item.isEnable==1}"><div style="color:green">启用</div></c:if>
	                <c:if test="${item.isEnable==0}"><div style="color:red">停用</div></c:if>
                 </td>
                <td>${item.externalChannelDesc}</td>
				<td><ry:formatDate date="${item.createDate}" toFmt="yyyy-MM-dd"></ry:formatDate> </td>
				<td>
					<a style="cursor:pointer;" onclick="openNav('externalChannelAdverInfo/list?externalChannelNum=${item.externalChannelNum}','外放广告管理','main_index2')"><div style="color: blue">外放广告管理</div></a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<%@include file="/WEB-INF/jsp/inc/page.jsp" %>

