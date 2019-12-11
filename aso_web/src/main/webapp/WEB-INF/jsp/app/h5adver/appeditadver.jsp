<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>

<%@include file="/WEB-INF/jsp/inc/ume.jsp"%>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<ry:binding bingdingName="downloadType,taskType,phoneType,adverStepCount,adverType,fileType,effectiveType,effectiveSource,userLevel,iosVersion" parentCode="DOWNLOAD_TYPE,TASK_TYPE,PHONE_TYPE,ADVER_STEP_COUNT,ADVER_TYPE,FILE_TYPE,EFFECTIVE_TYPE,EFFECTIVE_SOURCE,USER_LEVEL,OS_VERSION"></ry:binding>
<ry:binding parentCode="IS_AUTH" bingdingName="isAuth"></ry:binding>
<script src="dwz/js/jquery-1.7.2.js" type="text/javascript" charset="utf-8"></script>
<script src="js/datepicker/WdatePicker.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/dwz.util.date.js" type="text/javascript" charset="utf-8"></script>

<form method="post" id="myform"  action="edit?channelNum=${channelNum}" enctype="multipart/form-data">
<div style="margin-top:10px;width:100%;font-size:15px; font-family:微软雅黑">
	  任务类型：
	 <select name="taskType" class="mustFill" title="任务类型">
   		<option value="">请选择</option>
   		<c:forEach items="${taskType}" var="item"> 
   			<option id='taskType' value="${item.itemCode}" <c:if test="${item.itemCode==bean.taskType}">selected</c:if>>${item.itemName}</option>
   		</c:forEach>
	</select>
</div>
 <HR style= " FILTER: progid:DXImageTransform.Microsoft.Shadow ( color: #987cb9 ,direction : 145, strength :15 ) " width = "100%" color = #987cb9 SIZE= 1>
<div style="margin-top:10px;width:100%;font-size:15px; font-family:微软雅黑">
手机型号：
  <select name="phoneType" class="mustFill" title="手机型号">
				   		<option value="">请选择</option>
				   		<c:forEach items="${phoneType}" var="item"> 
				   			<option value="${item.itemCode}" <c:if test="${item.itemCode==bean.phoneType}">selected</c:if>>${item.itemName}</option>
				   		</c:forEach>
</select>
</div>
 <HR style= " FILTER: progid:DXImageTransform.Microsoft.Shadow ( color: #987cb9 ,direction : 145, strength :15 ) " width = "100%" color = #987cb9 SIZE= 1>
 <div style="margin-top:10px;width:100%;font-size:15px; font-family:微软雅黑">
手机系统：
   <select name="iosVersion" class="mustFill" title="手机系统">
				   		<option value="">请选择</option>
				   		<c:forEach items="${iosVersion}" var="item"> 
				   			<option value="${item.itemCode}" <c:if test="${item.itemCode==bean.iosVersion}">selected</c:if>>${item.itemName}</option>
				   		</c:forEach>
				   </select>
</div>
 <HR style= " FILTER: progid:DXImageTransform.Microsoft.Shadow ( color: #987cb9 ,direction : 145, strength :15 ) " width = "100%" color = #987cb9 SIZE= 1>
 <div style="margin-top:10px;width:100%;font-size:15px; font-family:微软雅黑">
任务等级：
     <select name="level" class="mustFill" title="任务等级">
				   		<option value="">请选择</option>
				   		<c:forEach items="${userLevel}" var="item"> 
				   			<option value="${item.itemCode}" <c:if test="${item.itemCode==bean.level}">selected</c:if>>${item.itemName}</option>
				   		</c:forEach>
				   </select>
</div>
  <HR style= " FILTER: progid:DXImageTransform.Microsoft.Shadow ( color: #987cb9 ,direction : 145, strength :15 ) " width = "100%" color = #987cb9 SIZE= 1>
 <div style="margin-top:10px;width:100%;font-size:15px; font-family:微软雅黑">
下载类型：
     <select name="downloadType" class="mustFill" title="下载类型">
				   		<option value="">请选择</option>
				   		<c:forEach items="${downloadType}" var="item"> 
				   			<option value="${item.itemCode}" <c:if test="${item.itemCode==bean.downloadType}">selected</c:if>>${item.itemName}</option>
				   		</c:forEach>
				   </select>
