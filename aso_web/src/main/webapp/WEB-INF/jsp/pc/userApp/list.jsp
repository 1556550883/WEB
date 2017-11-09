<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding parentCode="USER_APP_TYPE" bingdingName="userAppType"></ry:binding>
<form id="pagerForm" method="post" action="userApp/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}">
	<input type="hidden" name="orderDirection" value="${param.orderDirection}">
	<input type="hidden" name="phoneNum" value="${param.phoneNum }">
	<input type="hidden" name="userNum" value="${param.userNum }">
	<input type="hidden" name="userApppType" value="${param.userApppType }">
	<input type="hidden" name="userNick" value="${param.userNick }">
	<input type="hidden" name="loginName" value="${param.loginName }">
	<input type="hidden" name="flag2" value="${param.flag2 }">
</form>

<div class="pageHeader">
	<form rel="pagerForm" id="selForm" onsubmit="return navTabSearch(this);" action="userApp/list" method="post">
	<div class="searchBar">
		
		<table class="searchContent">
			<tr>
				<td>					
					用户类型：
					<select name="userApppType">
	                   <c:forEach items="${userAppType}" var="item">
	                          <option value="${item.itemCode}" <c:if test="${bean.userApppType == item.itemCode}">selected</c:if> >${item.itemName}</option>
	                   </c:forEach>
	                </select>
				</td>
				<td>登陆名：<input type="text" name="loginName" value="${bean.loginName}"/></td>
				<!-- <td>最近登录ip：<input type="text" name="flag2" value="${bean.flag2}"/></td> -->
				<!--<td>用户编号：<input type="text" name="userNum" value="${bean.userNum}"/></td>-->
				<!--<td>手机号：<input type="text" name="phoneNum" value="${bean.phoneNum}"/></td>-->
				<!--<td>					
					用户类型：<select  name="userApppType">
	                   <option value="">请选择</option>
	                   <c:forEach items="${userAppType}" var="item">
	                          <option value="${item.itemCode}" <c:if test="${bean.userApppType == item.itemCode}">selected</c:if> >${item.itemName}</option>
	                   </c:forEach>
	                </select>
				</td>-->
				
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
				<li><a class="add" onclick="add('userApp/toEdit','添加信息',900,500,'main_')"><span>添加</span></a></li>
				<li><a class="delete" title="确定要删除该信息吗？" href="userApp/del"  target="selectedTodo" postType="string" rel="ids"><span>删除</span></a></li>
				<!--<li><a class="edit" title="确定设为普通用户吗？" href="userApp/updateUserApp"  target="selectedTodo" postType="string" rel="ids"><span>设为普通用户</span></a></li>-->
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="101.7%" layoutH="130">
		<thead>
			<tr>
				<th width="30">
				<input type="checkbox" group="ids" class="checkboxCtrl" >
				</th>
				<!--<th align="center">用户编号</th>
				<th align="center">用户昵称</th>
				<th align="center">出生日期</th>
				<th align="center">性别</th>-->
				<th align="center">用户ID</th>
				<th align="center">登陆名</th>
				<th align="center">真实姓名</th>
				<th align="center">余额</th>
				<th align="center">支付宝账号</th>
				<th align="center">微信账号</th>
				<th align="center">用户等级</th>
				
				<!--<th align="center">用户类型</th>-->	
				<!--<th align="center">手机号</th>	
				<th align="center">新手任务是否完成</th>-->	
				
				<th align="center">操作</th>			
			</tr>
		</thead>
		<tbody>
		     <c:forEach var="item" items="${pageList.result}">
				<tr >
				  <td align="center">
       			 <input type="checkbox"  id="orderCheckBox" name="ids" value="${item.userNum}"></td>
					<!--<td>${item.userNum}</td>	               
	                <td>${item.userNick}</td>
	                <td>${item.birthday}</td>
	              	<td><ry:show parentCode="USERSEX" itemCode="${item.sex}"></ry:show></td>-->
	                <td>${item.userAppId}</td>
	                <td>${item.loginName}</td>
	                <td>${item.userNick}</td>
	                <td>${item.userScore.score}</td>
	                <td>${item.zhifubao}</td>
	                <td>${item.weixin}</td>
	                <td>${item.level }</td>
	                
	                <!--<td><ry:show parentCode="USER_APP_TYPE" itemCode="${item.userApppType}"></ry:show></td>-->
	                <!--<td>${item.phoneNum}</td>
	                <td>${item.taskNewStatus ==2? '已完成' : '未完成'}</td>-->
	                 
	                <td>
	                	<!-- <a style="cursor: pointer;" onclick="openNav('userApp/getScoreInfoList?userAppNum=${item.userNum}','积分明细','main_index2')">积分明细</a>                      
						<a style="cursor: pointer;" onclick="openNav('adverEffectiveInfo/list?userNum=${item.userNum}','有效下载任务','main_index2')">有效下载任务</a> -->
						<a style="cursor: pointer;" onclick="openNav('channelInfo/employeeIdfaStatistics?userAppId=${item.userAppId}','任务明细','main_index2')">任务明细</a>     	
						<a title="修改手机用户信息"   onclick="add('userApp/toEdit?userNum=${item.userNum}','修改手机用户信息',1100,550,'main_')"  rel="users_saveedit" class="btnEdit">手机用户</a>	
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<%@include file="/WEB-INF/jsp/inc/page.jsp" %>
