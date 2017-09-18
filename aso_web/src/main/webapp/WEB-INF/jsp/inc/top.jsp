<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
<div class="head_bg"></div>
<div class="head clearfix">
<div class="auto">
	<h2 class="fl">
    	<a href="#"><img src="img/logo.png" width="135" alt="logo"/></a>
    </h2>
   <div class="nav fr">
    	<ul class="clearfix">
        	<li><a <c:if test="${css==1}"> class="active"</c:if> href="home">首页</a></li>
            <li><a <c:if test="${css==2}"> class="active"</c:if> href="zxyx">筑心云校</a></li>
            <li>
            	<a href="#">筑心产品</a>
                <div class="index_down clearfix">
                	<div class="fl down1">
                    	<span class="down1_span">智能设备</span>
                        <div class="down2">
                        	<span>身心反馈类</span>
                            <div class="down2_all">
                            	<a href="#">身心减压调养舱（太空舱型）ZX—V8B</a>
                                <a href="#">身心减压调养舱（无太空舱型）ZX—V6B</a>
                                <a href="#">心理训练系统ZX—PT</a>
                            </div>
                        </div>
                        <div class="down2">
                        	<span>音乐放松类</span>
                            <div class="down2_all">
                            	<a href="#">智能音乐放松系统（二代反馈催眠型）ZX—YY</a>
                            </div>
                        </div>
                        <div class="down2">
                        	<span>认知训练</span>
                            <div class="down2_all">
                            	<a href="#">智能身心反馈训练系统（标准版）ZX—SX</a>
                            </div>
                        </div>
                        <div class="down2">
                        	<span>宣泄设备</span>
                            <div class="down2_all">
                            	<a href="#">智能互动呐喊宣泄系统ZX—NH1</a>
                            </div>
                        </div>
                    </div>
                    <div class="fl down1" >
                    	<span class="down1_span">非智能设备</span>
                        <div class="down2">
                        	<span>沙盘</span>
                            <div class="down2_all">
                            	<a href="#">标准版沙盘ZX—SPB</a>
                                <a href="#">专业版沙盘ZX—SPZ</a>
                            </div>
                        </div>
                        <div class="down2">
                        	<span>艺术治疗箱</span>
                            <div class="down2_all">
                            	<a href="#">标准艺术治疗箱ZX—YS</a>
                            </div>
                        </div>
                        <div class="down2">
                        	<span>团体活动包</span>
                            <div class="down2_all">
                            	<a href="#">室内活动包ZX—TTN</a>
                            </div>
                        </div>
                        <div class="down2">
                        	<span>基础宣泄设备</span>
                            <div class="down2_all">
                            	<a href="#">户外活动包ZX—TTN</a>
                                <a href="#">宣泄人ZX—XXR</a>
                                <a href="#">宣泄地板ZX—XXD</a>
                            </div>
                        </div>
                    </div>
                    <div class="fl down1" >
                    	<span class="down1_span">专案定制服务</span>
                        <a class="down2" href="#">普及统配</a>
                        <a class="down2" href="#">基础应用</a>
                        <a class="down2" href="#">标准成长</a>
                        <a class="down2" href="#">专业标杆</a>
                    </div>
                    <div class="fl down1" >
                    	<span class="down1_span">技术协作服务</span>
                        <a class="down2" href="#">售前市场支持</a>
                        <a class="down2" href="#">售中方案确认</a>
                        <a class="down2" href="#">售后安装培训</a>
                        <a class="down2" href="#">售后成长提升</a>
                        <a class="down2" href="#">战略合作共建</a>
                    </div>
                </div>
                
            </li>
            <li><a <c:if test="${css==4}"> class="active"</c:if> href="lszq">老师专区</a></li>
            <li><a <c:if test="${css==5}"> class="active"</c:if> href="jxzq">经销专区</a></li>
            <li><a <c:if test="${css==6}"> class="active"</c:if> href="xsxy">心商学院</a></li>
            <li><a <c:if test="${css==7}"> class="active"</c:if> href="gywm">关于我们</a></li>
        </ul>
    </div>
</div>    
</div>
</body>
</html>