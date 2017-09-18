<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<base href="<%=basePath%>"/>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link href="css/share/style.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="css/share/mui.min.css"/>
		<script type="text/javascript" src="js/jquery.min.js" ></script>
		<script type="text/javascript" src="js/mui.min.js" ></script>
		<script src="dwz/js/jquery-1.7.2.js" type="text/javascript" charset="utf-8"></script>
		<title>分享页</title>
	</head>
	<body style=" background: url(img/share/bg.png); background-size:cover; background-repeat: no-repeat;">
		<img id="logo" src="img/share/logo.png"/>
		<h3 class="slogan">${user.userNick }又在微赚巨星抢钱啦，<br>快来加入！</h3>
		<div class="share_box">
			<h2>邀请码：${user.userNum}</h2>
			<img src="img/share/ewm.png"/>
			<button id="btn_look">查看下载步骤</button>
			<button id="btn_download" onclick="window.location.href='http://fir.im/WZJX'">立即下载</button>
		</div>
		
		
		
    	<div class="set_show"></div>
       	<div id="slider" class="mui-slider" >
			<div class="mui-slider-group mui-slider-loop">
				<!-- 额外增加的一个节点(循环轮播：第一个节点是最后一张轮播) -->
				<div class="mui-slider-item mui-slider-item-duplicate">
					<a href="#">
						<img class="change_img" src="img/share/pic1.png">
					</a>
				</div>
				<!-- 第一张 -->
				<div class="mui-slider-item">
					<a href="#">
						<img src="img/share/pic1.png">
					</a>
				</div>
				<!-- 第二张 -->
				<div class="mui-slider-item">
					<a href="#">
						<img src="img/share/pic2.png">
					</a>
				</div>
				<!-- 第三张 -->
				<div class="mui-slider-item">
					<a href="#">
						<img src="img/share/pic3.png">
					</a>
				</div>
				<!-- 第四张 -->
				<div class="mui-slider-item">
					<a href="#">
						<img src="img/share/pic4.png">
					</a>
				</div>
				<!-- 额外增加的一个节点(循环轮播：最后一个节点是第一张轮播) -->
				<div class="mui-slider-item mui-slider-item-duplicate">
					<a href="#">
						<img src="img/share/pic1.png">
					</a>
				</div>
			</div>
			<div class="mui-slider-indicator">
				<div class="mui-indicator mui-active"></div>
				<div class="mui-indicator"></div>
				<div class="mui-indicator"></div>
				<div class="mui-indicator"></div>
			</div>
            <div class="close" id="turnOff"><img src='img/share/close.png' width="15"/></div>
		</div>
    
   
	<script type="text/javascript" charset="utf-8">
	$(function(){
			$('#btn_look').click(function(){
				$('.set_show').show();
				$('.mui-slider').show();
				$('.change_img').attr('src','img/share/pic1.png')
				})
			})
			$('.close').click(function(){
			$('.set_show').hide();
			$('.mui-slider').hide();
			})	
			$('.set_show').click(function(){
			$('.set_show').hide();
			$('.mui-slider').hide();
			})	
		
	</script>	
	</body>
</html>
