/**
 * 提示框
 * @param title 标题
 * @param content 内容
 * @param fun   方法名
 * @return
 */


function showAlert(msg,time){
	$('#myAlert').remove();
	var divHtml = "<div class='t-box t-box-align myAlert'  id='myAlert'>"
				 +"	   <div class=''>"
				 +         msg
				 +"    </div>"
				 +"</div>";
    //alert(divHtml)
	$('form').after(divHtml);
	$('#myAlert').show(100);
	if(time == null ) time = 1000;
	setTimeout(function(){
		$('#myAlert').remove();
	},time);
	//$('#myModals').modal('show');
	
}

