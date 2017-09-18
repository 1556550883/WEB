<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<form id="pagerForm" method="post" action="userStudentCart/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}">
	<input type="hidden" name="orderDirection" value="${param.orderDirection}">
</form>


<div class="panelBar">
<input type="hidden" value="${cartStatus }">
		<ul class="toolBar">
		
			<li><a title="确实要审核通过吗?" target="selectedTodo" rel="ids" postType="string" href="userStudentCart/cartStatus?type=2&cartStatus=${cartStatus }" class="edit"><span>审核通过</span></a></li>	
			<li><a title="确实要审核失败吗?" target="selectedTodo" rel="ids" postType="string" href="userStudentCart/cartStatus?type=3&cartStatus=${cartStatus }" class="icon"><span>审核失败</span></a></li>		
		
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
				<th align="center">院系</th>
				<th align="center">班级名称</th>
				<th align="center">学生编号</th>
				<th align="center">状态</th>
				
				
			</tr>
		</thead>
		<tbody>
		     <c:forEach var="item" items="${pageList.result}" varStatus="row">
				<tr >
				  <td align="center">
        		<input type="checkbox"  name="ids" value="${item.studentCartId}"></td>
        		<td>
				<input type="hidden" name="studentCartId" value="${item.studentCartId}">
				${(pageList.pageNum-1)*pageList.numPerPage+row.count}</td>
	            <td>${item.schoolName}</td>
	          	 <td>${item.departmentName}</td>
	          	 <td>${item.className }</td>
	          	<td>${item.userAppNum }</td>
	          	<td><ry:show parentCode="USER_CART_STATUS" itemCode="${item.cartStatus}"></ry:show></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<%@include file="/WEB-INF/jsp/inc/page.jsp" %>