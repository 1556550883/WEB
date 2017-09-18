<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>

<ry:binding bingdingName="parametertype,requesttype,inferfacetype" parentCode="PARAMETER_TYPE,REQUEST_TYPE,INFERFACE_TYPE"></ry:binding>
<div class="pageContent">
	<form method="post" id="myform"  action="adverInferface/updateInferface" onsubmit="return iframeCallback(this,navTabAjaxDone);" enctype="multipart/form-data" >
		<div class="pageFormContent" layoutH="60">
			<dl style="width: 1000px">
				<dt>广告编号：</dt>
				<dd>
					 <input type="text"   name="adverNum" value="${info.adverNum}" />
				</dd>
			</dl>
			<dl style="width: 1000px">
				<dt>参数名称：</dt>
				<dd>
					 <input type="text"   name="parameterType" value="${info.parameterType}" />
				</dd>
			</dl>
				
				<dl style="width: 1000px">
				<dt>对应参数名称：</dt>
				<dd>
					 <input type="text"   name="parameterName" value="${info.parameterName}" />
				</dd>
			</dl>

			<dl style="width: 1000px">
				<dt >接口回调方式：</dt>
				<dd >
					<select name="inferfaceRequestType" >
	                   <option >请选择</option>
	                   <c:forEach items="${requesttype}" var="item">
	                          <option value="${item.itemCode}"  <c:if test="${info.inferfaceRequestType == item.itemCode}">selected</c:if>>${item.itemName}</option>
	                   </c:forEach>
	                </select>
				</dd>
			</dl>	
				<dl style="width: 1000px">
				<dt >接口类型：</dt>
				<dd >
					<select name="inferfaceType" >
	                   <option >请选择</option>
	                   <c:forEach items="${inferfacetype}" var="item">
	                          <option value="${item.itemCode}"  <c:if test="${info.inferfaceType == item.itemCode}">selected</c:if>>${item.itemName}</option>
	                   </c:forEach>
	                </select>
				</dd>
			</dl>	
				<input type="hidden" name="inferaceId" value="${info.inferaceId}"/>
				  
			</div>
	    <div class="formBar">
			<ul>
				<c:if test="${type==1}"><li><div class="buttonActive"><div class="buttonContent"><button type="submit" >保存</button></div></div></li></c:if>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>


