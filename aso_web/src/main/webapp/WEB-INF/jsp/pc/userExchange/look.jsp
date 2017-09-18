<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<ry:binding bingdingName="pro" type="4"></ry:binding>
<div class="pageContent">
	<form method="post" action="shopInfo/edit" id="forms" class="pageForm" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="57">

			<dl>
				<dt>兑换商品名称：</dt>
				<dd>
					<input type="text" name="exchangeGoodsName" value="${bean.exchangeGoodsName}" size="30"  />
				</dd>
			</dl>
			
			<dl>
				<dt>兑换所需积分：</dt>
				<dd>
					<input type="text" name="exchangeScore" value="${bean.exchangeScore}" style="width: 367px" size="100" />
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

</div>