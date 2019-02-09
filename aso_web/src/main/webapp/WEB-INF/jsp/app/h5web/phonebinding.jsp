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

<body style="background:#F0F0F0; margin:0px;font-size:15px;">
    <div onclick="go()" class="title">
	 		<img style="height:0.4rem;float:left;margin-left:10px;margin-top:10px;" src="../img/h5web/back-icon.png"/>
            <span style=".flex1; line-height:0.8rem; font-weight: bold; color: #4a4a4a; font-size: 0.4rem;margin:auto;position: absolute;top: 0;  left: 0;right: 0;bottom: 0">手机绑定</span>
    </div>
    
      <div id="container" style="padding-top:0.9rem;position:relative;width:100%;">
    	<div style="background:#fff; margin-top:5px;height:600px">
    			<div style="margin-top:20px;margin-left:20px;position:absolute;"><span>手机号码</span></div>
    			<input type="text" id="userphone" style="margin-top:45px;margin-left:20px;width:85%;  border: 0px;font-size:20px;
    			position:absolute;" oninput = "value=value.replace(/[^\d]/g,'')" placeholder="请输入真实手机号码">
    			<div style = "width:90%;margin-left:20px; height:1px; background:#aab2bd;margin-top:78px;position:absolute"></div>
    			
    			<div style="margin-top:95px;margin-left:20px;position:absolute;"><span>验证码</span></div>
    			<input type="text" id="phonecode" style="margin-top:120px;margin-left:20px;width:40%;  border: 0px;font-size:20px;
    			position:absolute;" oninput = "value=value.replace(/[^\d]/g,'')"  placeholder="请输入验证码">
    			<span onclick="sendsms()" id="send_sms" type="button" style= "margin-top:120px; font-size:20px; float:right;width:140px;margin-right:30px;border:1px solid #AAAAAA;border-radius:10px;color:#AAAAAA;text-align: center; ">获取验证码</span>
    			<div style = "width:40%;margin-left:20px; height:1px; background:#aab2bd;margin-top:153px;position:absolute"></div>
    			
    			
    			<div onclick= "phonebinding()" style="position:absolute;background:#FFA500; text-align: center;margin-left:2%;width:96%;margin-top:270px;color:#FFFFFF; font-size: 0.5rem;border:1px dashed #CDCDC1;border-radius:10px;"><span>绑定手机</span></div>
    	</div>
    </div>
    <script>
    	var udid = localStorage.getItem("happyzhuan_user_udid");
		//udid = "d6638e6de42f029649654ad4b17badf532bb9bcc";
		var test = sessionStorage.getItem('count_time'); 
    	function phonebinding(){
    		
    		var userphone = $("#userphone").val();
    		var phonecode = $("#phonecode").val();
    		
    		//判断手机 号码是否正确
   			 if(!(/^1[34578]\d{9}$/.test(userphone))){ 
    	        alert("手机号码有误，请重填");  
    	        return false; 
    	    } 
    		
    		if(userphone != "" && phonecode != ""){
    			$.ajax({
		             type: "post",
		             url: "/app/user/verifySmsCode",
		             data: {udid:udid,phoneNumber:userphone,smsCode:phonecode},
		             dataType: "json",
		           	 success:function(data){
		           		var json = eval(data);
   		            	var result  = json["result"];
   		            	if(result == 1){
   		            		//返回上一页
   		            		window.history.go(-1);
   		            	}else{
   		            		alert("此账号已被使用，请使用其他账号！");
   		            	}
		            }
	             })
    		}
    	}
    	
    	var count = 60;
		function sendsms(){
			if(count != 60) {return}
    		var userphone = $("#userphone").val();
    		//判断手机 号码是否正确
    		 if(!(/^1[34578]\d{9}$/.test(userphone))){ 
     	        alert("手机号码有误，请重填");  
     	        return false; 
     	    } 
    		
    		if(userphone != null && userphone != ""){
    			$.ajax({
		             type: "post",
		             url: "/app/user/send_msg",
		             data: {udid:udid,phoneNumber:userphone},
		             dataType: "json",
		           	 success:function(data){
		           	   var resend = setInterval(function(){
			                   count--;
			                   if (count > 0){
			                	   //send_sms
			                   		$("#send_sms").text(count+"s重新获取");
			                   		sessionStorage.setItem('count_time',count); 
			                   }else {
			                       clearInterval(resend);
			                       $("#send_sms").text("获取验证码");
			                       count = 60;
			                   }
		                   }, 1000);
		           	   
		           		$("#day_score").attr('disabled',true).css('cursor','not-allowed');
		            }
	             })
    		}
    	}
		
		function go()
		{
			window.history.go(-1);
		}
    </script>
</body>
</html>