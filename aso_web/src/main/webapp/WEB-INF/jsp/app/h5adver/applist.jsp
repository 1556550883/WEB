<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding parentCode="CHANNEL_LEVEL,CHANNEL_TYPE,SYSTEM_TYPE" bingdingName="channellevel,channeltype,systemtype"></ry:binding>
 <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<table class="table" width="100%" layoutH="128">
	<tbody>
	     <c:forEach var="item" items="${pageList.result}">
			<tr >
				<td onclick="getAdverlist('${item.channelNum}')">
				  <div style="background:#fff;width:100%;margin-top:1px;height:40px;text-align:center;font-size:20px; font-family:微软雅黑" >
				  		<span>${item.channelNum}</span>
				  		<span>${item.channelName}</span>
				  </div>
				  <HR style= " FILTER: progid:DXImageTransform.Microsoft.Shadow ( color: #987cb9 ,direction : 145, strength :15 ) " width = "100%" color = #987cb9 SIZE= 1>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<script type="text/javascript">
function getAdverlist(channelnum){
	window.location.href = "appAdverList?channelNum=" + channelnum;
}
</script>
