<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<li>
	<label>省份：</label>
	<select id="provinceCodeQuery" name="provinceCode" onchange="ajaxLoadCity(this.value);">
		<option value="">请选择省份...</option>
    </select>
</li>
<li>
    <label>城市：</label>
    <select id="cityCodeQuery" name="cityCode" onchange="cityChange(this);">
		<option value="">请选择城市...</option>
	</select>
</li>	
	<script type="text/javascript" src="/js/ajax2"></script>
    <script type="text/javascript">
    	var provinceCodeQuery = document.getElementById('provinceCodeQuery');
    	var cityCodeQuery = document.getElementById('cityCodeQuery');
    	
    	ajaxLoadProvince();
    	
    	function ajaxLoadProvince(){
    		var url = 'provinceCity/province';
    		var result = new MyJqueryAjax(url,null,null,'json').request(); 		
    		for(var i=0;i<result.length;i++){
    			var item = result[i];
    			var option = new Option(item.provinceName, item.provinceCode);
    			if ('${bean.provinceCode}' == item.provinceCode+'') {
    				option.selected = true;
    				ajaxLoadCity(item.provinceCode);
				}
    			provinceCodeQuery.appendChild(option);
    		}
    	}
		
    	function ajaxLoadCity(provinceCode){
    		var url = 'provinceCity/city';
    		var params = 'provinceCode='+provinceCode;
    		cityCodeQuery.options.length=0; 
    		var option = new Option('请选择城市...', '');
			cityCodeQuery.appendChild(option);
    		var result = new MyJqueryAjax(url,params,null,'json').request();
    		for(var i=0;i<result.length;i++){
    			var item = result[i];
    			var option = new Option(item.cityName, item.cityCode);
    			if ('${bean.cityCode }' == item.cityCode+'') {
    				option.selected = true;
				}
    			cityCodeQuery.appendChild(option);
    		}
    		//如果需要其他操作，可以在自由页面实现该接口
    		otherOperating();
    	}

    	function cityChange(src){
    		//如果需要其他操作，可以在自由页面实现该接口
    		otherOperating();
        }
		
    	//如果需要其他操作，可以在自由页面实现该接口
    	otherOperating = function(){
    		
    	}
	</script>