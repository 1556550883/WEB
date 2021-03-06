<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<script type="text/javascript" charset="utf-8" src="../js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="../js/px2rem.js"></script>
	
	<script type="text/javascript" charset="utf-8" src="../js/showText.js"></script>
	<script type="text/javascript" charset="utf-8" src="../js/clipboard.min.js"></script>
	<style>
	.title{
   	 		.flex; width:100%; height: 0.8rem; background:#fff; box-shadow: 0 3px 5px #e0e0e0;text-align: center;position:fixed;z-index:100
		}
	#input {position: absolute;top: 0;left: 0;opacity: 0;z-index: -10;}
	</style>
</head>

<body style="background:#F0F0F0; margin:0px;box-shadow: 0 3px 5px #e0e0e0;font-size:20px; font-family:微软雅黑;color:#444;">
 	 <textarea id="input"></textarea>
      <div onclick="giveUpTask()" class="title">
	 		<img style="height:0.4rem;float:left;margin-left:10px;margin-top:10px;" src="../img/h5web/back-icon.png"/>
            <span style=".flex1; line-height:0.8rem; font-weight: bold; color: #4a4a4a; font-size: 0.4rem;margin:auto;position: absolute;top: 0;  left: 0;right: 0;bottom: 0">任务详情</span>
    </div>
     <div id="container" style="padding-top:0.9rem;position:relative;width:100%;">
     
    	<div style="background:#fff; margin-top:5px;height:490px">
    		<img id="adver_img" style="width:65px;height:65px;margin-top:20px;margin-left:20px;border-radius:5px;float:left" src="../img/h5web/happy_logo.png"/>
    		<span id= "adver_name" style="margin-left:10px;margin-top:25px;position:absolute;font-size:20px;"></span>
    		<span id= "time" style="margin-left:10px;margin-top:60px;position:absolute;padding-left:5px;padding-right:5px;font-size:10px;border:1px solid #F08080;color:#F08080;border-radius:5px;"></span>
    		<span id= "adver_price" style="margin-right:30px;margin-top:35px;font-size:25px;float:right;color:#F08080"></span>
	    	<div style = "width:96%;margin-left:2%; height:1px; background:#F08080;margin-top:100px;position:absolute"></div>
	    	<div style = "width:96%;margin-left:2%;margin-top:101px;position:absolute"><span style="padding-top:20px;position:absolute;">任务步骤：</span></div>
	    	<div style = "width:96%;margin-left:2%;margin-top:141px;position:absolute">
			    <ul style="font-size:15px;">
				  <li>点击复制按钮 ，拷贝关键词，自动跳转至App store</li>
				  <li>在Appstore底栏的"搜索 "中，粘贴并搜索关键词</li>
				  <li>找到对应的图标，位置大约在<font id="adesc" style="color:#F08080"></font></li>
				  <li>下载完点击"打开应用"按钮 ,<font style="color:#F08080;">务必允许应用连接网络(否则影响任务完成)</font>，体验三分钟时间</li>
				</ul>
	    	</div>
	    	<div style = "width:96%;margin-left:2%;margin-top:315px;position:absolute"><span style="position:absolute;font-size:15px;">注意：该任务需要您首次下载此应用</span></div>
    		<div style = "width:96%;margin-left:2%; height:1px; background:#F08080;margin-top:341px;position:absolute"></div>
   			
   			<div  onclick="clickAdverName()" id="adverName" 
   				style="position:absolute; -webkit-user-select:initial;text-align: center;margin-left:2%;width:96%;margin-top:365px;color:#0000EE; font-size: 0.6rem;border:1px dashed #CDCDC1;border-radius:10px;"
   				 data-clipboard-action="copy" data-clipboard-target="#adverName">
			</div>
			<div style = "width:70%;margin-left:15%;margin-top:428px;position:absolute;color:#AAAAAA;text-align: center; ">
					<span style="font-size:15px">点击复制，跳转至AppStore<font style="color:#F08080;">点击 "搜索"</font>，在搜索框中粘贴</span></div>
    	</div>
    	
    	<div style="background:#fff; margin-top:15px;height:300px">
    	 	<div onclick="openApp()" style="position:absolute;background:#0000EE; text-align: center;margin-left:2%;width:96%;margin-top:30px;color:#FFFFFF; font-size: 0.5rem;border:0px dashed #CDCDC1;border-radius:10px;"><span>打开应用</span></div>
    		<div style = "width:70%;margin-left:15%;margin-top:80px;position:absolute;color:#AAAAAA;text-align: center; ">
					<span style="font-size:15px">下载成功后在此打开应用试玩</span></div>
					
			<div onclick="commitTask()" style="position:absolute;background:#F08080; text-align: center;margin-left:2%;width:96%;margin-top:120px;color:#FFFFFF; font-size: 0.5rem;border:0px dashed #CDCDC1;border-radius:10px;"><span>提交任务</span></div>
    		<div style = "width:70%;margin-left:15%;margin-top:170px;position:absolute;color:#AAAAAA;text-align: center; ">
					<span style="font-size:15px">下载成功后在此提交任务</span>
			</div>
			
			<div onclick="giveUpTask()" style="position:absolute;background:#AAAAAA; text-align: center;margin-left:2%;width:96%;margin-top:210px;color:#FFFFFF; font-size: 0.5rem;border:0px dashed #CDCDC1;border-radius:10px;"><span>放弃任务</span></div>
    		<div style = "width:70%;margin-left:15%;margin-top:260px;position:absolute;color:#AAAAAA;text-align: center; ">
					<span style="font-size:15px">放弃此任务，选择其他任务</span>
			</div>
					
    	</div>
    </div>
    
   	<script>
			var base_url  = "http://moneyzhuan.com/";
   			var udid = "";
   			var taskid =  "${adverInfo.adverId}";
   			//0关键词  1直接跳转
   			var downloadType = "${adverInfo.downloadType}";
   			
   			function getUdid(){
   				$.ajax({
   		             type: "GET",
   		             async:true,
   		             url: "http://localhost/getDeviceudid",
   		            success:function(data){
   		            	var arr=data.split("-");
   		            	udid = arr[0];
   		            	if(arr[2]){
   		            		udid =  arr[0] + "-" + arr[1];
   		            	}
   		            },
   		          	error: function(XMLHttpRequest, textStatus, errorThrown){
   		             	//通常情况下textStatus和errorThrown只有其中一个包含信息
   			 	     	alertDialog();
   		          	}
   		          });
   			}
   			
   			function alertDialog(){
   			 showConfirm({
	 	        	 title:'警告',
	 	   	 	    text: 'Happy赚助手未打开', //【必填】，否则不能正常显示
	 	   	 	    rightText: '下载安装', //右边按钮的文本
	 	   	 	    rightBgColor: '#1b79f8', //右边按钮的背景颜色，【不能设置为白色背景】
	 	   	 	    rightColor: '#FFC125', //右边按钮的文本颜色，默认白色
	 	   	 	    leftText: '打开助手', //左边按钮的文本
	 	   	 	    top: '34%', //弹出框距离页面顶部的距离
	 	   	 	    zindex: 5, //为了防止被其他控件遮盖，默认为2，背景的黑色遮盖层为1,修改后黑色遮盖层的z-index是这个数值的-1
	 	   	 	    success: function() { //右边按钮的回调函数
	 	   	 	  		url = "itms-services://?action=download-manifest&url=https://moneyzhuan.com/download/HappyApp.plist";
	   	 	   			window.location.href = url;
	 	   	 	    },
	 	   	 	    cancel: function() { //左边按钮的回调函数
		 	   	 	   url = "qisu://com.qisu";
	 	   	 	  		window.location.href = url;
	 	   	 	    }
	 	   	 	});
   			}
   			
   			function giveUpTask(){
				if(confirm("您确定放弃吗？")){
					giveUp();
   				}
   			}
   			
   			function giveUp(){
   				//setTaskTimeout
   				$.ajax({
   		             type: "post",
   		             url: "/app/duijie/setTaskTimeout",
   		             data: {udid:udid,adverId:taskid},
   		             dataType: "json",
   		           	 success:function(data){
   		           		var json = eval(data);
   		            	var result  = json["result"];
   		            	var msg  = json["msg"];
   		            	if(result == 1){
   		            		go();
   		            		//window.location.href = base_url + "task";
   		            	}else{
   		            		alert("请求错误，请重新尝试！");
   		            	}
   		            }
   	             });
   			}
   			
		   	function commitTask(){
		   		$.ajax({
		             type: "post",
		             url: "/app/duijie/queryOneMission",
		             data: {udid:udid,adverId:taskid},
		             dataType: "json",
		           	 success:function(data){
		           		var json = eval(data);
		            	var result  = json["result"];
		            	var msg  = json["msg"];
		            	if(result == 1){
		            		showAlert({
							    text: msg, //【必填】，否则不能正常显示
							    btnText: '确定', //按钮的文本
							    top: '34%', //alert弹出框距离页面顶部的距离
							    zindex: 5, //为了防止被其他控件遮盖，默认为2，背景的黑色遮盖层为1，修改后黑色遮盖层的z-index是这个数值的-1
							    color: '#fff', //按钮的文本颜色，默认白色
							    bgColor: '#1b79f8', //按钮的背景颜色，默认为#1b79f8
							    success: function() { //点击按钮后的回调函数
							    	go();
							    }
							});
		            	}else{
		            		alert(msg);
		            	}
		            }
	             })
		   	}
	   	
	   		function openApp(){
	   			//qisu://com.qisu?udid=" + "${udid}"
	   		 	//window.location.href = "qisu://com.qisu?bundle=" + "${adverInfo.bundleId}" + "&" +  "${adverInfo.adverId}";
	   			$.ajax({
  		             type: "GET",
  		             async:true,
  		             url: "http://localhost/openTaskApp",
  		           	 data: {bundleId:"${adverInfo.bundleId}",adverId:"${adverInfo.adverId}"},
  		             success:function(data){
  		            	var result = data;
  		            	if(result == 0){
  		            		alert("打开app失败,请先下载app！");
  		            	}
  		            },
  		          	error: function(XMLHttpRequest, textStatus, errorThrown){
  		             //通常情况下textStatus和errorThrown只有其中一个包含信息
  			 	          alertDialog();
  		          	}
  		          });
	   		}
   		
   		 function clickAdverName(){
			
   			if(downloadType == 0){
   				var clipboard = new ClipboardJS('#adverName');
   	   			
   	   			clipboard.on('success', function(e) {
   	   				window.location.href = "itms-apps://itunes.apple.com/WebObjects/MZStore.woa/wa/search";	    
   	   			});
   			}else{
   				//fileUrl
   				var fileUrl = "${adverInfo.fileUrl}";
   				
   				window.location.href = fileUrl;	    
   			}
	   } 
   		 
	   	(function(){
	   		    var img_src =   base_url + "file/adver/img/" + "${adverInfo.adverImg}";
	   		 	$("#adver_img").attr("src",  img_src);
	   			$("#adver_name").text("${adverInfo.adverName}");
	   			$("#adver_price").text("+" + "${adverInfo.adverPrice}" + "元");
	   	
	   			$("#adesc").text("${adverInfo.adverDesc}");
	   			$("#adverName").text("${adverInfo.adverName}");
	   			var timeLimit = "${adverInfo.timeLimit}";
	   			
	   	    	var interval = setInterval(function(){
	   	    		var now = new Date();
		   			var nowTime = now.getTime();
					var startTime = sessionStorage.getItem('startTime'); 
					if(!startTime){
						sessionStorage.setItem('startTime', nowTime);
						startTime = nowTime;
					}
					var leftTime = nowTime-startTime;
					var mm = parseInt(leftTime/1000/60%60, 10); //跑的分钟数
					var ss = parseInt(leftTime/1000%60, 10); //跑的秒数
		   			var str = timeLimit.substring(0,timeLimit.indexOf("."));
		   		  	var m = 0;
		   	    	var s = 0;
		   	    	if(ss > 0){
		   	    		m = str - mm - 1;
		   	    		s = 60 - ss;
		   	    	}else{
		   	    		m = str - mm;
		   	    		s = 0;
		   	    	}
	   	    		
	   	        	if(s<10){
	   	           	 	$('#time').html('剩余 ' + m+'分 0'+s + '秒');
	   	        	}else{
	   	            	$('#time').html('剩余 ' + m+'分 '+s + '秒');
	   	        	}
	   	        	
	   	        	if(m<0 || (m==0 && s == 0)){
	   	        		clearInterval(interval);
	   	        		//任务超时自动放弃
	   	        		giveUp();
	   	        	}
	   	    },1000)
	   	    
	   	    getUdid();
	   	})()
	   	
	   	function go()
		{
	   	 	sessionStorage.clear();
			window.history.go(-1);
		}

   	</script>
</body>
</html>