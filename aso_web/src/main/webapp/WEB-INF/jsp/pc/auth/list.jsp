<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<div id="resultBox"></div>
<div class="pageContent" >
	<div class="pageFormContent" layoutH="21">
		<div style="float:left; display:block; margin:10px;  width:200px; height:100%;  line-height:21px; background:#FFF;">
			<ul class="tree treeFolder ">
			    <c:forEach var="firstIitem" items="${authList}" varStatus="firstStatus">
					<li><a href="auth/edit?authId=${firstIitem.authId}" target="dialog"  mask="true" title="${firstIitem.authName}" height="500" rel="dia_s_u">${firstIitem.authName}</a>
						<ul>
						   <c:forEach var="secondItem" items="${firstIitem.childAuthority}" varStatus="secondStatus">
							   <li><a href="auth/edit?authId=${secondItem.authId}" target="dialog"  mask="true" title="${secondItem.authName}" height="500" rel="dia_s_u">${secondItem.authName}</a>
							   <c:forEach var="thirdItem" items="${secondItem.childAuthority}" varStatus="thirdStatus">
							       <ul>
									   <c:if test="${secondItem.authCode==thirdItem.parentAuthCode }">
									      <li><a href="auth/edit?authId=${thirdItem.authId}" target="dialog"  mask="true" title="${thirdItem.authName}" height="500" rel="dia_s_u">${thirdItem.authName}</a>
												<c:if test="${not empty thirdItem.childAuthority}">
												<ul>
													<c:forEach var="fourthItem" items="${thirdItem.childAuthority}" varStatus="fourthStatus">
														<li ><a href="auth/edit?authId=${fourthItem.authId}&levels=level_4" target="dialog"  mask="true" title="${fourthItem.authName}" height="500" rel="dia_s_u">${fourthItem.authName}</a></li>
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
