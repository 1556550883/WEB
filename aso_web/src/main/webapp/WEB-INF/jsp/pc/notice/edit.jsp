<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
    <link href="umeditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" charset="utf-8" src="umeditor/umeditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="umeditor/umeditor.min.js"></script>
<ry:binding parentCode="CHANNEL_TYPE,PIC_TYPE,PIC_STATUS,SYSTEM_TYPE" bingdingName="channeltype,pictype,picstatus,systemtype"></ry:binding>
<div class="pageContent">
	<form method="post" id="myform"  action="notice/saveOrUpdate" onsubmit="return iframeCallback(this,navTabAjaxDone);" enctype="multipart/form-data" >
		
		
		<div class="pageFormContent" layoutH="60">
		
	<dl style="width: 100%">
	 <dt>标题:</dt>
	 <dd>
	 <input type="text" name="title"  value="${bean.title}"  />
	 </dd>
	</dl>

		<dl class="nowrap" style="width: 100%">
				<dt>商铺描述：</dt>
				<dd>
					<div id="myeditor" style="width: 900px"></div>
				    <input name="content" id="content" type="hidden" value="<c:out value="${bean.content}"></c:out>"/>
				</dd>
		    </dl>
			
			<input type="hidden" name="noticeId" value="${bean.noticeId}" />					  
			</div>
	    <div class="formBar">
			<ul>
				<c:if test="${type==0}"><li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="todo()" >保存</button></div></div></li></c:if>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">

var ue=UM.getEditor("myeditor",{
	autoHeightEnabled: false
});
	ue.ready(function() {
	ue.setContent($("#content").val());
});
function todo(){
	$("#content").val(ue.getContent());
 	$("#myform").submit();
}
</script>


