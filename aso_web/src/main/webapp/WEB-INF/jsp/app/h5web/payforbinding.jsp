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
        <div  onclick="go()" class="title">
	 		<img  style="height:0.4rem;float:left;margin-left:10px;margin-top:10px;" src="../img/h5web/back-icon.png"/>
            <span style=".flex1; line-height:0.8rem; font-weight: bold; color: #4a4a4a; font-size: 0.4rem;margin:auto;position: absolute;top: 0;  left: 0;right: 0;bottom: 0">支付宝绑定</span>
    </div>
    
    <div id="container" style="padding-top:0.9rem;position:relative;width:100%;">
    	<div style="background:#fff; margin-top:5px;height:600px">
    			<div style="margin-top:20px;margin-left:20px;position:absolute;"><span>支付宝账户实名</span></div>
    			<input type="text" id="usernick" style="margin-top:45px;margin-left:20px;width:85%;  border: 0px;font-size:20px;
    			position:absolute;"onkeyup="this.value=this.value.replace(/[^\u4e00-\u9fa5]/g,'')" placeholder="请输入绑定支付宝账户实名"/>
    			<div style = "width:90%;margin-left:20px; height:1px; background:#aab2bd;margin-top:78px;position:absolute"></div>
    			
    			<div style="margin-top:95px;margin-left:20px;position:absolute;"><span>支付宝账户</span></div>
    			<input type="text" id="payfornum" style="margin-top:120px;margin-left:20px;width:85%;  border: 0px;font-size:20px;
    			position:absolute;" oninput = "value=value.replace(/[\u4e00-\u9fa5]/ig,'')" placeholder="请输入需绑定支付宝账户"/>
    			<div style = "width:90%;margin-left:20px; height:1px; background:#aab2bd;margin-top:153px;position:absolute"></div>
    			
    			<div style="margin-top:170px;margin-left:20px;position:absolute;"><span>确认支付宝账户</span></div>
    			<input type="text" id="payfornum_t" style="margin-top:195px;margin-left:20px;width:85%;  border: 0px;font-size:20px;
    			position:absolute;" oninput = "value=value.replace(/[\u4e00-\u9fa5]/ig,'')"  placeholder="请再次确认支付宝账户"/>
    			<div style = "width:90%;margin-left:20px; height:1px; background:#aab2bd;margin-top:228px;position:absolute"></div>
    			
    			<div onclick= "payforbinding()" style="position:absolute;background:#FFA500; text-align: center;margin-left:2%;width:96%;margin-top:270px;color:#FFFFFF; font-size: 0.5rem;border:1px dashed #CDCDC1;border-radius:10px;"><span>绑定支付宝</span></div>
    	</div>
    </div>
    <script>
    	var udid = localStorage.getItem("happyzhuan_user_udid");
    	function payforbinding(){
    		var usernick = $("#usernick").val();
    		var payfornum = $("#payfornum").val();
    		var payfornum_t = $("#payfornum_t").val();
    		
    		if(usernick ==  "" ||payfornum ==  "" || payfornum_t == ""){
    			alert("内容不能为空!");
    		} else if(payfornum != payfornum_t){
    			alert("支付宝账号确认出错!");
    		}else{
    			//绑定  支付宝账号
    			$.ajax({
   		             type: "post",
   		             url: "/app/user/updateUserAlipay",
   		             data: {udid:udid,alipay:payfornum,usernick:usernick},
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
    	
    	function go()
		{
			window.history.go(-1);
		}
    </script>
</body>
</html>