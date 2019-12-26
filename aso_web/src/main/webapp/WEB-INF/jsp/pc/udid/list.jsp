<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>

<div class="panelBar">
	<ul class="toolBar">
		<li><a class="add" onclick= "openNav('udid/upload','上传udid文件','main_index')"><span>上传udid</span></a></li>
		<li><a class="add"  onclick= "activated()"><span>激活udid</span></a></li>
		<li class="line">line</li>
	</ul>
</div>

<table class="table" width="100%" layoutH="128">
	<thead>
		<tr>
			<th align="center">udid型号</th>
			<th align="center">昨天消耗</th>
			<th align="center">今日消耗</th>				
			<th align="center">总剩余</th>
			<th align="center">操作</th>		
		</tr>
	</thead>
	
	<tbody>
	     <c:forEach var="item" items="${udidModelList}">
			<tr >
				<td>${item.udidType}</td>
                <td>${item.yestDayAmountNum}</td>
                <td>${item.todayAmountNum}</td>
                <td>${item.totalNum}</td>
                <td>
					<a title="修改udid"   onclick="add('udid/toEdit?udidType=${item.udidType}','修改手机用户信息',1100,550,'main_')"  rel="users_saveedit" class="btnEdit">udid修改</a>	
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<script type="text/javascript">
function activated(){
	var msg = confirm("确认激活吗？");
	if(msg){
		$.ajax({
	             type: "GET",
	             url: "udid/activated",
	             dataType: "json",
	           	 success:function(data){
	           		 alert(data);
	            }
           });
	}
}
</script>

