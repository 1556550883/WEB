<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <script type="text/javascript" charset="utf-8" src="../js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="../js/px2rem.js"></script>
		<script type="text/javascript" charset="utf-8" src="../js/c3listview.min.js"></script>
	<style>
	.title{
   	 		.flex; width:100%; height: 0.8rem; background:#fff; box-shadow: 0 3px 5px #e0e0e0;text-align: center;position:fixed;z-index:100
		}
	</style>
</head>

<body style="background:#F0F0F0; margin:0px;font-size:20px; font-family:微软雅黑;">
       <div onclick="go()"  class="title">
	 		<img  style="height:0.4rem;float:left;margin-left:10px;margin-top:10px;" src="../img/h5web/back-icon.png"/>
            <span style=".flex1; line-height:0.8rem; font-weight: bold; color: #4a4a4a; font-size: 0.4rem;margin:auto;position: absolute;top: 0;  left: 0;right: 0;bottom: 0">有效邀请</span>
    </div>
    
    <div id="container" style="padding-top:0.9rem;position:relative;margin-left:2%;width:96%;"></div>
	<script>
		(function(){
			var result = ${userEffAppList};
			var adapter = new C3ListView.adapter(result);
			adapter.getHtml = function(position){
				var data = result[position];
				var name = data.weixin +"  id="  + data.userAppId;
				var src = data.flag5;
				status = "已认证";
				if(data.weixin && data.zhifubao && data.phoneNum && data.isEffective == 0){
					status = "已认证";
				}
				else if(data.weixin && data.zhifubao && data.phoneNum){
					status = "error";
				}
				
			 return "<div onclick=\"taskdetail("+position+")\" style=\"font-size:25px;color:#444;background:#fff;border-radius:10px;margin-top:8px;height:60px\" data-position=\""+position+"\">"
         	 	    +"<img style=\"width:50px;height:50px;border-radius:10px;margin:5px;float:left\" src=\""
         	 	    + src + "\"/>"
         	 	    +"<span style=\"position:absolute;margin-left:10px;margin-top:10px;font-size:13px;width:200px\">"+name+"</span>"
         	 	 	+"<span style=\"position:absolute;margin-left:10px;margin-top:32px;font-size:10px;width:120px;color:#CDCDC1;border:1px solid #CDCDC1;border-radius:5px;text-align:center;\">"+data.createDate+"</span>"
         	 	    +"<span style=\"float:right;margin-top:20px;margin-right:20px;color:#FF0000;font-size:15px;\">"+ status +"</span>"
         	 	    +"</div>";
			}
			
			var ct = document.getElementById("container");
			var lsit  = new C3ListView.builder(ct, adapter);
		})()
		
	function go()
	{
		window.history.go(-1);
	}
	</script>
</body>
</html>