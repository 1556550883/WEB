<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<%@include file="/WEB-INF/jsp/inc/ume.jsp"%>
    
<ry:binding bingdingName="taskType,phoneType,adverStepCount,adverType,fileType,effectiveType,effectiveSource,userLevel,iosVersion" parentCode="TASK_TYPE,PHONE_TYPE,ADVER_STEP_COUNT,ADVER_TYPE,FILE_TYPE,EFFECTIVE_TYPE,EFFECTIVE_SOURCE,USER_LEVEL,OS_VERSION"></ry:binding>
<ry:binding parentCode="IS_AUTH" bingdingName="isAuth"></ry:binding>
<style>
<style>
.new_tab {border-collapse:collapse;}
.new_tab td{height:29px;line-height:29px;border:1px solid #e0e0e0;text-indent:10px}
.new_tab td input{width: 100px}
.new_tab td a{ float:left;margin:6px -3px 0 10px}
.list_all p:hover{color:#2ea7ec}
.new_tab tbody tr:hover{background:#f5f5f5}
.new_tab thead tr td{background:#f0eff0 url(dwz/themes/default/images/grid/tableth.png) repeat-x}
</style>
<div class="pageContent">
	<form method="post" id="myform"  action="externalChannelAdverInfo/saveOrUpdate" onsubmit="return iframeCallback(this,navTabAjaxDone);" enctype="multipart/form-data" >
		<div class="pageFormContent nowrap" layoutH="57">
  		    <dl class="nowrap" style="width: 100%">
				<dt>任务类型：</dt>
				<dd>
				   <select name="taskType" class="mustFill" title="任务类型">
				   		<option value="">请选择</option>
				   		<c:forEach items="${taskType}" var="item"> 
				   			<option value="${item.itemCode}" <c:if test="${item.itemCode==bean.taskType}">selected</c:if>>${item.itemName}</option>
				   		</c:forEach>
				   </select>
				</dd>
		    </dl>
		    
			<dl>
				<dt>广告ID：</dt>
				<dd>
					<input name="adid" value="${bean.adid}" size="30" maxlength="100" class='mustFill' title="广告ID" />
				</dd>
			</dl>
		
			<dl style="width: 100%">
				<dt>广告名称：</dt>
				<dd>
					<input name="externalAdverName" value="${bean.externalAdverName}" size="30" maxlength="100" class='mustFill' title="广告名称" />
				</dd>
			</dl>
			<dl style="width: 100%;height: 100px;">
				<dt>得分描述：</dt>
				<dd>
				<textarea style="height:100px;"  name="externalAdverDesc" rows="5" cols="60">${bean.externalAdverDesc} </textarea>
				</dd>
			</dl>
			<dl class="nowrap" style="width: 100%">
				<dt>广告数量：</dt>
				<dd>
				    <input name="externalAdverCount" id="externalAdverCount" class="mustFill" title="广告数量" type="text" value="<c:out value="${bean.externalAdverCount}"></c:out>" maxlength="100"/>
				</dd>
		    </dl>
		    <dl class="nowrap" style="width: 100%">
				<dt>广告价格：</dt>
				<dd>
				    <input name="externalAdverPrice" id="externalAdverPrice" class="mustFill" title="广告价格" type="text" value="<c:out value="${bean.externalAdverPrice}"></c:out>" maxlength="100"/>
				</dd>
		    </dl>
		    
		    <dl class="nowrap" style="width: 100%">
				<dt>广告开始日期：</dt>
				<dd>
				 	<input type="text" name="externalAdverTimeStart" title="广告开始日期" class="mustFill date" id="externalAdverTimeStart" value="<ry:formatDate date='${bean.externalAdverTimeStart}' toFmt='yyyy-MM-dd HH:mm'/>"  readonly="readonly" dateFmt="yyyy-MM-dd HH:mm" >
				 	<input name="externalAdverTimeStart" id="externalAdverTimeStart" type="hidden" value="<c:out value="${bean.externalAdverTimeStart}"></c:out>" maxlength="100"/>
				</dd>
		    </dl>
		    <dl class="nowrap" style="width: 100%">
				<dt>广告结束日期：</dt>
				<dd>
					<input type="text" name="externalAdverTimeEnd" title="广告结束日期" class="mustFill date" id="externalAdverTimeEnd" value="<ry:formatDate date='${bean.externalAdverTimeEnd}' toFmt='yyyy-MM-dd HH:mm' />" readonly="readonly" dateFmt="yyyy-MM-dd HH:mm" >
					<input name="externalAdverTimeEnd" id="externalAdverTimeEnd" type="hidden" value="<c:out value="${bean.externalAdverTimeEnd}"></c:out>" maxlength="100"/>
				</dd>
		    </dl>
		    <input type="hidden" name="externalChannelNum" value="${externalChannelNum}"/>
		</div>
		
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="checkForm()">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
		<!-- 隐藏的值 -->
		<input type="hidden" name="externalAdverId" value="${bean.externalAdverId}"/>
	</form>
	
	<script type="text/javascript">
		function checkForm()
		{
			//$("#adverRemand").val(ue.getContent());
			if(check())
			{
				$("#externalAdverTimeStart").val($("#externalAdverTimeStart").val());
				$("#externalAdverTimeEnd").val($("#externalAdverTimeEnd").val());
				$("#myform").submit();
			}
		}
		
		function updSourse()
		{
			$(".sourse").text($("#effectiveSource").find("option:selected").text());
		}
	</script>
</div>