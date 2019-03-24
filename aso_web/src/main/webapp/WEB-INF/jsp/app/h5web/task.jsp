<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <script type="text/javascript" charset="utf-8" src="../js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="../js/px2rem.js"></script>
	<script type="text/javascript" charset="utf-8" src="../js/c3listview.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="../js/showText.js"></script>
	<script type="text/javascript" src="jquery.lazyload.js"></script>

	<style>
		.blur {
			background-repeat: no-repeat;
			background-position: center;
			background-size: cover;
			-webkit-filter: blur(5px);
			-moz-filter: blur(5px);
			-o-filter: blur(5px);
			-ms-filter: blur(5px);
			filter: blur(5px);
		｝
	</style>
</head>

<body style="background:#F0F0F0; margin:0px;font-size:20px; font-family:微软雅黑">
    <div style="width:100%;height:230px;box-shadow:0 3px 5px #e0e0e0;text-align:center;position:relative;z-index:1;background-image:url(../img/h5web/task_1.png);background-size:100% 100%;background-repeat:no-repeat;">
   			<div id="goback" onclick="go()" style="height:0.4rem;padding-top:10px;margin-left:10px;z-index:100">
					<img style="height:0.4rem;float:left;margin-top:4px;" src="../img/h5web/task_0.png"/>
			</div>
			<span style=".flex1; line-height:0.8rem; font-weight: bold; color: #fff; font-size: 0.4rem;margin:auto;position: absolute;top: 0;  left: 0;right: 0;bottom: 0">试玩任务</span>
   			<div style="text-align: center; padding-top:15px;font-size:17px;color:#fff;">①:下载安装    ②:打开试玩    ③:奖励到账</div>
   			
   			<div style="background-color:#fff;width:94%;margin-left:3%;height:120px;margin-top:15px;text-align: center;border-radius:8px;">
   				<div style="float:left;margin-top:20px;margin-left:30px;">
	 				<img style="width:60px;height:60px;border-radius:8px;" src="../img/h5web/task_2.png"/>
	 			</div>
	 			
				<div style="float:left;margin-top:25px;margin-left:10px;font-size:15px; font-family:微软雅黑;text-align:left;">
	 				<div><span>徒弟贡献奖励上不封顶</span></div>
	 				<div style="color:#8B8682;">每个徒弟额外奖励<span style="color:red">0.5-30</span>现金</div>
	 			</div>
   				
   				<div onclick="inviteUser()" style="width:94%;margin-top:75px;color:red;position: absolute;border:0px solid red">收徒GO</div>
   			</div>
    </div>
    
		  	<div style="background:#fff;width:100%;font-family:微软雅黑;font-size:15px;padding-top:5px;padding-bottom:5px;"><span style="margin:15px;">进行中</span></div>
		    <c:forEach var="item" items="${pageList.result}" varStatus="row">
		    	<div onclick="taskdetail(${item.adverId}, '${item.bundleId}')" style="background:#fff;width:100%;margin-top:1px;height:70px">
			    	<div style="float:left;height:60px;margin-left:15px;display:inline-block;">
		 				<img class="blur" alt="" style="width:50px;height:50px;border-radius:10px;padding-top:10px" src="../file/adver/img/${item.adverImg}"/>
				 	</div>
					<div style="height:70px;margin-left:20px;font-size:13px;font-family:微软雅黑;display:inline-block;">
				 		<div style="height:15px;width:170px;margin-top:12px">${item.adverName}</div>
				 		<div style="font-size:10px;margin-top:10px;height:15px;color:#CDCDC1;width:170px;">
				 		 
			 				<div style="display:inline-block;border:1px solid #CDCDC1;width:45px;border-radius:5px;text-align:center;">
			 						<c:if test="${item.adverCountRemain > 100}"><span>多量</span></c:if>
			 						<c:if test="${item.adverCountRemain >= 1}">
			 						<c:if test="${item.adverCountRemain <= 100}"><span>少量</span></c:if></c:if>
			 						<c:if test="${item.adverCountRemain < 1}"><span>已抢光</span></c:if>
			 				</div>
			 				<div style="display:inline-block;border:1px solid #CDCDC1;width:45px;border-radius:5px;text-align:center;">
			 						<c:if test="${item.isRegister != 1}"><c:if test="${item.taskType != 1}"><span>限时</span></c:if>
			 						<c:if test="${item.taskType == 1}"><span>回调</span></c:if></c:if>
			 						<c:if test="${item.isRegister == 1}"><span>注册</span></c:if>
			 				</div>
				 		</div>
				 	</div>
				 	<div style="height:60px;margin-left:15px;display:inline-block;">
				 			+${item.adverPrice}元
				 	</div>
			 	</div>
		    </c:forEach>
		    
			<div style="padding-top:5px;padding-bottom:5px;background:#fff;width:100%;margin-top:10px;font-family:微软雅黑;font-size:15px"><span style="margin:15px;">即将开始</span></div>
		    <c:forEach var="item" items="${pageList.result}" varStatus="row">
		    	<div  style="background:#fff;width:100%;margin-top:1px;height:70px">
			    	<div style="float:left;height:60px;margin-left:15px;display:inline-block;">
		 				<img class="blur" style="width:50px;height:50px;border-radius:10px;padding-top:10px" src="../img/h5web/happy_logo.png"/>
				 	</div>
					<div style="height:70px;margin-left:20px;font-size:13px;font-family:微软雅黑;display:inline-block;">
				 		<div style="height:15px;width:170px;margin-top:12px">${item.adverName}</div>
				 		<div style="font-size:10px;margin-top:10px;height:15px;color:#CDCDC1;width:170px;">
				 		 
			 				<div style="display:inline-block;border:1px solid #CDCDC1;width:45px;border-radius:5px;text-align:center;">
			 						<c:if test="${item.adverCountRemain > 100}"><span>多量</span></c:if>
			 						<c:if test="${item.adverCountRemain <= 100}"><span>少量</span></c:if>
			 				</div>
			 				<div style="display:inline-block;border:1px solid #CDCDC1;width:100px;border-radius:5px;text-align:center;">
			 						 ${item.adverTimeStart}
			 				</div>
				 		</div>
				 	</div>
				 	<div style="height:60px;margin-left:15px;display:inline-block;">
				 			+${item.adverPrice}元
				 	</div>
			 	</div>
		    </c:forEach>
	
 	<script>
 		var result = "";
 		var base_url  = "http://moneyzhuan.com/";
 		var udid = "";
		
 		function taskdetail(position, bundle){
 			if(position == null && position != 0){
 				alert("任务还未开始，请稍后！")
 				return;
 			}
 			
 			var taskid = position;
 			var bundleId = bundle;
 			$.ajax({
		             type: "post",
		             url: "/app/duijie/lingQuRenWu",
		             data: {udid:udid,adverId:taskid},
		             dataType: "json",
		           	 success:function(data){
		            	var json = eval(data);
		            	var result  = json["result"];
		            	var msg  = json["msg"];
		            	
		            	//先绑定个人信息
		            	if(result == 0){
		            		alert(msg);
		            		window.location.href = base_url + "user";
		            	}
		            	else if(result == -1){
		            		alert(msg);
		            	}else{
		            		var id  = json["obj"].adverId;
		            		//判断返回的id是否null，如果有值就代表已经 领取了task
		            		//id是空的时候，代表还未接任务，这个时候 判断手机是否已经安装了app
		            		if (id) {
		            			taskid = id;
		            			window.location.href = base_url + "taskDetail?taskID=" + taskid;
		            		}else{
		            			//判断手机是否已经安装了app
		            			$.ajax({
		           	             type: "GET",
		           	             async:true,
		           	             url: "http://localhost/isAppInstalled",
		           	             data: {bundleId:bundleId},
		           	            success:function(data){
		           	            	var result = data;
		           	            	if(result == 0){
		           	            		//代表未安装，继续任务
		           	            		window.location.href = base_url + "taskDetail?taskID=" + taskid;
		           	            	}else{
		           	            		//任务作废，不能继续任务
		           	            		//设置任务超时
		           	            		giveUpTask(udid,taskid);
		           	            	}
		           	            },
		           	          	error: function(XMLHttpRequest, textStatus, errorThrown){
		           	             //通常情况下textStatus和errorThrown只有其中一个包含信息
		           	          		alertDialog();
		           	          	}
		           	          });
		            		}
		            	}
		            }
	             })
 		}
 		
 		function giveUpTask(udidvalue,taskidvalue){
				$.ajax({
		             type: "post",
		             url: "/app/duijie/setTaskTimeout",
		             data: {udid:udidvalue,adverId:taskidvalue},
		             dataType: "json",
		           	 success:function(data){
		           		var json = eval(data);
		            	var result  = json["result"];
		            	var msg  = json["msg"];
		            	if(result == 1){
		            		alert("请先删除已安装的app，重新尝试！")
		            	}else{
		            		alert("请求错误，请重新尝试！");
		            	}
		            }
	             });
			}
 		
 		window.onpageshow=	function refresh(e){
		     showdata();
	 	};
	 	
	 	function alertDialog(){
	 		 showConfirm({
 	        	 title:'警告',
 	   	 	    text: 'Happy赚助手未打开', //【必填】，否则不能正常显示
 	   	 	    rightText: '下载安装', //右边按钮的文本
 	   	 	    rightBgColor: '#1b79f8', //右边按钮的背景颜色，【不能设置为白色背景】
 	   	 	    rightColor: '#FFC125', //右边按钮的文本颜色，默认白色
 	   	 	    leftText: '打开助手', //左边按钮的文本
 	   	 	    top: '34%', //弹出框距离页面顶部的距离
 	   	 	    zindex: 5, //为了防止被其他控件遮盖，默认为2，背景的黑色遮盖层为1,修改后黑色遮盖层的z-index是这个数值的-1
 	   	 	    success: function() { //右边按钮的回调函数
 	   	 	  		url = "itms-services://?action=download-manifest&url=https://moneyzhuan.com/download/HappyApp.plist";
	   	 	   			window.location.href = url;
 	   	 	    },
 	   	 	    cancel: function() { //左边按钮的回调函数
	 	   	 	   url = "qisu://com.qisu";
 	   	 	  		window.location.href = url;
 	   	 	    }
 	   	 	});
	 	}
	 	
		function showdata(){
			$.ajax({
	             type: "GET",
	             async:true,
	             url: "http://localhost/getDeviceudid",
	             //dataType: "json",
	            success:function(data){
	            	var arr=data.split("-");
	            	udid = arr[0];
	            },
	          	error: function(XMLHttpRequest, textStatus, errorThrown){
	             //通常情况下textStatus和errorThrown只有其中一个包含信息
	          		alertDialog();
	          	}
	          });
		}
	 	
	 	function getTasks(udid){
	 	}
		
		function inviteUser(){
	         window.location.href = base_url +  "invite?id=" + "${userid}";  
		}
		
		function go()
		{
			window.history.go(-1);
		}
 	</script>
</body>
</html>