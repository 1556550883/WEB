<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>

<form id="pagerForm" method="post" action="userExchange/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />

</form>
	<div class="panelBar">
		<ul class="toolBar">
		<li><a class="delete" title="确定要批量审核所选条目？" href="userExchange/updateStatus?isType=1"  target="selectedTodo" postType="string" rel="ids"><span>批量通过</span></a></li>		
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="85">
		<thead>
			<tr>
				<th width="30">
				<input type="checkbox" group="ids" class="checkboxCtrl" >
				</th>
				<th align="center">兑换商品名称</th>
				<th align="center">兑换所需积分</th>
				<th align="center">状态</th>
				<th align="center">备注</th>
				<th align="center">创建时间</th>
				<th align="center">用户编号</th>
				<th align="center">微信号</th>
				<th align="center">支付宝名称</th>
				<th align="center">支付宝号</th>
				<th align="center">审核</th>
			</tr>
		</thead>
		<tbody>
		     <c:forEach var="item" items="${pageList.result}">
				<tr >
				  <td align="center">
        			<input type="checkbox"  id="orderCheckBox" name="ids" value="${item.exchhangeId}"></td>
					<td>${item.exchangeGoodsName}</td>	               
	                <td>${item.exchangeScore}</td>
	              	<td><ry:show parentCode="EXCHANGE_STATUS" itemCode="${item.exchangeStatus}"></ry:show></td>
	              	 <td>${item.exchangeRemarks}</td>
	             	<td><ry:formatDate date="${item.createtime}" toFmt="yyyy-MM-dd"></ry:formatDate> </td>  
	             	<td>${item.exchangeUserNum}</td>
	             	<td>${item.weixin}</td>
	             	<td>${item.zhifubaoName}</td>
	             	<td>${item.zhifubao}</td> 
	             	<td><c:if test="${item.exchangeStatus==0}">	
	             	<c:if test="${item.exchangeCodeType=='1'}">  
	             <a class="add" onclick="add('userExchange/manual?exchhangeId=${item.exchhangeId}&isType=1','手动输入兑换码',600,300,'main_')"><span style="color: green;">通过</span></a>
	             	</c:if>   
	             	<c:if test="${item.exchangeCodeType=='2'}">
	             	<a  title="确定要审核通过" href="userExchange/updateStatus?exchhangeId=${item.exchhangeId}&isType=2"  target="ajaxTodo" ><span style="color: green;">通过</span></a>  
	             	</c:if>          	
	             	<c:if test="${item.exchangeCodeType=='3'}">
	             	<a  title="确定要审核通过" href="userExchange/updateStatus?exchhangeId=${item.exchhangeId}&isType=3"  target="ajaxTodo" ><span style="color: green;">通过</span></a>  
	             	</c:if>  
	             	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a  title="确定要审核拒绝" href="userExchange/updateStatus?exchhangeId=${item.exchhangeId}&isType=4"  target="ajaxTodo" ><span style="color: red;">拒绝</span></a>	             	
	             	</c:if>
	             	</td>     	             	            
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<%@include file="/WEB-INF/jsp/inc/page.jsp" %>