</div>

<HR style= " FILTER: progid:DXImageTransform.Microsoft.Shadow ( color: #987cb9 ,direction : 145, strength :15 ) " width = "100%" color = #987cb9 SIZE= 1>
<div>
    是否为注册任务：
    是：<input type="radio" name="isRegister"  value="1"/>
    否 ：<input type="radio" name="isRegister"  value="0" checked/>
</div>
<HR style= " FILTER: progid:DXImageTransform.Microsoft.Shadow ( color: #987cb9 ,direction : 145, strength :15 ) " width = "100%" color = #987cb9 SIZE= 1>
<div>
   是否为模拟任务：
    是：<input type="radio" name="isMock"  value="1"/>
     否 ：<input type="radio" name="isMock"  value="0" checked/>
</div>
<HR style= " FILTER: progid:DXImageTransform.Microsoft.Shadow ( color: #987cb9 ,direction : 145, strength :15 ) " width = "100%" color = #987cb9 SIZE= 1>
 <div> 
外放设置：
      工作室 ：<input type="radio" name="isOpen"  value="1" checked/>
      外放：<input type="radio" name="isOpen"  value="2"/>
      默认 ：<input type="radio" name="isOpen"  value="0"/>
</div>
<HR style= " FILTER: progid:DXImageTransform.Microsoft.Shadow ( color: #987cb9 ,direction : 145, strength :15 ) " width = "100%" color = #987cb9 SIZE= 1>
 <div> 
广告appleStore-ID：
      <input id="adverAdid" name="adverAdid" value="${bean.adverAdid}" size="30" maxlength="100" class='' title="广告storeID" />
</div>
<HR style= " FILTER: progid:DXImageTransform.Microsoft.Shadow ( color: #987cb9 ,direction : 145, strength :15 ) " width = "100%" color = #987cb9 SIZE= 1>
 <div> 
广告ADID：
<input id="adver_adid" name="adid" value="${bean.adid}" size="30" maxlength="100" class='mustFill' title="广告ID" />
</div>

<HR style= " FILTER: progid:DXImageTransform.Microsoft.Shadow ( color: #987cb9 ,direction : 145, strength :15 ) " width = "100%" color = #987cb9 SIZE= 1>
 <div> 
任务备注(可为空)：
<input name="remark" id="remark" style="width:200px" class="" title="备注" type="text" value="<c:out value="${bean.remark}"></c:out>" maxlength="1000" size="60"/>
</div>

<HR style= " FILTER: progid:DXImageTransform.Microsoft.Shadow ( color: #987cb9 ,direction : 145, strength :15 ) " width = "100%" color = #987cb9 SIZE= 1>
 <div> 
bundleId：
     <input id="bundleId" name="bundleId" value="${bean.bundleId}" size="30" maxlength="100" class='mustFill' title="bundleId" />
</div>

<HR style= " FILTER: progid:DXImageTransform.Microsoft.Shadow ( color: #987cb9 ,direction : 145, strength :15 ) " width = "100%" color = #987cb9 SIZE= 1>
 <div> 
广告名称：
   <input name="adverName" value="${bean.adverName}" size="30" maxlength="100" class='mustFill' title="广告名称" />
</div>
 
 <div> 
广告图片:
     <input type="file" name="fileAdverImg"/>
	<input id='adverImg' name='adverImg' type='hidden' value='${bean.adverImg}' maxlength='100'/>
</div>

 <div> 
得分描述：
 <input id='adverDesc' name="adverDesc" value="${bean.adverDesc}" size="30" maxlength="100" class='mustFill' title="得分描述" />
</div>
 
  <div> 
广告数量：
       <input name="adverCount" id="adverCount" class="mustFill" title="广告数量" type="text" value="<c:out value="${bean.adverCount}"></c:out>" maxlength="100"/>
</div>
 


<HR style= " FILTER: progid:DXImageTransform.Microsoft.Shadow ( color: #987cb9 ,direction : 145, strength :15 ) " width = "100%" color = #987cb9 SIZE= 1>
 <div> 
