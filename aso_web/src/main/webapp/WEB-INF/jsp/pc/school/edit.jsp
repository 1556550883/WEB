<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/inc/pression.jsp"%>
<div class="pageContent">
<ry:binding parentCode="EDUCATION_TYPE" bingdingName="educationtype"></ry:binding>
<form method="post" action="school/saveOrUpdate" id="forms" class="pageForm required-validate" onsubmit="return iframeCallback(this, navTabAjaxDone);" enctype="multipart/form-data">
<div class="pageFormContent nowrap" layoutH="57">
<dl style="width: 750px;" >
	<dt>学校名称：</dt>
	<dd>
		<input type="text" name="schoolName" value="${bean.schoolName}" style="width: 200px;">
	</dd>
	</dl>
	<dl style="width:750px;height: 30px;" >	
			<dt ><span style="color: red;">*</span>区域：</dt>
			<dd style="width: 400px;margin-top: -4px;">
				<%@include file="/WEB-INF/jsp/inc/regionalCascadeEditNew.jsp" %>
				<input type="hidden"  id="provinceid_edit"   value="${bean.province}" />
				<input type="hidden"  id="citiesid_edit"  value="${bean.city}" />
				
			</dd>
 		</dl>
	 
	 <dl  style="width: 750px;">
			<dt style="width: 130px"><span style="color: red;">*</span>学校类型：</dt>
			<select name="education" style=" width: 200px;">
			   <option value="">---请选择---</option>
				  <c:forEach items="${educationtype}" var="item">
					 <option value="${item.itemCode }" <c:if test="${item.itemCode==bean.education}">selected="selected"</c:if>>${item.itemName }</option>
			       </c:forEach>
			</select>	
		</dl>


<input type="hidden" name="schoolId" value="${bean.schoolId}" />	
</div>

<div class="formBar">
<ul>
	<li>
	<div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
	<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
</ul>
</div>

</form>
</div>
