<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<%@include file="/WEB-INF/jsp/inc/ume.jsp"%>
 <ry:binding parentCode="IS_AUTH" bingdingName="isAuth"></ry:binding>
<style>
.new_tab {border-collapse:collapse;}
.new_tab td{height:29px;line-height:29px;border:1px solid #e0e0e0;text-indent:10px}
.new_tab td input{width: 100px}
.new_tab td a{ float:left;margin:6px -3px 0 10px}
.list_all p:hover{color:#2ea7ec}
.new_tab tbody tr:hover{background:#f5f5f5}
.new_tab thead tr td{background:#f0eff0 url(dwz/themes/default/images/grid/tableth.png) repeat-x}
</style>
<div class="pageContent">
	<form method="post" id="myform"  action="udid/saveFile" onsubmit="return iframeCallback(this,navTabAjaxDone);" enctype="multipart/form-data" >
		<div class="pageFormContent nowrap" layoutH="57">
			<dl class="nowrap"  style="width: 100%;">
				<dt>udid文件：</dt>
				<dd >
					<input type="file" name="udid"/>
				</dd>
		    </dl>
		    
		     <dl class="nowrap" style="width: 100%">
			<dt>是否需要检测udid：</dt>
			<dd>
			    是：<input type="radio" name="isTest"  value="1"/>
			   否 ：<input type="radio" name="isTest"  value="0" checked/>
			</dd>
		    </dl>
		    <dl style="width:1000px;height: 220px">
				<dt>最新cookie：</dt>
				<dd style="width: 400px">
					<textarea style="height: 600px;width:800px;resize:none;"  name="cookie" cols="115" rows="20"></textarea>  

				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">
function todo(){	
}
</script>

