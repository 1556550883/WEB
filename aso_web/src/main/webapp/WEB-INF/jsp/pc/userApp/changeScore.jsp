<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<div class="pageContent">
	<form method="post" action="userApp/saveChangeUserScore" id="forms" class="pageForm required-validate" onsubmit="return iframeCallback(this, navTabAjaxDone);"enctype="multipart/form-data">
		<div class="pageFormContent nowrap" layoutH="57">
			<dl>
				<dt>金额：</dt>
				<dd>
					<input type="text" name="score"  value="${userScore.score}" maxlength="100" size="100" class="required" alt="输入减去的金额"  />
				</dd>
			</dl>
			<dl>
				<dt>说明原因：</dt>
				<dd>
					<input type="text" name="rankingNum"  value="${userScore.rankingNum}"  title="说明原因" />
				</dd>
			</dl>
			
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" class="close" onclick="formSubmit();">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
			<input type="hidden"  name=userNum value="${userScore.userNum}"/>
		</div>
	</form>
</div>

<script>
  function formSubmit(){
		$('#forms').submit();	
	}
</script>