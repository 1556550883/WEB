<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<form id="pagerForm" method="post" action="school/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}">
	<input type="hidden" name="orderDirection" value="${param.orderDirection}">
</form>

<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" onclick="add('school/toEdit','添加学校',900,550,'main_')" ><span>添加</span></a></li>			
			<li><a class="delete" title="确定要删除该信息吗？" href="school/del"  target="selectedTodo" postType="string" rel="ids"><span>删除</span></a></li>
		
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="85">
		<thead>
			<tr>
				<th width="30">
				<input type="checkbox" group="ids" class="checkboxCtrl" >
				</th>
				<th align="center">序号</th>
				<th align="center">学校名称</th>
				<th align="center">区域</th>
				<th align="center">学校类型</th>
				<th align="center">操作</th>
				
				
			</tr>
		</thead>
		<tbody>
		     <c:forEach var="item" items="${pageList.result}" varStatus="row">
				<tr >
				  <td align="center">
        		<input type="checkbox"  id="orderCheckBox" name="ids" value="${item.schoolId}"></td>
        		<td>
				<input type="hidden" name="schoolId" value="${item.schoolId }">
				${(pageList.pageNum-1)*pageList.numPerPage+row.count}</td>
	            <td>${item.schoolName}</td>
	            <td><ry:show parentCode="${item.province}" itemCode="${item.province}" type="2"></ry:show>
					<ry:show parentCode="${item.province}" itemCode="${item.city}" type="3"></ry:show>
					</td>
	            <td><ry:show itemCode="${item.education}" parentCode="EDUCATION_TYPE"></ry:show></td>
	          	 <td>
	           		<a title="修改学校信息"   onclick="add('school/toEdit?schoolId=${item.schoolId}','修改学校信息',900,550,'main_')"  rel="users_saveedit" class="btnEdit">修改</a>				
	           	</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<%@include file="/WEB-INF/jsp/inc/page.jsp" %>