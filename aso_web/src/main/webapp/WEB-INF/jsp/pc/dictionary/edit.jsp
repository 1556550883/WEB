<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/inc/pression.jsp"%>

<div class="pageContent">
<form method="post" action="dictionary/saveOrUpdate" id="forms" class="pageForm required-validate" onsubmit="return iframeCallback(this,dialogAjaxDone);" enctype="multipart/form-data">
<div class="pageFormContent nowrap" layoutH="63">
			<p>
				<label>appleId排重开关：</label>
				<select name="appleIdCheck">
					<option value="1" <c:if test="${bean.itemCode == 1}">selected="selected"</c:if>>开启</option>
					<option value="0" <c:if test="${bean.itemCode == 0}">selected="selected"</c:if>>关闭</option>
				</select>
			</p>
			<p>
				<label>app最少体验时间（单位：秒）：</label>
				<input type="text" name="leastTaskTime" value="${leastTaskTime}" size="10" class="required" alt="app最少体验时间（单位：秒）" />
			</p>
		
		<input type="hidden" value="${bean.parentCode }" name="parentCode">
</div>

<div class="formBar">
<ul>
	<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
	<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
</ul>
</div>

</form>
</div>