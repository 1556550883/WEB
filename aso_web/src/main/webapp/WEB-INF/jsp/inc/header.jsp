<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
	.nav label{
		position:relative;
	}
</style>

<div id="header">
	<div class="headerNav" style="z-index:-1;" >
		<a style="padding-top:3px" href="javascript:void(0);"><img src="dwz/themes/common/zs_logo.png" style="margin-top: 5px;"></a>
		<ul class="nav" style="top:10px;font-size:12px;" >
			<li><a href="user/editPersion?userId=${systemUser.userId }" target="navTab" rel="persion_edit"><label>个人信息</label></a></li>
			<li><a href="user/editpass?userId=${systemUser.userId }" target="dialog" mask="true"><label>密码修改</label></a></li>
			<li><label><a href="loginout">退出</a></label></li>
		</ul>
	</div>
</div>
	<script type="text/javascript">
		function loginout(){
			window.location.href='loginout';
		}
	</script>