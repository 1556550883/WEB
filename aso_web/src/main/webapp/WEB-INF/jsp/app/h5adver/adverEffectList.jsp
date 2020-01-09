<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
 <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<table class="table" width="100%" layoutH="128">
	<tbody>
	     <c:forEach var="item" items="${pageList.result}">
			<tr >
				<td>
				  <div style="background:#fff;width:100%;margin-top:1px;height:50px;text-align:center;font-size:20px; font-family:微软雅黑" >
				  		<span>${item.idfa}</span>
				  		<c:if test="${item.status=='1'}">
	                	<span>领取任务</span>
					</c:if>
					<c:if test="${item.status=='1.5'}">
	                	<span>打开任务</span>
					</c:if>
					<c:if test="${item.status=='1.6'}">
	                	<span>任务超时:${item.completeTime}</span>
					</c:if>
					<c:if test="${item.status=='2.1'}">
						<span>提交时间:${item.completeTime}</span>
					</c:if>
					<c:if test="${item.status=='2'}">
	                	<span>${item.completeTime}</span>
					</c:if>
				  </div>
				  <HR style= " FILTER: progid:DXImageTransform.Microsoft.Shadow ( color: #987cb9 ,direction : 145, strength :15 ) " width = "100%" color = #987cb9 SIZE= 1>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
