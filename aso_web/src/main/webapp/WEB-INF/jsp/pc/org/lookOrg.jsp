<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding parentCode="AUTH_TYPE,AUTH_REQUEST_TYPE" bingdingName="authTypes,authRequestTypes"></ry:binding>

<div class="pageContent">

	<div class="pageFormContent" layoutH="30">
		<div style="float:left; display:block; margin:10px;  width:200px; height:100%;  line-height:21px; background:#FFF;">
			<ul class="tree treeFolder ">
			    <c:forEach var="firstIitem" items="${lookOrgList}" varStatus="status">
					<li><a onclick="$.bringBack({orgId:'${firstIitem.orgId}', orgName:'${firstIitem.orgName}'});">${firstIitem.orgName}</a>
						<ul>
						   <c:forEach var="secondItem" items="${firstIitem.childOrg}">
							   <li><a onclick="$.bringBack({orgId:'${secondItem.orgId}', orgName:'${secondItem.orgName}'});">${secondItem.orgName}</a>
							   <c:forEach var="thirdItem" items="${secondItem.childOrg}">
							       <ul>
									   <c:if test="${secondItem.orgCode==thirdItem.parentOrgCode }">
									      <li><a onclick="$.bringBack({orgId:'${thirdItem.orgId}', orgName:'${thirdItem.orgName}'});">${thirdItem.orgName}</a>
												<c:if test="${not empty thirdItem.childOrg}">
												<ul>
													<c:forEach var="fourthItem" items="${thirdItem.childOrg}">
														<li ><a  onclick="javascript:$.bringBack({orgId:'${fourthItem.orgId}', orgName:'${fourthItem.orgName}'})">${fourthItem.orgName}</a></li>
										            </c:forEach>
									            </ul>
									             </c:if>
										  </li>
							            </c:if>
									</ul>		
						       </c:forEach>
					       </c:forEach>
						</ul>
					
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>

	
</div>