<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding parentCode="USERSEX,ORG_TYPE,USER_APP_TYPE,LOGIN_CONTROL,AUTHORITY" bingdingName="usersexs,orgTypeList,userApppType,loginControls,authoritys"></ry:binding>
<div class="pageContent">
	<form method="post" action="userApp/saveOrUpdate" id="forms" class="pageForm required-validate" onsubmit="return iframeCallback(this, navTabAjaxDone);"enctype="multipart/form-data">
		<div class="pageFormContent nowrap" layoutH="57">
			<dl>
				<dt>登录名：</dt>
				<dd>
					<input type="text" name="loginName"  value="${bean.loginName}" maxlength="20" size="30" class="required" alt="请输入登录名"  />
				</dd>
			</dl>
			<dl>
				<dt>密码：</dt>
				<dd>
					<input type="text" name="loginPwd"  value="${bean.loginPwd}"  title="密码" />
				</dd>
			</dl>
			<dl>
				<dt>登录控制：</dt>
				<dd>
					<c:forEach items="${loginControls}" var="item">
					    <label style="width: 80px;"><input type="radio" value="${item.itemCode }" name="loginControl" ${item.itemCode==bean.loginControl ? 'checked':(empty bean.loginControl ? 'checked':'') } >${item.itemName }</label>
		            </c:forEach>
				</dd>
			</dl>
			<!--<dl>
				<dt>出生日期：</dt>
				<dd>
					<input type="text" name="birthday"  value="${bean.birthday}" dateFmt="yyyy-MM-dd" class="date" readonly="true" title="出生日期" />
				</dd>
			</dl>
 
			<dl>
				<dt>用户性别：</dt>
				<dd>
				    <c:forEach items="${usersexs}" var="item">
					    <label style="width: 40px;"><input type="radio" value="${item.itemCode }" name="sex" ${item.itemCode==bean.sex ? 'checked':(empty bean.sex ? 'checked':'') } >${item.itemName }</label>
		            </c:forEach>
				</dd>
			</dl>
			<dl>
				<dt>手机号码：</dt>
				<dd>
					<input type="text" name="phoneNum" value="${bean.phoneNum}" size="30" class="required phone " alt="请输入手机号码" />
				</dd>
			</dl>-->			
		    
			<!--<div class="divider"></div>-->
			
			<!--<dl>
				<dt>用户类型：</dt>
				<dd>
					<select name="userApppType"  class="required">
					    <option value="">---请选择用户类型---</option>
						<c:forEach var="item" items="${userApppType}">
							<option value="${item.itemCode }"  <c:if test="${item.itemCode==bean.userApppType }">selected</c:if>>${item.itemName }</option>
						</c:forEach>
				     </select>
				</dd>
			</dl>-->
			<!--<dl style="width: 370px;height: 130px;">
				<dt>图片：</dt>
				<dd><input type="file" name="picFile" /></dd>
				<dd style="margin-left: 130px;">			
					<c:if test="${bean.headImg!=null}">
						<img  src='file/userapp/${bean.headImg}' style='width:100px;height:100px'>
					</c:if>
				</dd>	
			</dl>-->	
			<!--<dl style="width: 570px;height: 100px;">
				<dt>学生证：</dt>
				<dd><img  src='file/img/${userScoreInfo.studentCar}' style='width:320px;height:320px'></dd>	
			</dl>-->	
			
			<br/><br/><br/><br/><br/><br/><br/><br/><br/>
			
			<table class="table" width="101.7%" layoutH="130">
				<thead>
					<tr>
						<th align="center" width="100px">任务权限</th>
						<th align="center" width="180px">广告名称</th>
						<th align="center" width="80px">广告ID</th>
						<th align="center" width="50px">广告价格</th>
						<th align="center" width="50px">广告数量</th>
						<th align="center" width="65px">广告剩余数量</th>
						<th align="center" width="65px">广告完成数量</th>
						<th align="center" width="220px">广告开始时间——广告结束时间</th>
						<th align="center" width="40px">状态</th>		
					</tr>
				</thead>
				<tbody>
				     <c:forEach var="item" varStatus="idx" items="${adverAuthoritys}">
						<tr class="detail">
						  	<td align="center">
					        	<c:forEach items="${authoritys}" var="item2">
								    <label style="width: 40px;"><input type="radio" value="${item2.itemCode }" class="authority" name="authority${idx.index}" ${item2.itemCode==item.authority ? 'checked':(empty item.authority ? 'checked':'') } >${item2.itemName }</label>
					            </c:forEach>
		        			</td>
							<td>${item.adverName}</td>
							<td>${item.adid}</td>
			                <td>${item.adverPrice}</td>
			                <td>${item.adverCount}</td>
			                <td>${item.adverCountRemain}</td>
			                <td>${item.adverCountComplete}</td>
			                <td>${item.adverDayStart}--${item.adverDayEnd}</td>
			                <td><c:if test="${item.adverStatus==0}">未审核</c:if><c:if test="${item.adverStatus==1}">启用</c:if><c:if test="${item.adverStatus==2}">停用</c:if><c:if test="${item.adverStatus==3}">已支付</c:if></td>
							<input name="" class="adverId" id="" type="hidden" value="${item.adverId}"/>
						</tr>
					</c:forEach>
					<input name="excludeAdverId" id="excludeAdverId" type="hidden" value=""/>
				</tbody>
			</table>
		</div>
		
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="formSubmit();">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
		<!-- 隐藏的值 -->
		<input type="hidden"  name="userAppId" value="${bean.userAppId }"/>
		<input type="hidden"  name="userNum" value="${bean.userNum}"/>
		<input type="hidden"  name="userApppType" value="1"/>
	</form>
</div>
<script>
  function formSubmit(){
		//处理广告权限
	  	var excludeAdverId = "";
	    $(".detail").each(function(index,domEle){
	    	$(domEle).find(".authority").each(function(index2,domEle2){
		    	if($(domEle2).attr("value") == "0" && $(domEle2).attr("checked") == "checked"){
		    		excludeAdverId += $(domEle).find(".adverId").attr("value") + ",";
		    	}
			  });
	    	
		  });
	  	$("#excludeAdverId").attr("value", excludeAdverId);
	  	//alert($("#excludeAdverId").attr("value"));
		$('#forms').submit();	
	}
</script>