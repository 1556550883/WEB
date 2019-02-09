<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <script type="text/javascript" charset="utf-8" src="../js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="../js/px2rem.js"></script>
	<script type="text/javascript" charset="utf-8" src="../js/clipboard.min.js"></script>
		<script type="text/javascript" charset="utf-8" src="../js/jquery.qrcode.min.js"></script>
	<style>
	.title{
   	 		.flex; width:100%; height: 0.8rem; background:#fff; box-shadow: 0 3px 5px #e0e0e0;text-align: center;position:fixed;z-index:100
		}
	</style>
</head>

<body style="background:#F0F0F0; margin:0px;font-size:15px;">
     <div onclick="go()" class="title">
	 		<img  style="height:0.4rem;float:left;margin-left:10px;margin-top:10px;" src="../img/h5web/back-icon.png"/>
            <span style=".flex1; line-height:0.8rem; font-weight: bold; color: #4a4a4a; font-size: 0.4rem;margin:auto;position: absolute;top: 0;  left: 0;right: 0;bottom: 0">收徒赚钱</span>
    </div>
      <div id="container" style="padding-top:0.9rem;position:relative;width:100%;">
    	<div style="background:#fff; margin-top:5px;height:700px">
    		<div style="background:#F0F0F0;width:40%;float:right;margin-right:30px;margin-top:30px;height:100px;text-align: center;">
    			<div style="margin-top:30px;font-size:10px;">累积收益</div>
    			<div id="invite_score" style="margin-top:5px">0.0元</div>
    		</div>
    		
    		<div style="background:#F0F0F0;width:40%;margin-left:30px;margin-top:30px;position:absolute;height:100px;text-align: center;">
    			<div style="margin-top:30px;font-size:10px;">徒弟人数</div> 
    			<div id="invite_num" style="margin-top:5px">0人</div>
    		</div>
    		
    		<div style = "width:96%;margin-left:2%;margin-top:141px;position:absolute;color:#8B8682">
			    <ul style="font-size:15px;">
				  <li>徒弟做满5个任务，一次性奖励师傅5元</li>
				  <li>徒弟每次完成任务，奖励师傅0.5元</li>
				  <li>累计15元封顶</li>
				  <li>有效收徒满10人，一次性奖励10元，上不封顶</li>
				</ul>
	    	</div>
	    	<div style="position:absolute;color:#8B8682;margin-top:290px;font-size:20px;margin-left:30px;">
	    		邀请方式:
	    	</div>
	    	
	    	<div  onclick="invitelink()" id="invitelink" 
   				style="position:absolute; -webkit-user-select:initial;text-align: center;margin-left:5%;width:90%;margin-top:340px;color:#0000EE; font-size: 15px;border:1px dashed #CDCDC1;border-radius:10px;"
   				 data-clipboard-action="copy" data-clipboard-target="#invitelink">https://moneyzhuan.com/invite/guest?id=544606
			</div>
			<div style = "width:70%;margin-left:15%;margin-top:400px;position:absolute;color:#AAAAAA;text-align: center; ">
					<span style="font-size:15px">您的专属邀请链接，点击复制，发送给朋友即可得分!</span></div>
					
			<div id="qrcode" style = "margin-top:460px;position:absolute;text-align: center;width:100%">
			</div>
			
				<div style = "width:70%;margin-left:15%;margin-top:620px;position:absolute;color:#AAAAAA;text-align: center; ">
					<span style="font-size:15px">此二维码给朋友扫，即可成为您的徒弟</span></div>
    	</div>
    	
    </div>
	<script type="text/javascript">
		var link = "https://moneyzhuan.com/invite/guest?id=" + "${appuserid}";
		$("#invitelink").text(link);
		$('#qrcode').qrcode({width: 140,height: 140,text: link});
		$("#invite_score").text("${appuserscore.apprenticeScore}" + "元");
		$("#invite_num").text("${appuserscore.apprenticeCount}" + "人");
		function invitelink(){
			
  			var clipboard = new ClipboardJS('#invitelink');
  			
  			clipboard.on('success', function(e) {
  				alert("成功复制！");    
  			});
		}
		
	   	function go()
		{
			window.history.go(-1);
		}
	</script>
</body>
</html>