var video_btn = $('#video_upload'), interval;
  //上传附件
  new Ajax_upload(video_btn, {
      action: document.getElementById("video_url").value,
      name: 'myfile',
      autoSubmit: true, 
      onSubmit: function(file, ext) {
	  video_btn.val('上传中');
      this.disable();

      interval = window.setInterval( function() {
         var t = video_btn.val();
         if ( t.length < 2) { 
        	 video_btn.val( t  ); 
             } else { 
             video_btn.val('上传中');
             }
    	  }, 200);
    	   
    	 },
        onComplete: function(file, resp) {
    		video_btn.val('上传');
            window.clearInterval( interval );
            this.enable();
            if (resp != null && resp != "") 
            {
            	 if(resp=='false'){
                      $('#show_msg').show();
                   }else{
             			$('#videopath').val(resp);
             			$('#show_path').val(resp);
                    }
            }   
   	 } 
   });