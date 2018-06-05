<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<script type="text/javascript">
	/*$("#export").click(function(){
		exportExcel();
		});
	
    function exportExcel(){
        //创建AX对象excel
        var oXL = new ActiveXObject("Excel.Application");
        //获取workbook对象 
        var oWB = oXL.Workbooks.Add();
        //激活当前sheet
        var oSheet = oWB.ActiveSheet;

        $(".header th").each(function(index,domEle){
        	oSheet.Cells(1, index+1).value = $(this).text();
        	oSheet.Cells(1, index+1).HorizontalAlignment = 3;
		  });
        $(".body").each(function(index,domEle){
        	
        	$(this).find("td").each(function(index2,domEle2){
            	oSheet.Cells(index+2, index2+1).value = $(this).text();
            	oSheet.Cells(index+2, index2+1).HorizontalAlignment = 2;
    		  });
        	
		  });
        //设置excel可见属性
        oXL.Visible = true;
    }*/
</script>
<form id="pagerForm" method="post" action="channelInfo/employeeIdfaStatistics?userAppId=${userAppId}">
	<input type="hidden" name="pageNum" value="${pageList.pageNum }" />
	<input type="hidden" name="numPerPage" value="${pageList.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}">
	<input type="hidden" name="orderDirection" value="${param.orderDirection}">
</form>
<div class="pageHeader">
	<form rel="pagerForm" id="selForm" onsubmit="return navTabSearch(this);" action="channelInfo/employeeIdfaStatistics?userAppId=${userAppId}" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>完成日期：
					<input type="text" name="completeTime" title="完成日期" class="date" id="completeTime" value="<ry:formatDate date='${completeTime}' toFmt='yyyy-MM-dd'/>"  readonly="readonly" dateFmt="yyyy-MM-dd" />
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
				</ul>
			</div>
		</div>
	</form>
</div>
	<div class="panelBar">
		<ul class="toolBar">
			<!-- <li><a id="export" class="icon" href="javascript:;"><span>导出</span></a></li> -->
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="128">
		<thead>
			<tr class="header">
				<th align="center" width="100px;">任务状态</th>
				<th align="center" width="150px;">完成时间</th>
				<th align="center" width="100px;">广告ID</th>
				<th align="center" width="250px;">广告名称</th>
				<th align="center" width="80px;">广告价格</th>
				<th align="center" width="300px;">idfa</th>
				<th align="center" width="100px;">ip</th>
				<th align="center" width="250px;">苹果账号</th>
				<th align="center" width="250px;">手机型号</th>
				<th align="center" width="250px;">手机系统版本</th>
			</tr>
		</thead>
		<tbody>
		     <c:forEach var="item" items="${pageList.result}">
				<tr class="body">
					<td style="text-align:left;">${item.statusDescription}</td>
					<td style="text-align:left;">${item.completeTimeStr}</td>
	                <td style="text-align:left;">${item.adid}</td>	               
	                <td style="text-align:left;">${item.adverName}</td>
	                <td style="text-align:left;">${item.adverPrice}</td>
	                <td style="text-align:left;">${item.idfa}</td>
	                <td style="text-align:left;">${item.ip}</td>
	                <td style="text-align:left;">${item.appleId}</td>
	                <td style="text-align:left;">${item.phoneModel}</td>
	                 <td style="text-align:left;">${item.phoneVersion}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

<%@include file="/WEB-INF/jsp/inc/page.jsp" %>

