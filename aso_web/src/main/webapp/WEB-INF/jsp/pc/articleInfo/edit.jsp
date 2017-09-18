<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<%@include file="/WEB-INF/jsp/inc/ume.jsp"%>
<ry:binding parentCode="CHANNEL_TYPE,PIC_TYPE,PIC_STATUS,SYSTEM_TYPE,ARITCLE_TYPE,IS_AUTH" bingdingName="channeltype,pictype,picstatus,systemtype,articletype,isAuth"></ry:binding>
<div class="pageContent">
	<form method="post" id="myform"  action="articleInfo/saveOrUpdate" onsubmit="return iframeCallback(this,dialogAjaxDone);" enctype="multipart/form-data" >
 		<input type="hidden" name="articleId" value="${bean.articleId }"/>
 		<input type="hidden" name="articleNum" value="${bean.articleNum }"/>
		<div class="pageFormContent" layoutH="60">
			
			<!--<dl style="width: 1000px">
				<dt >文章URL：</dt>
				<dd >
					<input  type="text"  name="articleUrl" value="${bean.articleUrl}"/>
				</dd>
		</dl>	
		-->
		
		
		<dl style="width: 1000px">
				<dt >任务量：</dt>
				<dd >
					<input  type="text" class="required digits"  name="taskQuantity" value="${bean.taskQuantity}"/>
				</dd>
		</dl>
		<dl style="width: 1000px">
				<dt >任务说明：</dt>
				<dd >
					<input  type="text" class="required "  name="taskDesc" value="${bean.taskDesc}"/>
				</dd>
		</dl>
			<dl style="width: 1000px">
				<dt >显示任务量：</dt>
				<dd >
					<input  type="text" class="required digits"  name="showForwardQuantity"  value="${bean.showForwardQuantity}"/>
				</dd>
		</dl>
			<dl style="width: 1000px">
				<dt >奖励分数：</dt>
				<dd >
					<input  type="text" class="required"  name="rewardQuantity" value="${bean.rewardQuantity}"/>
				</dd>
		</dl>
			<dl style="width: 1000px">
				<dt >文章标题：</dt>
				<dd >
				<input type="text" name="articleDesc" class="required" value="${bean.articleDesc}">
				</dd>
		</dl>
		<!--<dl style="width: 1000px">
			<dt >发布对象：</dt>
			<dd>
				<select name="authType" onchange="todo(this.options[this.options.selectedIndex].value)">
					<option value="">---请选择---</option>
					<option value="1">城市</option>
					<option value="2">学校</option>
				</select>
			</dd>
		</dl>
		
		
 		
		--><dl style="width: 1000px" style='width:327px;height:212px'>
				<dt>图片：</dt>
				<dd>
				<input type="file" name="picFile" />
				</dd>
					<dd style="height: auto;">			
					<c:if test="${bean.articlePic!=null}">
						<img  src='file/img/${bean.articlePic}' style='width:327px;height:100px' >
					</c:if>
					</dd>
					</dl>	
			<!--<dl style="width: 1000px">
			<dt>是否添加权限：</dt>
			<dd>
				<select name="isAuth" class="required">
				   <option value="">---请选择---</option>
					  <c:forEach items="${isAuth}" var="item">
						 <option value="${item.itemCode }" <c:if test="${item.itemCode==bean.isAuth}">selected="selected"</c:if> >${item.itemName }</option>
				       </c:forEach>
				</select>	
			</dd>
			</dl>-->
			
			<dl style="width: 1000px">
			<dt >类型：</dt>
			<dd>
				<select name="articleType"  class="chosen-select" id="articleType" onchange="checkarticleType()" >
				   <option value="">---请选择---</option>
					  <c:forEach items="${articletype}" var="item">
						 <option value="${item.itemCode }" <c:if test="${item.itemCode==bean.articleType}">selected="selected"</c:if> >${item.itemName }</option>
				       </c:forEach>
				</select>	
			</dd>
			</dl>
			
			<dl style="width: 1000px" >
				<dt hidden="" id="Url">文章URL：</dt>
				<dd>
				<input hidden="" type="text" name="articleUrl" id="articleUrl" class="required"  value="${bean.articleUrl}">
				</dd>
			</dl>
			<dl  style="width: 1000px" >
				<dt hidden="" id="Content">文章内容：</dt>
				<dd><div id="myeditor" hidden="" style="width:600px;height: 400px;" title="文章内容"></div>
				<input type="hidden" id="articleContent"  name="articleContent" title="文章内容" value='${bean.articleContent}' />
				</dd>
			</dl>
<script type="text/javascript">
function checkarticleType(){
	var articleType=document.getElementById("articleType").value ;
	if(articleType==1){
		$("#Url").show();
		$("#articleUrl").show();
		$("#Content").hide();
		$("#myeditor").hide();
	}
	if(articleType==2){
		$("#Content").show();
		$("#myeditor").show();
		$("#Url").hide();
		$("#articleUrl").hide();
	}
	if(articleType==''){
		$("#Content").hide();
		$("#myeditor").hide();
		$("#Url").hide();
		$("#articleUrl").hide();
	}
}


</script>


			<input type="hidden" name="picId" value="${bean.articleId}" />	
			<input type="hidden" name="articlePic" value="${bean.articlePic}" />					  
			<input type="hidden" name="isAuth" value="3" />					  
			</div>
	    <div class="formBar">
			<ul>
				<c:if test="${type==0}"><li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="todo()">保存</button></div></div></li></c:if>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">
var ue=UE.getEditor("myeditor",{
	autoHeightEnabled: false});
	ue.ready(function() {
	ue.setContent($("#articleContent").val());
	
});
function todo(){
	$("#articleContent").val(ue.getContent());
	if(check()){
		$("#myform").submit();
	}
}

</script>


