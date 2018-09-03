<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
		<meta name="apple-mobile-web-app-capable" content="yes"> 
		<meta name="apple-mobile-web-app-status-bar-style" content="black"> 
		<meta name="format-detection" content="telephone=no">  
       <title>Happy赚助手下载</title>
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
				height:50%;
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
				margin-top:5px;
				margin-left:20%;
				text-align:center; 
				position:relative;
			}
			
			.text_style
			{
				margin-top:50%;
				text-align: center;
				width:80%;
				margin-left:10%;
				color: white;
			}
				
			.text_style_1
			{
				margin-top:12px;
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
			function ShowDiv(show_div,bg_div)
			{
				var scrollHeight = document.body.scrollHeight; //文档高度
				document.getElementById(bg_div).style.height=scrollHeight+'px';
				
				document.getElementById(show_div).style.display='block';
				document.getElementById(bg_div).style.display='block';
			};
			
			//关闭弹出层
			function CloseDiv(show_div,bg_div)
			{
				document.getElementById("phone").value = '';
				document.getElementById(show_div).style.display='none';
				document.getElementById(bg_div).style.display='none';
			};
		
			function goToAdd(deviceUuid){
				var masterid = "${master}";
				ajax({ 
					  type:"POST", 
					  url:"invite/deviceUuid", 
					  dataType:"json", 
					  data:{"deviceUuid":deviceUuid,"masterid":masterid}, 
					  beforeSend:function(){ 
							//失败和成功，最后都会执行
					  }, 
					  success:function(msg){ 
						  //请求成功执行
						  alert(msg);
					  }, 
					  error:function(){ 
						 //请求失败
						  alert("网络请求失败！");
					  } 
					})
			}
		
		function ajax(){ 
			  var ajaxData = { 
			    type:arguments[0].type || "GET", 
			    url:arguments[0].url || "", 
			    async:arguments[0].async || "true", 
			    data:arguments[0].data || null, 
			    dataType:arguments[0].dataType || "text", 
			    contentType:arguments[0].contentType || "application/x-www-form-urlencoded", 
			    beforeSend:arguments[0].beforeSend || function(){}, 
			    success:arguments[0].success || function(){}, 
			    error:arguments[0].error || function(){} 
			  } 
			  
			  ajaxData.beforeSend() 
			  var xhr = createxmlHttpRequest();  
			  xhr.responseType=ajaxData.dataType; 
			  xhr.open(ajaxData.type,ajaxData.url,ajaxData.async);  
			  xhr.setRequestHeader("Content-Type",ajaxData.contentType);  
			  xhr.send(convertData(ajaxData.data));  
			  xhr.onreadystatechange = function() 
			  {  
			    if (xhr.readyState == 4) {  
			      if(xhr.status == 200)
			      { 
			        ajaxData.success(xhr.response)
			      }
			      else
			      { 
			        ajaxData.error() 
			      }
			    } 
			  }  
			} 
			  
			function createxmlHttpRequest() {  
			  if (window.ActiveXObject) {  
			    return new ActiveXObject("Microsoft.XMLHTTP");  
			  } else if (window.XMLHttpRequest) {  
			    return new XMLHttpRequest();  
			  }  
			} 
			  
			function convertData(data){ 
			  if( typeof data === 'object' ){ 
			    var convertResult = "" ;  
			    for(var c in data){  
			      convertResult+= c + "=" + data[c] + "&";  
			    }  
			    convertResult=convertResult.substring(0,convertResult.length-1) 
			    return convertResult; 
			  }else{ 
			    return data; 
			  } 
			}
			
			function hrefs(v)
			{
				var url = "" ;  
				if(v == 0){
					var userId = "${userId}";
					url = "https://moneyzhuan.com/download/happyzhuan.mobileconfig?masterid=" +  "${master}" + "&userId=" + userId;
				}
				else
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
		
		<%
    		String path = request.getContextPath();
    		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
		%>
		<base href="<%=basePath%>"/>
    </head>
    <body>
	   <div class="text_style">下载立送现金红包，零成本，零投入，高奖励，完成任务可得现金！</div>
	   <div class="text_style_1" onclick="openWindow()">查看安装说明</div>
       <div class="bottom_menu">
       			<div style="width:100%;text-align:center;color: red; margin-top:13%;">一、安装助手，务必获取设备信任</div>
	       		<div class="bottom_pic_1" onClick="window.location.href='itms-services://?action=download-manifest&url=https://moneyzhuan.com/download/HappyApp.plist'"> 
	           	 	<img src="<%=path %>/img/invite/button.png" height="auto" width="100%"/>
	           	 	<div style="width:100%;position:absolute;z-indent:2;text-align:center;color: white; top:50%;">安装助手</div>
	            </div>
           		 <div style="width:100%;text-align:center;color: red; margin-top:8%;">二、安装入口后，可快速打开</div>
	           	<div class="bottom_pic_2" onClick="hrefs(0)"> 
	           	 	<img src="<%=path %>/img/invite/button.png" height="auto" width="100%"/>
	           	 	<div style="width:100%;position:absolute;z-indent:2;text-align:center;color: white; top:50%;">安装入口</div>
	            </div>
	             <div style="width:100%;text-align:center;color: red; margin-top:8%;">三、下载完成后，务必点击</div>
	           	<div class="bottom_pic_2" onClick="hrefs(1)"> 
	           	 	<img src="<%=path %>/img/invite/button.png" height="auto" width="100%"/>
	           	 	<div style="width:100%;position:absolute;z-indent:2;text-align:center;color: white; top:50%;">打开助手</div>
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
		if(issafariBrowser == true)
		{
			document.getElementById("fade_1").style.display='none';
			document.getElementById("safariTip").style.display='none';
		}
	</script>
    </body>
    </html>