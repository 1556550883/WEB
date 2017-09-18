<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="role/add" target="navTab" rel="role_saveedit"><span>添加角色</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				
				<th align="center">角色名称</th>
				<th align="center">角色排序</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		     <c:forEach var="item" items="${roles}">
				<tr >
					<td>${item.roleName }</td>
	                <td>${item.orderby }</td>
					<td>
						<a title="修改角色" target="navTab" rel="role_saveedit" href="role/edit?roleId=${item.roleId}" class="btnEdit">修改</a>
						<a target="ajaxTodo" href="role/del?roleId=${item.roleId}&roleName=${item.roleName }" title="您确定删除角色： “${item.roleName }” 吗？" class="btnDel">删除</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
