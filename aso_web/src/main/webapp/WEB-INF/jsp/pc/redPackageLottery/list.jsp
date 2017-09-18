<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding parentCode="CHANNEL_LEVEL,CHANNEL_TYPE,SYSTEM_TYPE" bingdingName="channellevel,channeltype,systemtype"></ry:binding>
<form id="pagerForm" method="post" action="redPackageLottery/list">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}">
	<input type="hidden" name="orderDirection" value="${param.orderDirection}">
</form>
	<div class="panelBar">
		<ul class="toolBar">
		
		<c:if test="${isDayLottery!=1}">
			<li><a class="add" onclick="openNav('redPackageLottery/toedit?type=1','添加信息','main_index')"><span>添加</span></a></li>			
		</c:if>
			<li><a mask=true class="edit"  onclick="openNavU('redPackageLottery/toedit?type=1&id=','修改信息','main_index')"><span>修改</span></a></li>
			<li><a mask=true class="search"  onclick="openNavU('redPackageLottery/toedit?id=','查看信息','main_index')"><span>查看</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="88">
		<thead>
			<tr>
				<th width="30">
				<input type="checkbox" group="ids" class="checkboxCtrl" >
				</th>
				<th align="center">开奖期号</th>
				<th align="center">开奖结果</th>
				<th align="center">开奖金额</th>
				<th align="center">是否开奖</th>
				<th align="center">创建时间</th>				
			</tr>
		</thead>
		<tbody>
		     <c:forEach var="item" items="${pageList.result}">
				<tr >
				  <td align="center">
        		<input type="checkbox"  id="orderCheckBox" name="ids" value="${item.lotteryId}"></td>
					<td>${item.lotteryNum}</td>	               	              
	                <td>${item.lotteryResult}</td>
	                	               
	                <td>${item.lotteryScore}</td>
	                <td>
	                <c:if test="${item.lotteryStatus==1}"><span style="color: green">已开</span></c:if>
	                 <c:if test="${item.lotteryStatus==0}"><span style="color: red">未开</span></c:if>
	                 </td>
					<td><ry:formatDate date="${item.createTime}" toFmt="yyyy-MM-dd"></ry:formatDate> </td>				
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<%@include file="/WEB-INF/jsp/inc/page.jsp" %>

