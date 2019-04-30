<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <script type="text/javascript" charset="utf-8" src="../js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="../js/px2rem.js"></script>
	
	<script type="text/javascript" charset="utf-8" src="../js/showText.js"></script>
	<style>
		.title{
	   	 		.flex; width:100%; height: 0.8rem; background:#fff; box-shadow:0px 2px 6px 0px rgba(23, 176, 81, 0.35);text-align: center;position:fixed;z-index:100
			}
	</style>
</head>

<body style="background:#F0F0F0; margin:0px;font-size:20px; font-family:微软雅黑;">
	 <div onclick="go()" class="title">
	 		<img  style="height:0.4rem;float:left;margin-left:10px;margin-top:10px;z-index:200;" src="../img/h5web/back-icon.png"></img>
            <span style=".flex1; line-height:0.8rem; font-weight: bold; color: #4a4a4a; font-size: 0.4rem;margin:auto;position: absolute;top: 0;  left: 0;right: 0;bottom: 0">个人中心</span>
    </div>
    
    <div id="container" style="background:#fff;padding-top:40px;position:relative;width:100%;height:550px">
    	<div>
	    	<div style="float:left;margin-top:5px;margin-left:15px;">
		 		<img id="user_img" style="width:40px;height:40px;border-radius:50px;margin-top:6px;border:0px solid red;" src="../img/h5web/happy_logo.png"/>
		 	</div>
		 	<div style="float:left;margin-top:24px;margin-left:10px;font-size:16px; font-family:微软雅黑">
		 		<div><span id="user_id"></span></div>
		 	</div>
		 	
		 	<div style="float:left;margin-top:24px;margin-left:15px;font-size:16px; font-family:微软雅黑;color:#8B8682;">
		 		<div><span  id= "phonenum"></span></div>
		 	</div>
	 	</div>
	 	
		 <div style="background-image:url(../img/h5web/userInfo_img_1.png);background-size:100% auto; 
				background-repeat: no-repeat;width:98%;margin-left:1%;height:200px;margin-top:60px;position: absolute;text-align: center;">
	 			<div style="width:200px;color:#FFFFFF;margin-top:20px;font-size:20px;"><span>我的资产 (元)</span></div>
	 			<div style="float:left;width:200px;color:#FFFFFF;margin-top:10px;font-size:30px;"><span id= "score"></span></div>
	 			<div onclick="putforword()" style="float:right;width:100px;color:#FFFFFF;margin-top:15px;margin-right:25px;border:1px solid #FFFFFF;border-radius:10px;"><span>提现</span></div>
	 			<div style="width:100%;background-color:#FFD39B;margin-top:70px;height:1px;"></div>
	 			<div style="width:100%;color:#FFFFFF;font-size:13px;margin-top:15px"><span>(提现之前请先绑定真实信息，否则会延迟到账！)</span></div>
	 	 </div>
	 	 
	 	 <div  onclick= "phonebinding()" style="margin-top:210px;margin-left:20px;display:inline-block;width:100%">
	 	 	 <div style="background-image:url(../img/h5web/userinfo_iphone_2.png);background-size: 100% auto; 
			background-repeat: no-repeat;width:20px;height:33px;float:left;border-radius:5px;"></div>
	 	 	<div style="margin-left:30px;position: absolute;width:200px;height:30px">
	 	 	<span style="margin:auto;position: absolute;top: 0;  left: 0;right: 0;bottom: 0">手机绑定</span></div>
	 	 	
	 	 	<div id="user_phonenum" style="background-image:url(../img/h5web/userinfo_back_3.png); background-size:100% 100%;; 
			background-repeat: no-repeat;width:20px;height:20px;float:right;margin-right:40px"></div>
	 	 </div>
	 	 
	 	  <div onclick= "payforbinding()"  style="margin-top:20px;margin-left:20px;display:inline-block;width:100%">
	 	 	 <div style="background-image:url(../img/h5web/userInfo_pay_4.png);background-size: 100% auto; 
			background-repeat: no-repeat;width:20px;height:33px;float:left;border-radius:5px;margin-top:6px"></div>
	 	 	<div style="margin-left:30px;position: absolute;width:200px;height:30px">
	 	 	<span style="margin:auto;position: absolute;top: 0;  left: 0;right: 0;bottom: 0">支付宝绑定</span></div>
	 	 	<div id="user_payfor" style="background-image:url(../img/h5web/userinfo_back_3.png); background-size:100% 100%;; 
			background-repeat: no-repeat;width:20px;height:20px;float:right;margin-right:40px"></div>
	 	 </div>
	 	 
	 	  <div  onclick= "wechatbinding()" style="margin-top:20px;margin-left:20px;display:inline-block;width:100%">
	 	 	 <div style="background-image:url(../img/h5web/userinfo_wechat_5.png);background-size: 100% auto; 
			background-repeat: no-repeat;width:20px;height:33px;float:left;border-radius:5px;margin-top:6px"></div>
	 	 	<div style="margin-left:30px;position: absolute;width:200px;height:30px">
	 	 	<span style="margin:auto;position: absolute;top: 0;  left: 0;right: 0;bottom: 0">微信绑定</span></div>
	 	 	<div id="user_wechat" style="background-image:url(../img/h5web/userinfo_back_3.png); background-size:100% 100%;; 
			background-repeat: no-repeat;width:20px;height:20px;float:right;margin-right:40px"></div>
	 	 </div>
	 	 
	 	   <div onclick= "userhelp()" style="margin-top:20px;margin-left:20px;display:inline-block;width:100%">
	 	 	 <div style="background-image:url(../img/h5web/userinfo_help_6.png);background-size: 100% auto; 
			background-repeat: no-repeat;width:20px;height:33px;float:left;border-radius:5px;margin-top:6px"></div>
	 	 	<div style="margin-left:30px;position: absolute;width:200px;height:30px">
	 	 	<span style="margin:auto;position: absolute;top: 0;  left: 0;right: 0;bottom: 0">帮助中心</span></div>
	 	 	<div  id="user_help"  style="background-image:url(../img/h5web/userinfo_back_3.png); background-size:100% 100%;; 
			background-repeat: no-repeat;width:20px;height:20px;float:right;margin-right:40px"></div>
	 	 </div>
	 	 
	 	   <div onclick= "contact()" style="margin-top:20px;margin-left:20px;display:inline-block;width:100%">
	 	 	 <div style="background-image:url(../img/h5web/userinfo_guan_6.png);background-size: 100% auto; 
			background-repeat: no-repeat;width:20px;height:33px;float:left;border-radius:5px;margin-top:6px"></div>
	 	 	<div style="margin-left:30px;position: absolute;width:200px;height:30px">
	 	 	<span style="margin:auto;position: absolute;top: 0;  left: 0;right: 0;bottom: 0">联系官方</span></div>
	 	 	<div  id="contact" style="background-image:url(../img/h5web/userinfo_back_3.png); background-size:100% 100%;; 
			background-repeat: no-repeat;width:20px;height:20px;float:right;margin-right:40px"></div>
	 	 </div>
    </div>
    
    
	<script>
		var base_url  = "http://moneyzhuan.com/";
		var udid = "";
		//udid = "d6638e6de42f029649654ad4b17badf532bb9bcc";
		var score  = "";
		var scoreDay  = "";
     	var scoreSum  = "";
     	var userAppId  = "";
     	var userNum  = "";
     	var weChatHeadUrl  = "";
     	var weixin  = "";
   		var phonenum  = "";
		var payfor  = "";
			
		function showdata(){
			//如果udid被手动清理，需要 去 重新注册设备
			$.ajax({
	             type: "GET",
	             async:true,
	             url: "http://localhost/getDeviceudid",
	             //dataType: "json",
	            success:function(data){
	            	var arr=data.split("-");
	            	udid = arr[0];
	            	if(arr[2]){
	            		udid =  arr[0] + "-" + arr[1];
	            	}
	            	
	            	showdetail();
	            },
	          	error: function(XMLHttpRequest, textStatus, errorThrown){
	             //通常情况下textStatus和errorThrown只有其中一个包含信息
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
	          });
		}
 	 	
		function showdetail(){
			 $.ajax({
	             type: "GET",
	             url: "/app/user/getUserForUdid",
	             data: {udid:udid},
	             dataType: "json",
	            success:function(data){
	            	var json = eval(data);
	            	 score  = json["obj"].score;
	            	 scoreDay  = json["obj"].scoreDay;
	            	 scoreSum  = json["obj"].scoreSum;
	            	 userAppId  = json["obj"].userAppId;
	            	 userNum  = json["obj"].userNum;
	            	 weChatHeadUrl  = json["obj"].flag5;
	            	 weixin  = json["obj"].weixin;
	          		 phonenum  = json["obj"].phoneNum;
	       			 payfor  = json["obj"].zhifubao;
	            	
	            	$("#user_id").text(userAppId);
	            	if(weixin != null && weixin != ""){
	            		$("#user_id").text(weixin);
	            	}
	            	
	            	$("#score").text(score);
	           
	            	if(weixin != "" && weChatHeadUrl != "")
	            	{
	           	    	$("#user_img").attr("src",  weChatHeadUrl);
	           	   		$("#user_wechat").css('background-image',"url(../img/h5web/userinfo_check_7.png)");
	            	}
	            	
	            	if(phonenum !=  ""){
	            		//显示手机号码
	            		var str2 = phonenum.substr(0,3) + "****" + phonenum.substr(7);
	            		$("#phonenum").text(str2);
	            		$("#user_phonenum").css('background-image',"url(../img/h5web/userinfo_check_7.png)");
	            	}
	            	
	            	if(payfor != ""){
	            		$("#user_payfor").css('background-image',"url(../img/h5web/userinfo_check_7.png)");
	            	}
	            }
	        });
		}
		
 	 	window.onpageshow =	function refresh(e){
		     showdata();
	 	};
 	 		
 	 	function putforword(){
 	 		if(phonenum  != "" && payfor != "" && weixin != ""){
 	 			window.location.href = base_url + "score?id=" + userAppId;  
 	 		}else{
 	 			alert("请先绑定个人信息！");
 	 		}
 	 	}
 	 	
 		
 	 	function phonebinding(){
 	 		if(phonenum == ""){
	 	 		window.location.href = base_url + "phone";
 	 		}else{
 	 			alert("已绑定手机号码！");
 	 		}
 	 	}
 	 	
 		
 	 	function payforbinding(){
 	 		if(payfor == ""){
 	 			window.location.href = base_url + "payfor";
 	 		}else{
 	 			alert("已绑定支付宝！");
 	 		}
 	 	}
 	 	
 	 	function wechatbinding(){
 	 		if(weixin == ""){
 	 			url = "qisu://com.qisu?wechatbind";
		 	 	window.location.href = url;
 	 		}else{
 	 			alert("已绑定微信！");
 	 		}
 	 	}
 	 	
 	 	function userhelp(){
 	 		window.location.href = base_url +  "help";
 	 	}
 	 	
 	 	function contact(){
 	 		window.location.href = base_url +  "contact";
 	 	}
 	 	
 		function go()
		{
			window.history.go(-1);
		}
 	 	</script>
</body>
</html>