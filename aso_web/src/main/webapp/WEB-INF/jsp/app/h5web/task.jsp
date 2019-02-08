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

<body style="background:#F0F0F0; margin:0px">
	 <div class="title">
	 		<span onclick="go()" style="line-height:0.8rem;color:Blue;font-size:0.6rem;float:left;margin-left:15px;margin-bottom:10px"><</span>
            <span style=".flex1; line-height:0.8rem; font-weight: bold;text-align: center; color: #4a4a4a;width:100%; font-size: 0.4rem;">试玩任务</span>
    </div>
    
   	<div id="container_01" style="padding-top:0.9rem;position:relative;margin-left:2%;width:96%;"></div>
   	
	<div style="text-align: center;margin-left:2%;width:96%;margin-top:10px;color: #9C9C9C; font-size: 0.4rem;border:1px dashed #CDCDC1;border-radius:5px">
		即将开始的任务
	</div>
	
	<div id="container_02" style="margin-left:2%;width:96%;margin-top:10px;"></div>
	
 	<script>
 		var result = "";
 		var base_url  = "https://moneyzhuan.com/";
 		var udid = localStorage.getItem("happyzhuan_user_udid");
		
 		function taskdetail(position){
 			if(position == null && position != 0){
 				alert("任务还未开始，请稍后！")
 				return;
 			}
 			var taskid = result[position].adverId;
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
		            		window.location.href = base_url + "taskDetail?taskID=" + taskid;
		            	}
		            }
	             })
 		}
 		
 		window.onpageshow=	function refresh(e){
		     showdata();
	 	};
	 	
	 	function showdata(){
	 		//如果udid被手动清理，需要 去 重新注册设备
	 		if(udid == null || udid == ""){
	 			url = "qisu://com.qisu?udid=none";
	 	 		window.location.href = url;
	 		}else{
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
	 	}
	 	
		function go()
		{
			window.history.go(-1);
		}
 	</script>
</body>
</html>