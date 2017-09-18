<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding parentCode="T_ADVER_AUTH_TYPE" bingdingName="authType"></ry:binding>
<form id="pagerForm" method="post" action="adverauth/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}">
	
	
	<input type="hidden" name="commonNum" value="${bean.commonNum}">
	<input type="hidden" name="commonType" value="${bean.commonType}">
	<input type="hidden" name="authType" value="${bean.authType}">
	<input type="hidden" name="commonNumName" value="${bean.commonNum}">
</form>


<div class="pageHeader">
<form onsubmit="return navTabSearch(this);" action="adverauth/list" method="post">
<div class="searchBar">
<ul class="searchContent">
	<input type="hidden" name="commonNum" value="${bean.commonNum}">
	<input type="hidden" name="commonNumName" value="${bean.commonNum}">
	<input type="hidden" name="commonType" value="${bean.commonType}">
	<input type="hidden" name="authType" value="${bean.authType}">

	<!--<li>
		<label>权限类型：</label>
		<select name="authType">
			<option value="">请选择</option>
			<c:forEach items="${authType }" var="item">
				<option value="${item.itemCode }" <c:if test="${item.itemCode == bean.authType}">selected="selected"</c:if>>${item.itemName }</option>
			</c:forEach>
		</select>
	</li>-->
</ul>

<div class="subBar">
<ul> 
    <li>
	<!--<div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div>-->
	</li>
</ul>
</div>
</div>
</form>
</div>

	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" onclick="add('adverauth/toedit?commonNum=${bean.commonNum }&commonType=${bean.commonType }&authType=${bean.authType }','添加信息','800','400','main_index')"><span>添加</span></a></li>			
			<li><a class="delete" title="确定要删除该信息吗？" href="adverauth/delAll"  target="selectedTodo" postType="string" rel="ids"><span>删除</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="135">
		<thead>
			<tr>
				<th width="30">
				<input type="checkbox" group="ids" class="checkboxCtrl" >
				</th>
				<th align="center">文章或广告编号</th>
				<th align="center">学校或城市编号</th>
				<c:if test="${bean.authType == 2}"><th align="center">学校名称</th></c:if>
				<th align="center">权限类型</th>
				<th align="center">类型</th>
			</tr>
		</thead>
		<tbody>
		     <c:forEach var="item" items="${pageList.result}" varStatus="row">
				<tr >
				  <td align="center">
        	<input type="checkbox"  id="orderCheckBox" name="ids" value="${item.authId}"></td>	
        							               
	                <td>${item.commonNum}</td>
	                <td>${item.commonAuthNum}</td>
	              <c:if test="${bean.authType == 2}"><td>${item.school.schoolName}</td></c:if>
	                <td><ry:show parentCode="T_ADVER_AUTH_TYPE" itemCode="${item.authType}"></ry:show></td>                 
	                <td><ry:show parentCode="T_ADVER_COMMON_TYPE" itemCode="${item.commonType}"></ry:show></td> 
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<%@include file="/WEB-INF/jsp/inc/page.jsp" %>
