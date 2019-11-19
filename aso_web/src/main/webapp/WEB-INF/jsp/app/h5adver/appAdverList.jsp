<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
 <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <script type="text/javascript" charset="utf-8" src="js/jquery-1.11.3.min.js"></script>
<style> 
 .footer{
		height: 60px;
		width: 100%;
		left:0px;
		background-color: #ddd;
		position: fixed;
		bottom: 0;
	}
</style>
<table class="table">
	<tbody>
	     <c:forEach var="item" items="${pageList.result}">
			<tr >
				<td>
				 <div style="background:#fff;width:100%;margin-top:1px;height:80px; position:relative;">
					 <div style="width:15px;height:15px;margin-left:2px;display:inline-block;margin-top:32px">
			    		<input type="checkbox"  id="orderCheckBox" name="ids" value="${item.adverId}">
				 	</div>
			    	<div style="width:40px;height:40px;margin-left:5px;display:inline-block;">
		 				<img class="" style="width:40px;height:40px;border-radius:8px;margin-top:10px" src="file/adver/img/${item.adverImg}"/>
				 	</div>
					<div style="z-index:9;height:70px;width:260px;margin-left:10px;font-size:13px;font-family:微软雅黑;display:inline-block;" onclick="toappedit('${item.adverId}')">
				 		<div style="height:15px;width:100%;">
				 				<span style="color:red;width:70px; white-space: nowrap; display:inline-block;overflow: hidden; text-overflow: ellipsis;line-height: 0.9; ">${item.adverName}</span>
								<span style="color:red;width:10px;">(${item.adverAdid})</span>
								<span style="color:red;width:90px; white-space: nowrap; display:inline-block;overflow: hidden; text-overflow: ellipsis;line-height: 0.9; ">(${item.adid})</span>
				 		</div>
				 		<div style="font-size:10px;margin-top:10px;height:15px;width:300px;">
			 				<div style="display:inline-block;border:1px solid #CDCDC1;width:50px;border-radius:5px;text-align:center;">
			 						量:${item.adverCount}
			 				</div>
			 				
			 				<div style="display:inline-block;border:1px solid #CDCDC1;width:50px;border-radius:5px;text-align:center;">
			 						剩:${item.adverCountRemain}
			 				</div>
			 				
			 				<div style="display:inline-block;border:1px solid #CDCDC1;width:50px;border-radius:5px;text-align:center;">
									完:${item.downloadCount}
			 				</div>
			 				
			 				<div style="display:inline-block;border:1px solid #CDCDC1;width:45px;border-radius:5px;text-align:center;">
			 						<c:if test="${item.isRegister != 1}"><c:if test="${item.taskType != 1}"><span>快速</span></c:if>
			 						<c:if test="${item.taskType == 1}"><span>回调</span></c:if></c:if>
			 						<c:if test="${item.isRegister == 1}"><span>注册</span></c:if>
			 				</div>
			 				<div style="display:inline-block;border:1px solid #CDCDC1;width:60px;border-radius:5px;text-align:center;">
			 					时:${item.timeLimit}
			 				</div>
			 				<div style="display:inline-block;border:1px solid #CDCDC1;width:60px;border-radius:5px;text-align:center;">
			 					级:${item.level}
			 				</div>
			 				<div style="display:inline-block;border:1px solid #CDCDC1;width:45px;border-radius:5px;text-align:center;color:red">
			 						<c:if test="${item.adverStatus==0}">未审核</c:if>
			 						<c:if test="${item.adverStatus == 1}"><span>启动</span></c:if>
			 						<c:if test="${item.adverStatus == 2}"><span>停止</span></c:if>
			 				</div>
			 				<div style="display:inline-block;border:1px solid #CDCDC1;width:60px;border-radius:5px;text-align:center;color:red">
			 					价:${item.adverPrice}
			 				</div>
				 		</div>
				 	</div>
			 	</div>
			 	<div style="font-size:10px;margin-top:2px;height:15px;width:100%;text-align:center;">
			 			<div style="display:inline-block;border:1px solid #CDCDC1;width:125px;border-radius:5px;text-align:center;">
			 						${item.adverDayStart}
			 				</div>
			 				
			 				<div style="display:inline-block;border:1px solid #CDCDC1;width:125px;border-radius:5px;text-align:center;">
			 						${item.adverDayEnd}
			 				</div>
			 	</div>
				  <HR style= "FILTER: progid:DXImageTransform.Microsoft.Shadow ( color: #987cb9 ,direction : 145, strength :15 ) " width = "100%" color = #987cb9 SIZE= 1>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<div class="footer">
 <div style="float:left;width:50%;height:60px;display:inline-block;text-align:center;margin-top:20px" onclick = "changeStatus()">
	切换任务状态
 </div>
 <div style="float:left;width:50%;height:60px;display:inline-block;text-align:center;margin-top:20px" onclick = "toappedit()">
 	  添加任务
</div>
</div>

<script type="text/javascript">
function changeStatus(){
	 var ids = "";
	 $("input[type='checkbox']").each(function () {
         if ($(this).is(":checked")) {
        	 if(ids == ""){
             	ids = $(this).val()
        	 }else{
        		 ids= ids + "," + $(this).val()
        	 }
         }
     });
	 
	$.ajax({
            type: "GET",
            url: "changeStatus",
            data: {ids:ids},
            dataType: "json",
          	 success:function(data){
          		 alert("切换成功！");
           }
      });
}

	function toappedit(id){
		if(id){
			window.location.href = "toappedit?channelNum=" + ${bean.channelNum} + "&id=" + id;
		}else{
			window.location.href = "toappedit?channelNum=" + ${bean.channelNum};
		}
	}
</script>