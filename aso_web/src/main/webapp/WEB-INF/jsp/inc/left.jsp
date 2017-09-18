<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<body scroll="no">
<div id="leftside">
	<div id="sidebar_s">
		<div class="collapse">
		   <div class="toggleCollapse"><div></div></div>
		</div>
	</div>
	<div id="sidebar">
	   <c:forEach var="firstItem" items="${sessionScope.leftUrls}">
			<div class="toggleCollapse"><h2>${firstItem.authName}</h2><div>收缩</div></div>
			<c:if test="${not empty systemUser }">
				<div class="accordion" fillSpace="sidebar">
				<c:set var="i" value="1"/>
					<c:forEach var="secondItem" items="${firstItem.childAuthority}" varStatus="row_second">
					<c:set var="i" value="${i+1}"/>
					
						<div class="accordionHeader">
						     <h2><span>Folder</span>${secondItem.authName}</h2>
						</div>
						<div class="accordionContent">
						<ul class="tree treeFolder ">
							<c:forEach var="thirdItem" items="${secondItem.childAuthority}" varStatus="row_third">
								<c:if test="${secondItem.authCode==thirdItem.parentAuthCode }">
										<li>
										<c:if test="${ empty thirdItem.childAuthority}"> 
											<a href="${thirdItem.authUrl}" target="navTab" rel="main_" >${thirdItem.authName}</a>
										</c:if>
										<c:if test="${not empty thirdItem.childAuthority}">  
										<a href="#" >${thirdItem.authName}</a>
											<ul>
												<c:forEach var="fourthItem" items="${thirdItem.childAuthority}" varStatus="row_fourth">
												      <li><a  href="${fourthItem.authUrl}" target="navTab" rel="main_">${fourthItem.authName}</a></li> 
												</c:forEach>
											</ul>
										</c:if>								
									    </li>
								</c:if>
							
							</c:forEach>
						</ul>						
						</div>
									 
					</c:forEach>
									 
				</div>
						
			</c:if>
		</div>  
	</div> 
	</c:forEach>
</div>
</body>
