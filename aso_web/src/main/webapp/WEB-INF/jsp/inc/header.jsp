<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ry:binding type="3"></ry:binding>
<!-- topbar starts -->
<style>
.nav label{
position:relative;
}
</style>
<div id="header"  >
	<div class="headerNav" style="z-index:-1;" >
		<%--<a class="logo" style="padding-top: 3" href="javascript:void(0);">标志</a> --%>
		<a style="padding-top: 3" href="javascript:void(0);"><img src="dwz/themes/common/logo.png"></a>
		<ul class="nav" style="top:10px;font-size:12px;" >
			
			<li><a href="user/editPersion?userId=${systemUser.userId }" target="navTab" rel="persion_edit"><label>个人信息</label></a></li>
			<li><a href="user/editpass?userId=${systemUser.userId }" target="dialog" mask="true"><label>密码修改</label></a></li>
			<li><label><a href="loginout">退出</a></label></li>
		</ul>
		<!--<ul class="themeList" id="themeList">
		    <li theme="default" ><div >蓝色</div></li>
			<li theme="green"><div>绿色</div></li>
			<li theme="red"><div>红色</div></li> 
			<li theme="purple"><div>紫色</div></li>
			<li theme="silver"><div>银色</div></li>
			<li theme="azure"><div class="selected">天蓝</div></li>
			<li style="color: white;" >欢迎您：[ ${systemUser.role.roleName } ：${systemUser.loginName } ]</li>
			<li style="color: white;" ><span id="datetime"></span></li>
			
		</ul>
<!--		<ul class="themeList" id="themeList">-->
<!--			<li theme="default"><div class="selected">蓝色</div></li>-->
<!--			<li theme="green"><div>绿色</div></li>-->
<!--			<li theme="red"><div>红色</div></li>-->
<!--			<li theme="purple"><div>紫色</div></li>-->
<!--			<li theme="silver"><div>银色</div></li>-->
<!--			<li theme="azure"><div>天蓝</div></li>-->
<!--		</ul>-->
	</div>
</div>
	<script type="text/javascript">
 
	
	function loginout(){
		window.location.href='loginout';
	}

	</script>