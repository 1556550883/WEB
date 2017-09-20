MyJqueryAjax = function (v_url, data, func, dataType) {
	this.url = v_url;
	this.data = data;
	this.func = func;
	this.request = function () {
		var v_response;
		$.ajax({
			async:false, //同步请求
			url:v_url, //请求地址
			//contentType:"application/x-www-form-urlencoded;charset=GBK",
			data: data, //参数
			cache:false, //设置为 false 将不会从浏览器缓存中加载请求信息
			type:"POST", 
			dataType:dataType == null ? "text" : dataType, 
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				alert("MyJqueryAjax Request Error!");
			}, 
			success:(func !== null && func != undefined) ? func : function (req) {
				v_response = req;
			}
		});
		return v_response;
	};
};

MyJqueryAjax2 = function (v_url, data, func, dataType,errorFunc) {
	this.url = v_url;
	this.data = data;
	this.func = func;
	this.request = function () {
		var v_response;
		$.ajax({async:true, url:v_url, data: data, cache:false, type:"POST", dataType:dataType == null ? "text" : dataType, 
		error: (errorFunc !== null && errorFunc != undefined) ? errorFunc: function (req) {
			alert("调用失败!");
		}, success:(func !== null && func != undefined) ? func: function (req) {
			v_response = req;
		}});
		
	};
};

//获取ID
function sunID(tabId)
{
	var type = "";
	var id = "";
	
	if(tabId == "main_index2")
	{
		type = 2;
	}
	
	if(tabId == "main_index3")
	{
		type = 3;
	}
	
	if(tabId == "main_index4")
	{
		type = 4;
	}
	
	$("[name='ids" + type+"']")
	.each(function (){
		if($(this).attr("checked"))
		{
			id += $(this).val() + ",";
		}
	});
	
	id = id.substring(0, id.length-1) ==	"" ? "," : id.substring(0, id.length-1);
	
	return id;
}
//增加
function add(url,msg,w,h,tabId)
{
	$.pdialog.open(url,tabId,msg,{width:w,height:h,max:false, mask:true, maxable:false, minable:true, resizable:false,drawable:false});	
}

//新打开navtab用于增加
function openNav(url, msg, tabId)
{
	var id = sunID(tabId);
	
	navTab.openTab(tabId, url, {title:msg});
}

//新打开navtab用于修改
function openNavU(url, msg, tabId)
{
	var id = sunID(tabId);
	if(id.indexOf(',') < 0)
	{
		navTab.openTab(tabId, url + id, {title:msg});
	}
	else
	{
		alertMsg.info('请选择一项！')
	}
}

//修改
function update(url,msg,w,h,tabId){
	var id=sunID(tabId);
	if(id.indexOf(',')<0){
		$.pdialog.open(url+id,tabId,msg,{width:w,height:h,max:false, mask:true, maxable:false, minable:true, resizable:false,drawable:false});	
	}else{
		alertMsg.info('请选择一项！')
		}
}
function up(num) {
	var id=sunID();
	if(id.indexOf(',')<0){
    	$("[name='ids"+num+"']").each(function (){
    		if($(this).attr("checked")){
    	        var $tr = $(this).parents("tr");
		        if ($tr.index() != 0) {
					$tr.fadeOut().fadeIn();
					$tr.prev().before($tr);		
		        }
    			}
    	});
	}else{
		alertMsg.info('此项操作需选择一条数据，请检查后重新选择！')
	}
}
//下移

function down(num){
	var id=sunID();
	if(id.indexOf(',')<0){
$("[name='ids"+num+"']").each(function (){
			if($(this).attr("checked")){
			var len=$(this).length;
	        var $tr = $(this).parents("tr");
		$tr.fadeOut().fadeIn();
        $tr.next().after($tr);
		}
    });
}else{
	alertMsg.info('此项操作需选择一条数据，请检查后重新选择！')
    }
}
//置顶
function topp(num){
var id=sunID();
if(id.indexOf(',')<0){
$("[name='ids"+num+"']").each(function (){
if($(this).attr("checked")){
    var $tr = $(this).parents("tr");
	$tr.fadeOut().fadeIn();
	$tr.parent().prepend($tr);
}
});
}else{
	alertMsg.info('此项操作需选择一条数据，请检查后重新选择！')
	}
}
//置底
function bottom(num){
	var id=sunID();
	if(id.indexOf(',')<0){
	$("[name='ids"+num+"']").each(function (){
		if($(this).attr("checked")){
	        var $tr = $(this).parents("tr");
			if($tr.parent().parent().html()==$("table[class='new_tab']").html()){
				$tr.fadeOut().fadeIn();
				$tr.parent().append($tr);
			}
	    }
	});
	}else{
		alertMsg.info('此项操作需选择一条数据，请检查后重新选择！')
		}
}
function save(url,num){
	 var ids=$("[name='ids"+num+"']").map(function(){return $(this).val()}).get().join(','); 
		$.ajax({
			type:'post',
			url:url+ids,
			dateType:'json', 
			success:function(data){
			alertMsg.correct('保存成功！')
			navTabSearch(this);
		},error:function(){
			alertMsg.error('保存失败！')
		 }
		});
}

