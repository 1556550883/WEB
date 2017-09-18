<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/inc/pression.jsp" %>
<head>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<base href="<%=basePath%>"/>
<title>如何收徒</title>
</head>
<style>
body{ margin:0; padding:0; width:640px; margin:auto; font-family:"微软雅黑";}
*{ font-size:18px;}
a{text-decoration:none; display:inline-block;}
ul li{list-style: none;}
ol li{list-style:none;}
img{background:none;}
h1,h2,h3,h4,h5,h6,strong,p{ font-weight:normal; margin:0;}
.clearfix{clear:both;}
/*导航*/
.nav{ width:100%;background:#e60000;}
.nav img{ width:1.5em; vertical-align:middle; margin:0 190px 10px 26px;}
.nav h1{ font-size:36px; height:78px; line-height:78px;font-weight:bold;color:#fff;display:inline-block;}
/*广告*/
.banner img{ width:640px;}
/*主体*/
.main{text-align:center;}
.main h1{ width:180px; background:#e60000; font-size:42px; border-radius:20px; font-style:italic; font-weight:bold;color:#fcd91d; display:inline-block; margin-top:56px; margin-bottom:36px;}
.main h2{ font-size:37px; color:#636363; line-height:64px;}
.main span{ color:#e60000; font-size:37px; }
.main p{line-height:48px; font-size:25px; color:#636363;}
.blank{ margin-bottom:100px;}
</style>
<body>
<!--<div class="nav">
    <h1><a><img src="images/arrow.png"></a>如何收徒</h1>
</div>
--><div class="banner">
	<img src="<%=path %>/img/banner.png">
</div>
<div class="main">
	<div>
    	<h1>最有效</h1>
        <h2>在这些位置<span>评论:</span></h2>
		<p>各大安卓应用商店中其他赚钱软件的评论区</br>安卓各大应用商店排行榜中软件评论区</br>微博大v的微博下、热门微博下</p>
    </div>
	<div>
    	<h1>最快捷</h1>
        <h2>在这些位置<span>分享:</span></h2>
		<p>你个人的QQ、微博、微信群、朋友圈 QQ空间、</br>人人、百度贴吧、天涯、豆瓣、知乎等</br>微博的热门话题中</br>你自己或你熟人的网站、论坛、app内</p>
    </div>
    <div class="blank">
    	<h1>最惊喜</h1>
        <h2>在这些位置<span>留言:</span></h2>
		<p>YY、斗鱼、秀色等美女游戏直播间 秒拍、潮自拍、</br>脸优等最潮最火app 优酷土豆爱奇艺乐视腾讯搜狐</br>等知名视频网站</p>
    </div>
</div>
</body>
