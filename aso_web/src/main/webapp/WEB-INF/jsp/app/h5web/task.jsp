<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <script type="text/javascript" charset="utf-8" src="../js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="../js/px2rem.js"></script>
	<script type="text/javascript" charset="utf-8" src="../js/c3listview.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="../js/showText.js"></script>
	<style>
	.title{
   	 		.flex; width:100%; height: 0.8rem; background:#fff; box-shadow: 0 3px 5px #e0e0e0;text-align: center;position:fixed;z-index:100
		}
	</style>
</head>

<body style="background:#F0F0F0; margin:0px;font-size:20px; font-family:微软雅黑">
    <div onclick="go()" class="title">
	 		<img style="height:0.4rem;float:left;margin-left:10px;margin-top:10px;" src="../img/h5web/back-icon.png"/>
            <span style=".flex1; line-height:0.8rem; font-weight: bold; color: #4a4a4a; font-size: 0.4rem;margin:auto;position: absolute;top: 0;  left: 0;right: 0;bottom: 0">试玩任务</span>
    		<div style="padding-top:0.8rem;font-size: 0.3rem;padding-left:20px;background:#fff;color:red;text-align: left">①:下载安装  ②:打开试玩  ③:奖励到账</div>
    </div>
  
   	<div id="container_01" style="padding-top:1.2rem;position:relative;margin-left:2%;width:96%;"></div>
   	
	<div style="text-align: center;margin-left:2%;width:96%;margin-top:10px;color: #9C9C9C; font-size: 0.4rem;border:1px dashed #CDCDC1;border-radius:5px">
		即将开始的任务
	</div>
	
	<div id="container_02" style="margin-left:2%;width:96%;margin-top:10px;"></div>
	
 	<script>
 		var result = "";
 		var base_url  = "http://moneyzhuan.com/";
 		var udid = "";
		
 		function taskdetail(position){
 			if(position == null && position != 0){
 				alert("任务还未开始，请稍后！")
 				return;
 			}
 			
 			var taskid = result[position].adverId;
 			var bundleId = result[position].bundleId;
 			$.ajax({
		             type: "post",
		             url: "/app/duijie/lingQuRenWu",
		             data: {udid:udid,adverId:taskid},
		             dataType: "json",
		           	 success:function(data){
		            	var json = eval(data);
		            	var result  = json["result"];
		            	var msg  = json["msg"];
		            	if(result == -1){
		            		alert(msg);
		            	}else{
		            		var id  = json["obj"].adverId;
		            		//判断返回的id是否null，如果有值就代表已经 领取了task
		            		//id是空的时候，代表还未接任务，这个时候 判断手机是否已经安装了app
		            		if (id) {
		            			taskid = id;
		            			window.location.href = base_url + "taskDetail?taskID=" + taskid;
		            		}else{
		            			//判断手机是否已经安装了app
		            			$.ajax({
		           	             type: "GET",
		           	             async:true,
		           	             url: "http://localhost/isAppInstalled",
		           	             data: {bundleId:bundleId},
		           	            success:function(data){
		           	            	var result = data;
		           	            	if(result == 0){
		           	            		//代表未安装，继续任务
		           	            		window.location.href = base_url + "taskDetail?taskID=" + taskid;
		           	            	}else{
		           	            		//任务作废，不能继续任务
		           	            		//设置任务超时
		           	            		giveUpTask(udid,taskid);
		           	            	}
		           	            },
		           	          	error: function(XMLHttpRequest, textStatus, errorThrown){
		           	             //通常情况下textStatus和errorThrown只有其中一个包含信息
		           	          		alertDialog();
		           	          	}
		           	          });
		            		}
		            	}
		            }
	             })
 		}
 		
 		function giveUpTask(udidvalue,taskidvalue){
				$.ajax({
		             type: "post",
		             url: "/app/duijie/setTaskTimeout",
		             data: {udid:udidvalue,adverId:taskidvalue},
		             dataType: "json",
		           	 success:function(data){
		           		var json = eval(data);
		            	var result  = json["result"];
		            	var msg  = json["msg"];
		            	if(result == 1){
		            		alert("请先删除已安装的app，重新尝试！")
		            	}else{
		            		alert("请求错误，请重新尝试！");
		            	}
		            }
	             });
			}
 		
 		window.onpageshow=	function refresh(e){
		     showdata();
	 	};
	 	
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
	 	
		function showdata(){
			$.ajax({
	             type: "GET",
	             async:true,
	             url: "http://localhost/getDeviceudid",
	             //dataType: "json",
	            success:function(data){
	            	udid = data;
	            	getTasks(data);
	            },
	          	error: function(XMLHttpRequest, textStatus, errorThrown){
	             //通常情况下textStatus和errorThrown只有其中一个包含信息
	          		alertDialog();
	          	}
	          });
		}
	 	
	 	function getTasks(udid){
	 		$.ajax({
	             type: "GET",
	             url: "/adverInfoLists",
	             data: {udid:udid},
	             dataType: "json",
	            success:function(data){
	            	var json = eval(data);
	            	result  = json["obj"].result;
	            	var curentTime= json["obj"].flag;
	            
	           	 	var adapter01 = new C3ListView.adapter(result);
	           		var adapter02 = new C3ListView.adapter(result);
	           	 	adapter01.getHtml = function(position){
	           	 	    var data = result[position];
	           			var adverstartTime = data.adverDayStart;
	           			
	           			if(curentTime > adverstartTime){
	 	           			 var count = "";
	 							if(data.adverCountRemain > 100){
	 								count = "多量";
		 	            		}else if(data.adverCountRemain < 1){
		 	            			count = "已抢光";
		 	            		}else{
		 	            			count = "少量";
		 	            		}
	 							
	 							var typeTask = "限时";
	 							
	 							if(data.taskType == 1){
	 								typeTask = "回调"
	 							}
	 							
	 							if(data.isRegister == 1){
	 								typeTask =  "注册";
	 							}
	 							
	 							 return "<div onclick=\"taskdetail("+position+")\" style=\"font-size:25px;color:#444;background:#fff;border-radius:10px;margin-top:8px;height:60px\" data-position=\""+position+"\">"
	 	 	           	 	    +"<img style=\"width:50px;height:50px;border-radius:10px;margin:5px;float:left\" src=\"https://moneyzhuan.com/file/adver/img/"+data.adverImg+"\"/>"
	 	 	           	 	    +"<span style=\"position:absolute;margin-left:10px;margin-top:10px;font-size:13px;width:200px\">"+data.adverName+"</span>"
	 	 	           	 	 	+"<span style=\"position:absolute;margin-left:10px;margin-top:32px;font-size:10px;width:45px;color:#CDCDC1;border:1px solid #CDCDC1;border-radius:5px;text-align:center;\">"+count+"</span>"
	 	 	           	 		+"<span style=\"position:absolute;margin-left:65px;margin-top:32px;font-size:10px;width:45px;color:#CDCDC1;border:1px solid #CDCDC1;border-radius:5px;text-align:center;\">"+typeTask+"</span>"
	 	 	           	 	    +"<span style=\"float:right;margin-top:20px;margin-right:20px;color:#FF0000;font-size:23px;\">+"+data.adverPrice+"元</span>"
	 	 	           	 	    +"</div>";
	           			}
	           	 	   
						return "<div style=\"display:block;height:1px\"></div>";         	 	   
	           	 	}
	           	 	
	           	adapter02.getHtml = function(position){
           	 	    var data = result[position];
           			var adverstartTime = data.adverDayStart;
           			
           			if(curentTime < adverstartTime){
	           			 var count = "";
							if(data.adverCountRemain > 100){
								count = "多量";
	 	            		}else if(data.adverCountRemain < 1){
	 	            			count = "已抢光";
	 	            		}else{
	 	            			count = "少量";
	 	            		}
							
							var typeTask = data.adverTimeStart;
							var name = data.adverName.substring(0,1) + "***";
							 return "<div onclick=\"taskdetail()\" style=\"display:block;font-size:25px;color:#444;background:#fff;border-radius:10px;margin-top:5px;height:60px\" data-position=\""+position+"\">"
	 	           	 	    +"<img style=\"width:50px;height:50px;border-radius:10px;margin:5px;float:left\" src=\"../img/h5web/happy_logo.png\"/>"
	 	           	 	    +"<span style=\"position:absolute;margin-left:10px;margin-top:10px;font-size:13px;width:200px\">"+name+"</span>"
	 	           	 	 	+"<span style=\"position:absolute;margin-left:10px;margin-top:32px;font-size:10px;width:45px;color:#CDCDC1;border:1px solid #CDCDC1;border-radius:5px;text-align:center;\">"+count+"</span>"
	 	           	 		+"<span style=\"position:absolute;margin-left:65px;margin-top:32px;font-size:10px;width:120px;color:#CDCDC1;border:1px solid #CDCDC1;border-radius:5px;text-align:center;\">"+typeTask+"</span>"
	 	           	 	    +"<span style=\"float:right;margin-top:20px;margin-right:20px;color:#FF0000;font-size:23px;\">+"+data.adverPrice+"元</span>"
	 	           	 	    +"</div>";
           			}
           	 	   
					return "<div style=\"display:block;height:1px\"></div>";	 	           	 	   
           	 	}
	           	
	           	 	var ct_01 = document.getElementById("container_01");
	           		var ct_02 = document.getElementById("container_02");
	           	 	var lsit01  = new C3ListView.builder(ct_01, adapter01);
	           		var lsit02  = new C3ListView.builder(ct_02, adapter02);
	            }
	        });
	 	}
	 	
		function go()
		{
			window.history.go(-1);
		}
 	</script>
</body>
</html>