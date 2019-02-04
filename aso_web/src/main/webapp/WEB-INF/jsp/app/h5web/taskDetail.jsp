<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<script type="text/javascript" charset="utf-8" src="../js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="../js/px2rem.js"></script>
	
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
	 <div class="title">
	 		<span onclick="go()" style="line-height:0.8rem;color:Blue;font-size:0.6rem;float:left;margin-left:15px;margin-bottom:10px"><</span>
          <span style=".flex1; line-height:0.8rem; font-weight: bold;text-align: center; color: #4a4a4a;width:100%; font-size: 0.4rem;">任务详情</span>
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
    	 	<div onclick="openApp()" style="position:absolute;background:#0000EE; text-align: center;margin-left:2%;width:96%;margin-top:10px;color:#FFFFFF; font-size: 0.5rem;border:1px dashed #CDCDC1;border-radius:10px;"><span>打开应用</span></div>
    		<div style = "width:70%;margin-left:15%;margin-top:60px;position:absolute;color:#AAAAAA;text-align: center; ">
					<span style="font-size:15px">下载成功后在此打开应用试玩</span></div>
					
			<div onclick="commitTask()" style="position:absolute;background:#F08080; text-align: center;margin-left:2%;width:96%;margin-top:100px;color:#FFFFFF; font-size: 0.5rem;border:1px dashed #CDCDC1;border-radius:10px;"><span>提交任务</span></div>
    		<div style = "width:70%;margin-left:15%;margin-top:150px;position:absolute;color:#AAAAAA;text-align: center; ">
					<span style="font-size:15px">下载成功后在此提交任务</span>
			</div>
			
			<div onclick="giveUpTask()" style="position:absolute;background:#AAAAAA; text-align: center;margin-left:2%;width:96%;margin-top:190px;color:#FFFFFF; font-size: 0.5rem;border:1px dashed #CDCDC1;border-radius:10px;"><span>放弃任务</span></div>
    		<div style = "width:70%;margin-left:15%;margin-top:240px;position:absolute;color:#AAAAAA;text-align: center; ">
					<span style="font-size:15px">放弃此任务，选择其他任务</span>
			</div>
					
    	</div>
    </div>
    
   	<script>
			var base_url  = "https://moneyzhuan.com/";
   			var udid = localStorage.getItem("happyzhuan_user_udid");
   			var taskid =  "${adverInfo.adverId}";
   			function giveUpTask(){
   				//setTaskTimeout
   				if(confirm("您确定放弃吗？")){
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
   		            		window.location.href = base_url + "task";
   		            	}else{
   		            		alert("请求错误，请重新尝试！");
   		            	}
   		            }
   	             })
   				}
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
		            		window.location.href = base_url + "task";
		            	}
		            	alert(msg);
		            }
	             })
		   	}
	   	
	   		function openApp(){
	   			//qisu://com.qisu?udid=" + "${udid}"
	   		 	window.location.href = "qisu://com.qisu?bundle=" + "${adverInfo.bundleId}" + "&" +  "${adverInfo.adverId}";
	   		}
   		
   		 function clickAdverName(){
   		
   			var clipboard = new ClipboardJS('#adverName');
   			
   			clipboard.on('success', function(e) {
   				window.location.href = "itms-apps://itunes.apple.com/WebObjects/MZStore.woa/wa/search";	    
   			});
	   } 
   		 
	   	(function(){
	   		    var img_src =   base_url + "file/adver/img/" + "${adverInfo.adverImg}";
	   		 	$("#adver_img").attr("src",  img_src);
	   			$("#adver_name").text("${adverInfo.adverName}");
	   			$("#adver_price").text("+" + "${adverInfo.adverPrice}" + "元");
	   	
	   			$("#adesc").text("${adverInfo.adverDesc}");
	   			$("#adverName").text("${adverInfo.adverName}");
	   			var timeLimit = ("${adverInfo.timeLimit}");
	   		
	   			var str = timeLimit.substring(0,timeLimit.indexOf("."));
	   		  	var m = str;
	   	    	var s = 0;
	   	    	var interval = setInterval(function(){
	   	        	if(s<10){
	   	           	 	$('#time').html('剩余 ' + m+'分 0'+s + '秒');
	   	        	}else{
	   	            	$('#time').html('剩余 ' + m+'分 '+s + '秒');
	   	        	}
	   	       	 	s--;
	   	        	if(s<0){
	   	           		s=59;
	   	            	m--;
	   	        	}
	   	        	
	   	        	if(m==0 && s == -1){
	   	        		clearInterval(interval)
	   	        		go()
	   	        	}
	   	    },1000)
	   	})()
	   	
	   	
	   	function go()
		{
			window.history.go(-1);
		}

   	</script>
</body>
</html>