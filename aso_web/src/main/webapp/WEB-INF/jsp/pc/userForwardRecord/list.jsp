<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding parentCode="USER_SCORE_TYPE" bingdingName="channellevel"></ry:binding>
<form id="pagerForm" method="post" action="userAppForwardRecord/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}">
	<input type="hidden" name="orderDirection" value="${param.orderDirection}">
</form>
	<div class="panelBar">
		<ul class="toolBar">
		
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="86">
		<thead>
			<tr>
				<th width="30">
				<input type="checkbox" group="ids" class="checkboxCtrl" >
				</th>
				<th align="center">用户编号</th>
				<th align="center">用户类型</th>
				<th align="center">提现金额</th>
				<th align="center">提现时间</th>
				<th align="center">提现状态</th>
				<th align="center">提现信息</th>
				<th align="center">操作</th>
				<th align="center">操作</th>
			</tr>
		</thead>
		<tbody>
		     <c:forEach var="item" items="${pageList.result}">
				<tr >
				  <td align="center">
       				<input type="checkbox"  id="orderCheckBox" name="ids" value="${item.userScoreInfoId}"></td>
					 <td id="${item.userAppNum}"><a style="cursor: pointer;" onclick="add('userApp/getUserByUserNum?userNum=${item.userAppNum}','用户详情',1500,1000,'main_')"><span style="color:blue">${item.userAppNum}</span></a></td>            
	                <td>${item.userType}</td>
	                <td>${item.score}</td>
	                <td>${item.scoreTime}</td>
	                <td id="${item.userScoreInfoId}" >未审核</td>
	                <td id="${item.userScoreInfoNum}">${item.subMsg}</td>
	                <td onclick="statusChange(${item.userScoreInfoId},1)"><div style="color: blue">通过</div></td>
	                <td onclick="statusChange(${item.userScoreInfoId},-1)"><div style="color: blue">驳回</div></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<%@include file="/WEB-INF/jsp/inc/page.jsp" %>
	
	<script>
		(function(){
			var userscores = ${userScoreInfos};
			for(let index in userscores) {  
		        var id = "#" + userscores[index].userScoreInfoId;
		        var status = userscores[index].status;
		        if(status == 1){
		        	 $(id).text("通过");
		        }else if(status == -1){
		        	 $(id).text("驳回");
		        	 $(id).css("color","red");
		        }
		    };  
			
		})();
		
		function statusChange(id, status){
			var msg = confirm("确认操作吗？");
			if(msg){
				$.ajax({
		             type: "GET",
		             url: "/userAppForwardRecord/verify",
		             data: {userScoreInfoId:id,status:status},
		             dataType: "json",
		             success:function(data){
		            	var json = eval(data);
		            	console.log(json);
		            	var obj = json.obj;
		            	id = "#" + id;
		            	if(json.result  == 1){
		            		alert("操作成功！");
		            		if(status  == 1){
		            			 $(id).text("通过");
		            		}
		            	}
		            	else if(json.result  == -2)
		            	{
		            		alert("驳回成功！");
		            		 $(id).text("驳回");
	            			 $(id).css("color","red");
		            	}
		            	
		            	if(obj){
		            		var name = "#" + obj.userScoreInfoNum;
			            	$(name).text(obj.subMsg);
		            	}
		             }
		          });
			}else{
			}
		}
	
	</script>