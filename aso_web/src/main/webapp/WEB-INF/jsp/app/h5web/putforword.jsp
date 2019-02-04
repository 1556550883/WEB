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

<body style="background:#F0F0F0; margin:0px;font-size:15px;text-align: center;">
	 <div class="title">
	 		<span onclick="go()" style="line-height:0.8rem;color:Blue;font-size:0.6rem;float:left;margin-left:15px;margin-bottom:10px"><</span>
            <span style=".flex1; line-height:0.8rem; font-weight: bold;text-align: center; color: #4a4a4a;width:100%; font-size: 0.4rem;">个人提现</span>
    </div>

    <div id="container" style="padding-top:0.9rem;position:relative;width:100%;">
    	<div style="background:#fff; margin-top:5px;height:600px; position: relative">
    			<div style="margin-top:20px;margin-left:20px;position:absolute;"><span>支付宝账户</span></div>
    			<div style="margin-top:55px;position:absolute;">
    				<span id="payfornum" style="margin-left:20px;color:#AAAAAA;"></span>
    			</div>
    			
    			<div style = "width:70%;margin-left:20px; height:1px; background:#AAAAAA;margin-top:78px;position:absolute"></div>
    		
    		<div onclick="selectscore(10)">
		 		<span  id="10_score" style="width:40%;padding-top:7px;padding-bottom:7px;margin-left:30px;float:left;border-radius:4px;margin-top:120px;border:1px solid">提现10元</span>
		 	</div>
    		
    		<div onclick="selectscore(30)">
		 		<span  id="30_score"  style="width:40%;padding-top:7px;padding-bottom:7px;margin-right:30px;float:right;border-radius:4px;margin-top:120px;border:1px solid">提现30元</span>
		 	</div>
    		
    		<div onclick="selectscore(50)">
		 		<span  id="50_score"  style="width:40%;padding-top:7px;padding-bottom:7px;margin-left:30px;float:left;border-radius:4px;margin-top:20px;border:1px solid">提现50元</span>
		 	</div>
		 	
		 	<div onclick="selectscore(100)">
		 		<span  id="100_score"  style="width:40%;padding-top:7px;padding-bottom:7px;margin-right:30px;float:right;border-radius:4px;margin-top:20px;border:1px solid">提现100元</span>
		 	</div>
		 	
		 	<div style="float:left;width:100%;text-align:left;margin-left:30px;margin-top:25px">
		 		<span>注：每次提现少于50元收取5%的手续费</span>
		 	</div>
		 	
		 	<div onclick="putforword()">
		 		<span  style="background:#FFC125;width:55%;padding-top:10px;padding-bottom:10px;margin-left:30px;float:left;border-radius:4px;margin-top:50px;color:white">提现</span>
		 	</div>
    	</div>
    </div>
    
    <script>
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
    	
    	function putforword(){
    		//${userappmodel.zhifubao}
    		var userScore  = "${HUserAppModel.score}";
    		var usernum = "${HUserAppModel.userNum}";
    		if(userScore > score){
    			 $.ajax({
	 	             type: "GET",
	 	             url: "/app/userScore/putForward",
	 	             data: {userNum:usernum,forward:score},
	 	             dataType: "json",
	 	            success:function(data){
	 	            	var json = eval(data);
	 	           		var result  = json["result"];
	 	           		if(result == -1){
	 	           			alert("抱歉，您的余额不足！");
	 	           		}else{
	 	           			alert("提现成功个，请等待后台管理审核！");
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