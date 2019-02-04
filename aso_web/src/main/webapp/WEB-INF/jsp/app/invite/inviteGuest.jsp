<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html>
<html>
 <head>
     <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  	<script type="text/javascript" charset="utf-8" src="../js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="../js/px2rem.js"></script>
       <title>Happy赚官方助手</title>
		<script type="text/javascript">
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
		</style>
    </head>
    
    <body style="font-size:15px">
	  <div class="footer">
		  <div style="width:100%;height:40px;margin-top:80px;font-size:20px;color:#FFFFFF; ">
		  		<span id="install_button" onclick="hrefs(1)" style="background:#7B68EE;border-radius:10px;width:60%;margin-left:20%;padding-top:5px;padding-bottom:5px;
		  		position: absolute;text-align:center;">安装助手</span>
		  		
		  		<span id="trust_button" onclick="hrefs(2)" style="display:none;background:#7B68EE;border-radius:10px;width:60%;margin-left:20%;padding-top:5px;padding-bottom:5px;
		  		position: absolute;text-align:center;">前往信任</span>
		  		
		  		<span id="open_button" onclick="hrefs(3)" style="display:none;background:#7B68EE;border-radius:10px;width:60%;margin-left:20%;padding-top:5px;padding-bottom:5px;
		  		position: absolute;text-align:center;">打开助手</span>
		  </div>
	  </div>
    </body>
    </html>