任务回调率0-1：<input name="random" id="random" class="mustFill" title="任务回调率" type="text" value="1" maxlength="100"/>
</div>

<HR style= " FILTER: progid:DXImageTransform.Microsoft.Shadow ( color: #987cb9 ,direction : 145, strength :15 ) " width = "100%" color = #987cb9 SIZE= 1>
 <div> 
任务排序等级：
<input name="adverSort" id="adverSort" class="mustFill" title="任务排序等级" type="text" value="0" maxlength="100"/>
</div>

<HR style= " FILTER: progid:DXImageTransform.Microsoft.Shadow ( color: #987cb9 ,direction : 145, strength :15 ) " width = "100%" color = #987cb9 SIZE= 1>
 <div> 
广告价格：
     <input name="adverPrice" id="adverPrice" class="mustFill" title="广告价格" type="text" value="<c:out value="${bean.adverPrice}"></c:out>" maxlength="100"/>
</div>

<HR style= " FILTER: progid:DXImageTransform.Microsoft.Shadow ( color: #987cb9 ,direction : 145, strength :15 ) " width = "100%" color = #987cb9 SIZE= 1>
 <div> 
广告差价：
     <input name="priceDiff" id="priceDiff" class="mustFill" title="广告差价" type="text" value="<c:out value="${bean.priceDiff}"></c:out>" maxlength="100"/>
</div>


<HR style= " FILTER: progid:DXImageTransform.Microsoft.Shadow ( color: #987cb9 ,direction : 145, strength :15 ) " width = "100%" color = #987cb9 SIZE= 1>
 <div> 
领取后的任务时效()分钟):
    <input name="timeLimit" id="timeLimit" class="mustFill" title="领取任务后的任务时效（单位：分钟）" type="text" value="<c:out value="${bean.timeLimit}"></c:out>" maxlength="10"/>
</div>

<HR style= " FILTER: progid:DXImageTransform.Microsoft.Shadow ( color: #987cb9 ,direction : 145, strength :15 ) " width = "100%" color = #987cb9 SIZE= 1>
 <div> 
应用需要打开的时间(秒)：
    <input name="openTime" id="openTime" class="mustFill" title="应用需要打开的时间（单位：秒）" type="text" value="<c:out value="${bean.openTime}"></c:out>" maxlength="10"/>
</div>

<HR style= " FILTER: progid:DXImageTransform.Microsoft.Shadow ( color: #987cb9 ,direction : 145, strength :15 ) " width = "100%" color = #987cb9 SIZE= 1>
 <div> 
任务增加的时间间隔(秒)：
   <input name="taskInterval" id="taskInterval" class="mustFill" title="任务增加的时间间隔（单位：秒）" type="text" value="<c:out value="${bean.taskInterval}"></c:out>" maxlength="10"/>
</div>
<HR style= " FILTER: progid:DXImageTransform.Microsoft.Shadow ( color: #987cb9 ,direction : 145, strength :15 ) " width = "100%" color = #987cb9 SIZE= 1>
 <div> 
每次增加任务的数量：
   <input name="addTask" id="addTask" class="mustFill" title="每次增加任务的数量" type="text" value="<c:out value="${bean.addTask}"></c:out>" maxlength="10"/>
</div>
<HR style= " FILTER: progid:DXImageTransform.Microsoft.Shadow ( color: #987cb9 ,direction : 145, strength :15 ) " width = "100%" color = #987cb9 SIZE= 1>
 <div> 
增加任务的总数量：
    <input name="addTaskLimit" id="addTaskLimit" class="mustFill" title="增加任务的总数量" type="text" value="<c:out value="${bean.addTaskLimit}"></c:out>" maxlength="10"/>
</div>
<HR style= " FILTER: progid:DXImageTransform.Microsoft.Shadow ( color: #987cb9 ,direction : 145, strength :15 ) " width = "100%" color = #987cb9 SIZE= 1>
 <div> 
领取任务间隔（单位：秒）：
    <input name="receInterTime" id="receInterTime" class="mustFill" title="领取任务间隔（单位：秒）" type="text" value="<c:out value="${bean.receInterTime}"></c:out>" maxlength="10"/>
