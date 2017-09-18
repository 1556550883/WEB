<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/inc/pression.jsp"%>

<ry:binding parentCode="USER_LEVEL"
	bingdingName="userlevel"></ry:binding>
<div class="pageContent">
<form method="post" id="myform" action="userLevel/saveOrUpdate" onsubmit="return iframeCallback(this,navTabAjaxDone);" enctype="multipart/form-data">
<div class="pageFormContent" layoutH="60">


			<dl style="width: 1000px">
				<dt>等级名称：</dt>
				<dd >
					<select name="levelName">
	                   <option>请选择</option>
	                   <c:forEach items="${userlevel}" var="item">
	                          <option value="${item.itemName}" <c:if test="${info.levelName == item.itemName}">selected</c:if> >${item.itemName}</option>
	                   </c:forEach>
	                </select>
				</dd>
			</dl>
			<dl style="width: 1000px">
				<dt>收徒开始数：</dt>
				<dd><input type="text" class="number" style="width: 600px" name="proportionStart" value="${info.proportionStart}" /></dd>
			</dl>
			<dl style="width: 1000px">
				<dt>收徒结束数：</dt>
				<dd><input type="text" class="number" style="width: 600px" name="proportionEnd"
					value="${info.proportionEnd}" /></dd>
			</dl>
			<dl style="width: 1000px; height: 220px">
				<dt>等级描述：</dt>
				<dd style="width: 400px"><textarea
					style="height: 200px; width: 650px; resize: none;" name="levelDesc"
					cols="115" rows="20">${info.levelDesc}</textarea></dd>
			</dl>
			<input type="hidden" name="levelId" value="${info.levelId}" />
			<input type="hidden" name="levelNum" value="${info.levelNum}" />
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

