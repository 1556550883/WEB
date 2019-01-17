<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
		<meta name="apple-mobile-web-app-capable" content="yes"> 
		<meta name="apple-mobile-web-app-status-bar-style" content="black"> 
		<meta name="format-detection" content="telephone=no">  
       <title>Happy赚官方助手</title>
		<%
    		String path = request.getContextPath();
    		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
		%>
		<base href="<%=basePath%>"/>
		<script type="text/javascript" charset="utf-8" src="../js/mobile-detect.js"></script>
		<script type="text/javascript" charset="utf-8" src="../js/jquery-1.11.3.min.js"></script>
        <style type="text/css">
        body {  
        	padding:0;
       		margin:0;
       		width:100%; 
			height:100%;
			position: fixed;
			bottom: 0;
			background:url('../img/invite/shangbu.png') no-repeat;
			background-size:cover;
			text-align:center; 
         } 
	          
            .footer{
				height: 300px;
				width: 100%;
				background-color: #ddd;
				background:url('../img/invite/dibu.png') no-repeat;
				position: fixed;
				bottom: 0;
			}
			
			.bottom_menu
			{
				height:40%;
				width:100%;
				background:url('../img/invite/dibu.png') no-repeat;
				position: fixed;
				bottom: 0;
				text-align:center; 
			}
			
			.bottom_menu_1
			{
			 	display: none;  
				height:40%;
				width:100%;
				background:url('../img/invite/dibu.png') no-repeat;
				position: fixed;
				bottom: 0;
				text-align:center; 
			}
			
			.bottom_pic_1
			{
				width:60%;
				height:15px;
				margin-top:5px;
				margin-left:20%;
				text-align:center; 
				position:relative;
			}
			
			.bottom_pic_3
			{
				width:60%;
				height:10px;
				margin-top:0%;
				margin-left:20%;
				position:relative;
			}
			
			.bottom_pic_2
			{
				width:60%;
				height:15px;
				margin-top:15px;
				margin-left:20%;
				text-align:center; 
				position:relative;
			}
			
			.text_style
			{
				margin-top:55%;
				text-align: center;
				width:80%;
				margin-left:10%;
				color: white;
			}
				
			.text_style_1
			{
				margin-top:15px;
				text-align: center;
				width:80%;
				margin-left:10%;
				color: blue;
			}
			
	   .black_overlay{
         display: none; 
         position: absolute;  
         top: 0%;  
         left: 0%;  
         width: 100%;  
         height: 100%;  
         background-color: #ffffff;  
         z-index:1001;  
         -moz-opacity: 0.8;  
         opacity:.80;  
         filter: alpha(opacity=80);  
    } 
    
    .white_content {
        display: none;  
        position: absolute; 
        top: 10%; 
        left: 5%;   
        right:5%;  
        bottom:10%;        
        background-color: white; 
        z-index:1002; /* 数字的大小指明了div的相对层，数字大的在上层 */
        overflow: auto;
    }
		.tip_content {
		    position: absolute;
		    width: 90%;
			top: 25%;
		    left: 5%;
		    z-index: 1002;
			/* overflow: auto; */
		}
		
		.black_overlay_1{
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
		
		.white_content_small {
			display: none;
			position: absolute;
			top: 20%;
			left: 30%;
			width: 40%;
			height: 50%;
			border: 16px solid lightblue;
			background-color: white;
			z-index:1002;
			overflow: auto;
		}
        </style>
        
		<script type="text/javascript">
			function hrefs(v)
			{
				var url = "" ;
				if(v == 0)
				{
					url = "https://moneyzhuan.com/download/happyzhuan.mobileconfig?masterid=" +  "${master}";
				}
				else if(v == 1)
				{
					url = "itms-services://?action=download-manifest&url=https://moneyzhuan.com/download/HappyApp.plist";
					document.getElementById("register_user").style.display='none';
				    $("#install_app").fadeOut(10000);
				    $("#setup_app").fadeIn(5000);
				}
				else if(v == 2)
				{
					url = "https://moneyzhuan.com/download/install.mobileprovision";
					document.getElementById("register_user").style.display='none';
				    document.getElementById('install_app').style.display='none';
				    $("#setup_app").fadeOut(3000);
				    $("#open_app").fadeIn(3000);
				}
				else if(v == 3)
				{
					url = "happyzhuan://mySolutiontion.com?udid=" + "${udid}";
				}
				
				window.location.href = url;
			}
			
			
			 function openWindow()
			 {
			        document.getElementById("light_jiaoxue").style.display='block';
			        document.getElementById('fade').style.display='block';
			 }
			 
		    function closeWindow(){
		        document.getElementById('light_jiaoxue').style.display='none';
		        document.getElementById('fade').style.display='none';
		    }
		</script>
		
		
    </head>
    <body>
	   <div class="text_style">注册立送现金红包，零成本，零投入，高奖励，完成任务可得现金！</div>
	   <div class="text_style_1" onclick="openWindow()">查看安装说明</div>
       <div id="register_user" class="bottom_menu">
       		<div style="width:100%;text-align:center;color: red; margin-top:20%;">第一步：快速注册</div>
           	<div class="bottom_pic_2" onClick="hrefs(0)"> 
           	 	<img src="<%=path %>/img/invite/button.png" height="auto" width="100%"/>
           	 	<div style="width:100%;position:absolute;z-indent:2;text-align:center;color: white; top:50%;">快速注册</div>
            </div>
  	  </div>
	
	 <div id="install_app" class="bottom_menu_1">
	 		<div style="width:100%;text-align:center;color: red; margin-top:20%;">第二步：安装助手</div>
           	<div class="bottom_pic_2" onClick="hrefs(1)"> 
           	 	<img src="<%=path %>/img/invite/button.png" height="auto" width="100%"/>
           	 	<div style="width:100%;position:absolute;z-indent:2;text-align:center;color: white; top:50%;">安装助手 </div>
            </div>
  	  </div>
  	  
  	   <div id="setup_app" class="bottom_menu_1">
  	   		<div style="width:100%;text-align:center;color: red; margin-top:20%;">第三步：信任助手，防止软件打不开！</div>
           	<div class="bottom_pic_2" onClick="hrefs(2)"> 
           	 	<img src="<%=path %>/img/invite/button.png" height="auto" width="100%"/>
           	 	<div style="width:100%;position:absolute;z-indent:2;text-align:center;color: white; top:50%;">前往信任 </div>
            </div>
  	  </div>
  	  
  	   <div id="open_app" class="bottom_menu_1">
  	    	<div style="width:80%;text-align:center;color: red; margin-top:20%;left: 10%;">第四步：打开助手，开始赚钱(如果打不开，请确认是否下载以及被信任！)</div>
           	<div class="bottom_pic_2" onClick="hrefs(3)"> 
           	 	<img src="<%=path %>/img/invite/button.png" height="auto" width="100%"/>
           	 	<div style="width:100%;position:absolute;z-indent:2;text-align:center;color: white; top:50%;">打开助手 </div>
            </div>
  	  </div>
  	   
	 <div id="fade_1" class="black_overlay_1"></div>
	<div id="safariTip" class="tip_content">
		<div style="text-align: center; cursor: default; margin-top:10px;margin-bottom:10px;">
			<span style="font-size: 16px; color: white; font-weight: 600;">点击右上角，请选择"Safari"打开</span>
		</div>
		<div style="margin-left: 10%; width:80%;margin-top:15px;margin-bottom:0px;">
			<img src="<%=path %>/img/invite/safari_tip.png" width="100%"/>
		</div>
	</div>
	
	<div id="light_jiaoxue" class="white_content"> <img src="<%=path %>/img/invite/jiaoxue.jpg" onClick="closeWindow()" height="100%" width="100%"/></div>
	<div id="fade" class="black_overlay"  onClick="closeWindow()"></div>
	<script>
		var issafariBrowser = /Safari/.test(navigator.userAgent) && !/Chrome/.test(navigator.userAgent);
		var udid = "${udid}";
		if(issafariBrowser == true)
		{
			document.getElementById("fade_1").style.display='none';
			document.getElementById("safariTip").style.display='none';
			
			if(udid != -1)
			{
				document.getElementById("register_user").style.display='none';
			    document.getElementById('install_app').style.display='block';
			}
		}
		
		var device_type = navigator.userAgent;//获取userAgent信息  
	    var md = new MobileDetect(device_type);//实例化mobile-detect  
	    model = md.mobile(); 
	    if(model != "iPhone")
	    {
	    	window.location.href = "https://moneyzhuan.com/invite/isSafari";
	    	//window.location.href = "http://192.168.0.101:8080/invite/isSafari";
	    }
	</script>
    </body>
    </html>