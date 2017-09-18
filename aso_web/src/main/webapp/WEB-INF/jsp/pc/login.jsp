<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<!-- saved from url=(0036)http://211.155.229.133:88/login.aspx -->
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>钻石社区管理平台</title>
<script src="dwz/js/jquery-1.7.2.js" type="text/javascript"></script>
<link href="dwz/themes/login/login.css" rel="stylesheet" type="text/css">
<link href="bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="dwz/themes/login/asyncbox.css" rel="stylesheet" type="text/css">
<script src="dwz/themes/login/AsyncBox.v1.4.5.js" ></script>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<base href="<%=basePath%>"/>
</head>
<body onload="init();" onkeydown="submitForm();">
	<div>
		<img src="<%=path %>/dwz/themes/login/top.jpg" width="100%" height="180"></img>

		<div class="login-panel">
		 	<div class="login-text" style="text-align:center;font-weight:bold;padding-top:20px">用户登陆</div>
			<form method="post" action="login" id="form1" style="margin-top:25px">	
				<div class="input-group">
			 		<span class="input-group-addon input-group-img" id="basic-addon1"><span class="glyphicon glyphicon-user" aria-hidden="true"></span></span>
			 		<input name="loginName" type="text" maxlength="16" id="loginName" value="${param.loginName }" class="form-control"
				 		placeholder="请输入用户名" style = "box-shadow: 0px 0px 0px;height:30px;width:286px">
			 	</div>
				<div class="place-holder"></div>
				<div class="input-group">
					<span class="input-group-addon input-group-img" id="basic-addon1"><span class="glyphicon glyphicon-lock" aria-hidden="true"></span></span>
					<input name="password" type="password" maxlength="32"  id="loginPass" value="" class="form-control" placeholder="请输入密码"
					style = "box-shadow: 0px 0px 0px;height:30px;width:286px">
				</div>
				
				<div class="input-group">
				 	<input type="submit" name="LoginButton" onclick="loginsumbit();" id="LoginButton" value="登陆" class="btn btn-default" 
				 	style="margin-top:30px;text-align:center;width:360px;height:50px;font-size:20px;font-weight:bold;">
				</div>
			</form>
		</div>
	</div>

   <div id="footer">
        <div class="pull-left" id="footer-left">@@2017&nbsp;钻石社区</div>
        <div class="pull-right" id="footer-right">
			<span class="text">联系人QQ：2126572197</span>
        </div>
    </div>

	<script>
		 function submitForm(event){ 
			var event=window.event?window.event:event; if(event.keyCode==13){ 
	 			loginsumbit(); 
			} 
		 } 
		 
		//登录错误信息提示
	    $(function () {
	        $("#LoginButton").click(function () {
	            if ($("#loginName").val().length <= 0) {
	                asyncbox.alert("请输入用户名！", "信息提示：", function () { $("#loginName").focus(); });                
	                return false;
	            }
	            else if ($("#loginPass").val().length <= 0) {
	                asyncbox.alert("请输入密码！", "信息提示：", function () { $("#loginPass").focus(); }); 
	                return false;
	            }
	            else {
	                return true;
	            }
	        });
	    });
		
	    $(function(){
	           if(${not empty loginResult}){
	               if(${loginResult==-2}){
	            	   asyncbox.alert("密码错误！", "登录失败：", function () { $("#loginPass").focus(); }); 
	            	   return false;
	               }
	               if(${loginResult==-1}){
	            	   asyncbox.alert("用户名错误！", "登录失败：", function () { $("#loginName").focus(); }); 
	            	   return false;
	               }
	           }
	        });
	    //记住登录信息
	    
	    function loginsumbit(){
			if(document.getElementById("AutoLogin").checked){
				SetCookie ("loginName", $('#loginName').val());
				SetCookie ("loginPass", $('#loginPass').val());
			}
			form1.submit();
		}
	
	    function init() {
			$('#loginName').val(getCookie("loginName") || "");
			$('#loginPass').val(getCookie("loginPass") || "");
		}
		
		function SetCookie(name,value) {
	    	var Days = 30; //此 cookie 将被保存 30 天
	    	var exp  = new Date();    //new Date("December 31, 9998");
	    	exp.setTime(exp.getTime() + Days*24*60*60*1000);
	    	document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
		}
		
		function getCookie(name) {
			var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
			if(arr != null) return unescape(arr[2]); return null;
		}
		
		function delCookie(name) {
			var exp = new Date();
			exp.setTime(exp.getTime() - 1);
			var cval=getCookie(name);
			if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString();
		}
	</script>
</body>
</html>