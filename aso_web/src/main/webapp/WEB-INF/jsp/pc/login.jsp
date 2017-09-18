<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="ry" uri="http://www.ruanyun.com/taglib/ry" %>
<!DOCTYPE html>
<!-- saved from url=(0036)http://211.155.229.133:88/login.aspx -->
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>赚钱网站管理平台</title>
<script src="dwz/js/jquery-1.7.2.js" type="text/javascript"></script>
<link href="dwz/themes/login/asyncbox.css" rel="stylesheet" type="text/css">
<link href="dwz/themes/login/login.css" rel="stylesheet" type="text/css">
<script src="dwz/themes/login/AsyncBox.v1.4.5.js" ></script>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<base href="<%=basePath%>"/>
</head>
<body onload="init();" onkeydown="submitForm();">
	<form method="post" action="login" id="form1">
		<div class="aspNetHidden">
		    <input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="/wEPDwUKLTk1NzQ1NzgzM2QYAQUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgMFCUF1dG9Mb2dpbgULTG9naW5CdXR0b24FDEltYWdlQnV0dG9uMQ==">
		</div>
	
		<table cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
			<tbody>
				<tr>
				   <td align="center">
				      <!-- 头部图片 -->
				      <img src="<%=path %>/dwz/themes/login/top.jpg" width="100%" height="180">
				   </td>
				</tr>
				<tr>
					<td align="center">
						<table cellspacing="0" cellpadding="0" width="700" align="center" border="0">
							<tbody>
								<tr>
									<td valign="top" align="center">
										<table cellspacing="0" cellpadding="0" width="460" align="center" border="0">
											<tbody>
												<tr>
												    <td height="60" valign="top" align="left">
												       <!-- 项目名称图片 -->
												       <img src="dwz/themes/common/logo_login.png">
												    </td>
												</tr>
											</tbody>
										</table>
										<table cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
											<tbody>
												<tr>
												    <td height="2"></td>
												</tr>
											</tbody>
										</table>
										<table cellspacing="0" cellpadding="0" width="460" align="center" border="0">
											<tbody>
												<tr>
												    <td height="55" valign="top" align="left"><img src="dwz/themes/login/img1.jpg" width="95" height="41"> </td>
												</tr>
												<tr>
													<td>
														<table cellspacing="0" cellpadding="0" width="400" align="center" border="0">
															<tbody>
																<tr>
																	<td class="a1" width="60" align="left">用户名： </td>
																	<td align="left">
																	    <input name="loginName" type="text" maxlength="16" id="loginName" value="${param.loginName }" style="width: 140px;" class="TxtUserNameCssClass">
																	</td>
																</tr>
																<tr>
																	<!--  class="TxtPasswordCssClass" -->
																	<td class="a1" align="left" style="width: 70px;">密 &nbsp;码： </td>
																	<td align="left">
																		<input name="password" type="password" maxlength="32"  id="loginPass" value="" style="width: 140px;" class="TxtPasswordCssClass">
																		<input id="AutoLogin" value="1" type="checkbox" name="AutoLogin">记住用户名和密码
																	</td>
																</tr>
																<!-- <tr>
																	<td class="a1" width="60" align="left"> </td>
																		<td align="left">
																		<a onclick="Open('ChangeUser.aspx','400','200');" class="red">修改用户名</a>
																		&nbsp;&nbsp;&nbsp;&nbsp;
																		<a onclick="Open('ChangeUser.aspx','400','235');" class="red">修改密码</a>
																	</td>
																</tr> -->
															</tbody>
														</table>
													</td>
												</tr>
												<tr>
													<td height="100">
														<table cellspacing="0" cellpadding="0" width="255" align="center" border="0">
															<tbody>
															<tr>
																<td width="70" align="left">
																    <input type="image" name="LoginButton" onclick="loginsumbit();" id="LoginButton" src="dwz/themes/login/img2.jpg">
																</td>
																<td width="70" align="left">
																    <input type="image" name="ImageButton1" id="ImageButton1" src="dwz/themes/login/img3.jpg">
																</td>
																<td align="left">&nbsp;</td>
															</tr>
															</tbody>
														</table>
													</td>
												</tr>
											</tbody>
										</table>
									</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<table cellspacing="0" cellpadding="0" width="1004" align="center" border="0">
			<tbody>
				<tr>
					<td height="150" align="center">
					    <span id="CopyRight1">Copyright©2016赚钱科技有限公司</span> 
					</td>
				</tr>
			</tbody>
		</table>  
	
		<!-- <script>
			function Open(url,w,h) {
			    asyncbox.open({
			        id: 'adder',
			        url: url,
			        width: w,
			        height: h
			    });
			};
		</script>   -->
	</form>


	<div id="asyncbox_cover" unselectable="on" style="opacity:0.1;filter:alpha(opacity=10);background:#000"></div>
	<div id="asyncbox_clone"></div>
	<div id="asyncbox_focus"></div>
	<div id="asyncbox_load">
		<div>
		     <span></span>
		</div>
	</div>
	<script>
	 function submitForm(event){ 
	      var event=window.event?window.event:event; if(event.keyCode==13){ 
	    	  loginsumbit(); 
	      } 
	    } 
	//登录错误信息提示
    $(function () {
        $("#LoginButton").click(function () {
            if ($("#loginName").val().length <= 0) {
                asyncbox.alert("请输入用户名！", "信息提示：", function () { $("#loginName").focus(); });                
                return false;
            }
            else if ($("#loginPass").val().length <= 0) {
                asyncbox.alert("请输入密码！", "信息提示：", function () { $("#loginPass").focus(); }); 
                return false;
            }
            else {
                return true;
            }
        });
    });
    $(function(){
           if(${not empty loginResult}){
               if(${loginResult==-2}){
            	   asyncbox.alert("密码错误！", "登录失败：", function () { $("#loginPass").focus(); }); 
            	   return false;
               }
               if(${loginResult==-1}){
            	   asyncbox.alert("用户名错误！", "登录失败：", function () { $("#loginName").focus(); }); 
            	   return false;
               }
           }
        });
    //记住登录信息
    
    function loginsumbit(){
		if(document.getElementById("AutoLogin").checked){
			SetCookie ("loginName", $('#loginName').val());
			SetCookie ("loginPass", $('#loginPass').val());
		}
		form1.submit();
	}

    function init() {
		$('#loginName').val(getCookie("loginName") || "");
		$('#loginPass').val(getCookie("loginPass") || "");
	}
	
	function SetCookie(name,value) {
    	var Days = 30; //此 cookie 将被保存 30 天
    	var exp  = new Date();    //new Date("December 31, 9998");
    	exp.setTime(exp.getTime() + Days*24*60*60*1000);
    	document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
	}
	
	function getCookie(name) {
		var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
		if(arr != null) return unescape(arr[2]); return null;
	}
	
	function delCookie(name) {
		var exp = new Date();
		exp.setTime(exp.getTime() - 1);
		var cval=getCookie(name);
		if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString();
	}
</script>
</body>
</html>