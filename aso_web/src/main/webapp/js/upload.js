/**
 * 图片 上传
 * @param button_id  上传按钮的 id
 * @param url        上传路劲
 * @param shows_id   显示的文本框的Id
 * @return errorshow 错误信息提示Id
 */
function upload_img(button_id,url,shows_id,errorshow){
	 var btn = $('#'+button_id), interval;
	  //上传附件
	  new Ajax_upload(btn, {
	      action: url,
	      name: 'myfile',
	      autoSubmit: true, 
	      onSubmit: function(file, ext) {
	      btn.val('上传中...');
	      this.disable();

	      interval = window.setInterval( function() {
	         var t = btn.val();
	         if ( t.length < 2) { 
	           	   btn.val( t  ); 
	             } else { 
	               btn.val('上传中...');
	             }
	    	  }, 200);
	    	   
	    	 },
	        onComplete: function(file, resp) {
	    		   btn.html('上传');
	    	          window.clearInterval( interval );
	    	          this.enable();
	    	          if (resp != null && resp != "") 
	    	          {
	    	        	  
	    				var detailFile = resp;
	    				if(detailFile )
	    				{
	    					if(detailFile){
	    						
	    						//$("#fieldname").val(detailFile.filename);
	    					}
	    					$("#"+shows_id).val(detailFile);
	    				}else{
	  				var result=detailFile.result;
	  				if(result==-4){//判断图片大小
	  					$('#'+errorshow).html();
	  					$('#'+errorshow).html('图片太大,请上传 2M 以内的图片');
	  				}
	  				if(result==-3){//判断图片格式
	  					$('#'+errorshow).html();
	  					$('#'+errorshow).html('格式错误,请上传.JPG,.DMP,.GIF,.JPEG,.TIFF,.PNG格式的图片');
	  				}
	  			  }
	            }   
	   	 } 
	   });
}