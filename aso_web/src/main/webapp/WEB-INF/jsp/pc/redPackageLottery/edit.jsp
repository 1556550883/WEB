<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<div class="pageContent">
	<form method="post" id="myform"  action="redPackageLottery/edit" onsubmit="return iframeCallback(this,navTabAjaxDone);" enctype="multipart/form-data" >
		<div class="pageFormContent" layoutH="60">
			
			<dl style="width: 1000px">
				<dt>开奖期号：</dt>
				<dd>
			<input    style="width: 110px" id="lotteryNum" type="text" name="lotteryNum" value="${bean.lotteryNum}" readonly="readonly"  />
				</dd>
			</dl>
			
			<dl style="width: 1000px">
				<dt>开奖结果：</dt>
				<dd style="width: 650px">
				 <c:forEach items="${packages}" var="item">
				<input name="lotteryResult" value="${item.orderby}" type=checkbox>${item.orderby}
				</c:forEach>		
				</dd>
			</dl>
			<dl style="width: 1000px">
				<dt>开奖金额：</dt>
				<dd>
					 <input type="text" class="required number" style="width: 600px" name="lotteryScore" value="${bean.lotteryScore}" />
				</dd>
			</dl>	
	
			
				<input type="hidden" name="lotteryId" value="${bean.lotteryId}"/>
				<input type="hidden"  name="lotteryStatus" value="${bean.lotteryStatus}" />	
				<input type="hidden"  id="xuanzhong" value="${bean.lotteryResult}" />				  
			</div>
	    <div class="formBar">
			<ul>
				<c:if test="${type==1}"><li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="todo()" >保存</button></div></div></li></c:if>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>

<script type="text/javascript">
var lotteryNum=$("#lotteryNum").val();
var now=new Date();
var Month= now.getMonth()+1;

if(lotteryNum==""){
	var day=now.getDate();
	if(day<10){
		day="0"+day;
	}
	if(Month<10){
		$("#lotteryNum").val(now.getFullYear()+"0"+Month+day);
	}else{
		$("#lotteryNum").val(now.getFullYear()+Month+day);
	}
}

	var val = document.getElementById("xuanzhong").value.split(",");
	var boxes = document.getElementsByName("lotteryResult");
	for(i=0;i<boxes.length;i++){
		for(j=0;j<val.length;j++){
			if(boxes[i].value == val[j]){
				boxes[i].checked = true;
				break
			}
		}
	}

function todo(){
	obj = document.getElementsByName("lotteryResult");
	check_val = [];
	for(var k=0;k<12;k++){
		if(obj[k].checked)
			check_val.push(obj[k].value);
	}	

	if(check_val.length<6){
		alert("选中不足6个");
	}else if(check_val.length>6){
		alert("选中超过6个");
	}else{
		$("#myform").submit();
	}
	
}
</script>