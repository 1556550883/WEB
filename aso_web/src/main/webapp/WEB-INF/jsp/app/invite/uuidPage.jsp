<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <script type="text/javascript" charset="utf-8" src="../js/jquery-1.11.3.min.js"></script>
</head>

<body>
 	<script>
 		//读取safari缓存的masterid
 		//url: "https://moneyzhuan.com/invite/setMaster",
 		localStorage.setItem("happyzhuan_user_udid","${udid}");
 		var  masterID = localStorage.getItem("happyzhuan_master_id");
		  $.ajax({
             type: "GET",
             url: "https://moneyzhuan.com/invite/userRegister",
             data: {udid:"${udid}",masterID:masterID},
             dataType: "json"});
    
 		  
 		url = "qisu://com.qisu?udid=" + "${udid}";
 		window.location.href = url;
	</script>
</body>
</html>