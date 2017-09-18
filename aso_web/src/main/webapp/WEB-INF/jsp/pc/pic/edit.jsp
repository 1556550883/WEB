<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding parentCode="CHANNEL_TYPE,PIC_TYPE,PIC_STATUS,SYSTEM_TYPE" bingdingName="channeltype,pictype,picstatus,systemtype"></ry:binding>
<div class="pageContent">
	<form method="post" id="myform"  action="pic/saveOrUpdate" onsubmit="return iframeCallback(this,navTabAjaxDone);" enctype="multipart/form-data" >
		
		
		<div class="pageFormContent" layoutH="60">
			<dl style="width: 1000px">
				<dt>系统类型：</dt>
				<dd style="width: 280px">
					<select name="flag1" onchange="queryCitiesEdit(this.value,'','')">
						        <option value="" >请选择</option>					
	                   <c:forEach items="${systemtype}" var="item">
	                          <option value="${item.itemCode}" <c:if test="${bean.flag1 == item.itemCode}">selected</c:if> >${item.itemName}</option>
	                   </c:forEach>
	                </select>
				</dd>
				<dt style="margin-left: -169px">渠道：</dt>
				<dd >
					<select name="citiesid" class="mustFill"  id="cityEdit" onchange="queryAreaEdit(this.value,'')"  >
							<option></option>
					</select>
				</dd>
					<dt style="margin-left: -71px">广告：</dt>
				<dd >
				  <select name="areaid"  id="areaEdit"  style="width: 100px">
							<option></option>
					</select>
				</dd>
		</dl>	
		<!--
			<dl style="width: 1000px">
				<dt>图片位置：</dt>
				<dd style="width: 280px">
					<select name="picPosition" >
	                   <option value="">请选择</option>
	                   <c:forEach items="${channeltype}" var="item">
	                          <option value="${item.itemCode}" <c:if test="${bean.picPosition == item.itemCode}">selected</c:if> >${item.itemName}</option>
	                   </c:forEach>
	                </select>

				</dd>
			</dl>	
	
		--><dl style="width: 1000px">
				<dt>图片类型：</dt>
				<dd style="width: 280px">
					<select name="picType" >
	                   <option value="">请选择</option>
	                   <c:forEach items="${pictype}" var="item">
	                          <option value="${item.itemCode}" <c:if test="${bean.picType == item.itemCode}">selected</c:if> >${item.itemName}</option>
	                   </c:forEach>
	                </select>

				</dd>
			</dl>	
		<dl style="width: 1000px">
				<dt>图片状态：</dt>
				<dd style="width: 280px">
					<select name="picStatus" >
	                   <option value="">请选择</option>
	                   <c:forEach items="${picstatus}" var="item">
	                          <option value="${item.itemCode}" <c:if test="${bean.picStatus == item.itemCode}">selected</c:if> >${item.itemName}</option>
	                   </c:forEach>
	                </select>

				</dd>
			</dl>	
		<dl style="width: 720px;height: 100px;">
				<dt>图片：</dt>
				<dd>
				<input type="file" name="picFile" />
				</dd>
				<dd>			
					<c:if test="${bean.picPath!=null}">
						<img  src='file/img/${bean.picPath}' style='width:230px;height:100px'>
					</c:if>
					</dd>	
			</dl>	
			
			<input type="hidden" name="picId" value="${bean.picId}" />					  
			</div>
	    <div class="formBar">
			<ul>
				<c:if test="${type==0}"><li><div class="buttonActive"><div class="buttonContent"><button type="submit" >保存</button></div></div></li></c:if>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">

function queryCities2(){
	var provinceId=document.getElementById("region_id").value;
$.ajax({
	type:'post',
	url:'shopInfo/allShop',
	data:'provinceId='+provinceId,
	dataType:'json',
	success:function(data){
	var city="<option value=''>请选择</option>";
	for(var i=0;i<data.length;i++){
		city+="<option  value="+data[i].shopNum+">"+data[i].shopName+"</option>";
		}
	$("#shops").html(city);
		},error:function(){
		alert("没有信息");
		}
	});
}
function queryCitiesEdit(provinceId){
	$("#cityEdit").html("<option value=''>请选择</option>");

	$.ajax({
		type:'post',
		url:'channelInfo/getChannelType',
		data:'provinceId='+provinceId,
		dataType:'json',
		success:function(data){
		var city="<option value=''>请选择</option>";
		for(var i=0;i<data.length;i++){
			var status="";			
			city+="<option "+status+" value="+data[i].itemCode+">"+data[i].itemName+"</option>";
			}
		$("#cityEdit").html(city);
			},error:function(){
			alert("没有信息");
			}
		});
}

function queryAreaEdit(cidyid){
	$("#areaEdit").html("<option value=''>请选择</option>");
	alert(cidyid);
	$.ajax({
		type:'post',
		url:'channelInfo/getChannelType',
		data:'provinceId='+provinceId,
		dataType:'json',
		success:function(data){
		var city="<option value=''>请选择</option>";
		for(var i=0;i<data.length;i++){
			var status="";			
			city+="<option "+status+" value="+data[i].itemCode+">"+data[i].itemName+"</option>";
			}
		$("#cityEdit").html(city);
			},error:function(){
			alert("没有信息");
			}
		});
}


</script>


