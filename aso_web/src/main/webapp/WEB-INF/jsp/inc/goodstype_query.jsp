<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<li>
	<label>一级类别：</label>
	<select id="goodsTypeId1Query" name="goodsTypeId1" onchange="ajaxLoadgoodsTypeId2(this.value);">
		<option value="">请选择一级类别...</option>
    </select>
</li>
<li>
    <label>二级类别：</label>
    <select id="goodsTypeId2Query" name="goodsTypeId2" onchange="goodsTypeId2Change(this);">
		<option value="">请选择二级类别...</option>
	</select>
</li>	
	<script type="text/javascript" src="/js/ajax2"></script>
    <script type="text/javascript">
    	var goodsTypeId1Query = document.getElementById('goodsTypeId1Query');
    	var goodsTypeId2Query = document.getElementById('goodsTypeId2Query');
    	 ajaxLoadgoodsTypeId1();
    	function ajaxLoadgoodsTypeId1(){
    		var url = 'goodsType/goodsTypeId1';
    		var result = new MyJqueryAjax(url,null,null,'json').request(); 		
    		for(var i=0;i<result.length;i++){
    			var item = result[i];
    			var option = new Option(item.goodsType,item.goodsTypeId);
    			if ('${bean.goodsTypeId1}' == item.goodsTypeId+'') {
    				option.selected = true;
    				ajaxLoadgoodsTypeId2(item.goodsTypeId);
    				
				}
    			goodsTypeId1Query.appendChild(option);
    		}
    	}
		
    	function ajaxLoadgoodsTypeId2(goodsTypeId1){
    		var url = 'goodsType/goodsTypeId2';
    		var params = 'goodsTypeId1='+goodsTypeId1;
    		goodsTypeId2Query.options.length=0; 
    		var option = new Option('请选择二级类别...', '');
    		goodsTypeId2Query.appendChild(option);
    		var result = new MyJqueryAjax(url,params,null,'json').request();
    		for(var i=0;i<result.length;i++){
    			var item = result[i];
    			var option = new Option(item.goodsType,item.goodsTypeId);
    			if ('${bean.goodsTypeId2}' == item.goodsTypeId+'') {
    				option.selected = true;
				}
    			goodsTypeId2Query.appendChild(option);
    		}
    		//如果需要其他操作，可以在自由页面实现该接口
    		otherOperating();
    	}

    	function goodsTypeId2Change(src){
    		//如果需要其他操作，可以在自由页面实现该接口
    		otherOperating();
        }
		
    	//如果需要其他操作，可以在自由页面实现该接口
    	otherOperating = function(){
    		
    	}
	</script>