<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding parentCode="AUTH_TYPE,AUTH_REQUEST_TYPE" bingdingName="authTypes,authRequestTypes"></ry:binding>
<div class="pageContent">
	<form method="post" action="auth/saveOrUpdate" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent" layoutH="56" >
			<p>
				<label>权限名称：</label>
				<!--<c:if test="${empty authList or auth.parentAuthCode=='A_0000'}" >-->
					
				<!--</c:if>-->
				<input type="hidden" name="parentAuthCode" value="${auth.parentAuthCode}"/>
				<input type="hidden" name="authId" value="${auth.authId}"></input>
				<input name="authName" class="required" type="text" size="30" value="${auth.authName}" alt="请输入权限名称"/>
			</p>
			<p>
				<label>权限值：</label>
				<input class="input-xlarge disabled" size="30"  type="text" value="${auth.authCode}" disabled="" alt="此项由系统管理">
				<input type="hidden"  name="authCode" value="${auth.authCode}">
			</p>
			<p>
				<label>权限类型：</label>
				<select id="selectError3" id="authType" name="authType" style="width: 183px;" >
				    <c:forEach var="item" items="${authTypes}">
					   <option value="${item.itemCode }" <c:if test="${item.itemCode==auth.authType}">selected</c:if>>${item.itemName }</option>
				    </c:forEach>
			   </select>
			</p>
			<p>
				<label>URL：</label>
				<input name="authUrl" class="required" type="text" value="${auth.authUrl}" size="30" alt="请输入URL"/>
			</p>
			<p>
				<label>排序值：</label>
				<input type="text" class="digits" name="authOrderby" size="30" value="${auth.authOrderby}" />
			</p>
			<!--<c:if test="${not empty authList && auth.parentAuthCode!='-1'}" >
			  <p>
				<label>父类：</label>
				<select  name="parentAuthCode" id="parentAuthCode" style="width: 183px;">
				 		<c:forEach var="item" items="${authList}">
							<option value="${item.authCode }" <c:if test="${item.authCode==auth.parentAuthCode}">selected</c:if>>${item.authName }</option>
						</c:forEach>
				 </select>
			</p>
		    </c:if>-->
			<p>
				<label>显示类型：</label>
				<select  name="requestType" id="requestType" style="width: 183px;">
				 	<c:forEach var="item" items="${authRequestTypes}">
						<option value="${item.itemCode }" <c:if test="${item.itemCode==auth.requestType}">selected</c:if>>${item.itemName }</option>
					</c:forEach>
				</select>
			</p>
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				
				<c:if test="${!empty auth.authCode}">
				    <c:if test="${empty levels or levels!='level_4'}">
					    <li><a class="buttonActive" href="auth/add?parentAuthCode=${auth.authCode}" target="dialog" rel="dia_s_u"  mask="true" title="新增菜单" height="500"><span>新增子菜单</span></a></li>
					</c:if>
					<c:if test="${auth.parentAuthCode!='A_0000'}" >
					<li><a class="buttonActive" href="auth/deleteAuth?authCode=${auth.authCode}" target="ajaxTodo" rel="del_au_id" callback="dialogAjaxDone" title="确定要删除 “${auth.authName}”吗?"><span>删除</span></a></li>
					</c:if>
				</c:if>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">
function testConfirmMsg(url, data){
	alertMsg.confirm("您修改的资料未保存，请选择保存或取消！", {
		okCall: function(){
			$.post(url, data, DWZ.ajaxDone, "json");
		}
	});
}

</script>
