<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<head>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>为什么要收徒</title>
</head>
<style>
body{ margin:0; padding:0; width:640px; margin:auto; font-family:"微软雅黑";}
*{ font-size:18px;}
a{text-decoration:none; display:inline-block;}
ul li{list-style: none;}
ol li{list-style:none;}
img{background:none;}
h1,h2,h3,h4,h5,h6,strong,b{ font-weight:normal}
.clearfix{clear:both;}
/*导航*/
.nav{ width:100%;background:#e60000;}
.nav img{ width:1.5em; vertical-align:middle; margin:0 160px 10px 26px;}
.nav h1{ font-size:36px; height:36px; line-height:36px;font-weight:bold;color:#fff;display:inline-block;}
/*广告*/
.banner img{ width:640px;}
/*主体*/
.main{text-align:center;}
h2{ font-size:28px; color:#636363; font-weight:bold;}
p{ font-size:24px; color:#636363;}
.s1{ font-size:24px; color:#e60000; font-weight:bold; line-height:38px;}
.s2{ font-size:38px; color:#e60000; font-weight:bold;}
.s3{ font-size:24px; color:#636363; line-height:48px; margin-top:12px;}
.s4{ font-size:30px; color:#ff6316; line-height:96px;}
</style>
<body>
<!--<div class="nav">
    <h1><a><img src="images/arrow.png"></a>为什么要收徒</h1>
</div>
--><div class="banner"><img src="<%=path %>/img/banner_02.png"></div>
<div class="main">
	<h2>来来来，我们来分析一下</h2>
    <p>
    <span class="s1"><span class="s2">首先</span>，你只要收了徒弟，</br>就可以躺着来收下面这些钱了！</br></span>
    <span class="s3">1个徒弟每天赚10元，你可以获得1元奖励；</br>10个徒弟每天赚10元，你可以获得10元奖励；</br>100个徒弟每天赚100元，你可以获得100元奖励</br>
    <span class="s4">(1个月就有3000元哦！！）</span></br>
	而且徒弟是永久的，那就意味着，只要你收了一堆的</br>徒 弟，即使自己不做任务，徒弟们都会给你帯来这么</br>多收入！躺着就把钱赚了有木有！！</br>
	<span class="s4">宝赚就是这么靠谱！ ！ ！</span></br>
	然后，再算算，100个徒弟每人再收10个徒弟</br>你就有1000个徒孙，来自徒孙的奖励......</br>(奖励我还算得过来吗）</br>所以，小伙伴们，不要抱怨赚钱慢了，那是因为你只</br>是 一个人在战斗，没有找对赚钱的方法 赶快去收徒</br>弟吧徒弟够多				，收入就蹭蹭蹭的指数上涨</br>
	<span class="s4">走上人生巅峰真是分分钟的事儿昂！</span></span>
	</p>
</div>
</body>