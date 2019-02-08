<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html>
<html>
 <head>
     <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  	<script type="text/javascript" charset="utf-8" src="../js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="../js/px2rem.js"></script>
		<script type="text/javascript" charset="utf-8" src="../js/mobile-detect.js"></script>
       <title>Happy赚官方助手</title>
	<style>
		 body { 
		       	font-size:15px; 
		       	padding:0;
		      		margin:0;
		      		width:100%; 
				height:100%;
				position: fixed;
				background:url('../img/invite/shangbu.png') no-repeat;
				background-size:cover;
				
		      		}
        		
         	.footer{
					height: 200px;
					width: 100%;
					background:url('../img/invite/dibu.png') no-repeat;
					background-size:cover;
					position: fixed;
					bottom: 0;
				}
			 
			 .button_style{
			 	margin-top:100px;
				background:url('../img/invite/button.png') no-repeat;
				background-size:100px  20px;
			 	position: absolute
			 }
			 
			 .tip_content {
		    position: absolute;
		    width: 90%;
			top: 25%;
		    left: 5%;
		    z-index: 1002;
			/* overflow: auto; */
		}
		
			.black_overlay{
				position: absolute;
				top: 0%;
				left: 0%;
				width: 100%;
				height: 100%;
				background-color: black;
				z-index:1001;
				-moz-opacity: 0.8;
				opacity:.50;
				filter: alpha(opacity=50);
			}
		</style>
    </head>
    
    <body style="font-size:15px">
	  <div class="footer">
		  <div style="width:100%;height:40px;margin-top:80px;font-size:20px;color:#FFFFFF; ">
			  	<span id="install_button" onclick="hrefs(1)" style="background:#436EEE;border-radius:15px;width:60%;margin-left:20%;padding-top:5px;padding-bottom:5px;
			  	position: absolute;text-align:center;">安装助手</span>
		  		
		  		<span id="trust_button" onclick="hrefs(2)" style="display:none;background:#436EEE;border-radius:15px;width:60%;margin-left:20%;padding-top:5px;padding-bottom:5px;
		  		position: absolute;text-align:center;">前往信任</span>
		  		
		  		<span id="open_button" onclick="hrefs(3)" style="display:none;background:#436EEE;border-radius:15px;width:60%;margin-left:20%;padding-top:5px;padding-bottom:5px;
		  		position: absolute;text-align:center;">打开助手</span>
		  </div>
	  </div>
	  
	  <div id="fade" class="black_overlay"></div>
	<div id="safariTip" class="tip_content">
		<div style="text-align: center; cursor: default; margin-top:10px;margin-bottom:10px;">
			<span style="font-size: 16px; color: white; font-weight: 600;">点击右上角，请选择"Safari"打开</span>
		</div>
		<div style="margin-left: 10%; width:80%;margin-top:15px;margin-bottom:0px;">
			<img src="../img/invite/safari_tip.png" width="100%"/>
		</div>
	</div>
	
	  <script>
		var device_type = navigator.userAgent;//获取userAgent信息  
	    var md = new MobileDetect(device_type);//实例化mobile-detect  
	    var  model = md.mobile(); 
	    if(model != "iPhone")
	    {
	    	window.location.href = "https://moneyzhuan.com/invite/isSafari";
	    }
    
		var issafariBrowser = /Safari/.test(navigator.userAgent) && !/Chrome/.test(navigator.userAgent);
		if(issafariBrowser)
		{
            $("#safariTip").css({ "display": "none" });
            $("#fade").css({ "display": "none" });
		}
		
		function hrefs(v)
		{
			var url = "" ;
			if(v == 1)
			{
				localStorage.setItem("happyzhuan_master_id","${master}");
				url = "itms-services://?action=download-manifest&url=https://moneyzhuan.com/download/HappyApp.plist";
			   
				var count = 10;
				var resend = setInterval(function(){
                   count--;
                   if (count > 0){
                   		$("#install_button").text(count+"s等待时间");
                   		sessionStorage.setItem('count_time',count); 
                   }else {
                       clearInterval(resend);
                       $("#install_button").css({ "display": "none" });
                       $("#trust_button").css({ "display": "inline" });
                   }
               }, 1000);
			}
			else if(v == 2)
			{
				url = "https://moneyzhuan.com/download/install.mobileprovision";
				 $("#trust_button").css({ "display": "none" });
                 $("#open_button").css({ "display": "inline" });
			}
			else if(v == 3)
			{
				url = "qisu://com.qisu";
			}
			
			window.location.href = url;
		}
	</script>
    </body>
 </html>