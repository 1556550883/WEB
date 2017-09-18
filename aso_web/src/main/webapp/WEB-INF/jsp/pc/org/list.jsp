<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<div id="resultBox"></div>
    <script type="text/javascript" src="js/dtree/dtree.js"></script>
<div class="pageContent" >
	<div class="pageFormContent" layoutH="21">
		 <div style="float:left; display:block; margin:10px;  width:200px; height:100%;  line-height:21px; background:#FFF;">
			<ul class="tree treeFolder ">
			    <c:forEach var="firstIitem" items="${orgList}" varStatus="status">
					<li><a href="org/edit?orgId=${firstIitem.orgId}" target="dialog"  mask="true" title="${firstIitem.orgName}" height="500" rel="org_dialog">${firstIitem.orgName}</a>
						<ul>
						   <c:forEach var="secondItem" items="${firstIitem.childOrg}">
							   <li><a href="org/edit?orgId=${secondItem.orgId}" target="dialog"  mask="true" title="${secondItem.orgName}" height="500" rel="org_dialog">${secondItem.orgName}</a>
							   <c:forEach var="thirdItem" items="${secondItem.childOrg}">
							       <ul>
									   <c:if test="${secondItem.orgCode==thirdItem.parentOrgCode }">
									      <li><a href="org/edit?orgId=${thirdItem.orgId}" target="dialog"  mask="true" title="${thirdItem.orgName}" height="500" rel="org_dialog">${thirdItem.orgName}</a>
												<c:if test="${not empty thirdItem.childOrg}">
												<ul>
													<c:forEach var="fourthItem" items="${thirdItem.childOrg}">
														<li ><a href="org/edit?orgId=${fourthItem.orgId}&levels=level_4" target="dialog"  mask="true" title="${fourthItem.orgName}" height="500" rel="org_dialog">${fourthItem.orgName}</a></li>
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
		<!--<script type="text/javascript">
			var d = new dTree('d');
			function buildTree() {
				try{
					<c:forEach var="item" items="${orgList}" varStatus="status">
						d.add("${item.orgCode}","${item.parentOrgCode}",'<font  color="black">${item.orgName}</font>','javascript:updateNode(${item.orgId})','javascript:void(0);','_blank');
					</c:forEach>
					document.write(d);
					}catch(e){
						alert(e.message);
					}
				}
				buildTree();
			
			
			 
					
				</script>-->
	</div>
</div>
