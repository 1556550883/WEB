<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
	<form method="post" action="userExchange/exchangeCode" id="forms" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">

	<div class="pageFormContent nowrap" layoutH="57">
	
	
	<dl style="margin-left: 420px;margin-top: 100px">

				<dt style="color: red;font-size: 20px">积分兑换：</dt>
				<dd>
				<input type="text"  name="exchangeCode" class="" style="height: 40px;width: 500px;font-size: 20px"/>	
				</dd>
			</dl>
	
	<div class="buttonActive" style="margin-left:-373px;margin-top: 200px"><div class="buttonContent"><button type="submit" >确认兑换</button></div></div>
	
	</div>
</form>