<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <script type="text/javascript" charset="utf-8" src="../js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="../js/px2rem.js"></script>
	<script type="text/javascript" charset="utf-8" src="../js/c3listview.min.js"></script>
	<style>
	.title{
   	 		.flex; width:100%; height: 0.8rem; background:#fff; box-shadow: 0 3px 5px #e0e0e0;text-align: center;position:fixed;z-index:100
		}
	</style>
</head>

<body style="background:#F0F0F0; margin:0px;font-size:20px; font-family:微软雅黑;text-align: center;">
   <div onclick="go()" class="title">
	 		<img style="height:0.4rem;float:left;margin-left:10px;margin-top:10px;" src="../img/h5web/back-icon.png"/>
            <span style=".flex1; line-height:0.8rem; font-weight: bold; color: #4a4a4a; font-size: 0.4rem;margin:auto;position: absolute;top: 0;  left: 0;right: 0;bottom: 0">个人提现</span>
    </div>
    
    <div id="container" style="padding-top:0.9rem;position:relative;width:100%;font-size:15px;">
    	<div style="background:#fff; margin-top:5px;height:600px; position: relative;">
    			<div style="margin-top:10px;margin-left:20px;position:absolute;">
    				<span>提现账户id：</span>
    				<span id="user_app_id"></span>
    			</div>
    			
    			<div style="margin-top:40px;margin-left:20px;position:absolute;">
    				<span>可用余额：</span>
    				<span id="user_score"></span>
    				<span  onclick="putforwordDetail()" style="background:#FFC125;width:100px;padding-top:5px;font-size:15px;padding-bottom:5px;margin-left:95px;float:right;border-radius:4px;color:white">提现明细</span>
    			</div>
		 		
    			<div style="margin-top:70px;margin-left:20px;position:absolute;"><span>支付宝账户</span></div>
    			<div style="margin-top:105px;position:absolute;font-size:20px;">
    				<span id="payfornum" style="margin-left:20px;color:#AAAAAA;"></span>
    			</div>
    			
    			<div style = "width:70%;margin-left:20px; height:1px; background:#AAAAAA;margin-top:132px;position:absolute"></div>
    		
    		<div onclick="selectscore(10)">
		 		<span  id="10_score" style="width:40%;padding-top:7px;padding-bottom:7px;margin-left:30px;float:left;border-radius:4px;margin-top:170px;border:1px solid">提现10元</span>
		 	</div>
    		
    		<div onclick="selectscore(30)">
		 		<span  id="30_score"  style="width:40%;padding-top:7px;padding-bottom:7px;margin-right:30px;float:right;border-radius:4px;margin-top:170px;border:1px solid">提现30元</span>
		 	</div>
    		
    		<div onclick="selectscore(50)">
		 		<span  id="50_score"  style="width:40%;padding-top:7px;padding-bottom:7px;margin-left:30px;float:left;border-radius:4px;margin-top:20px;border:1px solid">提现50元</span>
		 	</div>
		 	
		 	<div onclick="selectscore(100)">
		 		<span  id="100_score"  style="width:40%;padding-top:7px;padding-bottom:7px;margin-right:30px;float:right;border-radius:4px;margin-top:20px;border:1px solid">提现100元</span>
		 	</div>
		 	
		 	<div style="float:left;width:100%;text-align:left;margin-left:30px;margin-top:25px;font-size:10px">
		 		<span>注：每次提现少于50元收取5%的手续费</span>
		 	</div>
		 	
		 	<div onclick="putforword()">
		 		<span  style="background:#FFC125;width:55%;padding-top:10px;padding-bottom:10px;margin-left:30px;float:left;border-radius:4px;margin-top:50px;color:white">提现</span>
		 	</div>

    	</div>
    </div>
    
    <script>
	    var userScore  = "${HUserAppModel.score}";
		var usernum = "${HUserAppModel.userNum}";
		var appid = "${HUserAppModel.userAppId}";
		$("#user_app_id").text(appid)
		$("#user_score").text(userScore + "元")
    	$("#payfornum").text("${HUserAppModel.zhifubao}")
    	var oldid = "#10_score";
    	var score = 10;
    	$(oldid).css("background","#FFC125").css("color","white");
    	function selectscore(value){
    		score = value;
    		var id = "#" + value + "_score";
    		var tempid = "";
    		if(id != oldid){
    			tempid = id;
    		}else{
    			return;
    		}
    		
    		$(id).css("background","#FFC125").css("color","white");
    		$(oldid).css("background","#fff").css("color","black");
    		oldid = tempid;
    	}
    	
    	var base_url  = "http://moneyzhuan.com/";
 		function putforwordDetail(){
	         window.location.href = base_url + "cashDetail?id=" + appid;
 		}
 		
    	function putforword(){
    		//${userappmodel.zhifubao}
    		
    		if(userScore >= score){
    			 $.ajax({
	 	             type: "GET",
	 	             url: "/app/userScore/putForward",
	 	             data: {userNum:usernum,forward:score},
	 	             dataType: "json",
	 	            success:function(data){
	 	            	var json = eval(data);
	 	           		var result  = json["result"];
	 	           		var msg  = json["msg"];
	 	           		if(result == -1){
	 	           			alert("抱歉，您的余额不足！");
	 	           		}else if(result == 2){
	 	           			alert(msg);
	 	           		}else{
	 	           			alert("提现成功，请等待后台管理审核！");
	 	           		}
	 	            }});
    		}else{
    			alert("抱歉，您的余额不足！");
    		}
    	}
    	
    	function go()
		{
			window.history.go(-1);
		}
    </script>
</body>
</html>