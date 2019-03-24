<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <script type="text/javascript" charset="utf-8" src="../js/jquery-1.11.3.min.js"></script>
   	 <meta name="format-detection" content="telephone=no" />
	<script type="text/javascript" charset="utf-8" src="../js/px2rem.js"></script>
	<script type="text/javascript" charset="utf-8" src="../js/c3listview.min.js"></script>
	<style>
	.title{
   	 		.flex; width:100%; height: 0.8rem;text-align: center; background:#fff; box-shadow:0px 2px 6px 0px rgba(23, 176, 81, 0.35);text-align:text-align: center;position:fixed;z-index:100
		}
	</style>
</head>

<body style="background:#F0F0F0; margin:0px;font-size:20px; font-family:微软雅黑;">
   <div onclick="go()" class="title">
	 		<img style="height:0.4rem;float:left;margin-left:10px;margin-top:10px;" src="../img/h5web/back-icon.png"/>
            <span style=".flex1; line-height:0.8rem; font-weight: bold; color: #4a4a4a; font-size: 0.4rem;margin:auto;position: absolute;top: 0;  left: 0;right: 0;bottom: 0">个人提现</span>
    </div>
    
    <div id="container" style="padding-top:40px;width:100%;font-size:15px;">
    	<div style="background:#fff; margin-top:5px;height:150px;">
    		<div style="padding-top:15px;margin-left:25px;line-height:30px;font-family:MicrosoftYaHei;font-weight:400;color:rgba(36,36,36,1);">
    			<span >提现账户ID：</span>
    			<span id="user_app_id"></span>
    		</div>
    		<div style="padding-top:15px;margin-left:25px;line-height:30px;font-family:MicrosoftYaHei;font-weight:400;color:rgba(36,36,36,1);">
    			<span>可用余额：</span>
    			<span id="user_score"></span>
    			<span onclick="putforwordDetail()" style="text-align: center;margin-right:25px;width:90px;float:right;color:#348AD3;border:1px solid #348AD3;border-radius:8px;">提现明细</span>
    		</div>
    		<div style="padding-top:15px;margin-left:25px;line-height:30px;font-family:MicrosoftYaHei;font-weight:400;color:rgba(36,36,36,1);">
    			<span>支付宝账户：</span>
    			<span id="payfornum"></span>
    		</div>
    	</div>
    	
    	<div style="background:#fff; margin-top:5px;height:400px;">
    		<div style="display:inline-block;padding-top:30px;margin-left:20px;line-height:30px;font-family:MicrosoftYaHei;font-weight:400;">
    			<span id="10_score" onclick="selectscore(10)" style="text-align: center;margin-left:25px;width:120px;float:left;color:#348AD3;border:1px solid #348AD3;border-radius:8px;">提现10元</span>
    			<span id="30_score"onclick="selectscore(30)" style="text-align: center;margin-left:40px;float:right;width:120px;color:#348AD3;border:1px solid #348AD3;border-radius:8px;">提现30元</span>
    		</div>
    		
    		<div style="display:inline-block;padding-top:20px;margin-left:20px;line-height:30px;font-family:MicrosoftYaHei;font-weight:400;color:rgba(36,36,36,1);">
    			<span id="50_score" onclick="selectscore(50)" style="text-align: center;margin-left:25px;float:left;width:120px;color:#348AD3;border:1px solid #348AD3;border-radius:8px;">提现50元</span>
    			<span id="100_score" onclick="selectscore(100)" style="text-align: center;margin-left:40px;width:120px;float:right;color:#348AD3;border:1px solid #348AD3;border-radius:8px;">提现100元</span>
    		</div>
    		
    		<div style="display:inline-block;padding-top:20px;margin-left:20px;line-height:30px;font-family:MicrosoftYaHei;font-weight:400;color:rgba(36,36,36,1);">
    			<span id="200_score" onclick="selectscore(200)" style="text-align: center;margin-left:25px;float:left;width:120px;color:#348AD3;border:1px solid #348AD3;border-radius:8px;">提现200元</span>
    			<span id="500_score" onclick="selectscore(500)" style="text-align: center;margin-left:40px;width:120px;float:right;color:#348AD3;border:1px solid #348AD3;border-radius:8px;">提现500元</span>
    		</div>
    		
    		<div style="width:100%;text-align:center;margin-top:30px;font-size:12px;color:#8B8682;">
		 		<span>注：每次提现少于50元收取5%的手续费</span>
		 	</div>
		 	
		 	<div  onclick="putforword()" style="width:100%;text-align:center;margin-top:45px;font-size:20px;color:#fff;">
		 		<span style="background-color:#348AD3;padding:5px 125px;border-radius:8px;">提现</span>
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
    	$(oldid).css("background","#EE9572").css("color","white");
    	
    	function selectscore(value){
    		score = value;
    		var id = "#" + value + "_score";
    		var tempid = "";
    		if(id != oldid){
    			tempid = id;
    		}else{
    			return;
    		}
    		
    		$(id).css("background","#EE9572").css("color","white");
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