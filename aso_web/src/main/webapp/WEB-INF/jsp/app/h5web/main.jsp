<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<script type="text/javascript" charset="utf-8" src="../js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="../js/px2rem.js"></script>
	<script type="text/javascript" charset="utf-8" src="../js/showText.js"></script>
</head>

<body style="font-size:20px; font-family:微软雅黑;color:#444; background:#fff;margin-left:5%;width:90%;">
 	<div style="background:#A020F0;width:100%; height:150px;margin-top:5px; border-radius:5px">
	 	<div style="text-align:center; ">
	 		<FONT face=楷体_GB2312 color=#FFFFFF size=5><STRONG>HAPPY 赚</STRONG></FONT>
	 	</div>
	 	 	
	 	<div>
	 		<img id="head_img" style="width:50px;height:50px;margin-top:5px;margin-left:15px;border-radius:50px;float:left" src="../img/h5web/happy_logo.png"/>
			<button onclick= "putforword()" style="color:#FFFFFF;width:80px;float:right;padding:3px;margin-top:20px;margin-right:10px;border-radius:5px;background: rgba(255, 255, 255, 0.2)">提现</button>
	 		<div><span id="user_id" style="margin-left:20px;width:200px;color:#FFFFFF;"></span></div>
	 		<div><span style="margin-left:20px;color:#FFFFFF;">今日收入:</span>
	 		<span id="day_score" style="color:#FFFFFF;"></span></div>
	 	</div>
	 	
		<div style="width=50%;margin-top:15px;float:left;text-align:center;">
			<span style="margin-left:30px;color:#FFFFFF;">余额:</span>
			<span id="score"  style="color:#FFFFFF;"></span>
		</div>
		<div style="width=50%;margin-top:15px;text-align:center;">
			<span style="margin-left:30px;color:#FFFFFF;">总收入:</span>
			<span id="sum_score"  style="color:#FFFFFF;"></span>
		</div>
 	</div>
 	
 	<div style="background:#FFAEB9;width:100%;border-radius:5px;">
 		<marquee width=100% scrollamount=4>
 		<a href="itms-services://?action=download-manifest&url=https://moneyzhuan.com/download/HappyApp.plist">
 		<FONT face=楷体_GB2312 color=#FFFFFF size=3><STRONG>用户交流群4群：496011441。申请进群请正确输入happy赚ID，官方唯一客服QQ：2126572197</STRONG></FONT></a></marquee>
 	</div>
 	
 	<div style="width:100%;margin-top:10px;">
	    <div id = "task" onclick="taskDetail()">
		 	<img  style="width:48%;height:100px;float:left;border-radius:5px;" src="../img/h5web/shiwanrenwu-3.png"/>
		 </div>
		
		<div onclick="scoredetail()">
			<img  style="width:48%;height:100px;float:right;border-radius:5px;" src="../img/h5web/shiwanrenwu-2.png"/>
 		</div>
 		
 		<div style="float:left;width:100%;margin-top:15px"><FONT face=楷体_GB2312 color=#000000 size=5><STRONG>|热门推荐</STRONG></FONT></div>
 		
 		<div onclick="inviteUser()">
			 <img style="margin-top:10px;width:48%;height:100px;float:left;border-radius:5px;" src="../img/h5web/shiwanrenwu-1.png"/>
		</div>
		
		<div onclick="userInfo()">
			<img  style="margin-top:10px;width:48%;height:100px;float:right;border-radius:5px;" src="../img/h5web/shiwanrenwu.png"/>
		</div>
 		
 		<div style="float:left;width:100%;margin-top:15px"><FONT face=楷体_GB2312 color=#000000 size=5><STRONG>|更多赚钱</STRONG></FONT></div>
 		<div>
			<img  style="margin-top:10px;width:100%;height:100px;float:right;border-radius:5px;" src="../img/h5web/zhuanqian.png"/>
 		</div>
 	</div>	


 	<script>
   		var weixin  = "";
		var phonenum  = "";
		var payfor  = "";
		var userAppId  = "";
		function showdata(){
			$.ajax({
	             type: "GET",
	             async:true,
	             url: "http://localhost/getDeviceudid",
	             //dataType: "json",
	            success:function(data){
	            	getUser(data);
	            },
	          	error: function(XMLHttpRequest, textStatus, errorThrown){
	             //通常情况下textStatus和errorThrown只有其中一个包含信息
		 	        showdialog();
	          	}
	          });
		}
		
		function showdialog(){
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
		
		
		function getUser(udid){
			 $.ajax({
	             type: "GET",
	             url: "/app/user/getUserForUdid",
	             data: {udid:udid},
	             dataType: "json",
	            success:function(data){
	            	//成功之后移除 师傅id
	            	localStorage.removeItem("happyzhuan_master_id");
	            	var json = eval(data);
	            	if(json["result"] == -1){
	            		alert(json["msg"]);
	            	}else{
	            		var score  = json["obj"].score;
	 	            	var scoreDay  = json["obj"].scoreDay;
	 	            	var scoreSum  = json["obj"].scoreSum;
	 	            	userAppId  = json["obj"].userAppId;
	 	            	var userNum  = json["obj"].userNum;
	 	            	var weChatHeadUrl  = json["obj"].flag5;
	 	            	    weixin  = json["obj"].weixin;
	 	            	 	phonenum  = json["obj"].phoneNum;
	 	       				 payfor  = json["obj"].zhifubao;
	 	            	$("#user_id").text(userAppId);
	 	            	if(weixin != null && weixin != ""){
	 	            		$("#user_id").text(weixin);
	 	            	}
	 	            	
	 	            	$("#day_score").text(scoreDay  + "元");
	 	            	$("#score").text(score  + "元");
	 	            	$("#sum_score").text(scoreSum  + "元");
	 	            	
	 	            	if(weChatHeadUrl != null && weChatHeadUrl != "")
	 	            	{
	 	           	    	$("#head_img").attr("src",  weChatHeadUrl);
	 	            	}
	            	}
	            }
	        });
		}
		
 	 	window.onpageshow=	function refresh(e){
		     showdata();
	 	};
	 	
 	 	var base_url  = "http://moneyzhuan.com/";
 		function taskDetail(){
	         window.location.href = base_url + "task"
 		}
 	 	
 	 	function userInfo(){
	         window.location.href = base_url +  "user"
		}
 	 	
 	 	function inviteUser(){
	         window.location.href = base_url +  "invite?id=" + userAppId;  
		}
 	 	
 	 	function scoredetail(){
	         window.location.href = base_url +  "scoredetail?id=" + userAppId;  
		}
 	 	
 	 	function putforword(){
 	 		if(phonenum  != "" && payfor != "" && weixin != ""){
 	 			window.location.href = base_url + "score?id=" + userAppId;  
 	 		}else{
 	 			alert("请先绑定个人信息！");
 	 			window.location.href = base_url +  "user"
 	 		}
 	 	}
	</script>
</body>
</html>