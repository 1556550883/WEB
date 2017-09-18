<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>

<div class="pageContent" >
   <form method="post" action="role/saveOrUpdate" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
   <div class="pageFormContent" layoutH="53">
		<div id="t2" style="float:left; display:block; margin:10px;  width:250px; height:100%; line-height:21px; background:#FFF;" >
			<ul id="treeUl" class="tree treeFolder treeCheck expand" >
			    <c:forEach var="firstIitem" items="${authList}" >
			    
					<li><a tname="authCode" tvalue="${firstIitem.authCode}" <c:if test="${not empty firstIitem.roleId }">checked="true"</c:if> >${firstIitem.authName}</a>
						<ul>
						   <c:forEach var="secondItem" items="${firstIitem.childAuthority}">
							   <li><a tname="authCode" tvalue="${secondItem.authCode}"  <c:if test="${not empty secondItem.roleId  }">checked="true"</c:if>>${secondItem.authName}</a>
							   <ul>
							   <c:forEach var="thirdItem" items="${secondItem.childAuthority}">
									   <c:if test="${secondItem.authCode==thirdItem.parentAuthCode }">
									      		<li><a tname="authCode" tvalue="${thirdItem.authCode}"  <c:if test="${not empty thirdItem.roleId  }">checked="true"</c:if>>${thirdItem.authName}</a>
												<c:if test="${not empty thirdItem.childAuthority}">
												<ul>
													<c:forEach var="fourthItem" items="${thirdItem.childAuthority}"> 
														<li >
															
															<a tname="authCode" tvalue="${fourthItem.authCode}" <c:if test="${not empty fourthItem.roleId  }">checked="true"</c:if>>${fourthItem.authName}</a>
																
														</li>
										            </c:forEach>
									            </ul>
									             </c:if>
										  </li>
							            </c:if>
						       </c:forEach>
						       </ul>
					       </c:forEach>
						</ul>
					
					</li>
				</c:forEach>
			</ul>
		</div>
		<!-- 输入框 -->
		<div style="float:left; display:block; margin:10px; width:320px; height:100px; line-height:21px; background:#FFF;padding-left: 150px;">
		    <dl>
				<dt>角色名称：</dt>
				<dd><input id="authName" name="roleName" value="${role.roleName}" type="text" size="30" alt="请输入角色名称" class="required"/></dd>
			</dl>
		    <dl>
				<dt>角色排序：</dt>
				<dd><input id="orderby" name="orderby" value="${role.orderby}" type="text" size="30" alt="请输入排序值" class="required digits"/></dd>
			</dl>
		</div>
			
		<!-- 隐藏的值 -->
		<input type="hidden" name="roleId" value="${role.roleId}"></input>
		
 </div>
 <div class="formBar">
	<ul>
		<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
		<li>
			<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
		</li>
	</ul >

 </div>
</form>
</div>

