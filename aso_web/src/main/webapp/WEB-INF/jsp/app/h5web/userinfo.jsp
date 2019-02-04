<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <script type="text/javascript" charset="utf-8" src="../js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="../js/px2rem.js"></script>
	<style>
	.title{
   	 		.flex; width:100%; height: 0.8rem; background:#fff; box-shadow: 0 3px 5px #e0e0e0;text-align: center;position:fixed;z-index:100
		}
	</style>
</head>

<body style="background:#F0F0F0; margin:0px" onpageshow = "">
	 <div class="title">
	 <span onclick="go()" style="line-height:0.8rem;color:Blue;font-size:0.6rem;float:left;margin-left:15px;margin-bottom:10px"><</span>
            <span style=".flex1; line-height:0.8rem; font-weight: bold;text-align: center; color: #4a4a4a;width:100%; font-size: 0.4rem;">个人中心</span>
    </div>
    <div id="container" style="padding-top:0.9rem;position:relative;width:100%;">
    		<div style="background:#fff; margin-top:5px;height:600px">
	    		<img id="user_img" style="width:40px;height:40px;margin-top:20px;margin-right:60px;border-radius:50px;float:right" src="../img/h5web/happy_logo.png"/>
	    		<span style="margin-left:10px;margin-top:20px;position:absolute;font-size:20px;color:#AAAAAA">我的资产 </span>
	    		<span id= "score" style="margin-left:10px;margin-top:70px;position:absolute;padding-left:5px;padding-right:5px;font-size:40px;border-radius:5px;"></span>
	    		<span id= "user_id" style="margin-right:-100px;margin-top:70px;font-size:15px;float:right;color:#F08080;width:160px;color:#AAAAAA;text-align: center;"></span>
	    		<span id= "phonenum" style="margin-right:-160px;margin-top:100px;font-size:10px;float:right;width:160px;text-align: center;">绑定手机</span>
		    	<div style = "width:96%;margin-left:2%; height:1px; background:#F08080;margin-top:130px;position:absolute"></div>
		    	<div style = "width:96%;margin-left:2%;margin-top:135px;position:absolute">
		    		<span style="padding-top:20px;position:absolute;font-size:15px;color:#AAAAAA;width:200px; ">(提现之前请先绑定真实信息，否则会延迟到账！)</span>
		    		<span onclick="putforword()" style="margin-top:20px;margin-right:20px;font-size:20px;color:#AAAAAA;padding: 2px 8px;float:right;border:1px solid #AAAAAA;border-radius:10px;">立即提现</span>
		    	</div>
		    	
		    	<div>
		    		<img onclick= "phonebinding()" id="user_phonenum" style="width:40px;height:40px;margin-top:330px;margin-left:50px;border-radius:50px;position:absolute;" src="../img/h5web/frown.png"/>
		    		<img onclick= "payforbinding()" id="user_payfor" style="width:40px;height:40px;margin-top:330px;margin-left:130px;border-radius:50px;position:absolute;" src="../img/h5web/frown.png"/>
		    		<img onclick= "wechatbinding()" id="user_wechat" style="width:40px;height:40px;margin-top:330px;margin-left:210px;border-radius:50px;position:absolute;" src="../img/h5web/frown.png"/>
		    		<img onclick= "userhelp()" id="user_help" style="width:40px;height:40px;margin-top:330px;margin-left:290px;border-radius:50px;position:absolute;" src="../img/h5web/smile.png"/>
		    	</div>
		    	
		    	<div>
		    		<span  style="width:80px;height:40px;margin-top:380px;font-size:15px;margin-left:30px;position:absolute;text-align: center;">手机绑定</span>
		    		<span style="width:80px;height:40px;margin-top:380px;font-size:15px;margin-left:110px;position:absolute;text-align: center;">支付宝绑定</span>
		    		<span  style="width:80px;height:40px;margin-top:380px;font-size:15px;margin-left:190px;position:absolute;text-align: center;">微信绑定</span>
		    		<span  style="width:80px;height:40px;margin-top:380px;font-size:15px;margin-left:270px;position:absolute;text-align: center;">帮助中心</span>
		    	</div>
	    	</div>
    </div>
	<script>
		var base_url  = "https://moneyzhuan.com/";
		var udid = localStorage.getItem("happyzhuan_user_udid");
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
 		
	 		if(udid == null || udid == ""){
	 			url = "qisu://com.qisu?udid=none";
	 	 		window.location.href = url;
	 		}else{
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
	 	           	   		 $("#user_wechat").attr("src",  "../img/h5web/smile.png");
	 	            	}
	 	            	
	 	            	if(phonenum !=  ""){
	 	            		//显示手机号码
	 	            		var str2 = phonenum.substr(0,3) + "****" + phonenum.substr(7);
	 	            		$("#phonenum").text(str2);
	 	            		$("#user_phonenum").attr("src",  "../img/h5web/smile.png");
	 	            	}
	 	            	
	 	            	if(payfor != ""){
	 	            		$("#user_payfor").attr("src",  "../img/h5web/smile.png");
	 	            	}
	 	            }
	 	        });
	 		}
		}
 	 	
 	 	window.onpageshow=	function refresh(e){
		     showdata();
	 	};
 	 		
 	 	function putforword(){
 	 		if(phonenum  != "" && payfor != "" && weixin != ""){
 	 			window.location.href = base_url + "score";
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
 	 			url = "qisu://com.qisu?wechatbind=1";
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