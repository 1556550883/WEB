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
	<form method="post" id="myform"  action="channelAdverInfo/add?channelNum=${channelNum}" onsubmit="return iframeCallback(this, navTabAjaxDone);" enctype="multipart/form-data" >
		<div class="pageFormContent nowrap" layoutH="57">
  		    <dl class="nowrap" style="width: 100%">
				<dt>任务类型：</dt>
				<dd>
				   <select name="taskType" class="mustFill" title="任务类型">
				   		<option value="">请选择</option>
				   		<c:forEach items="${taskType}" var="item"> 
				   			<option id='taskType' value="${item.itemCode}" <c:if test="${item.itemCode==0}">selected</c:if>>${item.itemName}</option>
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
				   			<option value="${item.itemCode}" <c:if test="${item.itemCode=='iPhone6'}">selected</c:if>>${item.itemName}</option>
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
				   			<option value="${item.itemCode}" <c:if test="${item.itemCode==9}">selected</c:if>>${item.itemName}</option>
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
				   			<option value="${item.itemCode}" <c:if test="${item.itemCode==10}">selected</c:if>>${item.itemName}</option>
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
				   			<option value="${item.itemCode}" <c:if test="${item.itemCode==0}">selected</c:if>>${item.itemName}</option>
				   		</c:forEach>
				   </select>
				</dd>
		    </dl>
		    
		    <dl class="nowrap" style="width: 100%">
				<dt>是否为注册任务：</dt>
				<dd>
				    是：<input type="radio" name="isRegister"  value="1"/>
				   否 ：<input type="radio" name="isRegister"  value="0" checked/>
				</dd>
		    </dl>
	       <dl class="nowrap" style="width: 100%">
			<dt>是否为模拟 任务：</dt>
			<dd>
			    是：<input type="radio" name="isMock"  value="1"/>
			   否 ：<input type="radio" name="isMock"  value="0" checked/>
			</dd>
		    </dl>
		     
		     <dl class="nowrap" style="width: 100%">
				<dt>外放设置：</dt>
				<dd style="width: 300px">
					   工作室 ：<input type="radio" name="isOpen"  value="1"/>
					    外放：<input type="radio" name="isOpen"  value="2"/>
					    默认 ：<input type="radio" name="isOpen"  value="0" checked/>
				</dd>
		    </dl>
		    
			<dl class="nowrap" style="width: 100%;">
				<dt>广告appleStore-ID：</dt>
				<dd>
					<input id="adverAdid" name="adverAdid" value="${bean.adverAdid}" size="30" maxlength="100" class='' title="广告storeID" />
				</dd>
			</dl>
			
			<dl class="nowrap" style="width: 100%;">
				<dt>广告ADID：</dt>
				<dd>
					<input id="adver_adid" name="adid" value="${bean.adid}" size="30" maxlength="100" class='mustFill' title="广告ID" />
				</dd>
			</dl>
			 <dl class="nowrap" style="width: 100%;">
				<dt>任务备注(可为空)：</dt>
				<dd>
				    <input name="remark" id="remark" style="width: 467px" class="" title="备注" type="text" value="<c:out value="${bean.remark}"></c:out>" maxlength="1000" size="60"/>
				</dd>
		    </dl>
			<dl style="width: 100%">
				<dt>bundleId：</dt>
				<dd>
					<input id="bundleId" name="bundleId" value="${bean.bundleId}" size="30" maxlength="100" class='mustFill' title="bundleId" />
				</dd>
			</dl>
			<dl class="nowrap"  style="width: 100%;">
				<dt>广告图片：</dt>
				<dd >
					<input type="file" name="fileAdverImg"/>
					<input id='adverImg' name='adverImg' type='hidden' value='${bean.adverImg}' maxlength='100'/>
				</dd>
		    </dl>
		    <dl class="nowrap" style="width: 100%">
				<dt>广告价格：</dt>
				<dd>
				    <input name="adverPrice" id="adverPrice" class="mustFill" title="广告价格" type="text" value="<c:out value="${bean.adverPrice}"></c:out>" maxlength="100"/>
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
		    
		    <dl style="width: 100%;">
			    <div id="advers">
			    	<button type="button" onclick="addOneAdver()">>>添加广告</button>
			    	<br>
			   			<hr class="dotline" color="#111111" size="1"/>
			   			<dl style="width: 100%">
							<dt>广告名称：</dt>
							<dd>
								<input id='adverName' name="adverName" value="" size="30" maxlength="100" class='mustFill' title="广告名称" />
							</dd>
						</dl>
						<dl style="width: 100%">
							<dt>广告数量：</dt>
							<dd>
								<input name="adverCount" value="" size="30" maxlength="100" class='mustFill' title="广告数量" />
							</dd>
						</dl>
						<dl style="width: 100%">
							<dt>得分描述：</dt>
							<dd>
								<input id='adverDesc' name="adverDesc" value="" size="30" maxlength="100" class='mustFill' title="得分描述" />
							</dd>
						</dl>
				</div>
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
		<input type="hidden" id="adversJson" name="adversJson" value=''/>
	</form>
	
	<script type="text/javascript">
		var addOneAdverIndex = 0;
		var channelNum = ${channelNum};
		$(function(){
			 $("#adverAdid").change(function(){
			 	var storeid = $("#adverAdid").val();
		   		$.ajax({
	             type: "GET",
	             url: "channelAdverInfo/getAppDetail",
	             data: {appStoreID:storeid,channelNum:channelNum},
	             dataType: "json",
	            success:function(data){
	            	var json = eval(data);
	            	if(json["isexist"]){
	            		var adverinfo = json["adverInfo"];
	            		$("#adver_adid").val(adverinfo["adid"]);
	            		$("#bundleId").val(adverinfo["bundleId"]);
	            		$("#flag2").val(adverinfo["flag2"]);
	            		$("#flag3").val(adverinfo["flag3"]);
	            		$("#flag4").val(adverinfo["flag4"]);
	            		$("#openTime").val(adverinfo["openTime"]);
	            		$("#timeLimit").val(adverinfo["timeLimit"]);
	            		$("#adverPrice").val(adverinfo["adverPrice"]);
	            		$("#adverImg").val(adverinfo["adverImg"]);
	            		$("#priceDiff").val(adverinfo["priceDiff"]);
	            		$("#taskInterval").val(adverinfo["taskInterval"]);
	            		$("#addTask").val(adverinfo["addTask"]);
	            		$("#addTaskLimit").val(adverinfo["addTaskLimit"]);
	            		$("#remark").val(adverinfo["remark"]);
	            	}else{
	            		var bundleid = json["bundleid"];
	            		$("#bundleId").val(bundleid);
	            	}
	            }
		  	});
		  });
		  
		  $("#adverName").change(function(){
		  	var storeid = $("#adverAdid").val();
		  	var keyword = $("#adverName").val();
		   		$.ajax({
	             type: "GET",
	             url: "channelAdverInfo/getKeywordRank",
	             data: {keyword:keyword,appStoreID:storeid},
	             dataType: "json",
	            success:function(data){
	            		var json = eval(data);
	            		var rank = json["rank"] + "位";
	            		$("#adverDesc").val(rank);
	            	}
	            });
		  }); 
		});
		
		function checkForm(){
			//$("#adverRemand").val(ue.getContent());
			if(check()){
				$("#adverTimeStart").val($("#adverDayStart").val());
				$("#adverTimeEnd").val($("#adverDayEnd").val());
				
				var adversJson = '[';
				$("#advers").find("input").each(function(index,domEle){
		    		  if(index%3 == 0){
		    			  if(adversJson.length > 1){
		    				  adversJson += ',';
		    			  }
		    			  adversJson += '{';
		    			  adversJson += '"adverName":"'+ $(this).val() +'"';
		    		  }else if(index%3 == 1){
		    			  adversJson += ',"adverCount":'+ $(this).val();
		    		  }else{
		    			  adversJson += ',"adverDesc":"'+ $(this).val() +'"';
		    			  adversJson += '}';
		    		  }
				  });
				adversJson += ']';
				$("#adversJson").val(adversJson);
				
				$("#myform").submit();
			}
		}
		
		function updSourse(){
			$(".sourse").text($("#effectiveSource").find("option:selected").text());
		}
		
		//添加广告
		function addOneAdver()
		{
			adverNameId = 'adverName' + addOneAdverIndex; 
			adverDescId = 'adverDesc' + addOneAdverIndex; 
			addOneAdverIndex = addOneAdverIndex + 1;
			var oneAdver = "<hr class='dotline' color='#111111' size='1'/>"
				+"<dl style='width: 100%'>"
				+"<dt>广告名称：</dt>"
				+"<dd>"
				+"<input id=" + adverNameId 
				+" name='adverName' size='30' maxlength='100' class='mustFill' title='广告名称' />"
				+"</dd>"
				+"</dl>"
				+"<dl style='width: 100%'>"
				+"<dt>广告数量：</dt>"
				+"<dd>"
				+"<input name='adverCount' size='30' maxlength='100' class='mustFill' title='广告数量' />"
				+"</dd>"
				+"</dl>"
				+"<dl style='width: 100%'>"
				+"<dt>得分描述：</dt>"
				+"<dd>"
				+"<input id="+adverDescId
				+ " name='adverDesc' size='30' maxlength='100' class='mustFill' title='得分描述' />"
				+"</dd>"
				+"</dl>";
			$("#advers").append(oneAdver); 
			
		  var nameid = "#" + adverNameId;
		  var descid = "#" + adverDescId;
		  $(nameid).change(function(){
		  	var storeid = $("#adverAdid").val();
		  	var keyword = $(nameid).val();
		   		$.ajax({
	             type: "GET",
	             url: "channelAdverInfo/getKeywordRank",
	             data: {keyword:keyword,appStoreID:storeid},
	             dataType: "json",
	            success:function(data){
	            		var json = eval(data);
	            		var rank = json["rank"] + "位";
	            		$(descid).val(rank);
	            	}
	            });
		  }); 
		}
	</script>
</div>