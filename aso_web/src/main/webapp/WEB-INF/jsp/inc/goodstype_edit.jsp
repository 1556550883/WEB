<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<dl>
	<dt>一级类别：</dt>
	<dd>
	<select id="goodsTypeId1Edit" name="goodsTypeId1"  onchange="ajaxLoadgoodsTypeId2(this.value);">
		<option value="">请选择一级类别...</option>
    </select>
	</dd>
</dl>
<dl>
	<dt>二级类别：</dt>
	<dd>
	<select id="goodsTypeId2Edit" name="goodsTypeId2"   onchange="goodsTypeId2Change(this);">
		<option value="">请选择二级类别...</option>
	</select>
	</dd>
</dl>
	<script type="text/javascript" src="/js/ajax2"></script>
    <script type="text/javascript">
    	var goodsTypeId1Edit = document.getElementById('goodsTypeId1Edit');
    	var goodsTypeId2Edit = document.getElementById('goodsTypeId2Edit');
    	ajaxLoadgoodsTypeId1();
    	function ajaxLoadgoodsTypeId1(){
    		var url = 'goodsType/goodsTypeId1';
    		var result = new MyJqueryAjax(url,null,null,'json').request();
    		for(var i=0;i<result.length;i++){
    			var item = result[i];
    			var option = new Option(item.goodsType,item.goodsTypeId);
    			if ('${bean.goodsTypeId1}'==item.goodsTypeId+'') {
    				option.selected = true;
    				ajaxLoadgoodsTypeId2(item.goodsTypeId);
				}
    			goodsTypeId1Edit.appendChild(option);
    		}
    		
    	}
		
    	function ajaxLoadgoodsTypeId2(goodsTypeId){
    		var url = 'goodsType/goodsTypeId2';
    		var params = 'goodsTypeId1='+goodsTypeId;
    		goodsTypeId2Edit.options.length=0; 
    		var option = new Option('请选择二级类别...', '');
    		goodsTypeId2Edit.appendChild(option);
    		var result = new MyJqueryAjax(url,params,null,'json').request();
    		for(var i=0;i<result.length;i++){
    			var item = result[i];
    			var option = new Option(item.goodsType,item.goodsTypeId);
    			if ('${bean.goodsTypeId2}' == item.goodsTypeId+'') {
    				option.selected = true;
				}
    			goodsTypeId2Edit.appendChild(option);
    		}
    		//如果需要其他操作，可以在自由页面实现该接口
    		otherOperating();
    	}

    	function goodsTypeId2Change(src){
    		//如果需要其他操作，可以在自由页面实现该接口
    		otherOperat(src.value);
        }
		function otherOperating(){
		
		}
    	//如果需要其他操作，可以在自由页面实现该接口
    	//otherOperating = function(){
    		
    	//}
	</script>