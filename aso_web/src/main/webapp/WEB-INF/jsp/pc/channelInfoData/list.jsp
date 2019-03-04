<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding parentCode="CHANNEL_LEVEL,CHANNEL_TYPE,SYSTEM_TYPE" bingdingName="channellevel,channeltype,systemtype"></ry:binding>

<form id="pagerForm" method="post" action="channelInfo/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}">
	<input type="hidden" name="orderDirection" value="${param.orderDirection}">
</form>

<div class="pageHeader">
	<form rel="pagerForm" id="selForm" onsubmit="return navTabSearch(this);" action="channelInfo/list" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>渠道名称：<input type="text" name="channelName" value="${bean.channelName}"/></td>
					<td>渠道类型：<select  id="channelType" name="channelType">
		                   <option value="">请选择</option>
		                   <c:forEach items="${channeltype}" var="item">
		                          <option value="${item.itemCode}"><c:if test="${bean.channelType == item.itemCode}">selected</c:if>${item.itemName}</option>
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
		<li><a class="add" onclick="openNav('channelInfo/toEdit?type=1','添加信息','main_index')"><span>添加</span></a></li>			
		<li><a mask=true class="edit"  onclick="openNavU('channelInfo/toEdit?type=0&channelId=','修改信息','main_index')"><span>修改</span></a></li>
		<li><a class="delete" title="确定要启用吗？" href="channelInfo/updateIsEnable?isEnable=1"  target="selectedTodo" postType="string" rel="ids"><span>启用</span></a></li>
		<li><a class="delete" title="确定要停用吗？" href="channelInfo/updateIsEnable?isEnable=0"  target="selectedTodo" postType="string" rel="ids"><span>停用</span></a></li>
		<li><a mask=true class="search"  onclick="openNavU('channelInfo/toEdit?channelId=','查看信息','main_index')"><span>查看</span></a></li>
		<li class="line">line</li>
	</ul>
</div>

<table class="table" width="100%" layoutH="128">
	<thead>
		<tr>
			<th width="30">
			<input type="checkbox" group="ids" class="checkboxCtrl" >
			</th>
			<th align="center">渠道名称</th>
			<th align="center">渠道编号</th>
			<th align="center">创建时间</th>				
			<th align="center">昨日跑量</th>
			<th align="center">今日跑量</th>
			<th align="center">本月跑量</th>
			<th align="center">昨日金额</th>
			<th align="center">今日金额</th>
			<th align="center">本月金额</th>
		</tr>
	</thead>
	
	<tbody>
	     <c:forEach var="item" items="${pageList.result}">
			<tr >
			  	<td align="center"><input type="checkbox"  id="orderCheckBox" name="ids" value="${item.channelId}"></td>
				<td>${item.channelName}</td>
                <td>${item.channelNum}</td>
				<td><ry:formatDate date="${item.createDate}" toFmt="yyyy-MM-dd"></ry:formatDate> </td>
				  <td>${item.ydayNum}</td>
				    <td>${item.todayNum}</td>
				      <td>${item.monNum}</td>
				        <td>${item.ydayScore}</td>
				          <td>${item.todayScore}</td>
				              <td>${item.monScore}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<%@include file="/WEB-INF/jsp/inc/page.jsp" %>

