<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <script type="text/javascript" charset="utf-8" src="../js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="../js/px2rem.js"></script>
	<style>
	.title{
   	 		.flex; width:100%; height: 0.8rem; background:#fff; box-shadow: 0 3px 5px #e0e0e0;text-align: center;position:fixed;z-index:100
		}
	</style>
</head>

<body style="background:#F0F0F0; margin:0px">
	 <div class="title">
	 <span onclick="go()" style="line-height:0.8rem;color:Blue;font-size:0.6rem;float:left;margin-left:15px;margin-bottom:10px"><</span>
            <span style=".flex1; line-height:0.8rem; font-weight: bold;text-align: center; color: #4a4a4a;width:100%; font-size: 0.4rem;">帮助中心</span>
    </div>
    
    <div id="container" style="padding-top:0.9rem;position:relative;width:100%;">
    	<div style="background:#fff; margin-top:5px;height:600px"></div>
    </div>
    <script>
	function go()
	{
		window.history.go(-1);
	}
    </script>
</body>
</html>