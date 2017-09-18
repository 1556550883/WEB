<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<%@include file="/WEB-INF/jsp/inc/ume.jsp"%>
    
<ry:binding bingdingName="taskType,phoneType,adverStepCount,adverType,fileType,effectiveType,effectiveSource" parentCode="TASK_TYPE,PHONE_TYPE,ADVER_STEP_COUNT,ADVER_TYPE,FILE_TYPE,EFFECTIVE_TYPE,EFFECTIVE_SOURCE"></ry:binding>
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
			<dl>
				<dt>广告ID：</dt>
				<dd>
					<input name="adid" value="${bean.adid}" size="30" maxlength="100" class='mustFill' title="广告ID" />
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
			<!-- <dl style="width: 100%">
				<dt>广告包名：</dt>
				<dd>
					<input type="text" name="packageName" value="${bean.packageName}" style="width: 367px" maxlength="100" size="100" title="广告包名" class=''  />
				</dd>
			</dl> -->
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
			<!-- <dl style="width: 100%">
				<dt>在appstore位置：</dt>
				<dd>
				<input type="text" value="${bean.flag1}" name="flag1"/>
				</dd>
			</dl> -->
			<dl class="nowrap" style="width: 100%">
				<dt>广告数量：</dt>
				<dd>
				    <input name="adverCount" id="adverCount" class="mustFill" title="广告数量" type="text" value="<c:out value="${bean.adverCount}"></c:out>" maxlength="100"/>
				</dd>
		    </dl>
		    <dl class="nowrap" style="width: 100%">
				<dt>广告价格：</dt>
				<dd>
				    <input name="adverPrice" id="adverPrice" class="mustFill" title="广告价格" type="text" value="<c:out value="${bean.adverPrice}"></c:out>" maxlength="100"/>
				</dd>
		    </dl>
		    <dl class="nowrap" style="width: 100%">
				<dt>领取任务后的任务时效（单位：分钟）：</dt>
				<dd>
				    <input name="timeLimit" id="timeLimit" class="mustFill" title="领取任务后的任务时效（单位：分钟）" type="text" value="<c:out value="${bean.timeLimit}"></c:out>" maxlength="10"/>
				</dd>
		    </dl>
		    <!-- <dl class="nowrap" style="width: 100%">
				<dt>广告类型：</dt>
				<dd>
				   <select name="adverType"  class="" title="广告类型">
				   		<option value="">请选择</option>
				   		<c:forEach items="${adverType}" var="item"> 
				   			<option value="${item.itemCode}" <c:if test="${item.itemCode==bean.adverType}">selected</c:if>>${item.itemName}</option>
				   		</c:forEach>
				   </select>
				</dd>
		    </dl> -->
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
		    <!-- <dl class="nowrap" style="width: 100%">
				<dt>广告来源：</dt>
				<dd>
				   <select name="effectiveSource" id="effectiveSource" onchange="updSourse()" class="" title="广告来源" >
				   		<option value="">请选择</option>
				   		<c:forEach items="${effectiveSource}" var="item">
				   			<option value="${item.itemCode}" <c:if test="${item.itemCode==bean.effectiveSource}">selected</c:if>>${item.itemName}</option>
				   		</c:forEach>
				   </select>
				</dd>
		    </dl>
		    <dl class="nowrap" style="width: 100%;height: auto;">
				<dt>广告介绍：</dt>
				<dd style="height: auto;">
					<div id="myeditor" style="width: 600px;height: 350px;"></div>
				    <input name="adverRemand" id="adverRemand" type="hidden" value='${bean.adverRemand}'/>
				</dd>
		    </dl>-->
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
		    <!-- <dl class="nowrap" style="width: 100%"  >
				<dt>完成步骤：</dt>
				<dd>
				   <select name="adverStepCount" onchange="addStep(this.value)"  class="" title="完成步骤" >
				   		<option value="">请选择</option>
				   		<c:forEach items="${adverStepCount}" var="item">
				   			<option value="${item.itemCode}" <c:if test="${item.itemCode==bean.adverStepCount}">selected</c:if>>${item.itemName}</option>
				   		</c:forEach>
				   </select>
				</dd>
		    </dl>
		    <dl class="nowrap" style="width: 100%;height: auto" >
				<dt>完成步骤：</dt>
				<dd style="width: 90%;height: auto">
					<table class="new_tab">
				   		<thead>
				   			<tr>
				   				<td width="100px">步骤序号</td>
				   				<td width="100px">步骤名称</td>
				   				<td width="100px">描述</td>
				   				<td width="100px">分数</td>
				   				<td width="100px">使用时间(天)</td>
				   				<td width="100px">判断有效来源</td>
				   				<td width="100px">有效时间(分钟)</td>
				   			</tr>
				   		</thead>
				   		<tbody class="stepTable" id="stepBody">
				   			<c:forEach items="${adverStepList}" var="items" varStatus="s">
				   				<tr><td>${s.count}</td><td><input name='stepName' value='${items.adverStepName}' class='mustFill' title='步骤名称' maxlength='100'/></td><td>
				   					<input name='stepDesc' value='${items.stepDesc}' class='mustFill' title='步骤描述' maxlength='100'/></td>
					   				<td><input name='stepScore' value='${items.price}' class='mustFill' title='步骤分数' maxlength='20'/></td>
					   				<td><input name='stepUseTime' value='${items.useTimeDay}' class='mustFill' title='使用时间（天）' maxlength='10'/></td>
					   				<td class='sourse'><ry:show itemCode="${items.effectiveSource}" parentCode="EFFECTIVE_SOURCE"></ry:show></td>
					   				<td><input name='stepMinCount' value='${items.effectiveMinCount}' class='mustFill' title='有效时间（分钟）' maxlength='10'/></td></tr>
				   			</c:forEach>
				   		</tbody>
					</table>
				</dd>
		    </dl>
		    <dl class="nowrap" style="width: 100%">
				<dt>文件类型：</dt>
				<dd>
				     <select name="fileType" onchange="addfile(this.value)"  class="" title="文件类型" >
				   		<option value="">请选择</option>
				   		<c:forEach items="${fileType}" var="item">
				   			<option value="${item.itemCode}" <c:if test="${item.itemCode==bean.fileType}">selected</c:if>>${item.itemName}</option>
				   		</c:forEach>
				   </select>
				</dd>
		    </dl> -->
		   <!--<dl style="width: 100%;">
			<dt>是否添加权限：</dt>
			<dd>
				<select name="isAuth" class="required">
				   <option value="">---请选择---</option>
					  <c:forEach items="${isAuth}" var="item">
						 <option value="${item.itemCode }" <c:if test="${item.itemCode==bean.isAuth}">selected="selected"</c:if> >${item.itemName }</option>
				       </c:forEach>
				</select>	
			</dd>
			</dl>
		    <dl class="nowrap" id="showType" style="width: 100%;display: none;">
				<dt>上传文件或填写url：</dt>
				<dd id="fileType">
				</dd>
		    </dl>-->
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
	/*var ue=UE.getEditor("myeditor",{
		autoHeightEnabled: false
	});
		ue.ready(function() {
		ue.setContent($("#adverRemand").val());
	});*/
		
	
	function checkForm(){
		//$("#adverRemand").val(ue.getContent());
		if(check()){
			$("#adverTimeStart").val($("#adverDayStart").val());
			$("#adverTimeEnd").val($("#adverDayEnd").val());
			$("#myform").submit();
			}
		}
	function updSourse(){
		$(".sourse").text($("#effectiveSource").find("option:selected").text());
		}
		/*function addStep(stepNum){
			var stepbody="";
			for(var i=0;i<stepNum;i++){
				var tr="<tr><td>"+(i+1)+"</td><td><input name='stepName' class='mustFill' title='步骤名称' value='' maxlength='100'/></td><td><input name='stepDesc' title='步骤描述' class='mustFill' value='' maxlength='100'/></td>";
   				tr+="<td><input name='stepScore' value='' class='mustFill' title='步骤分数' maxlength='10'/></td>";
   				tr+="<td><input name='stepUseTime' value='' class='mustFill' title='使用时间' maxlength='10'/></td>";
   				tr+="<td class='sourse'>"+$("#effectiveSource").find("option:selected").text();+"</td>";
   				tr+="<td><input name='stepMinCount' value='5' class='mustFill' title='有效时间（分钟）' maxlength='10'/></td></tr>";
   				stepbody+=tr;
				}
   				$("#stepBody").html(stepbody);
			}
		addfile(${bean.fileType});
		function addfile(type){
			var file="";
			if(type==1){
				$("#showType").css('display','block');
				file="<input name='file' type='file' value='上传'/><input name='fileUrl' type='hidden' value='${bean.fileUrl}' maxlength='100'/>";
				}else if(type==2){
					$("#showType").css('display','block');
					file="<input name='fileUrl' type='text' value='${bean.fileUrl}' maxlength='100'/>";
					}
			$("#fileType").html(file);
		}*/
	</script>
</div>