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
	<script type="text/javascript" charset="utf-8" src="../js/nativeShare.js"></script>
	<script type="text/javascript" charset="utf-8" src="../js/showText.js"></script>
	
	<style>
	.title{
   	 		.flex; width:100%; height: 0.8rem;text-align: center; background:#fff; box-shadow:0px 2px 6px 0px rgba(23, 176, 81, 0.35);text-align:text-align: center;position:fixed;z-index:100
		}
		
	.tip_content {
		   position: absolute;
		    width: 90%;
		   	height: 70%;
			top: 25%;
		    left: 5%;
		    background: rgba(255, 255, 255, 0.6);
		    border-radius:10px;
		    z-index: 1002;
		}
		
	</style>
</head>

<body style="background:#F0F0F0; margin:0px;font-size:15px;">
    <div onclick="go()" class="title">
	 		<img style="height:0.4rem;float:left;margin-left:10px;margin-top:10px;" src="../img/h5web/back-icon.png"/>
            <span style=".flex1; line-height:0.8rem; font-weight: bold; color: #4a4a4a; font-size: 0.4rem;margin:auto;position: absolute;top: 0;  left: 0;right: 0;bottom: 0">收徒赚钱</span>
    </div>
    <div style="text-align: center; padding-top:50px;font-size:17px;color:#8B8682;">①:邀请好友    ②:好友试玩    ③:奖励到账</div>
   
   <div style="background-color:#8CA3E9;width:100%;height:100px;margin-top:15px;text-align: center;">
   		<div style="float:left;margin-top:20px;margin-left:30px; font-family:微软雅黑;width:90px;color:#fff">
	 		<div style="font-size:15px;"><span>累计收益</span></div>
	 		<div style="font-size:20px;margin-top:10px"><span id="invite_score"></span></div>
	 	</div>
	 	<div style="background-color:#D1D1D1;height:50px;width:1px;float:left;margin-left:10px;margin-top:25px"></div>
	 	<div  onclick="inviteUserDetail()" style="float:left;margin-top:20px;margin-left:10px; font-family:微软雅黑;width:90px;color:#fff">
	 		<div style="font-size:15px;"><span>邀请人数</span></div>
	 		<div style="font-size:20px;margin-top:10px"><span id="invite_num"></span></div>
	 	</div>
	 	<div style="background-color:#D1D1D1;height:50px;width:1px;float:left;margin-left:10px;margin-top:25px"></div>
	 	<div onclick="inviteEffUserDetail()" style="float:left;margin-top:20px;margin-left:10px; font-family:微软雅黑;width:90px;color:#fff">
	 		<div style="font-size:15px;"><span>有效人数</span></div>
	 		<div style="font-size:20px;margin-top:10px"><span id="invite_eff_user"></span></div>
	 	</div>
   </div>
   
   	<div style="background:#fff; margin-top:20px;height:215px;width:94%;margin-left:3%;border-radius:8px;">
   		<div style="display:inline-block;margin-top:10px;width:100%;margin-left:20px;line-height:30px;font-family:MicrosoftYaHei;font-weight:400">
   			<div style="float:left;background-image:url(../img/h5web/invite_1.png); background-size:100% 100%;; 
				background-repeat: no-repeat;width:22px;height:22px;margin-top:4px"></div>
   			<div style="float:left;margin-left:10px;font-size:20px;">规则说明</div>
   		</div>
   		<div style="background-color:#D1D1D1;height:1px;width:94%;margin-left:3%;margin-top:10px"></div>
   		<div style="margin-top:10px">
   			<ul style="font-size:15px;color:#8B8682">
				  <li>徒弟每次完成任务,奖励师傅0.5元</li>
				  <li>徒弟做满5个任务,一次性奖励师傅5元</li>
				  <li>累计15元封顶(不包含一次性奖励5元)</li>
				  <li>有效收徒满20人,一次性奖励20元,上不封顶</li>
				  <li style="color:red">羊毛党恶意收徒将不参与此活动</li>
				  <li style="color:red">有效徒弟必须完成一个任务</li>
   			</ul>
   		</div>
   	</div>
   	
	<div style="background:#fff; margin-top:10px;height:300px;width:94%;margin-left:3%;border-radius:8px;">
		<div style="display:inline-block;margin-top:10px;width:100%;margin-left:20px;line-height:30px;font-family:MicrosoftYaHei;font-weight:400">
	   			<div style="float:left;background-image:url(../img/h5web/invite_2.png); background-size:100% 100%;; 
					background-repeat: no-repeat;width:22px;height:22px;margin-top:4px"></div>
	   			<div style="float:left;margin-left:10px;font-size:20px;">邀请方式</div>
	   	</div>
	   	
	   	<div onclick="wechatShare(0)" style="float:left;margin-top:30px;margin-left:30px; font-family:微软雅黑;width:90px;text-align: center">
	   	 	<div style="background-image:url(../img/h5web/invite_3.png); background-size:100% 100%;; 
					background-repeat: no-repeat;width:50px;height:50px;margin-left:20px">
	   		</div>
	   		<div style="margin-top:10px">朋友圈</div>
	   	</div>
	   	
	   	
	   	<div onclick="wechatShare(1)" style="float:left;margin-top:30px;margin-left:10px; font-family:微软雅黑;width:90px;text-align: center">
	   	 	<div style="background-image:url(../img/h5web/invite_4.png); background-size:100% 100%;; 
					background-repeat: no-repeat;width:50px;height:50px;margin-left:20px">
	   		</div>
	   		<div style="margin-top:10px">微信好友</div>
	   	</div>
	   	
	   	<div onclick="call('qqFriend')"  style="float:left;margin-top:30px;margin-left:10px; font-family:微软雅黑;width:90px;text-align: center">
	   	 	<div style="background-image:url(../img/h5web/invite_5.png); background-size:100% 100%;; 
					background-repeat: no-repeat;width:50px;height:50px;margin-left:20px">
	   		</div>
	   		<div style="margin-top:10px">QQ好友</div>
	   	</div>
	   	
	   	<div onclick="call('qZone')" style="float:left;margin-top:30px;margin-left:30px; font-family:微软雅黑;width:90px;text-align: center">
	   	 	<div style="background-image:url(../img/h5web/invite_6.png); background-size:100% 100%;; 
					background-repeat: no-repeat;width:50px;height:50px;margin-left:20px">
	   		</div>
	   		<div style="margin-top:10px">QQ空间</div>
	   	</div>
	   	
	   	 	<div onclick="linkAlert()" style="float:left;margin-top:30px;margin-left:10px; font-family:微软雅黑;width:90px;text-align: center">
	   	 	<div style="background-image:url(../img/h5web/invite_7.png); background-size:100% 100%;
					background-repeat: no-repeat;width:50px;height:50px;margin-left:20px">
	   		</div>
	   		<div style="margin-top:10px">链接邀请</div>
	   	</div>
	   	
	   		<div style="float:left;margin-top:30px;margin-left:10px; font-family:微软雅黑;width:90px;text-align: center">
	   	 	<div style="background-image:url(../img/h5web/invite_8.png); background-size:100% 100%;; 
					background-repeat: no-repeat;width:50px;height:50px;margin-left:20px">
	   		</div>
	   		<div style="margin-top:10px">二维码</div>
	   		<textarea id="invitelink"  onclick="invitelink()" style="text-align: center;width:100px;margin-left:-90px;margin-top:-60px;position: absolute;z-index:-20" data-clipboard-action="copy" data-clipboard-target="#invitelink" rows="3" cols="20">
				https://moneyzhuan.com/invite/guest
			</textarea>
	   	</div>
	</div>
	
	<div id="safariTip" class="tip_content">
	
		<div style="text-align: center; cursor: default; margin-top:10px;margin-bottom:10px;">
			<span style="font-size: 16px; font-weight: 600;">您的专属二维码</span>
		</div>
		
		<div id="qrcode" style="text-align: center;margin-top:50px;"></div>
		
		<div style="text-align: center; margin-top:50px;">
			<span onclick="saveQrcode()" style="font-size: 22px;padding:3px 50px; font-weight: 600;background-color:#8CA3E9; color:#fff; border-radius:10px;">点击保存</span>
		</div>
	</div>
	
	<script type="text/javascript">
		var link = "https://moneyzhuan.com/invite/guest?id=" + "${appuserid}";
		$("#invitelink").text(link);
		$("#qrcode").qrcode({width: 180,height: 180,text: link});
		$("#invite_score").text("${appuserscore.apprenticeScore}" + "元");
		$("#invite_num").text("${appuserscore.apprenticeCount}" + "人");
		$("#invite_eff_user").text("${appuserscore.effectiveUserCount}" + "人");
		
		
		var clipboard = new ClipboardJS('#invitelink');
		
		function linkAlert(){
			showAlert({
    		    text: link, //【必填】，否则不能正常显示
    		    btnText: '点击复制', //按钮的文本
    		    top: '34%', //alert弹出框距离页面顶部的距离
    		    zindex: 5, //为了防止被其他控件遮盖，默认为2，背景的黑色遮盖层为1，修改后黑色遮盖层的z-index是这个数值的-1
    		    color: '#fff', //按钮的文本颜色，默认白色
    		    bgColor: '#1b79f8', //按钮的背景颜色，默认为#1b79f8
    		    success: function() { //点击按钮后的回调函数
    		    	$("#invitelink").trigger("click");
    		    }
    		});
    	}
		
		function invitelink(){
  			clipboard.on('success', function(e) {
  				alert("成功复制！");
  				 clipboard.destroy();
                 clipboard = new ClipboardJS('#invitelink');  
  			});
  		  
  			clipboard.on('error', function(e) {
              alert('复制失败,请手动复制');
              clipboard.destroy();
              clipboard = new ClipboardJS('#invitelink');  
          });
		}
		
		var base_url  = "http://moneyzhuan.com/";
 		function inviteEffUserDetail(){
	         window.location.href = base_url + "inviteEffUserDetail?id=" + "${appuserid}";
 		}
		
 		function saveQrcode(){
 			var canvas = $('#qrcode').find("canvas").get(0);
	        var blob = canvas.msToBlob();
	        navigator.msSaveBlob(blob, 'qrcode.jpg');
 		}
 		
 		function inviteUserDetail(){
	         window.location.href = base_url + "inviteUserDetail?id=" + "${appuserid}";
		}
 		
	   	function go()
		{
			window.history.go(-1);
		}
	   	
	   	function wechatShare(value){
	   		var shaerturl = "";
	   		if(value == 0){
	   			shaerturl = "qisu://com.qisu?isShareQZone=" + "${appuserid}";
	   		}else{
	   			shaerturl =  "qisu://com.qisu?isShareFriend=" + "${appuserid}";
	   		}
	   		
	 	 	window.location.href = shaerturl;
	   	}
	   	
	    var nativeShare = new NativeShare();
	    var shareData = {
	        title: 'Happy赚',
	        desc: '专注于赚钱的平台',
	        // 如果是微信该link的域名必须要在微信后台配置的安全域名之内的。
	        link: link,
	        icon: 'http://moneyzhuan.com/img/h5web/happy_logo.png',
	        // 不要过于依赖以下两个回调，很多浏览器是不支持的
	        success: function() {
	            console.log("success")
	        },
	        fail: function() {
	            console.log("fail");
	        }
	    }
	    
	    nativeShare.setShareData(shareData)
	    
	    function call(command) {
	        try {
	            nativeShare.call(command)
	        } catch (err) {
	            // 如果不支持，你可以在这里做降级处理
	        }
	    }
	</script>
</body>
</html>