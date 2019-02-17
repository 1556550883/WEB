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
   	 		.flex; width:100%; height: 0.8rem; background:#fff; box-shadow: 0 3px 5px #e0e0e0;text-align: center;position:fixed;z-index:100
		}
	</style>
</head>

<body style="background:#F0F0F0; margin:0px;font-size:20px; font-family:微软雅黑;">
	 <div onclick="go()" class="title">
	 		<img  style="height:0.4rem;float:left;margin-left:10px;margin-top:10px;z-index:200;" src="../img/h5web/back-icon.png"></img>
            <span style=".flex1; line-height:0.8rem; font-weight: bold; color: #4a4a4a; font-size: 0.4rem;margin:auto;position: absolute;top: 0;  left: 0;right: 0;bottom: 0">个人中心</span>
    </div>
    <div id="container" style="padding-top:0.9rem;position:relative;width:100%;">
    		<div style="background:#fff; margin-top:5px;height:600px">
	    		<img id="user_img" style="width:40px;height:40px;margin-top:20px;margin-right:60px;border-radius:50px;float:right" src="../img/h5web/happy_logo.png"/>
	    		<span style="margin-left:10px;margin-top:20px;position:absolute;font-size:20px;color:#AAAAAA">我的资产 </span>
	    		<span id= "score" style="margin-left:10px;margin-top:70px;position:absolute;padding-left:5px;padding-right:5px;font-size:40px;border-radius:5px;"></span>
	    		<span id= "user_id" style="margin-right:-100px;margin-top:70px;font-size:15px;float:right;color:#F08080;width:160px;color:#AAAAAA;text-align: center;">11</span>
	    		<span id= "phonenum" style="margin-right:-160px;margin-top:100px;font-size:10px;float:right;width:160px;text-align: center;">绑定手机</span>
		    	<div style = "width:96%;margin-left:2%; height:1px; background:#F08080;margin-top:130px;position:absolute"></div>
		    	<div style = "width:96%;margin-left:2%;margin-top:135px;position:absolute">
		    		<span style="padding-top:20px;position:absolute;font-size:15px;color:#AAAAAA;width:200px; ">(提现之前请先绑定真实信息，否则会延迟到账！)</span>
		    		<span onclick="putforword()" style="margin-top:20px;margin-right:20px;font-size:20px;color:#AAAAAA;padding: 2px 8px;float:right;border:1px solid #AAAAAA;border-radius:10px;">立即提现</span>
		    	</div>
		    	
		    	<div  onclick= "phonebinding()"  style="line-height:25px;margin-top:250px;position:absolute;text-align: left;margin-left:30px;width:80%">
		    		<img   style="height:25px;" src="../img/h5web/icon_file.png"/>
		    		<span style="height:25px;font-size:20px;">手机绑定</span>
		    		<img id="user_phonenum" style="height:25px;float:right;" src="../img/h5web/right.png"/>
		    	</div>
		    	<div style = "width:96%;margin-left:2%; height:1px; background:#CDC9C9;margin-top:285px;position:absolute"></div>
		    	
		    	<div onclick= "payforbinding()"  style="line-height:25px;margin-top:300px;position:absolute;text-align: left;margin-left:30px;width:80%">
		    		<img   style="height:25px;" src="../img/h5web/icon_file.png"/>
		    		<span style="height:25px;font-size:20px;">支付宝绑定</span>
		    		<img  id="user_payfor" style="height:25px;float:right;" src="../img/h5web/right.png"/>
		    	</div>
		    	<div style = "width:96%;margin-left:2%; height:1px; background:#CDC9C9;margin-top:335px;position:absolute"></div>
		    		
		    	<div onclick= "wechatbinding()" style="line-height:25px;margin-top:350px;position:absolute;text-align: left;margin-left:30px;width:80%">
		    		<img   style="height:25px;" src="../img/h5web/icon_file.png"/>
		    		<span style="height:25px;font-size:20px;">微信绑定</span>
		    		<img id="user_wechat" style="height:25px;float:right;" src="../img/h5web/right.png"/>
		    	</div>
		    	<div style = "width:96%;margin-left:2%; height:1px; background:#CDC9C9;margin-top:385px;position:absolute"></div>
		    	
		    	<div onclick= "userhelp()"  style="line-height:25px;margin-top:400px;position:absolute;text-align: left;margin-left:30px;width:80%">
		    		<img   style="height:25px;" src="../img/h5web/icon_file.png"/>
		    		<span style="height:25px;font-size:20px;">帮助中心</span>
		    		<img   id="user_help" style="height:25px;float:right;" src="../img/h5web/right.png"/>
		    	</div>
		    	<div style = "width:96%;margin-left:2%; height:1px; background:#CDC9C9;margin-top:435px;position:absolute"></div>
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
	            	udid = data;
	            	showdetail(data);
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
	            	
	            	$("#score").text(score + "元");
	           
	            	if(weixin != "" && weChatHeadUrl != "")
	            	{
	           	    	$("#user_img").attr("src",  weChatHeadUrl);
	           	   		 $("#user_wechat").attr("src",  "../img/h5web/check.png");
	            	}
	            	
	            	if(phonenum !=  ""){
	            		//显示手机号码
	            		var str2 = phonenum.substr(0,3) + "****" + phonenum.substr(7);
	            		$("#phonenum").text(str2);
	            		$("#user_phonenum").attr("src",  "../img/h5web/check.png");
	            	}
	            	
	            	if(payfor != ""){
	            		$("#user_payfor").attr("src",  "../img/h5web/check.png");
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
 	 	
 		function go()
		{
			window.history.go(-1);
		}
 	 	</script>
</body>
</html>