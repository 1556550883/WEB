//附件

var btn = $('#btn_upload'), interval;

//上传附件
new Ajax_upload(btn, {
    action: $("#upUrl").val(),
    name: 'file',
    responseType:"json",
    autoSubmit: true, 
    onSubmit: function(file, ext) {
    btn.val('上传中...');
    this.disable();

    interval = window.setInterval( function() {
       var t = btn.val();
       if ( t.length < 13) { 
         	   btn.val( t + "." ); 
           } else { 
             btn.val('上传中');
           }
  	  }, 200); 
  	 },
       onComplete: function(file, resp) {
           btn.val('继续上传');
           window.clearInterval( interval );
           this.enable();
           if (resp != null && resp != ""){
        	   var detailFile =resp;
        	 
				if(true)
				{   
					$("#annex").after("<tr id='annex_" + detailFile.pictureId + "'><td style='text-align:center;'></td><td style='text-align:center;'><input type='hidden' name='pictureId' value='"+detailFile.pictureId+"'>" + detailFile.picture + "</td><td style='text-align:center;'><a onclick='return deleteAnnex(" + detailFile.pictureId + "); ' style='cursor: pointer;padding:4px 10px;border-radius:3px; background:#ff4c4c;display:inline-block;color:#fff'>删除</a></td></tr> " );
					$("#pic").append("<li id='pic_"+detailFile.pictureId+"'><img alt=\""+detailFile.picture+"\" src='file/goods/"+detailFile.picture+"'  style='width:60px;height:60px'> </li>");
					$("#userImg").html("<img  src='http://7xo6tp.com2.z0.glb.qiniucdn.com/"+detailFile.fileName+"' style='width:60px;height:60px'><input type='hidden' name='attaId' value='"+detailFile.id+"'>");
      	   			$("#schoolImg").val(detailFile.fileName);
      	   			$("#MechCardZ").html("<img alt='"+detailFile.fileName+"'src='http://7xo6tp.com2.z0.glb.qiniucdn.com/"+detailFile.fileName+"'><input type='hidden' name='"+$("#MechCardZ").attr("title")+"' value='"+detailFile.fileName+"'/>");
					$("tr[id^='annex_']").each(function(i)
      	      	   	{
      	   				$(this).children(":first").text(i+1);
      	   			});
				}
				else
				{
					if(detailFile.error)
						alert(detailFile.error);
				}
           }   
  	 } 
	 });
var MechImg = $('#MechImg'), interval;
//上传附件
new Ajax_upload(MechImg, {
    action: document.getElementById("upUrl").value,
    name: 'file',
    responseType:"json",
    autoSubmit: true, 
    onSubmit: function(file, ext) {
	MechImg.val('上传中...');
    this.disable();

    interval = window.setInterval( function() {
       var t = MechImg.val();
       if ( t.length < 13) { 
    	   MechImg.val( t + "." ); 
           } else { 
        	   MechImg.val('上传中');
           }
  	  }, 200);
  	   
  	 },
       onComplete: function(file, resp) {
  		MechImg.val('继续上传');
           window.clearInterval( interval );
           this.enable();
           if (resp != null && resp != ""){
        	   var detailFile =resp;
				if(detailFile && detailFile.id)
				{
					$("#MechCardF").html("<img alt='"+detailFile.fileName+"' src='http://7xo6tp.com2.z0.glb.qiniucdn.com/"+detailFile.fileName+"'><input type='hidden' name='"+$("#MechCardZ").attr("title")+"' value='"+detailFile.fileName+"'/>");
				}
				else
				{
					if(detailFile.error)
						alert(detailFile.error);
				}
           }   
  	 } 
	 });
//删除附件
function deleteAnnex(id)
{
	if(confirm('确认删除文件？'))
	{
		$.ajax({
			type:'post',
			url:'supplierGoods/delPicture',
			data:'pictureId='+id,
			dataType:'json',
			success:function(){
				$("#annex_"+id+"").remove();
				$("#pic_"+id+"").remove();
				alertMsg.correct('删除成功！')
				},error:function(){
					alertMsg.warn('出现了未知的错误！');
				}
			});
		$("tr[id^='annex_']").each(function(i)
		{
			$(this).children(":first").text(i+1);
		});
	}	
	return false;
}
