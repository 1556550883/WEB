<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<%@include file="/WEB-INF/jsp/inc/ume.jsp"%>
    
<ry:binding bingdingName="downloadType,taskType,phoneType,adverStepCount,adverType,fileType,effectiveType,effectiveSource,userLevel,iosVersion" parentCode="DOWNLOAD_TYPE,TASK_TYPE,PHONE_TYPE,ADVER_STEP_COUNT,ADVER_TYPE,FILE_TYPE,EFFECTIVE_TYPE,EFFECTIVE_SOURCE,USER_LEVEL,OS_VERSION"></ry:binding>
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
	<form method="post" id="myform"  action="channelAdverInfo/edit?channelNum=${channelNum}" onsubmit="return iframeCallback(this,navTabAjaxDone);" enctype="multipart/form-data" >
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
		    <dl class="nowrap" style="width: 100%">
				<dt>手机型号：</dt>
				<dd>
				   <select name="phoneType" class="mustFill" title="手机型号">
				   		<option value="">请选择</option>
				   		<c:forEach items="${phoneType}" var="item"> 
				   			<option value="${item.itemCode}" <c:if test="${item.itemCode==bean.phoneType}">selected</c:if>>${item.itemName}</option>
				   		</c:forEach>
				   </select>
				</dd>
		    </dl>
		    <dl class="nowrap" style="width: 100%">
				<dt>手机系统：</dt>
				<dd>
				   <select name="iosVersion" class="mustFill" title="手机系统">
				   		<option value="">请选择</option>
				   		<c:forEach items="${iosVersion}" var="item"> 
				   			<option value="${item.itemCode}" <c:if test="${item.itemCode==bean.iosVersion}">selected</c:if>>${item.itemName}</option>
				   		</c:forEach>
				   </select>
				</dd>
		    </dl>

		    <dl class="nowrap" style="width: 100%">
				<dt>任务等级：</dt>
				<dd>
				   <select name="level" class="mustFill" title="任务等级">
				   		<option value="">请选择</option>
				   		<c:forEach items="${userLevel}" var="item"> 
				   			<option value="${item.itemCode}" <c:if test="${item.itemCode==bean.level}">selected</c:if>>${item.itemName}</option>
				   		</c:forEach>
				   </select>
				</dd>
		    </dl>
		    
		    <dl class="nowrap" style="width: 100%">
				<dt>下载类型：</dt>
				<dd>
				   <select name="downloadType" class="mustFill" title="下载类型">
				   		<option value="">请选择</option>
				   		<c:forEach items="${downloadType}" var="item"> 
				   			<option value="${item.itemCode}" <c:if test="${item.itemCode==bean.downloadType}">selected</c:if>>${item.itemName}</option>
				   		</c:forEach>
				   </select>
				</dd>
		    </dl>
		    
		    <dl class="nowrap" style="width: 100%">
				<dt>是否为注册任务：</dt>
				<dd>
				    是：<input type="radio" id="isRegister_1" name="isRegister"  value="1"/>
				   否 ：<input type="radio" id="isRegister_0" name="isRegister"  value="0"/>
				</dd>
		    </dl>
		      <dl class="nowrap" style="width: 100%">
			<dt>是否为模拟 任务：</dt>
			<dd>
			    是：<input type="radio" id="isMock_1"  name="isMock"  value="1"/>
			   否 ：<input type="radio" id="isMock_0" name="isMock"  value="0"/>
			</dd>  </dl>
			
			  <dl class="nowrap" style="width: 100%">
			<dt>外放设置：</dt>
			<dd style="width: 300px">
			    工作室：<input type="radio" id="isOpen_1"  name="isOpen"  value="1"/>
			   散户：<input type="radio" id="isOpen_2" name="isOpen"  value="2"/>
			   默认：<input type="radio" id="isOpen_0" name="isOpen"  value="0"/>
			</dd>  
			</dl>
			
			<dl class="nowrap" style="width: 100%;">
				<dt>广告storeID：</dt>
				<dd>
					<input name="adverAdid" value="${bean.adverAdid}" size="30" maxlength="100" class='' title="广告storeID" />
				</dd>
			</dl>
			
			<dl>
				<dt>广告ID：</dt>
				<dd>
					<input name="adid" value="${bean.adid}" size="30" maxlength="100" class='mustFill' title="广告ID" />
				</dd>
			</dl>
			
			<dl style="width: 100%">
				<dt>备注：</dt>
				<dd>
					<input name="remark" value="${bean.remark}" size="30" maxlength="100"  title="remark" />
				</dd>
			</dl>
			
			<dl style="width: 100%">
				<dt>bundleId：</dt>
				<dd>
					<input name="bundleId" value="${bean.bundleId}" size="30" maxlength="100" class='mustFill' title="bundleId" />
				</dd>
			</dl>
			<dl style="width: 100%">
				<dt>广告名称：</dt>
				<dd>
					<input name="adverName" value="${bean.adverName}" size="30" maxlength="100" class='mustFill' title="广告名称" />
				</dd>
			</dl>
			<dl class="nowrap"  style="width: 100%;">
				<dt>广告图片：</dt>
				<dd >
					<input type="file" name="fileAdverImg"/>
					<input name='adverImg' type='hidden' value='${bean.adverImg}' maxlength='100'/>
				</dd>
		    </dl>
			<dl style="width: 100%;height: 100px;">
				<dt>得分描述：</dt>
				<dd>
				<textarea style="height:100px;"  name="adverDesc" rows="5" cols="60">${bean.adverDesc} </textarea>
				</dd>
			</dl>
			<dl class="nowrap" style="width: 100%">
				<dt>广告数量：</dt>
				<dd>
				    <input name="adverCount" id="adverCount" class="mustFill" title="广告数量" type="text" value="<c:out value="${bean.adverCount}"></c:out>" maxlength="100"/>
				</dd>
		    </dl>
		    
		        <dl class="nowrap" style="width: 100%">
				<dt>任务回调率0-1：</dt>
				<dd>
				    <input name="random" id="random" class="mustFill" title="任务回调率" type="text" value="<c:out value="${bean.random}"></c:out>" maxlength="100"/>
				</dd>
		    </dl>
		    
		     <dl class="nowrap" style="width: 100%">
				<dt>任务排序等级：</dt>
				<dd>
				    <input name="adverSort" id="adverSort" class="mustFill" title="任务排序等级" type="text" value="<c:out value="${bean.adverSort}"></c:out>" maxlength="100"/>
				</dd>
		    </dl>
		    
		    <dl class="nowrap" style="width: 100%">
				<dt>广告价格：</dt>
				<dd>
				    <input name="adverPrice" id="adverPrice" class="mustFill" title="广告价格" type="text" value="<c:out value="${bean.adverPrice}"></c:out>" maxlength="100" readonly="readonly"/>
				</dd>
		    </dl>
		       <dl class="nowrap" style="width: 100%">
				<dt>广告差价：</dt>
				<dd>
				    <input name="priceDiff" id="priceDiff" class="mustFill" title="广告差价" type="text" value="<c:out value="${bean.priceDiff}"></c:out>" maxlength="100"/>
				</dd>
		    </dl>
		    <dl class="nowrap" style="width: 100%">
				<dt>领取任务后的任务时效（单位：分钟）：</dt>
				<dd>
				    <input name="timeLimit" id="timeLimit" class="mustFill" title="领取任务后的任务时效（单位：分钟）" type="text" value="<c:out value="${bean.timeLimit}"></c:out>" maxlength="10"/>
				</dd>
		    </dl>
		      <dl class="nowrap" style="width: 100%">
				<dt>应用需要打开的时间（单位：秒）：</dt>
				<dd>
				    <input name="openTime" id="openTime" class="mustFill" title="应用需要打开的时间（单位：秒）" type="text" value="<c:out value="${bean.openTime}"></c:out>" maxlength="10"/>
				</dd>
		    </dl>
		    
		    
		    <dl class="nowrap" style="width: 100%">
				<dt>任务增加的时间间隔（单位：秒）：</dt>
				<dd>
				    <input name="taskInterval" id="taskInterval" class="mustFill" title="任务增加的时间间隔（单位：秒）" type="text" value="<c:out value="${bean.taskInterval}"></c:out>" maxlength="10"/>
				</dd>
		    </dl>
		    
		       <dl class="nowrap" style="width: 100%">
				<dt>每次增加任务的数量：</dt>
				<dd>
				    <input name="addTask" id="addTask" class="mustFill" title="每次增加任务的数量" type="text" value="<c:out value="${bean.addTask}"></c:out>" maxlength="10"/>
				</dd>
		    </dl>
		    
		     <dl class="nowrap" style="width: 100%">
				<dt>增加任务的总数量：</dt>
				<dd>
				    <input name="addTaskLimit" id="addTaskLimit" class="mustFill" title="增加任务的总数量" type="text" value="<c:out value="${bean.addTaskLimit}"></c:out>" maxlength="10"/>
				</dd>
		    </dl>
		    
		     <dl class="nowrap" style="width: 100%">
				<dt>领取任务间隔（单位：秒）默认0：</dt>
				<dd>
				    <input name="receInterTime" id="receInterTime" class="mustFill" title="领取任务间隔（单位：秒）" type="text" value="<c:out value="${bean.receInterTime}"></c:out>" maxlength="10"/>
				</dd>
		    </dl>
		    
		    
		       <dl class="nowrap" style="width: 100%">
				<dt>提交任务间隔（单位：秒）默认0：</dt>
				<dd>
				    <input name="submitInterTime" id="submitInterTime" class="mustFill" title="提交任务间隔（单位：秒）" type="text" value="<c:out value="${bean.submitInterTime}"></c:out>" maxlength="10"/>
				</dd>
		    </dl>
		    
		    	       <dl class="nowrap" style="width: 100%">
				<dt>任务掺机型配置0-10(0代表全真实)：</dt>
				<dd>
				    <input name="phoneModelPercent" id="phoneModelPercent" class="mustFill" title="任务掺机型配置0-10" type="text" value="<c:out value="${bean.phoneModelPercent}"></c:out>" maxlength="10"/>
				</dd>
		    </dl>
		    
		    
		    <dl class="nowrap" style="width: 100%">
				<dt>广告开始日期：</dt>
				<dd>
				 	<input type="text" name="adverDayStart" title="广告开始日期" class="mustFill date" id="adverDayStart" value="<ry:formatDate date='${bean.adverDayStart}' toFmt='yyyy-MM-dd HH:mm'/>"  readonly="readonly" dateFmt="yyyy-MM-dd HH:mm" >
				 	<input name="adverTimeStart" id="adverTimeStart" type="hidden" value="<c:out value="${bean.adverTimeStart}"></c:out>" maxlength="100"/>
				</dd>
		    </dl>
		    <dl class="nowrap" style="width: 100%">
				<dt>广告结束日期：</dt>
				<dd>
					<input type="text" name="adverDayEnd" title="广告结束日期" class="mustFill date" id="adverDayEnd" value="<ry:formatDate date='${bean.adverDayEnd}' toFmt='yyyy-MM-dd HH:mm' />" readonly="readonly" dateFmt="yyyy-MM-dd HH:mm" >
					<input name="adverTimeEnd" id="adverTimeEnd" type="hidden" value="<c:out value="${bean.adverTimeEnd}"></c:out>" maxlength="100"/>
				</dd>
		    </dl>
		     <dl class="nowrap" style="width: 100%;">
				<dt>下载地址：</dt>
				<dd>
				    <input name="fileUrl" id="fileUrl" style="width: 467px" class="" title="下载地址" type="text" value="<c:out value="${bean.fileUrl}"></c:out>" maxlength="1000" size="60"/>
				</dd>
		    </dl>
		    <dl class="nowrap" style="width: 100%;">
				<dt>排重地址：</dt>
				<dd>
				    <input name="flag2" id="flag2" style="width: 467px" class="" title="上传数据地址" type="text" value="<c:out value="${bean.flag2}"></c:out>" maxlength="1000" size="60"/>
				</dd>
		    </dl>
		    <dl class="nowrap" style="width: 100%;">
				<dt>点击地址：</dt>
				<dd>
				    <input name="flag3" id="flag3" style="width: 467px" class="" title="上传数据地址" type="text" value="<c:out value="${bean.flag3}"></c:out>" maxlength="1000"/>
				</dd>
		    </dl>
		    <dl class="nowrap" style="width: 100%;">
				<dt>激活地址：</dt>
				<dd>
				    <input name="flag4" id="flag4" style="width: 467px" class="" title="上传数据地址" type="text" value="<c:out value="${bean.flag4}"></c:out>" maxlength="1000"/>
				</dd>
		    </dl>
		</div>
		
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="checkForm()">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
		<!-- 隐藏的值 -->
		<input type="hidden" name="adverId" value="${bean.adverId}"/>
		<input type="hidden" name="isAuth" value="3"/><!-- 无权限限制 -->
	</form>
	
	<script type="text/javascript">
		 $(function(){
			var val = '${bean.isRegister}';
			if(val == 1)
			{
				$("#isRegister_1").attr("checked", true);
			}
			else
			{
				$("#isRegister_0").attr("checked", true);
			}
			
			var val2 = '${bean.isMock}';
			if(val2 == 1)
			{
				$("#isMock_1").attr("checked", true);
			}
			else
			{
				$("#isMock_0").attr("checked", true);
			}
			
			
			var val3 = '${bean.isOpen}';
			if(val3 == 0)
			{
				$("#isOpen_0").attr("checked", true);
			}
			else if(val3 == 1)
			{
				$("#isOpen_1").attr("checked", true);
			}else
			{
				$("#isOpen_2").attr("checked", true);
			}
		})
		
		function checkForm()
		{
			//$("#adverRemand").val(ue.getContent());
			if(check())
			{
				$("#adverTimeStart").val($("#adverDayStart").val());
				$("#adverTimeEnd").val($("#adverDayEnd").val());
				$("#myform").submit();
			}
		}
		
		function updSourse()
		{
			$(".sourse").text($("#effectiveSource").find("option:selected").text());
		}
	</script>
</div>