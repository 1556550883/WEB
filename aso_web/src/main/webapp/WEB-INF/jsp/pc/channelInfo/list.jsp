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
				<td>					
					渠道类型：<select  id="channelType" name="channelType">
	                   <option value="">请选择</option>
	                   <c:forEach items="${channeltype}" var="item">
	                          <option value="${item.itemCode}" <c:if test="${bean.channelType == item.itemCode}">selected</c:if> >${item.itemName}</option>
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
				<th align="center">渠道描述</th>
				<th align="center">渠道推荐等级</th>
				<th align="center">渠道类型</th>
				<th align="center">系统类型</th>
				<th align="center">应用key</th>
				<th align="center">应用密码</th>
				<th align="center">是否启用</th>
				<th align="center">创建时间</th>				
				<th align="center">管理</th>
				<th align="center">查看回调地址</th>
			
			</tr>
		</thead>
		<tbody>
		     <c:forEach var="item" items="${pageList.result}">
				<tr >
				  <td align="center">
        <input type="checkbox"  id="orderCheckBox" name="ids" value="${item.channelId}"></td>
					<td>${item.channelName}</td>	               
	                <td><ry:show parentCode="CHANNEL_NUM" itemCode="${item.channelNum}"></ry:show></td>
	                <td>${item.channelDesc}</td>
	                <td><ry:show parentCode="CHANNEL_LEVEL" itemCode="${item.channelLevel}"></ry:show></td>
	             	 <td><ry:show parentCode="CHANNEL_TYPE" itemCode="${item.channelType}"></ry:show></td>
	             	 <td><ry:show parentCode="SYSTEM_TYPE" itemCode="${item.systemType}"></ry:show></td>	  	               
	                <td>${item.appKey}</td>
	                 <td>${item.appPassword}</td>
	                <td>
	                <c:if test="${item.isEnable==1}"><span style="color: green">启用</span></c:if>
	                 <c:if test="${item.isEnable==0}"><span style="color: red">停用</span></c:if>
	                 </td>
					<td><ry:formatDate date="${item.createDate}" toFmt="yyyy-MM-dd"></ry:formatDate> </td>
					<td><c:if test="${item.channelType!=3}"><a style="cursor: pointer;" onclick="openNav('channelAdverInfo/list?channelNum=${item.channelNum}','广告管理','main_index2')">广告管理</a></c:if>
					<c:if test="${item.channelType==3}">
						<!-- <a style="cursor: pointer;" onclick="openNav('adverInferface/toConfigure?inferfaceType=2&inferfaceRequestType=2&adverNum=${item.channelNum}','参数配置','main_index2')"><span style="color: blue">调用服务端</span> </a> -->
						<a style="cursor: pointer;" onclick="openNav('adverInferface/toConfigure?inferfaceType=2&inferfaceRequestType=1&adverNum=${item.channelNum}','参数配置','main_index2')"><span style="color: green">客户回调我们</span> </a>
					</c:if>
					</td>
				<td><a class="add" onclick="add('channelInfo/toLookReq?channelNum=${item.channelNum}','查看回调地址',600,200,'main_')"><span style="color: green;">查看回调地址</span></a></td>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<%@include file="/WEB-INF/jsp/inc/page.jsp" %>