//行选中
function boxcheck(obj){
	if(obj.children[0].children[0].checked){
		obj.children[0].children[0].checked="";
	}else{
		obj.children[0].children[0].checked="checked";
		}
}
	//行选中2
function boxcheck2(obj){
	if(obj.children[0].children[0].children[0].disabled==false){
		if(obj.children[0].children[0].children[0].checked ){
			obj.children[0].children[0].children[0].checked="";
		}else{
			obj.children[0].children[0].children[0].checked="checked";
			}
	}
}

 

function getCitiesByProvinces(provinceId,citiesid,areaid){
	$("#area1").html("<option value=''>请选择</option>");
	$.ajax({
		type:'post',
		url:'area/citys',
		data:'provinceId='+provinceId,
		dataType:'json',
		success:function(data){
		var city="<option value=''>请选择</option>";
		for(var i=0;i<data.length;i++){
			var status="";
			if(citiesid==data[i].cityCode){
				status="selected";
				queryArea(data[i].cityCode,areaid);
			}
			city+="<option "+status+" value="+data[i].cityCode+">"+data[i].cityName+"</option>";
			}
		$("#cities1").html(city);
			},error:function(){
			alert("没有信息");
			}
		});
}
function queryArea(cityID,areaid){
	$.ajax({
		type:'post',
		url:'area/areas',
		data:'cityid='+cityID,
		dataType:'json',
		success:function(data){
		var area="<option value=''>请选择</option>";
		for(var i=0;i<data.length;i++){
			var status="";
			if(areaid==data[i].areaid){
				status="selected";
			}
			area+="<option "+status+" value="+data[i].areaid+">"+data[i].area+"</option>";
			}
		$("#area1").html(area);
			},error:function(){
			alert("没有信息");
			}
		});
}
 
//$(function(){
	//$('.add_big').hover(function(){
		//$('.ci_p2').toggle()
		//})
	//})
/**
 * 登录名验证
 */
var isVal=false;
function searchAjaxNameForId(relationId,obj,loginName){
	if(relationId==''){
	     var login=loginName.value;
	     if(login!=""){
		     $.ajax({
		    	 type:'post',//可选get
		    	 url:'user/searchAjaxName',//这里是接收数据的PHP程序
		    	 data:'loginName='+login,//传给PHP的数据，多个参数用&连接
		    	 dataType:'text',//服务器返回的数据类型 可选XML ,Json jsonp script html text等
		    	 success:function(msg){
		    	    if(msg=='fail'){
		    	    	alertMsg.warn("该"+obj+"已经存在！");
			    	    loginName.value="";
			    	    return false;
			    	}else{
			    		isVal=true;
				    }
		    	 },
		    	 error:function(){
		    	 }
		    });
	     }
	}
	}
//验证class=mustFill的元素不能为空
function check(){
	var prompt="";
	$("[class*='mustFill']").each(function(){
			if($(this).val() == ""){
				if(prompt.indexOf($(this).attr("title"))<0){
					prompt+=$(this).attr("title")+",";
				}
				}
		if($(this).attr("type")=="radio" || $(this).attr("type")=="checkbox"){
			if(checkCheckedByType($(this).attr("name"))==0){
				if($(this).attr("checked") != "checked" && prompt.indexOf($(this).attr("title"))<0){
					prompt+=$(this).attr("title")+",";
					}
			}
		}
		});
	if(prompt==""){
		return true
	}else{
		if(prompt.indexOf(",")<0){
			alertMsg.info(prompt+"信息不能为空！请检查后重新提交")
			return false;
			}else{
				alertMsg.info(prompt.substring(0,prompt.length-1)+"信息不能为空！请检查后重新提交")
				return false;
			}
	}
}
	