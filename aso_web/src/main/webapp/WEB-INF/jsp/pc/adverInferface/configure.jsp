<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding bingdingName="parametertype,requesttype,inferfacetype" parentCode="PARAMETER_TYPE,REQUEST_TYPE,INFERFACE_TYPE"></ry:binding>
<div class="pageContent">
	<form method="post" id="myform"  action="adverInferface/saveConfigure" onsubmit="return iframeCallback(this,navTabAjaxDone);" enctype="multipart/form-data" >
		<div class="pageFormContent nowrap" layoutH="57">

		<c:forEach items="${list}" var="item">
			<dl style="width: 1000px">
				<dt style="width: 300px">${item.parameterType}(<ry:show parentCode="PARAMETER_TYPE" itemCode="${item.parameterType}"></ry:show>)：</dt>
				<dd>
					<input name="parameterType" type="hidden" value="${item.parameterType }"  />
					<input name="parameterName" value="${item.parameterName }"  />
				</dd>
			</dl>
			 </c:forEach>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit" >提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
		<!-- 隐藏的值 -->
		<input type="hidden" name="inferfaceRequestType" value="${info.inferfaceRequestType}"/>
		<input type="hidden" name="inferfaceType" value="${info.inferfaceType}"/>
		<input type="hidden" name="adverNum" value="${info.adverNum}"/>
	</form>
</div>