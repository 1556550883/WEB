<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>

<ry:binding parentCode="CHANNEL_LEVEL,CHANNEL_NUM,CHANNEL_TYPE,SYSTEM_TYPE,IS_IP_LIMIT_ENABLE" bingdingName="channellevel,channelnum,channeltype,systemtype,isIpLimitEnable"></ry:binding>
<div class="pageContent">
	<form method="post" id="myform"  action="channelInfo/saveOrUpdate" onsubmit="return iframeCallback(this,navTabAjaxDone);" enctype="multipart/form-data" >
		<div class="pageFormContent" layoutH="60">
			<dl style="width: 1000px">
				<dt>渠道名称：</dt>
				<dd>
					 <input type="text" class="required"  style="width: 600px" name="channelName" value="${info.channelName}" />
				</dd>
			</dl>
			<input type="hidden" name="systemType" value="2"/>
			<input type="hidden" name="cumulativeTotal" value="${info.cumulativeTotal}"/>
			<input type="hidden" name="dayTotal" value="${info.dayTotal}"/>
			<input type="hidden" name="channelType" value="${info.channelType}"/>
			<input type="hidden" name="isEnable" value="${info.isEnable}"/>
			<input type="hidden" id="channelId" name="channelId" value="${info.channelId}" />	
			<input type="hidden" name="channelNum" value="${info.channelNum}" />
			<input type="hidden" name="channelImg" value="${info.channelImg}" />				  
			</div>
	    <div class="formBar">
			<ul>
				<c:if test="${type==0||type==1}"><li><div class="buttonActive"><div class="buttonContent"><button type="button"" onclick="todo()" >保存</button></div></div></li></c:if>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">

function todo(){	
	var re=$("#channelType").val();
	var chid=$("#channelId").val();
	if(re==3&&chid==""){
		alert("快赚钱渠道不能新增");
	}else{
		$("#myform").submit();
	}
}
</script>

