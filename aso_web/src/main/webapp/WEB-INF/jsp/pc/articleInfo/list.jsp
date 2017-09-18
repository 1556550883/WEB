<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding parentCode="ADVER_TYPE,ARTICLE_STATUS" bingdingName="advertype,articlestatus"></ry:binding>
<form id="pagerForm" method="post" action="articleInfo/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}">
	
</form>


<div class="pageHeader">
<form onsubmit="return navTabSearch(this);" action="articleInfo/list" method="post">
<div class="searchBar">
<ul class="searchContent">
	


</ul>

<div class="subBar">
<ul> 
    <li>
	<div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div>
	</li>
</ul>
</div>
</div>
</form>
</div>

	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" onclick="openNav('articleInfo/toedit?type=0','添加信息','main_index')"><span>添加</span></a></li>			
			<li><a mask=true class="edit"  onclick="openNavU('articleInfo/toedit?type=0&articleId=','修改信息','main_index')"><span>修改</span></a></li>
			<li><a class="delete" title="确定要删除该信息吗？" href="articleInfo/delAll"  target="selectedTodo" postType="string" rel="ids"><span>删除</span></a></li>
			<li><a mask=true class="search"  onclick="openNavU('articleInfo/toedit?articleId=','查看信息','main_index')"><span>查看</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="135">
		<thead>
			<tr>
				<th width="30">
				<input type="checkbox" group="ids" class="checkboxCtrl" >
				</th>
				<th align="center">文章编号</th>
				<!--<th align="center">文章URL地址</th>
				-->
				<th align="center">文章标题</th>
				<th align="center">文章任务量</th>
				<th align="center">文章显示任务量</th>
				<th align="center">文章转发量</th>
				<th align="center">访问次数</th>
				<th align="center">状态</th>
				<!--<th align="center">权限</th>-->
				<th align="center">创建时间</th>
				<!--<th align="center">操作</th>-->
			</tr>
		</thead>
		<tbody>
		     <c:forEach var="item" items="${pageList.result}" varStatus="row">
				<tr >
				  <td align="center">
        	<input type="checkbox"  id="orderCheckBox" name="ids" value="${item.articleId}"></td>	
        							               
	                <td>${item.articleNum}</td>
	                <td>${item.articleDesc}</td>
	                <td>${item.taskQuantity}</td>                 
	                <td>${item.showForwardQuantity}</td> 
	                 <td>${item.forwardQuantity}</td>
	                 <td>${item.accessTimes}</td>
	                 <td>${item.status==1?'进行中':'已抢完' }</td>
	                 <!--<td><ry:show parentCode="IS_AUTH" itemCode="${item.isAuth}"></ry:show></td>-->
	           	   <td><ry:formatDate date="${item.createTime}" toFmt="yyyy-MM-dd"></ry:formatDate></td>  
	           	   <!--<td>
		           	   <c:if test="${item.isAuth == 1 || item.isAuth == 2}">
		           	   		<a href="adverauth/list?commonNumName=${item.articleNum}&commonNum=${item.articleNum}&commonType=1&authType=${item.isAuth}" target="navTab" rel="main_1" style="color: blue;">权限管理</a>
		           	   </c:if>
	           	   </td>-->
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<%@include file="/WEB-INF/jsp/inc/page.jsp" %>
