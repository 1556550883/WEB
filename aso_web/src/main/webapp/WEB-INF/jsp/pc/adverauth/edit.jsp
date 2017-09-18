<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/inc/pression.jsp"%>
<ry:binding parentCode="T_ADVER_AUTH_TYPE" bingdingName="authType"></ry:binding>
<style>
.new_tab {border-collapse:collapse;}
.new_tab td{height:29px;line-height:29px;border:1px solid #e0e0e0;text-indent:10px;font-size: 12px;text-align: center;}
.new_tab td a{ margin:6px -3px 0 10px}
.new_tab tbody tr:hover{ background:#f5f5f5}
.new_tab tbody tr:hover{background:#f5f5f5}	
.new_tab thead tr td{background:#f0eff0 url(dwz/themes/default/images/grid/tableth.png) repeat-x}
</style>
<div class="pageContent">
<form method="post" action="adverauth/saveOrUpdate" id="forms" class="pageForm required-validate" onsubmit="return iframeCallback(this,dialogAjaxDone);" enctype="multipart/form-data">
<div class="pageFormContent nowrap" layoutH="57">

<!--<dl style="width: 770px;height: auto;">
	<dt>权限类型：</dt>
	<dd>
		<select name="authType" class="mustFill" id="authType" onchange="choice(this.value)">
			<option value="">请选择</option>
			<c:forEach items="${authType }" var="item">
				<option value="${item.itemCode }" <c:if test="${item.itemCode == bean.authType}">selected="selected"</c:if>>${item.itemName }</option>
			</c:forEach>
		</select>
	</dd>
</dl>-->

<dl style="width: 770px;height: auto;">
<dd style="width: 770px;height: auto;">
<a style="color: blue" href="javascript:void(0);" onclick="addRows();" class="lookup itemDetail">添加一行</a>
	<table class="new_tab" style="width:430px;height: auto;"  >  
		<thead>
			<tr>
				<td id="schoolTd" >学校名称</td>
				<td class="TDid">省</td>
				<td class="TDid">市</td>
				<td>操作</td>
			</tr>
		</thead>
		
		<tbody id="schoolTGTbody">
			<tr class='unitBox' id="schoolDiv">
				<td style="display: none;"><input type="hidden" name="hiddenID" value="0"></td>
				<td>
					<input type="hidden" id="commonNum" value="${bean.commonNum }">
					<input type="text" readonly="readonly" name="items[0].school.schoolName" id="schoolName" autocomplete="off" lookupgroup="items[0].school" suggestfields="schoolName" size="12" class=" textInput valid" title="学校名称" style="margin-left: 10px;">
					<input type="hidden" name="items[0].school.schoolId" id="schoolId" autocomplete="off" lookupgroup="items[0].school" suggestfields="schoolId" title="id">
					<a class="btnLook" warn="请选择权限类型" href="adverauth/getFindBackSchool?commonNumName={commonNum}" id="btnLook" maxable="false" lookupgroup="items[0].school" suggestfields="school" lookupPk="school" title="查找带回" >选择学校</a>
				</td>
				<!--<td>
					<input type="text" readonly="readonly" name="items[0].school.provinceName" id="province" autocomplete="off" lookupgroup="items[0].school" suggestfields="provinceName" size="12" class=" textInput valid" title="省">
				</td>
				<td>
					<input type="text" readonly="readonly" name="items[0].school.cityName" id="city" autocomplete="off" lookupgroup="items[0].school" suggestfields="cityName" size="12" class=" textInput valid" title="省">
				</td>-->
				<td  style="width: 40px;"><a onclick="deletes(this)" class="btnDel">删除</a></td> 
			</tr>
			
			<tr class='unitBox' id="provinceDiv">
				<td style="display: none;"><input type="hidden" name="hiddenID" value="0"></td>
				<td>
					<select id="provinceCode" name="provinceCode"  onchange="ajaxLoadCity(this,this.value);">
						<option value="">请选择省份...</option>
						<c:forEach items="${provinces}" var="item">
							<option value="${item.provinceCode }">${item.provinceName }</option>
						</c:forEach>
				    </select>
				</td>
				<td>
					<select id="cityCode" name="commonAuthNum" >
						<option value="">请选择城市...</option>
					</select>
				</td>
				<td  style="width: 40px;"><a onclick="deletes(this)" class="btnDel">删除</a></td> 
			</tr>
		</tbody>
		</table>
	</dd>
</dl>

<input type="hidden" name="jsonStr" id="jsonStr">

</div>
<!-- 隐藏值 -->
<input type="hidden" name="commonNum" value="${bean.commonNum }">
<input type="hidden" name="commonType" value="${bean.commonType }">
<input type="hidden" name="authType" id="authType" value="${bean.authType }">
<div class="formBar">
<ul>
	<li>
	<button class="btn btn-primary" TYPE="button" onclick="todo()">提交</button></li>
	<li><button TYPE="button" class="btn btn-default close">取消</button></li>
</ul>
</div>

</form>
</div>
<script type="text/javascript">
var schoolTGTbodyDIV;
 var schoolDiv = $("#schoolDiv").html();//获取学校模板 
 var provinceDiv = $("#provinceDiv").html();//获取省市模板 
 $("#schoolDiv").remove();
 $("#provinceDiv").remove();
 
 choice('${bean.authType}'); //进入调用
function choice(num){
	$("#schoolTGTbody").children('tr').remove();
	if(num == 2){//学校
		$("#schoolTd").show();
		$(".TDid").hide();
		schoolTGTbodyDIV = "<tr class='unitBox'>"+schoolDiv+"</tr>";
	}else{
		$("#schoolTd").hide();
		$(".TDid").show();
		schoolTGTbodyDIV = "<tr class='unitBox'>"+provinceDiv+"</tr>";
	}
}


function ajaxLoadCity(src,provinceCode){
	$(src).parent().parent().find("select[id='cityCode']").each(function(k){
		var url = 'city/listAjax';
		var params = 'provinceCode='+provinceCode;
		$(this).find("option").remove(); 
		$(this).append("<option value=''>请选择城市...</option>");
		var result = new MyJqueryAjax(url,params,null,'json').request();
		var html = '';
		for(var i=0;i<result.length;i++){
			var item = result[i];
			html += "<option value='"+item.cityCode+"'>"+item.cityName+"</option>";
		}
		//alert(html);
		$(this).append(html);
	});
  }


//动态添加一行 项目
function addRows(){
	var authType = $("#authType").val();
	if(authType == ''){
		return alert("请选择权限类型");
	}
	$('#schoolTGTbody').append(schoolTGTbodyDIV);
	if(authType == 2){ //学校
		setSelVal();//设置索引
		$('#schoolTGTbody').find('.unitBox').each(function(i){$(this).initUI();});
	}
}

//设置索引
function setSelVal(){
	$('#schoolTGTbody').find("input[id='hiddenID']").each(function(i){
		this.value=i;
		setSelVals($(this).parent().parent().find("input[id='schoolName']"));//设置索引 学校名称
		
		$(this).parent().parent().find("input[id='province']").each(function(j){ //省
			$(this).attr("name","items["+i+"].school.province");
			$(this).attr("lookupgroup","items["+i+"].school");
		});
		$(this).parent().parent().find("input[id='city']").each(function(j){ //市
			$(this).attr("name","items["+i+"].school.city");
			$(this).attr("lookupgroup","items["+i+"].city");
		});
	})
}
//js 删除一行
function deletes(src){
	$(src).parent().parent().remove();
	setSelVal();
}
//提交
function todo(){
	if('${bean.authType == 1}'){
		var jsonObj = {};
		jsonObj.arrayObj = [];
		var num = $('#schoolTGTbody').find('.unitBox').size();
		if(num > 0){
			$('#schoolTGTbody').find('.unitBox').each(function(i){
				var schools = {};
				schools.schoolName = $(this).find("input[id='schoolName']").val();
				schools.schoolId = $(this).find("input[id='schoolId']").val();
		
				jsonObj.arrayObj[i] = schools;
			});
			var jsonStr = JSON.stringify(jsonObj); 
				//alert(jsonStr);
			$("#jsonStr").val(jsonStr);
		}else{
			return alert("至少添加一条");
		}
	}

if(check()){
	if('${bean.authType == 2}'){//学校
		var strs = judgmentRepeated();
		if(strs != '')
			return alert(strs+"重复，请检查");
	}
	$("#forms").submit();
}
}
//判断是否有重复学校
function judgmentRepeated(){
	var schoolIds='';
	var schoolName='';
	$('#schoolTGTbody').find("input[id='schoolId']").each(function(){
		var schoolId = $(this).val();
		if(schoolIds.indexOf(schoolId) != -1)
			schoolName += $(this).parent().find("input[id='schoolName']").val()+"；";
		schoolIds += schoolId+",";
	});
	if(schoolName != '')
		schoolName = schoolName.substring(0,schoolName.length-1);
	return schoolName;
}
</script>
