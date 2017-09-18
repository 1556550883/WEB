<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding bingdingName="pro" type="4"></ry:binding>
<div class="pageContent">
	<form method="post" action="shopInfo/edit" id="forms" class="pageForm" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="57">
			<dl>
				<dt>登录名称：</dt>
				<dd>
					<input type="text" id="loginName" name="loginName" <c:if test="${not empty bean.shopId}">readonly</c:if>  value="${bean.loginName}" onblur="searchAjaxNameForId('${bean.shopId}','登录名',this)" maxlength="20" size="30" class="required" alt="请输入登录名称" />
				</dd>
			</dl>
			<dl>
				<dt>商铺名称：</dt>
				<dd>
					<input type="text" name="shopName" value="${bean.shopName}" size="30" class="required" alt="请输入商铺名称" />
				</dd>
			</dl>
			<dl style="width: 100%">
				<dt><strong style="color: red">*</strong>商铺地区：</dt>
				<dd style="width: 800px">
					<select name="provinceid" class="mustFill" title="学校地区-省" id="region" onchange="getCitiesByProvinces(this.value,'','')"  >
						<option value="">请选择</option>
						<c:forEach items="${pro}" var="item">
								<option value="${item.provinceCode}" <c:if test="${item.provinceCode==bean.provinceid}">selected</c:if>>${item.provinceName}</option>
						</c:forEach>
		       		</select> 
					<select name="citiesid" class="mustFill" title="学校地区-市" id="cities1" onchange="queryArea(this.value,'')"  >
					<option></option>
					</select>
					<select name="areaid" class="mustFill" title="学校地区-县" id="area1" class="zxth_sr">
						<option></option>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>商铺地址：</dt>
				<dd>
					<input type="text" name="shopAddress" value="${bean.shopAddress}" style="width: 367px" size="100" alt="请输入商铺地址" class="required" />
				</dd>
			</dl>
			<dl class="nowrap" style="width: 100%">
				<dt>商铺描述：</dt>
				<dd>
					<div id="myeditor" style="width: 900px"></div>
				    <input name="shopDesc" id="shopDesc" type="hidden" value="<c:out value="${bean.shopDesc}"></c:out>"/>
				</dd>
		    </dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="todo()">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
		<!-- 隐藏的值 -->
		<input type="hidden" name="shopId" value="${bean.shopId}"/>
	</form>
    <link href="umeditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" charset="utf-8" src="umeditor/umeditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="umeditor/umeditor.min.js"></script>
    <script type="text/javascript" src="umeditor/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript">
	var citiesid='${bean.citiesid}';
	var areaid='${bean.areaid}';
	var provinceId='${bean.provinceid}';
	if(provinceId!=""){
		getCitiesByProvinces(provinceId,citiesid,areaid);
	}
	var ue=UM.getEditor("myeditor",{
		autoHeightEnabled: false
	});
		ue.ready(function() {
		ue.setContent($("#shopDesc").val());
	});
	function todo(){
		$("#shopDesc").val(ue.getContent());
	 	$("#forms").submit();
	}
	</script>
</div>