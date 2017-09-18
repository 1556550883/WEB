<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<base href="<%=basePath%>"/>
<meta charset="utf-8">
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<title>金赚后台管理系统</title>
<link href="dwz/themes/azure/style.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="dwz/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="dwz/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
<link href="dwz/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>

<!-- 自己添加的css -->
<link href="dwz/bootstrap_btn.css" rel="stylesheet" type="text/css" />

<!--<link href="bootstrap/css/bootstrap-theme.css" rel="stylesheet" type="text/css" />-->
<!-- [end mystyle.css] -->
  
<!--[if IE]>
<link href="themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->

<!--[if lte IE 9]>
<script src="js/speedup.js" type="text/javascript"></script>
<![endif]--><!--
<!-- ueditor-->
<script type="text/javascript" charset="utf-8" src="ueditor/ueditor.config.js" charset="utf-8"></script>
<script type="text/javascript" charset="utf-8" src="ueditor/ueditor.all.min.js" charset="utf-8"> </script>
<script type="text/javascript" charset="utf-8" src="ueditor/lang/zh-cn/zh-cn.js" charset="utf-8"></script>

<script src="dwz/js/jquery-1.7.2.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/jquery.cookie.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/jquery.validate.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/jquery.bgiframe.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/xheditor/xheditor-1.1.14-zh-cn.min.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/uploadify/scripts/jquery.uploadify.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/uploadify/scripts/jquery.uploadify.min.js" type="text/javascript" charset="utf-8"></script>
<!-- svg图表  supports Firefox 3.0+, Safari 3.0+, Chrome 5.0+, Opera 9.5+ and Internet Explorer 6.0+ -->
<script type="text/javascript" src="dwz/chart/raphael.js" charset="utf-8"></script>
<script type="text/javascript" src="dwz/chart/g.raphael.js" charset="utf-8"></script>
<script type="text/javascript" src="dwz/chart/g.bar.js" charset="utf-8"></script>
<script type="text/javascript" src="dwz/chart/g.line.js" charset="utf-8"></script>
<script type="text/javascript" src="dwz/chart/g.pie.js" charset="utf-8"></script>
<script type="text/javascript" src="dwz/chart/g.dot.js" charset="utf-8"></script>

<script src="dwz/js/dwz.core.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/dwz.util.date.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/dwz.validate.method.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/dwz.regional.zh.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/dwz.barDrag.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/dwz.drag.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/dwz.tree.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/dwz.accordion.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/dwz.ui.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/dwz.theme.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/dwz.switchEnv.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/dwz.alertMsg.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/dwz.contextmenu.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/dwz.navTab.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/dwz.tab.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/dwz.resize.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/dwz.dialog.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/dwz.dialogDrag.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/dwz.sortDrag.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/dwz.cssTable.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/dwz.stable.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/dwz.taskBar.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/dwz.ajax.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/dwz.pagination.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/dwz.database.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/dwz.datepicker.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/dwz.effects.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/dwz.panel.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/dwz.checkbox.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/dwz.history.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/dwz.combox.js" type="text/javascript" charset="utf-8"></script>
<script src="dwz/js/dwz.print.js" type="text/javascript" charset="utf-8"></script>
<!--
<script src="bin/dwz.min.js" type="text/javascript"></script>
-->
<script src="dwz/js/dwz.regional.zh.js" type="text/javascript" charset="utf-8"></script>
<!--自己添加的js-->
<script src="js/ajax2.js" type="text/javascript" charset="utf-8"></script>

<script type="text/javascript"  charset="utf-8">
$(function(){
	DWZ.init("dwz/dwz.frag.xml", {
		loginUrl:"login.jsp", loginTitle:"登录",	// 弹出登录对话框
		statusCode:{ok:200, error:300, timeout:301}, //【可选】
		pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
		debug:false,	// 调试模式 【true|false】
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"dwz/themes"}); // themeBase 相对于index页面的主题base路径
		}
	});
});
</script>
