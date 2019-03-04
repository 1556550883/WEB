<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <meta name="format-detection" content="telephone=no" />
  <script type="text/javascript" charset="utf-8" src="../js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="../js/px2rem.js"></script>
	<style>
	.title{
   	 		.flex; width:100%; height: 0.8rem; background:#fff; box-shadow: 0 3px 5px #e0e0e0;text-align: center;position:fixed;z-index:100
		}
	</style>
</head>

<body style="background:#F0F0F0; margin:0px">
       <div  onclick="go()" class="title">
	 		<img style="height:0.4rem;float:left;margin-left:10px;margin-top:10px;" src="../img/h5web/back-icon.png"/>
            <span style=".flex1; line-height:0.8rem; font-weight: bold; color: #4a4a4a; font-size: 0.4rem;margin:auto;position: absolute;top: 0;  left: 0;right: 0;bottom: 0">联系官方</span>
    </div>
    
    <div id="container" style="padding-top:0.9rem;position:relative;width:100%;font-size:20px;">
    	<div style="background:#fff; margin-top:5px;height:600px;text-align:center;">
    		<img style="width:100px;height:100px;border-radius:5px; margin-top:15px" src="../img/h5web/qisu_logo.jpg"/>
    		<div style="font-size:15px;color:#AAAAAA;">
    			<span>微信公众号</span>
    		</div>
    		<div>
    			<span>qisuzz</span>
    		</div>
    		
    		<div style="margin-top:40px;font-size:15px;color:#AAAAAA;">
    			<span>联系方式</span>
    		</div>
    		<div>
    			<span>客服QQ：2126572197</span>
    		</div>
    		<div>
    			<span>QQ群：496011441</span>
    		</div>
    		
    		<div style="margin-top:40px;font-size:15px;color:#AAAAAA;">
    			<span>商务合作</span>
    		</div>
    		<div>
    			<span>QQ：738504610 </span>
    		</div>
    		<div>
    			<span>邮箱：asocpahezuo2016@163.com</span>
    		</div>
    	</div>
    </div>
    <script>
	function go()
	{
		window.history.go(-1);
	}
    </script>
</body>
</html>