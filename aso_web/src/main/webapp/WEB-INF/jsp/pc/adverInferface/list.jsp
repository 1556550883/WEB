<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding bingdingName="parametertype,requesttype,inferfacetype" parentCode="PARAMETER_TYPE,REQUEST_TYPE,INFERFACE_TYPE"></ry:binding>
<form id="pagerForm" method="post" action="adverInferface/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}">
	<input type="hidden" name="orderDirection" value="${param.orderDirection}">

</form>
<div class="pageHeader">
	<form rel="pagerForm" id="selForm" onsubmit="return navTabSearch(this);" action="adverInferface/list" method="post">
	<div class="searchBar">
		
		<table class="searchContent">
			<tr>
				<td>广告名称：<input type="text" name="adverNum" value="${bean.adverNum}"/></td>
				<td>参数名称：<input type="text" name="parameterType" value="${bean.parameterType}"/></td>
				<td>					
					接口回调方式：<select  name="inferfaceRequestType">
	                   <option value="">请选择</option>
	                   <c:forEach items="${requesttype}" var="item">
	                          <option value="${item.itemCode}" <c:if test="${bean.inferfaceRequestType == item.itemCode}">selected</c:if> >${item.itemName}</option>
	                   </c:forEach>
	                </select>
				</td>
				<td>					
					接口类型：<select  name="inferfaceType">
	                   <option value="">请选择</option>
	                   <c:forEach items="${inferfacetype}" var="item">
	                          <option value="${item.itemCode}" <c:if test="${bean.inferfaceType == item.itemCode}">selected</c:if> >${item.itemName}</option>
	                   </c:forEach>
	                </select>
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
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" onclick="openNav('adverInferface/toConfigure','配置参数信息','main_index')"><span>配置参数</span></a></li>			
			<li><a mask=true class="edit"  onclick="openNavU('adverInferface/toEdit?type=1&inferaceId=','修改信息','main_index')"><span>修改</span></a></li>
			<li><a mask=true class="search"  onclick="openNavU('adverInferface/toEdit?inferaceId=','查看信息','main_index')"><span>查看</span></a></li>
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
				<th align="center">参数名称</th>
				<th align="center">对应参数名称</th>
				<th align="center">接口回调方式</th>
				<th align="center">接口类型</th>	
			</tr>
		</thead>
		<tbody>
		     <c:forEach var="item" items="${pageList.result}">
				<tr >
				  <td align="center">
        			<input type="checkbox"  id="orderCheckBox" name="ids" value="${item.inferaceId}"></td>
	                <td>${item.adverNum}</td>
        			<td>${item.parameterType}</td>	
        			<td>${item.parameterName}</td>		           
	                 <td><ry:show parentCode="REQUEST_TYPE" itemCode="${item.inferfaceRequestType}"></ry:show></td>	  	
	                 <td><ry:show parentCode="INFERFACE_TYPE" itemCode="${item.inferfaceType}"></ry:show></td>	                
				</tr>
			</c:forEach>
		</tbody>
	</table>
		<%@include file="/WEB-INF/jsp/inc/page.jsp" %>
</div>
