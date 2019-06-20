<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<form id="pagerForm" method="post" action="externalChannelAdverInfo/list?externalChannelNum=${bean.externalChannelNum}">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}">
	<input type="hidden" name="orderDirection" value="${param.orderDirection}">
	<input type="hidden" name="shopName" value="" />
</form>
<div class="pageHeader">
  <form rel="pagerForm" onsubmit="return navTabSearch(this);"  action="externalChannelAdverInfo/list?externalChannelNum=${bean.externalChannelNum}" method="post">
    <div class="searchBar">
		<ul class="searchContent ys_wa">
		<li><label class="new_addLabel">广告名称：</label><input type="text" name="shopName" value="${bean.externalAdverName}" /></li>
		</ul>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" onclick="openNav('externalChannelAdverInfo/toedit?externalChannelNum=${bean.externalChannelNum}','添加广告信息','main_index3')"><span>添加</span></a></li>
			<li><a class="edit" title="确定要启用选择的信息吗？" href="externalChannelAdverInfo/updateAdverStatus?externalAdverStatus=1"  target="selectedTodo" postType="string" rel="ids"><span>启用</span></a></li>
			<li><a class="edit" title="确定要停用选择的信息吗？" href="externalChannelAdverInfo/updateAdverStatus?externalAdverStatus=2"  target="selectedTodo" postType="string" rel="ids"><span>停用</span></a></li>
			<li><a class="edit" title="确定要刷新选择的信息吗？" href="externalChannelAdverInfo/freshAdverNum"  target="selectedTodo" postType="string" rel="ids"><span>刷新</span></a></li>
			<li><span style="" onclick= "show()">导出</span></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="132">
		<thead>
			<tr>
				<th width="30">
				<input type="checkbox" group="ids" class="checkboxCtrl" >
				</th>
				<th align="center">广告名称</th>
				<th align="center">广告ADID</th>
				<th align="center">广告价格</th>
				<th align="center">广告数量</th>
				<th align="center">广告完成的有效数量</th>
				<th align="center">广告开始时间——广告结束时间</th>
				<th align="center">状态</th>
				<th align="center">创建时间</th>
				<th align="center">操作</th>
				<th align="center">外放任务记录 </th>	
			</tr>
		</thead>
		<tbody>
		     <c:forEach var="item" items="${pageList.result}">
				<tr >
				  <td align="center">
        			<input type="checkbox"  id="orderCheckBox" name="ids" value="${item.externalAdverId}"></td>
					<td>${item.externalAdverName}</td>	  
					<td>${item.adid}</td>	              
	                <td>${item.externalAdverPrice}</td>
	                <td>${item.externalAdverCount}</td>
	                <td>${item.externalAdverActivationCount}</td>
	                <td>${item.externalAdverTimeStart}--${item.externalAdverTimeEnd}</td>
	            
	                <td><c:if test="${item.externalAdverStatus==0}">未审核</c:if><c:if test="${item.externalAdverStatus==1}">启用</c:if><c:if test="${item.externalAdverStatus==2}">停用</c:if><c:if test="${item.externalAdverStatus==3}">已支付</c:if></td>
	       
	                <td><ry:formatDate date="${item.externalAdverCreatetime}" toFmt="yyyy-MM-dd"></ry:formatDate> </td> 
					<td><a class="btnEdit" title="编辑" href="javascript:;;" onclick="openNav('externalChannelAdverInfo/toedit?id=${item.externalAdverId}','修改广告信息','main_index3')"><span>修改</span></a></td>
					<td>
						<a style="cursor: pointer;" onclick="openNav('externalChannelAdverInfo/completeList?adverId=${item.externalAdverId}','广告完成情况','main_index2')"><div style="color: blue">广告完成情况</div></a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
		<script type="text/javascript">
		
function show(){
    var obj = document.getElementsByName("ids");
    var check_val = "";
    for(k in obj){
        if(obj[k].checked){
        	check_val = obj[k].value
        }
    }
   	console.log(check_val);
    if(check_val != ""){
	    window.location.href = "http://moneyzhuan.com/externalChannelAdverInfo/export?adverIds="+  check_val;
    }else{
    	alert("请先选择一个任务！")
    }
}
</script>

<%@include file="/WEB-INF/jsp/inc/page.jsp" %>
</div>




