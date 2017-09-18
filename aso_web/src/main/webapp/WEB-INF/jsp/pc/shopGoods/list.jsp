<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding parentCode="ADVER_TYPE" bingdingName="advertype"></ry:binding>
<form id="pagerForm" method="post" action="channelInfo/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}">
	<input type="hidden" name="orderDirection" value="${param.orderDirection}">
</form>
<div class="pageHeader">
	<form rel="pagerForm" id="selForm" onsubmit="return navTabSearch(this);" action="shopGoods/list" method="post">
	<div class="searchBar">
		
		<table class="searchContent">
			<tr>
				<td>商品名称：<input type="text" name="goodName" value="${bean.goodName}"/></td>
				<td>					
					商品类型：<select  name="goodType">
	                   <option value="">请选择</option>
	                   <c:forEach items="${advertype}" var="item">
	                          <option value="${item.itemCode}" <c:if test="${bean.goodType == item.itemCode}">selected</c:if> >${item.itemName}</option>
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
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" onclick="openNav('shopGoods/toEdit?type=0','添加信息','main_index')"><span>添加</span></a></li>			
			<li><a mask=true class="edit"  onclick="openNavU('shopGoods/toEdit?type=0&goodId=','修改信息','main_index')"><span>修改</span></a></li>
			<li><a class="delete" title="确定要删除该信息吗？" href="shopGoods/delAll"  target="selectedTodo" postType="string" rel="ids"><span>删除</span></a></li>
			<li><a mask=true class="search"  onclick="openNavU('shopGoods/toEdit?goodId=','查看信息','main_index')"><span>查看</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="85">
		<thead>
			<tr>
				<th width="30">
				<input type="checkbox" group="ids" class="checkboxCtrl" >
				</th>
				<th align="center">序号号</th>
				<th align="center">店铺编号</th>
				<th align="center">商品名称</th>
				<th align="center">商品兑换所需积分</th>
				<th align="center">商品类型</th>
				<th align="center">兑换码生成方式</th>
				<th align="center">商品描述</th>
				
			</tr>
		</thead>
		<tbody>
		     <c:forEach var="item" items="${pageList.result}" varStatus="row">
				<tr >
				  <td align="center">
        <input type="checkbox"  id="orderCheckBox" name="ids" value="${item.goodId}"></td>	
        			<td>${(pageList.pageNum-1)*pageList.numPerPage+row.count}</td>  				               
	                <td>${item.shopNum}</td>
	                <td>${item.goodName}</td>
	                <td>${item.goodPrice}</td>
	                 <td><ry:show parentCode="GOOD_TYPE" itemCode="${item.goodType}"></ry:show></td>
	                 <td><ry:show parentCode="EXCHANGE_CODE_TYPE" itemCode="${item.exchangeCodeType}"></ry:show></td>			             
	                <td>${item.goodDesc}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<%@include file="/WEB-INF/jsp/inc/page.jsp" %>
