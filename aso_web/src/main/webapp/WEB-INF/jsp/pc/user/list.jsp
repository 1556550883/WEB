<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding parentCode="USERSEX,ORG_TYPE" bingdingName="usersexs,orgTypeList"></ry:binding>
<form id="pagerForm" method="post" action="user/users">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
	
	<!-- 分页时 带模糊查询的值 -->
	<input type="hidden" name="loginName" value="${param.loginName}"/>
	<input type="hidden" name="userName" value="${param.userName}"/>
	<input type="hidden" name="userSex" value="${param.userSex}"/>
	<input type="hidden" name="createdate" value="${param.createdate}" />
	<input type="hidden" name="endDate" value="${param.endDate}" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="user/users" method="post">
	<div class="searchBar">
		
		<table class="searchContent">
			<tr>
				<td>
					登录名称：<input type="text" name="loginName" value="${tuser.loginName }" />
				</td>
				<td>真实姓名：<input type="text" name="userName" value="${tuser.userName }"/></td>
				<td>
					
					<select class="combox" name="userSex" >
						<option value="">---请选择性别---</option>
						<c:forEach items="${usersexs}" var="item">
						   <option value="${item.itemCode }" ${item.itemCode==tuser.userSex ? 'selected':'' } >${item.itemName }</option>
		                </c:forEach>
				    </select>
				</td>
				<td>
					生效日期：
					<input type="text" name="createDate" id="user_startDate" maxId="user_endDate" value="<fmt:formatDate value="${tuser.createDate}" pattern="yyyy-MM-dd"/>" class="date" readonly="true" title="开始时间" />
					   ～
					<input type="text" name="endDate" minId="user_startDate" class="date" value="<fmt:formatDate value="${tuser.endDate}" pattern="yyyy-MM-dd"/>" id="user_endDate" readonly="true" title="结束时间"/>
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
			<li><a class="add" onclick="add('user/toUserEdit','添加信息',900,500,'main_')"><span>添加</span></a></li>
				
			<li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids" postType="string" href="user/batchDelete" class="delete"><span>批量删除</span></a></li>
<!--			<li class="line">line</li>-->
<!--			<li><a class="icon" href="user/exportExcel" target="dwzExport" targetType="navTab" title="您确定导出这些记录吗?"><span>导出EXCEL</span></a></li>-->
		</ul>
	</div>
	<table class="table" width="100%" layoutH="141">
		<thead>
			<tr>
				<th width="30"><input type="checkbox" group="ids" class="checkboxCtrl" ></th>
				<th width="40"  align="center">序号</th>
				<th width="180" align="center">登录名称</th>
				<th width="180" align="center">真实姓名</th>
				<th width="50" align="center">性别</th>
				<th width="60" align="center" >电话号码</th>
				<th width="70" align="center">邮箱</th>
				<th width="100" align="center">生效日期</th>
				<th width="70" align="center" orderField="we">状态</th>
				<th width="70" >操作</th>
			</tr>
		</thead>
		<tbody>
		    <c:forEach var="item" items="${pageList.result}" varStatus="row">
			<tr target="sid_user" rel="1">
				<td><input name="ids" value="${item.userId }" type="checkbox"></td>
				<td>${(pageList.pageNum-1)*pageList.numPerPage+row.count}</td>
				<td>${item.loginName}</td>
				<td>${item.userName}</td>
				<td><ry:show parentCode="USERSEX" itemCode="${item.userSex}"></ry:show></td>
				<td>${item.userPhone}</td>
				<td>${item.userEmail}</td>
				<td><fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd"/></td>
				<td><ry:show parentCode="USERSTATUS" itemCode="${item.userStatus}"></ry:show></td>
				<td>
					<a title="您确定重置用户 “${item.loginName}”的密码吗？" target="ajaxTodo" href="user/resetPassword?userId=${item.userId }"  class="btnView">重置密码</a>
					<a title="修改用户" target="navTab" href="user/toUserEdit?userId=${item.userId }" rel="users_saveedit" class="btnEdit">编辑</a>
					<a title="您确定删除用户 ”${item.loginName}“ 吗？" target="ajaxTodo" href="user/del?userId=${item.userId}&loginName=${item.loginName}" class="btnDel">删除</a>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<%@include file="/WEB-INF/jsp/inc/page.jsp" %>
</div>
