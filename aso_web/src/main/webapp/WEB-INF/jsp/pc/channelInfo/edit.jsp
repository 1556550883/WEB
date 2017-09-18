<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>

<ry:binding parentCode="CHANNEL_LEVEL,CHANNEL_NUM,CHANNEL_TYPE,SYSTEM_TYPE" bingdingName="channellevel,channelnum,channeltype,systemtype"></ry:binding>
<div class="pageContent">
	<form method="post" id="myform"  action="channelInfo/saveOrUpdate" onsubmit="return iframeCallback(this,navTabAjaxDone);" enctype="multipart/form-data" >
		<div class="pageFormContent" layoutH="60">
			<dl style="width: 1000px">
				<dt>渠道名称：</dt>
				<dd>
					 <input type="text" class="required"  style="width: 600px" name="channelName" value="${info.channelName}" />
				</dd>
			</dl>
				
				<dl style="width: 1000px">
				<dt>系统类型：</dt>
				<dd >
					<select name="systemType" >
	                   <option >请选择</option>
	                   <c:forEach items="${systemtype}" var="item">
	                          <option value="${item.itemCode}" <c:if test="${info.systemType == item.itemCode}">selected</c:if> >${item.itemName}</option>
	                   </c:forEach>
	                </select>
				</dd>
			</dl>	
				<dl style="width: 1000px">
				<dt>渠道推荐等级：</dt>
				<dd >
					<select name="channelLevel" >
	                   <option >请选择</option>
	                   <c:forEach items="${channellevel}" var="item">
	                          <option value="${item.itemCode}" <c:if test="${info.channelLevel == item.itemCode}">selected</c:if> >${item.itemName}</option>
	                   </c:forEach>
	                </select>
				</dd>
			</dl>	
		<!--
						<dl style="width: 1000px">
				<dt>渠道类型：</dt>
				<dd >
					<select id="channelType" name="channelType" <c:if test="${type==0||type==1}"> disabled="ture"</c:if>>
	                   <option >请选择</option>
	                   <c:forEach items="${channeltype}" var="item">
	                          <option value="${item.itemCode}" <c:if test="${info.channelType == item.itemCode||(item.itemCode==2&&type==1)}">selected</c:if> >${item.itemName}</option>
	                   </c:forEach>
	                </select>
				</dd>
			</dl>	
			-->
			<c:if test="${type==1}">
			<dl style="width: 1000px">
				<dt>渠道类型：</dt>
				<dd >
					<select id="channelType" name="channelType">
					<c:forEach items="${channeltype}" var="item">
					<c:if test="${type==1 && item.itemCode==1}">
					 <option value="${item.itemCode}" >${item.itemName}</option>
					</c:if>
					<c:if test="${type!=1}">
						 <option value="${item.itemCode}" <c:if test="${info.channelType == item.itemCode}">selected</c:if> >${item.itemName}</option>
					</c:if>
    				</c:forEach>
	                </select>
				</dd>
			</dl>
			</c:if>	
		<!-- <dl style="width: 1000px">
				<dt>用户拥有的渠道：</dt>
				<dd>
					 <input type="text"   style="width: 600px" name="channelUser" value="${info.channelUser}" />
				</dd>
			</dl>
			 -->	
			<dl style="width: 1000px">
				<dt>应用key：</dt>
				<dd>
					 <input type="text"   style="width: 600px" name="appKey" value="${info.appKey}" />
				</dd>
			</dl>
				<dl style="width: 1000px">
				<dt>应用密码：</dt>
				<dd>
					 <input type="text"   style="width: 600px" name="appPassword" value="${info.appPassword}" />
				</dd>
			</dl>
					<dl style="width: 1000px">
				<dt>应用其他信息：</dt>
				<dd>
					 <input type="text"   style="width: 600px" name="appOther" value="${info.appOther}" />
				</dd>
			</dl>
					<dl style="width: 1000px">
				<dt>应用回调key：</dt>
				<dd>
					 <input type="text"   style="width: 600px" name="appCallbackKey" value="${info.appCallbackKey}" />
				</dd>
			</dl>
			
			<dl style="width:1000px;height: 220px">
				<dt>渠道描述：</dt>
				<dd style="width: 400px">
			<textarea style="height: 200px;width:650px;resize:none;"  name="channelDesc" cols="115" rows="20">${info.channelDesc}</textarea>  

				</dd>
			</dl>
						
		<dl style="width: 720px;height: 100px;">
				<dt>图片：</dt>
				<dd>
				<input type="file" name="picFile" value="" />
				</dd>
				<dd>			
					<c:if test="${info.channelImg!=null}">
						<img  src='file/img/${info.channelImg}' style='width:230px;height:100px'>
					</c:if><lable style="color:red;">(135*135px)</lable>
					</dd>	
			</dl>	
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

