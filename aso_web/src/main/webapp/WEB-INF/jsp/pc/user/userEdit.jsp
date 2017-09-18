<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding parentCode="USERSEX,ORG_TYPE" bingdingName="usersexs,orgTypeList"></ry:binding>
<div class="pageContent">
	<form method="post" action="user/saveOrUpdate" id="forms" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="57">
			<dl>
				<dt>登录名称：</dt>
				<dd>
					<input type="text" id="loginName" name="loginName"  value="${user.loginName}" maxlength="20" size="30" class="required" alt="请输入登录名称"  />
					<span id="fali_show" class="info" style="color: red;display:none;">登录名已被使用，请重新输入</span>
				</dd>
			</dl>
			<dl>
				<dt>真实姓名：</dt>
				<dd>
					<input type="text" name="userName" value="${user.userName}" size="30" class="required" alt="请输入真实姓名"/>
				</dd>
			</dl>
 
			<dl>
				<dt>用户性别：</dt>
				<dd>
				    <c:forEach items="${usersexs}" var="item">
					    <label><input type="radio" value="${item.itemCode }" name="userSex" ${item.itemCode==user.userSex ? 'checked':(empty user.userSex ? 'checked':'') } >${item.itemName }</label>
		            </c:forEach>
				</dd>
			</dl>
			<dl>
				<dt>电话号码：</dt>
				<dd>
					<input type="text" name="userPhone" value="${user.userPhone}" size="30" class="required phone " alt="请输入电话号码" />
				</dd>
			</dl>			
			<dl>
				<dt>邮箱地址：</dt>
				<dd>
					<input type="text" name="userEmail" value="${user.userEmail}"  size="30" alt="请输入邮箱地址" class="required email" />
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>地址：</dt>
				<dd>
				<!-- class="required textInput" -->
				    <textarea name="address"  cols="60" rows="6">${user.address }</textarea>
				</dd>
		    </dl>
			<div class="divider"></div>
			
			<dl>
				<dt>用户角色：</dt>
				<dd>
					<select id="roleId" name="roleId"  style="width: 31%" class="required">
					    <option value="">---请选择角色---</option>
						<c:forEach var="item" items="${allRoles}">
							<option value="${item.roleId }"  <c:if test="${item.roleId==userRole.roleId}">selected</c:if>>${item.roleName }</option>
						</c:forEach>
				     </select>
				</dd>
			</dl>
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="formSubmit();">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
		<!-- 隐藏的值 -->
		<input type="hidden" id="userId" name="userId" value="${user.userId}"/>
	</form>
</div>
<script>
  function formSubmit(){
	     var loginName=$('#loginName').val();
	     var userId=$('#userId').val();
	     if(loginName!='${user.loginName}'){
		     $.ajax({
		    	 type:'post',//可选get
		    	 url:'user/searchAjaxName',//这里是接收数据的PHP程序
		    	 data:'loginName='+loginName+'&userId='+userId,//传给PHP的数据，多个参数用&连接
		    	 dataType:'text',//服务器返回的数据类型 可选XML ,Json jsonp script html text等
		    	 success:function(msg){
		    	    if(msg=='fail'){
			    	    $('#fali_show').show();
			    	}else{
			    		$('#fali_show').hide();
			    		 $('#forms').submit();	
				    }
		    	 },
		    	 error:function(){
		    	 }
		    });
	   }else{
		   $('#fali_show').hide();
		   $('#forms').submit();
		  }	    	 	     
	}
</script>