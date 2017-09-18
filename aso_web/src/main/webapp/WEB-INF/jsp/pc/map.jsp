<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>  
<html>  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />  
<title>百度地图</title>  
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.3"></script>  
</head>  
<body>  
<body style="background:#EEEEEE;">
    <div style="text-align: center;">   
        要查询的地址：<input id="text_" type="text" value="" style="margin-right:100px;"/>
        查询结果(经纬度)：<input id="result_" type="text" />
        <input type="button" value="查询" onclick="searchByStationName();"/>
         <input type="button" value="添加" onclick="addResult();"/>
        <div id="container" 
            style="
                margin-top:30px; 
				margin-left:30px; 
                width: 1260px; 
                height: 690px; 
                top: 50; 
                border: 1px solid gray;
                overflow:hidden;">
        </div>
    </div>
</body>
<script type="text/javascript">
    var map = new BMap.Map("container");
    map.centerAndZoom("北京", 12);
    map.enableScrollWheelZoom();    //启用滚轮放大缩小，默认禁用
    map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用

    map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
    map.addControl(new BMap.OverviewMapControl()); //添加默认缩略地图控件
    map.addControl(new BMap.OverviewMapControl({ isOpen: true, anchor: BMAP_ANCHOR_TOP_LEFT}));   //右下角，打开

    var localSearch = new BMap.LocalSearch(map);
    localSearch.enableAutoViewport(); //允许自动调节窗体大小


function searchByStationName() {
	var placeName=document.getElementById("text_").value;
    if(placeName!='' || placeName!=null){
		try{
	    map.clearOverlays();//清空原来的标注
	    var keyword = document.getElementById("text_").value;
	    localSearch.setSearchCompleteCallback(function (searchResult) {
	        var poi = searchResult.getPoi(0);
			if(!poi){
				alert("未找到地址");
				return ;
			}
	        document.getElementById("result_").value = poi.point.lng + "," + poi.point.lat;
	        map.centerAndZoom(poi.point, 13);
	        var marker = new BMap.Marker(new BMap.Point(poi.point.lng, poi.point.lat));  // 创建标注，为要查询的地方对应的经纬度
			marker.enableDragging(); //标记开启拖拽  
			var gc = new BMap.Geocoder();//地址解析类  
			//添加标记拖拽监听  
			marker.addEventListener("dragend", function(e){  
				//获取地址信息  
				gc.getLocation(e.point, function(rs){  
					showLocationInfo(e.point, rs);  
				});  
			}); 
	
	
	        map.addOverlay(marker);
	        //var content = document.getElementById("text_").value + "<br/><br/>经度：" + poi.point.lng + "<br/>纬度：" + poi.point.lat;
	       // var infoWindow = new BMap.InfoWindow("<p style='font-size:14px;'>" + content + "</p>");
	        //marker.addEventListener("click", function () { this.openInfoWindow(infoWindow); });
	        // marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
	    });
	    localSearch.search(keyword);
		}catch(e){alert(e.message);}
    }
} 

//显示地址信息窗口  
function showLocationInfo(pt, rs){  
try{
    var addComp = rs.addressComponents;  
   // var addr = "当前位置：" + addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber + "<br/>";  
    //addr += "纬度: " + pt.lat + ", " + "经度：" + pt.lng;  
    //alert(addr);  
     document.getElementById("text_").value=addComp.province+addComp.city+ addComp.district+ addComp.street+ addComp.streetNumber;
	 document.getElementById("result_").value= pt.lng+ ", "+pt.lat ; 
	 }catch(e){alert(e.message);}
}
function addResult(){
	var _result=document.getElementById("result_").value;
	if(!_result){
		return;
	}
	var results=_result.split(",");
	var windows=window.opener;
	if(!windows){
		windows=window.parent;	
	}
	if(windows){
		windows.document.getElementById("longitude").value=results[1];
		windows.document.getElementById("latitude").value=results[0];
	}
	window.close();
}
</script>
</html>