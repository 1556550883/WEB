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
		
	.qrcode_content {
		   position: absolute;
		   display:none;	
		    width: 90%;
		   	height: 60%;
			top: 45%;
		    left: 5%;
		    text-align: center;
		    -webkit-touch-callout:default;
		    background: #fff;
		    border-radius:10px;
		    
		    z-index: 1002;
		}
		
		.black_overlay{
				position: absolute;
				top: 0%;
				left: 0%;
				width: 100%;
				display:none;
				height: 750px;
				background-color: black;
				z-index:1001;
				-moz-opacity: 0.8;
				opacity:.50;
				filter: alpha(opacity=50);
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
				  <li>累计10元封顶</li>
				  <li style="color:red">羊毛党恶意收徒将不参与此活动</li>
				  <li style="color:red">有效徒弟必须完成一个任务</li>
   			</ul>
   		</div>
   	</div>
   	
	<div style="background:#fff; margin-top:10px;height:300px;width:94%;margin-left:3%;border-radius:8px;">
		<div style="display:inline-block;margin-top:10px;width:100%;margin-left:20px;line-height:30px;font-family:MicrosoftYaHei;font-weight:400">
	   			<div style="float:left;background-image:url(../img/h5web/invite_2.png); background-size:100% 100%;
					background-repeat: no-repeat;width:22px;height:22px;margin-top:4px"></div>
	   			<div style="float:left;margin-left:10px;font-size:20px;">邀请方式</div>
	   	</div>
	   	
	   	<div onclick="wechatShare(0)" style="float:left;margin-top:30px;margin-left:30px; font-family:微软雅黑;width:90px;text-align: center">
	   	 	<div style="background-image:url(../img/h5web/invite_3.png); background-size:100% 100%;
					background-repeat: no-repeat;width:50px;height:50px;margin-left:20px">
	   		</div>
	   		<div style="margin-top:10px">朋友圈</div>
	   	</div>
	   	
	   	
	   	<div onclick="wechatShare(1)" style="float:left;margin-top:30px;margin-left:10px; font-family:微软雅黑;width:90px;text-align: center">
	   	 	<div style="background-image:url(../img/h5web/invite_4.png); background-size:100% 100%;
					background-repeat: no-repeat;width:50px;height:50px;margin-left:20px">
	   		</div>
	   		<div style="margin-top:10px">微信好友</div>
	   	</div>
	   	
	   	<div onclick="call('qqFriend')"  style="float:left;margin-top:30px;margin-left:10px; font-family:微软雅黑;width:90px;text-align: center">
	   	 	<div style="background-image:url(../img/h5web/invite_5.png); background-size:100% 100%;
					background-repeat: no-repeat;width:50px;height:50px;margin-left:20px">
	   		</div>
	   		<div style="margin-top:10px">QQ好友</div>
	   	</div>
	   	
	   	<div onclick="call('qZone')" style="float:left;margin-top:30px;margin-left:30px; font-family:微软雅黑;width:90px;text-align: center">
	   	 	<div style="background-image:url(../img/h5web/invite_6.png); background-size:100% 100%; 
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
	   	
	   <div onclick="showQrcode()" style="float:left;margin-top:30px;margin-left:10px; font-family:微软雅黑;width:90px;text-align: center">
	   	 	<div style="background-image:url(../img/h5web/invite_8.png); background-size:100% 100%;
					background-repeat: no-repeat;width:50px;height:50px;margin-left:20px">
	   		</div>
	   		
	   		<div style="margin-top:10px">二维码</div>
	   	</div>
	   	
   		<textarea id="invitelink"  onclick="invitelink()" style="text-align: center;width:100px;margin-left:-80px;margin-top:-40px;position: absolute;z-index:-20" data-clipboard-action="copy" data-clipboard-target="#invitelink" rows="3" cols="20">
				https://moneyzhuan.com/invite/guest
		</textarea>
	</div>
	
	<div id="fade" class="black_overlay"></div>
	<div  id="qrcodeContent" class="qrcode_content">
	
		<div  onclick="hideQrcode()" style="font-size: 16px; font-weight: 600; cursor: default; margin-top:10px;margin-bottom:10px;">
				<span style="position: absolute;left: 0;right: 0;">您的专属二维码</span>
				<img  style = "width:25px;height:25px;margin-right:10px;float:right" alt="" src="../img/h5web/close.png"></img>
	    </div>
		<div style = "color:red;width:100%;position: absolute;left: 0;right: 0;margin-top:30px">（长按二维码进行保存）</div>
		<div id="qrcodeCanvas" style="text-align: center;position: absolute;left: 0;right: 0;margin-top:75px;"></div>
		
	</div>
	
	<script type="text/javascript">
		var link = "https://moneyzhuan.com/invite/guest?id=" + "${appuserid}";
		$("#invitelink").text(link);
		$("#invite_score").text("${appuserscore.apprenticeScore}" + "元");
		$("#invite_num").text("${appuserscore.apprenticeCount}" + "人");
		$("#invite_eff_user").text("${appuserscore.effectiveUserCount}" + "人");
		
        $('#qrcodeCanvas').qrcode({
                    render    : "canvas",
                    text    : link,
                    width : "200",               //二维码的宽度
                    height : "200",              //二维码的高度
                    background : "#ffffff",       //二维码的后景色
                    foreground : "#000000",        //二维码的前景色
                    src: '../img/h5web/happy_logo.png'             //二维码中间的图片
                });

        //获取网页中的canvas对象
        var mycanvas1=document.getElementsByTagName('canvas')[0];
        mycanvas1.style.display = 'none'//隐藏生成的canvas
        //将转换后的img标签插入到html中
        var img=convertCanvasToImage(mycanvas1);
 
        $('#qrcodeCanvas').append(img);//imagQrDiv表示你要插入的容器id
 
        //从 canvas 提取图片 image
        function convertCanvasToImage(canvas) {
            //新Image对象，可以理解为DOM
            var image = new Image();
            // canvas.toDataURL 返回的是一串Base64编码的URL，当然,浏览器自己肯定支持
            // 指定格式 PNG
            image.src = canvas.toDataURL("image/png");
            return image;
        }
        
        function showQrcode(){
        	$("#qrcodeContent").attr("style","display:block;");
        	$("#fade").attr("style","display:block;");
		}
		
        function hideQrcode(){
        	$("#qrcodeContent").attr("style","display:none;");
        	$("#fade").attr("style","display:none;");
		}
        
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