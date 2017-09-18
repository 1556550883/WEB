<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<ry:pageMessage result="${msg}" replaceStr="message">
	<script>$(document).ready(function(){var options = {"text":"message","layout":"top","type":"information"};noty(options);});</script>
</ry:pageMessage>
<ry:authorize ifAllGranted="A_1042">
<script type="text/javascript">
	function serviceCenterOrderCount(){
		$.ajax({
			type:"get",
			url:basePath+"order/serviceCenterOrderCount",
			data:"",
			dataType:"html",
			success:function(count){
				if(count>0){
					$.messager.lays(200, 150);
					$.messager.show(0, '服务中心有<font style="color:red;">'+count+'</font>条待初审的工单');
				}
			}
		});
	}
	$(document).ready(function(){
		serviceCenterOrderCount();
		window.setInterval("serviceCenterOrderCount()",60000);//1分钟刷新1次
	});
</script>
</ry:authorize>