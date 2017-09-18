<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset=utf-8"UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<script src="../../dwz/js/mui.min.js"></script>
		<link rel="stylesheet" href="../../dwz/themes/css/mui.min.css">
		<script type="text/javascript" src="../../dwz/js/jquery-1.7.1.min.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<title>首页</title>
</head>
<style>
body{ background:url(../../img/index/bj2.png) #e60000 no-repeat; background-size:cover}
*{padding:0px;margin:0px; list-style:none}
body{ font-family:微软雅黑; font-size:0.8em;color:#fff;-webkit-text-size-adjust:none;}
h1,h2,h3,h4,h5,h6,strong,b{ font-weight:normal}
a{ text-decoration:none;color:#fffabf}

.con1{ text-align:center;margin-top:28%}
.con1 h3{font-size:1.3em;}
.con1 .con1_key{width:50px;height:50px;margin:1em auto}
.con1 p{ font-size:1.15em;color:#fffabf}
.con2{ text-align:center;color:#fffabf;margin-top:1.8em}
.con2 li{margin:.6em 0; font-size:1.15em}
.con2:a{color:#fffabf}
.con3 a{ text-decoration:underline; font-size:1.15em; position:absolute;bottom:2em;left:50%;margin-left:-2em;z-index:99; }
.con3 a:active{color:#fffabf}

.set_show{ background:rgba(0,0,0,0.4);width:100%;height:100%; position:fixed;top:0px;z-index:999; display:none}
.mui-slider{width:90%;margin:0 5%; position:absolute;top:60px;z-index:9999;display:none; overflow:hidden}
.close{ position:absolute;top:10px;right:10px;z-index:999}
.mui-slider-indicator .mui-active.mui-indicator{ background:#F00}
.mui-slider-indicator .mui-indicator{width:10px;height:10px;background:#fff;border:1px solid #f00; box-shadow:none;-webkit-box-shadow:none}
.mui-slider .mui-slider-group .mui-slider-item{ margin:0 2px}

</style>
<body>
<section>
	<article>
    	<div class="set_show"></div>
       	<div id="slider" class="mui-slider" >
			<div class="mui-slider-group mui-slider-loop">
				<!-- 额外增加的一个节点(循环轮播：第一个节点是最后一张轮播) -->
				<div class="mui-slider-item mui-slider-item-duplicate">
					<a href="#">
						<img class="change_img" src="../../img/index/4.png">
					</a>
				</div>
				<!-- 第一张 -->
				<div class="mui-slider-item">
					<a href="#">
						<img src="../../img/index/1.png">
					</a>
				</div>
				<!-- 第二张 -->
				<div class="mui-slider-item">
					<a href="#">
						<img src="../../img/index/2.png">
					</a>
				</div>
				<!-- 第三张 -->
				<div class="mui-slider-item">
					<a href="#">
						<img src="../../img/index/3.png">
					</a>
				</div>
				<!-- 第四张 -->
				<div class="mui-slider-item">
					<a href="prefs:root=General&path=ManagedConfigurationList">
						<img src="../../img/index/4.png">
					</a>
				</div>
				<!-- 额外增加的一个节点(循环轮播：最后一个节点是第一张轮播) -->
				<div class="mui-slider-item mui-slider-item-duplicate">
					<a href="#">
						<img src="../../img/index/1.png">
					</a>
				</div>
			</div>
			<div class="mui-slider-indicator">
				<div class="mui-indicator mui-active"></div>
				<div class="mui-indicator"></div>
				<div class="mui-indicator"></div>
				<div class="mui-indicator"></div>
			</div>
            <div class="close" id="turnOff"><img src='../../img/index/close.png' width="15"/></div>
		</div>
    
    </article>
	<article class="con1">
        <h3>您激活的助手是</h3>
        <div class="con1_key"><img src="../../img/index/key.png" width="50"/></div>
        <p>抢钱钥匙</p>
    </article>
    <article class="con2">
    	<ul>

        	<li><a href="<c:if test='${systemType==2}'>http://fir.im/2xab</c:if>"><img src="../../img/index/btn2.png" width="120"/></a></li>
        	<div id="myDiv">
            <li><img src="../../img/index/arrow.png" width="12"/></li>
            <li id="set_describe"><a><img src="../../img/index/btn3.png" width="120"/></a></li>
            <li><img src="../../img/index/arrow.png" width="12"/></li>

            <li><a href="prefs:root=General&path=ManagedConfigurationList">去桌面打开</a></li>
            </div>
        </ul>
    </article>
    <article class="con3">
    	<a href="#">遇到问题</a>
    </article>
    
</section>

<script type="text/javascript" charset="utf-8">

var systemType='${systemType}';
	$(function(){
if(systemType==1){
	document.getElementById("myDiv").style.display="none";
}else if(systemType==2){
	document.getElementById("myDiv").style.display="";
}else{
}
			$('#set_describe').click(function(){
				$('.set_show').show();
				$('.mui-slider').show();
				$('.change_img').attr('src','../../img/index/1.png')
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