<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding parentCode="USERSEX,ORG_TYPE" bingdingName="usersexs,orgTypeList"></ry:binding>
<div class="pageContent">
	<form method="post" action="user/updatePersonalInfo" id="forms" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="57">
			<dl>
				<dt>登录名称：</dt>
				<dd>
					<input type="text" id="loginName" name="loginName" readonly="readonly" value="${user.loginName}" maxlength="20" size="30"/>
				</dd>
			</dl>
			<dl>
				<dt>真实姓名：</dt>
				<dd>
					<input type="text" name="userName" value="${user.userName}" size="30" class="required" alt="请输入真实姓名"/>
				</dd>
			</dl>
<!--			<dl  class="nowrap">-->
<!--				<dt>所属组织：</dt>-->
<!--				<dd>-->
<!--					<input   name="orgId" value="${user.orgId}" type="hidden"/>-->
<!--				    <input size="30" type="text" readonly="readonly" value="${user.orgName}"/>-->
<!--				</dd>-->
<!--			</dl>-->
<!--			<dl>-->
<!--				<dt>单位名称：</dt>-->
<!--				<dd>-->
<!--				   <input type="text" value="${user.unitName}" name="unitName" size="30" class="required" alt="请输入单位名称" >-->
<!--				</dd>-->
<!--			</dl>-->
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
					<input type="text" name="userPhone" value="${user.userPhone}" size="30" class="required" alt="请输入电话号码" />
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
				    <textarea name="address" class="required textInput" cols="60" rows="6">${user.address }</textarea>
				</dd>
		    </dl>
			
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit" >提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
		<!-- 隐藏的值 -->
		<input type="hidden" name="userId" value="${user.userId}"/>
	</form>
</div>