</div>
<HR style= " FILTER: progid:DXImageTransform.Microsoft.Shadow ( color: #987cb9 ,direction : 145, strength :15 ) " width = "100%" color = #987cb9 SIZE= 1>
 <div> 
提交任务间隔（单位：秒）：
    <input name="submitInterTime" id="submitInterTime" class="mustFill" title="提交任务间隔（单位：秒）" type="text" value="<c:out value="${bean.submitInterTime}"></c:out>" maxlength="10"/>
</div>
<HR style= " FILTER: progid:DXImageTransform.Microsoft.Shadow ( color: #987cb9 ,direction : 145, strength :15 ) " width = "100%" color = #987cb9 SIZE= 1>
 <div> 
掺机型配置0-10：
    <input name="phoneModelPercent" id="phoneModelPercent" class="mustFill" title="掺机型配置0-10" type="text" value="<c:out value="${bean.phoneModelPercent}"></c:out>" maxlength="10"/>
</div>

<HR style= " FILTER: progid:DXImageTransform.Microsoft.Shadow ( color: #987cb9 ,direction : 145, strength :15 ) " width = "100%" color = #987cb9 SIZE= 1>
 <div> 
广告开始日期：
	<input type="text" name="adverDayStartSetting" title="广告开始日期" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" id="adverDayStartSetting" value="<ry:formatDate date='${bean.adverDayStart}' toFmt='yyyy-MM-dd HH:mm'/>"  readonly="readonly" dateFmt="yyyy-MM-dd HH:mm" >
	<input name="adverTimeStart" id="adverTimeStart" type="hidden" value="<c:out value="${bean.adverTimeStart}"></c:out>" maxlength="100"/>
</div>
<HR style= " FILTER: progid:DXImageTransform.Microsoft.Shadow ( color: #987cb9 ,direction : 145, strength :15 ) " width = "100%" color = #987cb9 SIZE= 1>
 <div> 
广告结束日期：
    <input type="text" name="adverDayEndSetting" title="广告结束日期" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" id="adverDayEndSetting" value="<ry:formatDate date='${bean.adverDayEnd}' toFmt='yyyy-MM-dd HH:mm' />" readonly="readonly" dateFmt="yyyy-MM-dd HH:mm" >
					<input name="adverTimeEnd" id="adverTimeEnd" type="hidden" value="<c:out value="${bean.adverTimeEnd}"></c:out>" maxlength="100"/>
</div>

<HR style= " FILTER: progid:DXImageTransform.Microsoft.Shadow ( color: #987cb9 ,direction : 145, strength :15 ) " width = "100%" color = #987cb9 SIZE= 1>
 <div> 
下载地址：
    <input name="fileUrl" id="fileUrl" style="width: 100%" class="" title="下载地址" type="text" value="<c:out value="${bean.fileUrl}"></c:out>" maxlength="1000" size="60"/>
</div>

 <div> 
排重地址：
<input name="flag2" id="flag2" style="width: 100%" class="" title="上传数据地址" type="text" value="<c:out value="${bean.flag2}"></c:out>" maxlength="1000" size="60"/>
</div>
 <div> 
激活地址:
        <input name="flag3" id="flag3" style="width:100%" class="" title="上传数据地址" type="text" value="<c:out value="${bean.flag3}"></c:out>" maxlength="1000"/>
</div>
 <div> 
点击地址：
     <input name="flag4" id="flag4" style="width: 100%" class="" title="上传数据地址" type="text" value="<c:out value="${bean.flag4}"></c:out>" maxlength="1000"/>
</div>
<input type="hidden" name="adverId" value="${bean.adverId}"/>
<input type="hidden" name="isAuth" value="3"/><!-- 无权限限制 -->
	<div class="formBar">
			<button type="button" style="width:100%;height:40px" onclick="checkForm()">提交任务</button>
	</div>
	
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
			if(check())
			{
				$("#adverTimeStart").val($("#adverDayStartSetting").val());
				$("#adverTimeEnd").val($("#adverDayEndSetting").val());
				$("#myform").submit();
			}
		}
		
	</script>
