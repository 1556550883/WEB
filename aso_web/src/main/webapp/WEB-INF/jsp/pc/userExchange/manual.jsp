<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
	<form method="post" action="userExchange/upManual?exchhangeId=${info.exchhangeId}&isType=1" id="forms" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">

	<div class="pageFormContent nowrap" layoutH="57">
	
	
	<dl style="width:520px">
		<dt style="">兑换码：</dt>
		<dd style="width: 200px">
			<input type="text" style="width: 300px" name="exchangeCode" class="required" alt="请输入兑换码"/>	
		</dd>
			</dl>
	
	</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit" >提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
</form>
