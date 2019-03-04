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
			
			.title{
   	 			.flex; width:100%; height: 0.8rem; background:#fff; box-shadow: 0 3px 5px #e0e0e0;text-align: center;position:fixed;z-index:100
			}
		</style>
    </head>
    
<body style="background:#F0F0F0; margin:0px">
     <div  class="title">
           <span style=".flex1; line-height:0.8rem;   background-color:#8968CD;font-weight: bold; color: #FFFFFF; font-size: 0.4rem;margin:auto;position: absolute;top: 0;  left: 0;right: 0;bottom: 0">Happy赚下载</span>
   	</div>
	  
  <div id="container" style="padding-top:0.9rem;position:relative;width:100%;font-size:20px;">
   	<div style="background:#fff; margin-top:5px;height:300px;text-align:center;">
  		<div style="padding-top:15px">
   			<span>下载安装</span>
   		</div>
   		<div style="font-size:15px;color:#AAAAAA;">
   			<span>让您更加便捷的使用happy赚</span>
   		</div>
   		<img style="width:80px;height:80px;border-radius:10px; margin-top:15px" src="../img/h5web/happy_logo.png"/>
   		<div style="font-size:15pxss">
   			<span>happy赚</span>
   		</div>
	 	<div onclick="hrefs(1)" style="position:absolute;background:#8968CD;
	 	 text-align: center;margin-left:20%;width:60%;margin-top:30px;color:#FFFFFF;padding-top:5px;padding-bottom:5px;
	 	  font-size: 15px;border-radius:10px;"><span>立即安装</span></div>
   	</div>
   	
  	 <div style="background:#fff; margin-top:10px;height:790px;text-align:center;">
   		<div style="padding-top:15px;font-size:15px;color:#AAAAAA;">
   			<span>如果出现以下情况</span>
   		</div>
   		<img style="width:240px;height:160px;border-radius:10px; margin-top:15px" src="../img/h5web/qisu_tip.jpg"/>
   		<div style="padding-top:15px;font-size:15px;color:#AAAAAA;">
   			<span>设置-通用-设备管理-信任</span>
   		</div>
   		<img style="width:240px;height:400px;border-radius:10px; margin-top:15px" src="../img/h5web/qiu_tip_2.png"/>
   			<div style="padding-top:15px;font-size:15px;color:#AAAAAA;">
   			<span>如果点击未跳转，请手动前往信任</span>
   		</div>
   		<div onclick="hrefs(2)" style="position:absolute;background:#8968CD;
	 	 text-align: center;margin-left:20%;width:60%;margin-top:20px;color:#FFFFFF;padding-top:5px;padding-bottom:5px;
	 	  font-size: 15px;border-radius:10px;"><span>前往信任</span></div>
  	</div>


 <div  onclick="hrefs(3)" style="background:#fff; margin-top:10px;height:130px;text-align:center;">
   		<div style="padding-top:15px;font-size:15px;color:#AAAAAA;">
   			<span>完成后即可点击打开</span>
   		</div>
   		<div style="position:absolute;background:#8968CD;
	 	 text-align: center;margin-left:20%;width:60%;margin-top:20px;color:#FFFFFF;padding-top:5px;padding-bottom:5px;
	 	  font-size: 15px;border-radius:10px;"><span>打开Happy赚</span></div>
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
			}
			else if(v == 2)
			{
				url = "https://moneyzhuan.com/download/install.mobileprovision";
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