<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  	 <meta name="format-detection" content="telephone=no" />
	<script type="text/javascript" charset="utf-8" src="../js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="../js/showText.js"></script>
	
	<style>
	
	</style>
</head>

<body style="background:#F0F0F0;width:98%;margin-left:1%;">
 	<div style="background-image:url(../img/h5web/main_back_1.png);background-size: 100% auto; 
		background-repeat: no-repeat; width:100%;height:230px;margin-top:2px;position: relative;">
		<div style="float:left;margin-top:5px;margin-left:15px;">
	 		<img id="head_img" style="width:40px;height:40px;border-radius:50px;margin-top:6px;" src="../img/h5web/happy_logo.png"/>
	 	</div>
		<div style="float:left;margin-top:14px;margin-left:10px;font-size:12px; font-family:微软雅黑">
	 		<div><span id="user_id"></span></div>
	 		<div><span id="day_score"></span></div>
	 	</div>
	 	<div onclick="install_enter()" style="float:right;margin-top:18px;margin-right:20px;background-image:url(../img/h5web/main_back_2.png);background-size: 100% auto; 
			background-repeat: no-repeat;width:60px;height:25px">
	 	</div>
	 	
	 	<div style="background-image:url(../img/h5web/main_back_3.png);background-size:100% auto; 
				background-repeat: no-repeat;width:98%;margin-left:1%;height:100px;margin-top:60px;position: absolute;text-align: center;">
				<div style="float:left;margin-top:15px;margin-left:10px;font-size:17px; font-family:微软雅黑;width:100px">
			 		<div style="font-size:18px;"><span id="score"></span></div>
			 		<div style="font-size:15px;margin-top:10px"><span>余额(元)</span></div>
	 			</div>
	 			
	 			<div style="float:left;margin-top:15px;margin-left:10px;font-size:17px; font-family:微软雅黑;width:100px">
			 		<div style="font-size:18px;"><span id="sum_score"></span></div>
			 		<div style="font-size:15px;margin-top:10px"><span>总收入(元)</span></div>
	 			</div>
		 </div>
		 <div style= "position: absolute;margin-top:165px;width:95%;margin-left:2.5%;font-size:15px;color:#8B8682;text-align: center;">
				 用户交流群4群：496011441。申请进群请正确输入happy赚ID，官方唯一客服QQ：2126572197
		 </div>
 	</div>
 	
 	<div style="width:100%;margin-top:2px;">
	    <div id = "task" onclick="taskDetail()" style="background-image:url(../img/h5web/main_back_4.png);background-size: 100% auto; 
			background-repeat: no-repeat;width:49%;height:110px;float:left;border-radius:5px;">
			<span style="color:#FFFFFF;margin-top:20px;margin-left:15px;position: absolute;">试玩任务</span>
		</div>
		<div onclick="userInfo()" style="background-image:url(../img/h5web/main_back_5.png);background-size: 100% auto; 
			background-repeat: no-repeat;width:49%;height:110px;float:right;border-radius:5px;">
			<span style="color:#FFFFFF;margin-top:20px;margin-left:15px;position: absolute;">个人中心</span>
 		</div>
 	</div>	
	<div style="width:98%;margin-left:1%;margin-top:5px;display: inline-block;">
		<span><FONT face=楷体_GB2312 color=#000000 size=4>|热门推荐</FONT></span>
	</div>
	<div style="width:100%;margin-top:2px;">
		<div onclick="inviteUser()" style="background-image:url(../img/h5web/main_back_6.png);background-size: 100% auto; 
			background-repeat: no-repeat;width:49%;height:70px;float:left;border-radius:5px;">
			<span style="color:#FFFFFF;margin-top:10px;margin-left:10px;position: absolute;">邀请好友</span>
		</div>
		
		<div onclick="scoredetail()" style="background-image:url(../img/h5web/back_main_8.png);background-size: 100% auto; 
			background-repeat: no-repeat;width:49%;height:70px;float:right;border-radius:5px;">
			<span style="color:#FFFFFF;margin-top:10px;margin-left:10px;position: absolute;">收入明细</span>
		</div>
	 </div>	
 	<div style="width:98%;margin-left:1%;margin-top:10px;display: inline-block;">
		<span><FONT face=楷体_GB2312 color=#000000 size=4>|更多赚钱</FONT></span>
	</div>
	
	<div style="background-image:url(../img/h5web/main_foot.png);background-size: 100% auto; 
			background-repeat: no-repeat;width:100%;height:120px;border-radius:5px;">
 	</div>
 	<script>
		var base_url  = "http://moneyzhuan.com/";
   		var weixin  = "";
		var phonenum  = "";
		var payfor  = "";
		var userAppId  = "";
		var version = "v1.6";
		
		function showdata(){
			$.ajax({
	             type: "GET",
	             async:true,
	             url: "http://localhost/getDeviceudid",
	             //dataType: "json",
	            success:function(data){
	            	//带有版本信息
	            	var arr=data.split("-");
	            	var userudid = arr[0];
	            	var appVersion =  arr[1];
	            	if(arr[2]){
		            	userudid = arr[0] + "-" + arr[1];
		            	appVersion = arr[2];
	            	}
	            	
	            	getUser(userudid);
	            	
	            	if(version != appVersion){
	            		showAlert({
	            		    text: 'Happy赚有更新的版本啦(请先删除已有的APP)！', //【必填】，否则不能正常显示
	            		    btnText: '去更新', //按钮的文本
	            		    top: '34%', //alert弹出框距离页面顶部的距离
	            		    zindex: 5, //为了防止被其他控件遮盖，默认为2，背景的黑色遮盖层为1，修改后黑色遮盖层的z-index是这个数值的-1
	            		    color: '#fff', //按钮的文本颜色，默认白色
	            		    bgColor: '#1b79f8', //按钮的背景颜色，默认为#1b79f8
	            		    success: function() { //点击按钮后的回调函数
	            		    	url = "itms-services://?action=download-manifest&url=https://moneyzhuan.com/download/HappyApp.plist";
	    	   	 	   			window.location.href = url;
	            		    }
	            		});
	            	}
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
	 	            	$("#user_id").text("id:" + userAppId);
	 	            	$("#day_score").text("今日收入" + scoreDay + "(元)");
	 	            	$("#score").text(score);
	 	            	$("#sum_score").text(scoreSum);
	 	            	
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
	 	
 		function taskDetail(){
	         window.location.href = base_url + "task?id=" + userAppId;  
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
 	 	
 	 	function install_enter(){
	         window.location.href = "https://moneyzhuan.com/download/happywebclip.mobileconfig";  
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