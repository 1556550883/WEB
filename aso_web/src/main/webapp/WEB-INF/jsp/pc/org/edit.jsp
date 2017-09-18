<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding parentCode="AUTH_TYPE,AUTH_REQUEST_TYPE" bingdingName="authTypes,authRequestTypes"></ry:binding>
<div class="pageContent">
	<form method="post" action="org/saveOrUpdate" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent" layoutH="56" >
			<p>
				<label>组织名称：</label>
				<!--<c:if test="${empty orgList or org.parentOrgCode=='R_199'}" >-->
					
				<!--</c:if>-->
				<input type="hidden" name="parentOrgCode" value="${org.parentOrgCode}"/>
				<input type="hidden" name="orgId" value="${org.orgId}"></input>
				<input name="orgName" class="required" type="text" size="30" value="${org.orgName}" alt="请输入组织名称"/>
			</p>
			<p>
				<label>组织CODE值：</label>
				<input class="input-xlarge disabled" size="30"  type="text" value="${org.orgCode}" alt="此项由系统管理" disabled="">
				<input type="hidden"  name="orgCode" value="${org.orgCode}">
			</p>
			<!--<c:if test="${not empty orgList && levels=='level_4'}" >
			  <p>
				<label>父类：</label>
				<select  name="parentOrgCode" id="parentOrgCode" style="width: 183px;">
				 		<c:forEach var="item" items="${orgList}">
							<option value="${item.orgCode }" <c:if test="${item.orgCode==org.parentOrgCode}">selected</c:if>>${item.orgName }</option>
						</c:forEach>
				 </select>
			</p>
		    </c:if>-->
			<p>
				<label>排序值：</label>
				<input type="text" class="digits" name="orgOrder" size="30" value="${org.orgOrder}" />
			</p>
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				
				<c:if test="${!empty org.orgCode}">
				    <c:if test="${empty levels or levels!='level_4'}">
				        <li><a class="buttonActive" href="org/add?parentOrgCode=${org.orgCode}" target="dialog" rel="org_dialog"  mask="true" title="新增菜单" height="500"><span>新增子菜单</span></a></li>
				    </c:if>
				    <c:if test="${org.parentOrgCode!='R_199'}" >
				        <li><a class="buttonActive" href="org/delOrg?orgCode=${org.orgCode}&orgId=${org.orgId}" target="ajaxTodo" callback="dialogAjaxDone" rel="del_au_id"  title="确定要删除 “${org.orgName}”吗?"><span>删除</span></a></li>
					</c:if>
				</c:if>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>

