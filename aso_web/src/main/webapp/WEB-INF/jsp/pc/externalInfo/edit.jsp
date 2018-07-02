<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>

<ry:binding parentCode="" bingdingName=""></ry:binding>
<div class="pageContent">
	<form method="post" id="myform"  action="externalInfo/saveOrUpdate" onsubmit="return iframeCallback(this,navTabAjaxDone);" enctype="multipart/form-data" >
		<div class="pageFormContent" layoutH="60">
			<dl style="width: 1000px">
				<dt>渠道名称：</dt>
				<dd>
					 <input type="text" class="required"  style="width: 600px" name="externalChannelName" value="${info.externalChannelName}" />
				</dd>
			</dl>
				
			<dl style="width:1000px;height: 220px">
				<dt>渠道描述：</dt>
				<dd style="width: 400px">
			<textarea style="height: 200px;width:650px;resize:none;"  name="externalChannelDesc" cols="115" rows="20">${info.externalChannelDesc}</textarea>  

				</dd>
			</dl>  
			</div>
	    <div class="formBar">
			<ul>
				<c:if test="${type==0||type==1}"><li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="todo()" >保存</button></div></div></li></c:if>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>

<script type="text/javascript">
	function todo()
	{	
		$("#myform").submit();
	}
</script>

