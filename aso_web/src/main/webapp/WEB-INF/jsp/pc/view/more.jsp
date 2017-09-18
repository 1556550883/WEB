<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding bingdingName="pro" type="4"></ry:binding>
<div class="pageContent">
	<form method="post" action="view/saveOrUpdate" id="forms" class="pageForm" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="57">

		    <dl class="nowrap" style="width: 100%">
				<dt>内容：</dt>
				<dd>
					<div id="myeditor" style="width: 900px"></div>
				    <input name="viewContent" id="viewContent" type="hidden" value="<c:out value="${info.viewContent}"></c:out>"/>
				</dd>
		    </dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="todo()">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
		<!-- 隐藏的值 -->
		<input type="hidden" name="viewId" value="${info.viewId}"/>
	</form>
</div>
    <link href="umeditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" charset="utf-8" src="umeditor/umeditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="umeditor/umeditor.min.js"></script>
    <script type="text/javascript" src="umeditor/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript">

	var ue=UM.getEditor("myeditor",{
		autoHeightEnabled: false
	});
		ue.ready(function() {
		ue.setContent($("#viewContent").val());
	});
		function todo(){
		$("#viewContent").val(ue.getContent());
	 	$("#forms").submit();
	}
	</script>