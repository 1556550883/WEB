<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/inc/pression.jsp"%>

<ry:binding parentCode="LEVEL_RATE_TYPE"
	bingdingName="levelratetype"></ry:binding>
<div class="pageContent">
<form method="post" id="myform" action="userLevelRate/saveOrUpdate" onsubmit="return iframeCallback(this,navTabAjaxDone);" enctype="multipart/form-data">
<div class="pageFormContent" layoutH="60">
			<dl style="width: 1000px">
				<dt>等级编号：</dt>
				<dd><input type="text"  style="width: 600px" name="levelNum" value="${info.levelNum}"readonly="true" /></dd>
			</dl>
			<dl style="width: 1000px">
				<dt>等级汇率：</dt>
				<dd><input type="text" class="number" style="width: 600px" name="levelRate" value="${info.levelRate}" /></dd>
			</dl>			
			<dl style="width: 1000px">
				<dt>徒弟等级：</dt>
				<dd >
					<select name="rateType">
	                   <option>请选择</option>
	                   <c:forEach items="${levelratetype}" var="item">
	                          <option value="${item.itemCode}" <c:if test="${info.rateType == item.itemCode}">selected</c:if> >${item.itemName}</option>
	                   </c:forEach>
	                </select>
				</dd>
			</dl>
			<input type="hidden" name="levelRateId" value="${info.levelRateId}" />
			</div>
<div class="formBar">
<ul>
	<c:if test="${type==0}">
		<li>
		<div class="buttonActive">
		<div class="buttonContent">
		<button type="submit">保存</button>
		</div>
		</div>
		</li>
	</c:if>
	<li>
	<div class="button">
	<div class="buttonContent">
	<button type="button" class="close">取消</button>
	</div>
	</div>
	</li>
</ul>
</div>
</form>
</div>

