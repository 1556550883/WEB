<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- 公用的分页按钮   ，需要每个使用的方法都要返回  pageList      共${pageList.totalPage}页， -->
<div class="panelBar">
		<div class="pages" style="width: 520px;">
			<span>
			
			<c:if test="${pageList.totalCount!=0 }">
			     当前第${pageList.currentPage}页,显示第 ${pageList.currentPage==1 ? 1 : ((pageList.currentPage-1)*pageList.numPerPage+1)}至 ${pageList.currentPage*pageList.numPerPage < pageList.totalCount ? (pageList.currentPage*pageList.numPerPage) : pageList.totalCount}项结果,共 ${pageList.totalCount }条数据,共 ${pageList.totalPage}页
			 
			</c:if>
			</span>   
		</div>
		<div class="pagination"  targetType="dialog" totalCount="${pageList.totalCount}" numPerPage="${pageList.numPerPage }" pageNumShown="10" currentPage="${pageList.currentPage}"></div>

	</div>
