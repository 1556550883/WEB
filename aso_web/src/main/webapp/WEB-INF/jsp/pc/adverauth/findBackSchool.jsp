<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>

<form id="pagerForm" method="post" targetType="dialog" action="adverauth/getFindBackSchool?commonNumName=${bean.commonNumName }">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	
	<!-- 分页时 带模糊查询的值 -->
	<input type="hidden" name="schoolName" value="${param.schoolName}" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" method="post" action="adverauth/getFindBackSchool?commonNumName=${bean.commonNumName }" onsubmit="return dwzSearch(this, 'dialog');">
	<div class="searchBar">
		<ul class="searchContent">
			 <li>
			    <label style="width: 70px;">学校名称：</label>
			   	<input type="text" name="schoolName" value="${bean.schoolName }" />
			</li>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
		</ul>
		<!--<div class="subBar">
			<ul style="position: relative;top: 38px;">
				
			</ul>
		</div>-->
	</div>
	</form>
</div>

<div class="pageContent">
	<table class="table" width="100%" layoutH="128" targetType="dialog" style="width:786px;">
		<thead>
			<tr>
				<th width="center" align="center">序号</th>
				<th style="width: 149px; cursor: pointer;" align="center">学校名称</th>
				<th style="width: 149px; cursor: pointer;" align="center">区域</th>
				<th style="width: 149px; cursor: pointer;" align="center">学校类型</th>
			</tr>
		</thead>
		<tbody>
			 <c:forEach var="item" items="${pageList.result}" varStatus="row" >
	            <tr onclick="javascript:$.bringBack({schoolId:'${item.schoolId }', schoolName:'${item.schoolName }', provinceName:'${item.tProvince.provinceName }', cityName:'${item.tCity.cityName }', education:'${item.education }'})" style="cursor:pointer;">
			        <td>${(pageList.pageNum-1)*pageList.numPerPage+row.count}</td> 
					
					<td>${item.schoolName}</td>
					<td>
						<ry:show parentCode="${item.province}" itemCode="${item.province}" type="2"></ry:show>
			            <ry:show parentCode="${item.province}" itemCode="${item.city}" type="3"></ry:show>
					</td>
					<td>${item.education}</td>
	     		</tr>
         </c:forEach>
		</tbody>
	</table>
		<div class="panelBar" style="clear:both;">
		<div class="pages" style="width: 250px;height:25px">
			<span style="height:25px;display:inline-block">
			<p style="float:left;height:25px;line-height:20px"><c:if test="${pageList.totalCount!=0 }">
			      共 ${pageList.totalPage}页
			 
			</c:if>
			每页显示条数</p><select  style="width: 60px;float:left;height: 21px"  name="numPerPage" onchange="dialogPageBreak({numPerPage:this.value})">
				<option value="10" ${pageList.numPerPage==10?'selected':''}>10</option>
				<option value="50" ${pageList.numPerPage==50?'selected':''}>50</option>
				<option value="300" ${pageList.numPerPage==300?'selected':''}>300</option>
				<option value="500" ${pageList.numPerPage==500?'selected':''}>500</option>
			</select>共 ${pageList.totalCount }条数据
			</span>   
		</div>
		<div class="pagination"  targetType="dialog" totalCount="${pageList.totalCount}" numPerPage="${pageList.numPerPage }" pageNumShown="10" currentPage="${pageList.currentPage}"></div>
	</div> 
	<!-- 分页 -->
</div>
