<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding parentCode="USER_APP_TYPE" bingdingName="userAppType"></ry:binding>
<form id="pagerForm" method="post" action="userApp/effUserList">
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
	<form rel="pagerForm" id="selForm" onsubmit="return navTabSearch(this);" action="userApp/effUserList" method="post">
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
				<td>用户id：<input type="text" name="userAppId" value="${bean.userAppId}"/></td>
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
				<li><span style="" onclick= "show()">导出</span></li>
				<li><span style="" onclick= "clearScore()">清除余额</span></li>
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
				<th align="center">用户ID</th>
				<th align="center">手机号码</th>
				<th align="center">手机号码等级</th>
				<th align="center">机型版本</th>
				<th align="center">登陆名</th>
				<th align="center">微信账号</th>
				<th align="center">微信头像</th>
				<th align="center">真实姓名</th>
				<th align="center">支付宝账号</th>
				<th align="center">余额</th>
				<th align="center">总收益</th>
				<th align="center">用户等级</th>
				<th align="center">邀请人ID</th>
				<th align="center">关系解除</th>	
				<th align="center">是否提现</th>
				<th align="center">徒弟列表</th>
				<th align="center">徒弟分红</th>
				<th align="center">任务明细</th>
				<th align="center">操作</th>			
			</tr>
		</thead>
		<tbody>
		     <c:forEach var="item" items="${pageList.result}">
				<tr style="height:50px">
				  <td align="center">
       			 <input type="checkbox"  id="orderCheckBox" name="ids" value="${item.userNum}"></td>
					<!--<td>${item.userNum}</td>
					
	              	<td><ry:show parentCode="USERSEX" itemCode="${item.sex}"></ry:show></td>-->
	                <td>${item.userAppId}</td>
	             	<td>${item.phoneNum}</td>
	             	<td><c:if test="${item.isEffective == 0}">正常</c:if>
	             	<c:if test="${item.isEffective == 1}"><span style="color:red">黑名单</span></c:if>
	             	<c:if test="${item.isEffective == 2}"><span style="color:red">可疑</span></c:if>
	             	<c:if test="${item.isEffective == 3}"><span style="color:red">未检测</span></c:if></td>
					<td>${item.phoneModel}</td>	
					<td><c:if test="${item.userApppType == 1}">${item.loginName}</c:if>
					<c:if test="${item.userApppType == 2}">散户</c:if></td>	 
					<td>${item.weixin}</td>
					<td style="background:url(${item.flag5});background-repeat:no-repeat; background-size:100% 100%;-moz-background-size:100% 100%;}"></td> 
	                <td>${item.userNick}</td>
	                <td>${item.zhifubao}</td>
	             	<td>${item.userScore.score}</td>
					<td>${item.userScore.scoreSum}</td>  
	                <td>${item.level }</td>
	                <td id="${item.userAppId}">${item.masterID }</td>
	                <td  onclick="removeMaster(${item.userAppId},${item.masterID})"><span style="color:blue">解除</span></td>
	                <td>
						<a style="cursor: pointer;" onclick="add('userApp/putwardList?userAppId=${item.userAppId}','提现记录',1500,1000,'main_')"><span style="color:blue">否</span></a>     	
					</td>
	                <td>
						<a style="cursor: pointer;" onclick="add('userApp/apprenticeList?userAppId=${item.userAppId}','徒弟列表',1500,1000,'main_')"><span style="color:blue">徒弟列表</span></a>     	
					</td>
					  <td>
						<a style="cursor: pointer;" onclick="add('userApp/apprenticeScoreList?userAppId=${item.userAppId}','徒弟分红',1500,1000,'main_')"><span style="color:blue">徒弟分红</span></a>     	
					</td>
					<td>
						<a style="cursor: pointer;" onclick="openNav('channelInfo/employeeIdfaStatistics?userAppId=${item.userAppId}','任务明细','main_index2')"><span style="color:blue">任务明细</span></a>     	
					</td>
	                <td>
						<a title="修改手机用户信息"   onclick="add('userApp/toEdit?userNum=${item.userNum}','修改手机用户信息',1100,550,'main_')"  rel="users_saveedit" class="btnEdit">手机用户</a>	
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<%@include file="/WEB-INF/jsp/inc/page.jsp" %>

<script type="text/javascript">
	(function(){
　		$("body").css("background-image","url(s)");
	})();	
	

function show(){
			  window.location.href = "userApp/exportScore";
	}

function clearScore(){
		var msg = confirm("确认操作吗？");
		if(msg){
			$.ajax({
	             type: "GET",
	             async:true,
	             url: "/userApp/clearWorkScore",
	             //dataType: "json",
	            success:function(data){
					alert("成功")
	            },
	          	error: function(XMLHttpRequest, textStatus, errorThrown){
					alert("失败")
	          	}
	          });
		}
}

function removeMaster(id, masterid){
		var msg = confirm("确认移除吗？");
		if(msg){
			$.ajax({
	             type: "GET",
	             url: "/userApp/removeMaster",
  				data: {userAppId:id,masterID:masterid},
	            dataType: "json",
	            success:function(data){
					alert("成功")
					id = "#" + id;
					$(id).text("-1");
	            },
	          	error: function(XMLHttpRequest, textStatus, errorThrown){
					alert("失败")
	          	}
	          });
		}
}
</script>