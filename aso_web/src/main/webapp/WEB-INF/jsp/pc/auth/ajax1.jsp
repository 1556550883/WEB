<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="ry" uri="http://www.ruanyun.com/taglib/ry" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="js/showModal.js"></script>
<ry:binding parentCode="AUTH_TYPE,AUTH_REQUEST_TYPE" bingdingName="authTypes,authRequestTypes"></ry:binding>
<form class="form-horizontal" method="post" action="auth/saveOrUpdate" name="myform" id="myform" >
				<fieldset>
							  <div class="control-group">
								<label class="control-label" for="focusedInput">权限名称</label>
								<div class="controls">
								<c:if test="${empty authList or parentAuthCode==-1}" >
									<input type="hidden" name="parentAuthCode" value="${auth.parentAuthCode}"></input>
								</c:if>
									<input type="hidden" name="authId" value="${auth.authId}"></input>
								    <input id="authName" name="authName" class="input-xlarge focused" id="focusedInput" type="text" value="${auth.authName}">
								</div>
								</div>
									  <div class="control-group">
								<label class="control-label" for="disabledInput">权限值</label>
								<div class="controls">
								 <input class="input-xlarge disabled"  type="text" placeholder="${auth.authCode}" disabled="">
								 <input type="hidden"  name="authCode" value="${auth.authCode}">
								</div>
							  </div>
							   <div class="control-group">
								<label class="control-label" for="select">权限类型</label>
								<div class="controls">
								  <select id="selectError3" id="authType" name="authType" data-rel="chosen" style="width: 62%">
									<c:forEach var="item" items="${authTypes}">
										<option value="${item.itemCode }" <c:if test="${item.itemCode==auth.authType}">selected</c:if>>${item.itemName }</option>
									</c:forEach>
								  </select>
								</div>
							  </div>
							  <div class="control-group">
								<label class="control-label" for="disabledInput">URL</label>
								<div class="controls">
								 <input class="input-xlarge focused" id="authUrl" name="authUrl" type="text" value="${auth.authUrl}">
								</div>
							  </div>
							  <div class="control-group">
								<label class="control-label" for="disabledInput">排序值</label>
								<div class="controls">
								<input id="authOrderby" name="authOrderby" class="input-xlarge focused" id="focusedInput" type="text" value="${auth.authOrderby}">
								</div>
							  </div>
							  <c:if test="${not empty authList && auth.parentAuthCode!='-1'}" >
								   <div class="control-group">
									<label class="control-label" for="disabledInput">父类</label>
									<div class="controls">
										<select  name="parentAuthCode" id="parentAuthCode" style="width: 62%">
									 		<c:forEach var="item" items="${authList}">
												<option value="${item.authCode }" <c:if test="${item.authCode==auth.parentAuthCode}">selected</c:if>>${item.authName }</option>
											</c:forEach>
									  </select>
									</div>
								  </div>
							  </c:if>
							  <div class="control-group">
								<label class="control-label" for="disabledInput">显示类型</label>
								<div class="controls">
								<select  name="requestType" id="requestType" style="width: 62%">
								 	<c:forEach var="item" items="${authRequestTypes}">
										<option value="${item.itemCode }" <c:if test="${item.itemCode==auth.requestType}">selected</c:if>>${item.itemName }</option>
									</c:forEach>
								  </select>
								</div>
							  </div>
							  <div class="control-group" style="margin-left: 25%;">
								<button type="button" class="btn btn-primary" onclick="saveOrUpdateNode();">保存</button>
								<c:if test="${!empty auth.authCode}">
									<button type="button" class="btn btn-primary" onclick="addNode('${auth.authCode}');">新增子菜单</button>
									<%-- <button type="button" class="btn" onclick="showModal('删除','您确定删除  ${auth.authName} 吗？','dels(${auth.authCode})');">删除</button> --%>
									 <a class="btn btn-danger" href="javascript:showModal('删除','您确定删除  ${auth.authName} 吗？','dels(\'${auth.authCode}\',\'${auth.authName}\')');">
	                                        <i class="icon-trash icon-white"></i>删除</a>
								</c:if>
								<button type="button" class="btn" onclick="clearAuth();">清除</button>
							  </div>
	</fieldset>
</form>
