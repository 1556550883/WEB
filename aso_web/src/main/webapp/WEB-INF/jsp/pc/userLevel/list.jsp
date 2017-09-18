<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding parentCode="CHANNEL_LEVEL" bingdingName="channellevel"></ry:binding>

	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" onclick="openNav('userLevel/toedit?type=0','添加信息','main_index')"><span>添加</span></a></li>			
			<li><a mask=true class="edit"  onclick="openNavU('userLevel/toedit?type=0&levelId=','修改信息','main_index')"><span>修改</span></a></li>
			<li><a class="delete" title="确定要删除该信息吗？" href="userLevel/delAll"  target="selectedTodo" postType="string" rel="ids"><span>删除</span></a></li>
			<li><a mask=true class="search"  onclick="openNavU('userLevel/toedit?levelId=','查看信息','main_index')"><span>查看</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="90">
		<thead>
			<tr>
				<th width="30">
				<input type="checkbox" group="ids" class="checkboxCtrl" >
				</th>
				<th align="center">等级编号</th>
				<th align="center">等级名称</th>
				<th align="center">等级描述</th>
				<th align="center">收徒开始数</th>
				<th align="center">收徒结束数</th>
				<th align="center">徒弟管理</th>
			</tr>
		</thead>
		<tbody>
		    <c:forEach var="item" items="${pageList.result}">
				<tr >
				  <td align="center">
        <input type="checkbox"  id="orderCheckBox" name="ids" value="${item.levelId}"></td>
					<td>${item.levelNum}</td>	               
	                <td>${item.levelName}</td>
	                <td>${item.levelDesc}</td>
	                <td>${item.proportionStart}</td>
	                <td>${item.proportionEnd}</td>	     
	                <td><a style="cursor: pointer;" onclick="openNav('userLevelRate/list?levelNum=${item.levelNum}','徒弟等分比例管理','main_index2')">徒弟等分比例管理</a></td>                                         
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<%@include file="/WEB-INF/jsp/inc/page.jsp" %>
