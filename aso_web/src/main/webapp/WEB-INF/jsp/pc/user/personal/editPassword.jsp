<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>

<ry:binding parentCode="USERSEX,ORG_TYPE" bingdingName="usersexs,orgTypeList"></ry:binding>
<div class="pageContent">
	<form method="post" action="user/updatePass" id="forms" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="57">
			<div class="unit">
				<label>当前密码：</label>
				<input type="password" id="oldPass" name="oldPass" size="30" class="required textInput">
				<span class="info" id="error_show" style="color: red;display: none;">当前密码输入错误</span>
			</div>
			<div class="unit">
				<label>新密码：</label>
				<input type="password" id="loginPass" name="loginPass" size="30" class="required alphanumeric" minlength="6" maxlength="20" alt="字母、数字、下划线 6-20位"/>
			</div>
			<div class="unit">
				<label>确认新密码：</label>
				<input type="password" name="relPass" size="30" class="required" equalto="#loginPass">
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="checkPass();">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
		<!-- 隐藏的值 -->
		<input type="hidden" name="userId" value="${systemUser.userId}"/>
		<input type="hidden" id="sqlPass" name="sqlPass" value="${systemUser.loginPass}"/>
	</form>
</div>
<script type="text/javascript" src="js/md5.js"></script>
<script>
function checkPass(){
	var sqlPass=$('#sqlPass').val();
	//var loginPass=$('#loginPass').val();
	var oldPass=$('#oldPass').val();
	var md5OldPass=hex_md5(oldPass);
	if(md5OldPass==sqlPass){
		$('#error_show').hide();
		$('#forms').submit();
	}else{
		if(oldPass!=''){
			$('#error_show').show();
			return false;
		}
		$('#forms').submit();
	}
}

</script>
