<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding parentCode="ADVER_TYPE" bingdingName="advertype"></ry:binding>
<form id="pagerForm" method="post" action="notice/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}">
	<input type="hidden" name="orderDirection" value="${param.orderDirection}">
</form>

	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" onclick="openNav('notice/toedit?type=0','添加信息','main_index')"><span>添加</span></a></li>			
			<li><a mask=true class="edit"  onclick="openNavU('notice/toedit?type=0&noticeId=','修改信息','main_index')"><span>修改</span></a></li>
			<li><a class="delete" title="确定要删除该信息吗？" href="notice/delAll"  target="selectedTodo" postType="string" rel="ids"><span>删除</span></a></li>
			<li><a mask=true class="search"  onclick="openNavU('notice/toedit?noticeId=','查看信息','main_index')"><span>查看</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="85">
		<thead>
			<tr>
				<th width="30">
				<input type="checkbox" group="ids" class="checkboxCtrl" >
				</th>
				<th align="center">标题</th>
				<th align="center">内容</th>
				<th align="center">创建时间</th>
			</tr>
		</thead>
		<tbody>
		     <c:forEach var="item" items="${pageList.result}" varStatus="row">
				<tr >
				  <td align="center">
        			<input type="checkbox"  id="orderCheckBox" name="ids" value="${item.noticeId}"></td>       							               
	                <td>${item.title}</td>	                
	                <td>${item.content}</td>
	           	   <td><ry:formatDate date="${item.createDate}" toFmt="yyyy-MM-dd"></ry:formatDate> </td>  
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<%@include file="/WEB-INF/jsp/inc/page.jsp" %>
