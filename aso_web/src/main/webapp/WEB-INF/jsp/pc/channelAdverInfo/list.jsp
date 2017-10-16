<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<form id="pagerForm" method="post" action="channelAdverInfo/list?channelNum=${bean.channelNum}">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}">
	<input type="hidden" name="orderDirection" value="${param.orderDirection}">
	<input type="hidden" name="shopName" value="" />
</form>
<div class="pageHeader">
  <form rel="pagerForm" onsubmit="return navTabSearch(this);"  action="channelAdverInfo/list?channelNum=${bean.channelNum}" method="post">
    <div class="searchBar">
		<ul class="searchContent ys_wa">
		<li><label class="new_addLabel">广告名称：</label><input type="text" name="shopName" value="${bean.adverName}" /></li>
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
			<li><a class="add" onclick="openNav('channelAdverInfo/toedit?channelNum=${bean.channelNum}','添加广告信息','main_index3')"><span>添加</span></a></li>
			<li><a class="delete" title="确定要删除选择的信息吗？" href="channelAdverInfo/delAll"  target="selectedTodo" postType="string" rel="ids"><span>删除</span></a></li>
			<li><a class="edit" title="确定要启用选择的信息吗？" href="channelAdverInfo/updateAdverStatus?status=1"  target="selectedTodo" postType="string" rel="ids"><span>启用</span></a></li>
			<li><a class="edit" title="确定要停用选择的信息吗？" href="channelAdverInfo/updateAdverStatus?status=2"  target="selectedTodo" postType="string" rel="ids"><span>停用</span></a></li>
			<li><a class="edit" title="确定要刷新选择的信息吗？" href="channelAdverInfo/freshAdverNum"  target="selectedTodo" postType="string" rel="ids"><span>刷新</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="132">
		<thead>
			<tr>
				<th width="30">
				<input type="checkbox" group="ids" class="checkboxCtrl" >
				</th>
				<th align="center">广告名称</th>
				<th align="center">广告ID</th>
				<th align="center">广告价格</th>
				<th align="center">任务时效（单位：分钟）</th>
				<th align="center">广告数量</th>
				<th align="center">广告剩余数量</th>
				<th align="center">广告完成数量</th>
				<th align="center">广告开始时间——广告结束时间</th>
				<th align="center">状态</th>
				<!--<th align="center">权限</th>-->
				<th align="center">创建时间</th>
				<th align="center">操作</th>
				<th align="center">广告下载记录 </th>		
			</tr>
		</thead>
		<tbody>
		     <c:forEach var="item" items="${pageList.result}">
				<tr >
				  <td align="center">
        			<input type="checkbox"  id="orderCheckBox" name="ids" value="${item.adverId}"></td>
					<td>${item.adverName}</td>	  
					<td>${item.adid}</td>	              
	                <td>${item.adverPrice}</td>
	                <td>${item.timeLimit}</td>
	                <td>${item.adverCount}</td>
	                <td>${item.adverCountRemain}</td>
	                <td>${item.adverCountComplete}</td>
	                <td>${item.adverDayStart}--${item.adverDayEnd}</td>
	               <!-- <td>${item.adverTimeStart}--${item.adverTimeEnd}</td> -->
	                <td><c:if test="${item.adverStatus==0}">未审核</c:if><c:if test="${item.adverStatus==1}">启用</c:if><c:if test="${item.adverStatus==2}">停用</c:if><c:if test="${item.adverStatus==3}">已支付</c:if></td>
	              	<!--<td><ry:show parentCode="IS_AUTH" itemCode="${item.isAuth}"></ry:show></td>-->
	                <td><ry:formatDate date="${item.adverCreatetime}" toFmt="yyyy-MM-dd"></ry:formatDate> </td> 
					<td><a class="btnEdit" title="编辑" href="javascript:;;" onclick="openNav('channelAdverInfo/toedit?id=${item.adverId}','修改广告信息','main_index3')"><span>修改</span></a></td>
					<td>
						<a style="cursor: pointer;" onclick="openNav('adverEffectiveInfo/completeList?adverId=${item.adverId}','广告下载记录','main_index2')"><div style="color: blue">广告下载记录</div></a>
						<c:if test="${item.effectiveSource==1}">
							<a style="cursor: pointer;" onclick="openNav('adverInferface/toConfigure?inferfaceType=1&inferfaceRequestType=2&adverNum=${item.adverNum}','参数配置','main_index2')"><span style="color: blue">上传数据配置</span> </a>
							<a style="cursor: pointer;" onclick="openNav('adverInferface/toConfigure?inferfaceType=1&inferfaceRequestType=1&adverNum=${item.adverNum}','参数配置','main_index2')"><span style="color: green">客户回调我们</span> </a>
						</c:if>
					 <!--<c:if test="${item.isAuth == 1 || item.isAuth == 2}">
		           	   		<a href="adverauth/list?commonNumName=${item.adverNum}&commonNum=${item.adverNum}&commonType=2&authType=${item.isAuth}" target="navTab" rel="main_1" style="color: blue;">权限管理</a>
		           	 </c:if>-->
					</td>
				
				</tr>
			</c:forEach>
		</tbody>
	</table>
		<%@include file="/WEB-INF/jsp/inc/page.jsp" %>
</div>
