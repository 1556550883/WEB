<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
   <!--  <%@include file="/WEB-INF/jsp/inc/base.jsp" %> -->
<script type="text/javascript" src="js/dtree/dtree_check.js"></script>
</head>
<body>
<%@include file="/WEB-INF/jsp/inc/header.jsp" %>
<form class="form-horizontal" method="post" action="role/saveOrUpdate" name="myform" id="myform" onsubmit="return $(this).validate();" >
<div class="container-fluid">
    <div class="row-fluid ">
        <%@include file="/WEB-INF/jsp/inc/left.jsp" %>
			<div id="content1" class="span10 box">
				<div class="box-header well data-original-title">
						<h2><i class="icon-edit"></i>权限信息 </h2>
					</div>
			<div class="span4" style="margin-top: 4px;">
				<script type="text/javascript">
			    var d = new dTree('d',true,'authCode',true,true,false,false);
				function buildTree() {
					try{
						<c:forEach var="item" items="${authList}" varStatus="status">
							d.add("${item.authCode}","${item.parentAuthCode}",'<font  color="black">${item.authName}</font>','javascript:updateNode(${item.authId})','javascript:void(0);','_blank',${not empty item.roleId});
						</c:forEach>
						document.write(d);
						}catch(e){
							alert(e.message);
						}
					}
					buildTree();
				</script>
			</div>
        	<div class="box-content span6" id="upageNode">
	        		
						<fieldset>
								<div class="control-group">
										<label class="control-label" for="focusedInput">角色名称</label>
										<div class="controls">
											<input type="hidden" name="roleId" value="${role.roleId}"></input>
										    <input id="authName" name="roleName" class="input-xlarge focused" placeholder="请输入角色名称" valitype="require" id="focusedInput" type="text" value="${role.roleName}">
											<span class="help-inline">角色名称不能为空</span>
										</div>
										</div>
									  <div class="control-group">
										<label class="control-label" for="disabledInput">角色排序</label>
										<div class="controls">
										 <input class="input-xlarge focused" id="orderby" name="orderby" placeholder="请输入角色排序" valitype="require" type="text" value="${role.orderby}">
										 <span class="help-inline">角色排序不能为空</span>
										</div>
									  </div>
									  
									  <div class="control-group" style="margin-left: 25%;">
										<button type="submit" class="btn btn-primary" >保存</button>
										<button type="button" class="btn" onclick="back();">返回</button>
								</div>
							</fieldset>
				
        	</div>
        </div>
        <!--/#content.span10-->
    </div>
</div>
</form>
<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
<script type="text/javascript">
//保存或修改权限菜单
function saveOrUpdateRole(){
	$("#myform").submit();
}
</script>
</body>
</html>