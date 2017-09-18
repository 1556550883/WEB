<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>

	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" onclick="openNav('userLevelRate/toedit?type=0&levelNum=${bean.levelNum}','添加信息','main_index')"><span>添加</span></a></li>			
			<li><a mask=true class="edit"  onclick="openNavU('userLevelRate/toedit?type=0&levelRateId=','修改信息','main_index')"><span>修改</span></a></li>
			<li><a class="delete" title="确定要删除该信息吗？" href="userLevelRate/delAll"  target="selectedTodo" postType="string" rel="ids"><span>删除</span></a></li>
			<li><a mask=true class="search"  onclick="openNavU('userLevelRate/toedit?levelRateId=','查看信息','main_index')"><span>查看</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="86">
		<thead>
			<tr>
				<th width="30">
				<input type="checkbox" group="ids" class="checkboxCtrl" >
				</th>
				<th align="center">等级编号</th>
				<th align="center">等级汇率</th>
				<th align="center">类型</th>
			</tr>
		</thead>
		<tbody>
		     <c:forEach var="item" items="${pageList.result}">
				<tr >
				  <td align="center">
        <input type="checkbox"  id="orderCheckBox" name="ids" value="${item.levelRateId}"></td>
					<td>${item.levelNum}</td>	               
	                <td>${item.levelRate}</td>
	              	<td><ry:show parentCode="LEVEL_RATE_TYPE" itemCode="${item.rateType}"></ry:show></td>              
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<%@include file="/WEB-INF/jsp/inc/page.jsp" %>
