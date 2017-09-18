<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding parentCode="GOOD_TYPE,EXCHANGE_CODE_TYPE" bingdingName="goodtype,exchangecodetype"></ry:binding>
<div class="pageContent">
	<form method="post" id="myform"  action="shopGoods/saveOrUpdate" onsubmit="return iframeCallback(this,navTabAjaxDone);" enctype="multipart/form-data" >
		
		
		<div class="pageFormContent" layoutH="60">

			<dl style="width: 1000px">
				<dt>商品名称：</dt>
				<dd>
					 <input type="text" class="required"  style="width: 600px" name="goodName" value="${info.goodName}" />
				</dd>
			</dl>
			
			<dl style="width: 1000px">
				<dt>商品分类123：</dt>
				<dd style="width: 280px">
					<select name="goodType"  id="region_id" onchange="queryCities2(this.value)" >
	                   <option value="">请选择</option>
	                   <c:forEach items="${goodtype}" var="item">
	                          <option value="${item.itemCode}" <c:if test="${info.goodType == item.itemCode}">selected</c:if> >${item.itemName}</option>
	                   </c:forEach>
	                </select>
	                
	                	<select name="shopNum" id="shops" >
	                	<option value="">请选择</option>
							 <c:forEach items="${shopList}" var="item">
	                          <option value="${item.shopNum}" <c:if test="${info.shopNum == item.shopNum}">selected</c:if> >${item.shopName}</option>
	                  	 </c:forEach>
						</select>
						
				</dd>
			</dl>	
	
	
	<dl style="width: 1000px">
				<dt>兑换码生成方式：</dt>
				<dd >
					<select name="exchangeCodeType" >
	                   <option value="">请选择</option>
	                   <c:forEach items="${exchangecodetype}" var="item">
	                          <option value="${item.itemCode}" <c:if test="${info.exchangeCodeType == item.itemCode}">selected</c:if> >${item.itemName}</option>
	                   </c:forEach>
	                </select>
				</dd>
			</dl>	
			<dl style="width: 1000px">
				<dt>商品价格：</dt>
				<dd>
					 <input type="text" class="number"  style="width: 600px" name="goodPrice" value="${info.goodPrice}" />
				</dd>
			</dl>
	
			<dl style="width:1000px;height: 220px">
				<dt>商品描述：</dt>
				<dd style="width: 400px">
			<textarea style="height: 200px;width:650px;resize:none;"  name="goodDesc" cols="115" rows="20">${info.goodDesc}</textarea>  

				</dd>
			</dl>
			
		<dl style="width: 720px;height: 100px;">
				<dt>图片：</dt>
				<dd>
				<input type="file" name="picFile" />
				</dd>
				<dd>			
					<c:if test="${info.goodImg!=null}">
						<img  src='file/img/${info.goodImg}' style='width:230px;height:100px'>
					</c:if>
					</dd>	
			</dl>	
			
			<input type="hidden" name="goodId" value="${info.goodId}" />
			<input type="hidden" name="goodImg" value="${info.goodImg}" />				  
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
</script>
