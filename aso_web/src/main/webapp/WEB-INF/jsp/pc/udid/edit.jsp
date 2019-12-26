<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<div class="pageContent">
	<form method="post" action="udid/editNumber" id="forms" class="pageForm required-validate" onsubmit="return iframeCallback(this, navTabAjaxDone);"enctype="multipart/form-data">
		<div class="pageFormContent nowrap" layoutH="57">
			<dl>
				<dt>udid类型：</dt>
				<dd>
					<input type="text" name="udidType"  value="${udidType}" maxlength="100" size="100" class="required" />
				</dd>
			</dl>
			<dl>
				<dt>跳过数量：</dt>
				<dd>
					<input type="text" name="number"  value="0"  title="跳过数量" />
				</dd>
			</dl>
		</div>
		
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>
