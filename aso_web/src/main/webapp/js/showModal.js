/**
 * 提示框
 * @param title 标题
 * @param content 内容
 * @param fun   方法名
 * @return
 */


function showModal(title,content,fun){
	$('#myModals').remove();
	var divHtml = "<div class='modal hide fade'  id='myModals'>"
				 +"	<div class='modal-header'>"
				 +"	<button type='button' class='close' data-dismiss='modal'>×</button>"
				 +"	<h3>"+title+"： </h3>"
				 +"</div>"
				 +"<div class='modal-body'><p>"+content+"</p></div>"
				 +"<div class='modal-footer'>"
				 +"	<a href='#' class='btn' data-dismiss='modal'>关闭</a>"
				 +"	<a href='#' id='aok' class='btn btn-primary'  onclick=\""+fun+";return false;\">确定</a>"
				 +"</div>"
				 +"</div>";
    $('#content1').after(divHtml);
	$('#content').after(divHtml);
	if(fun == null || fun==''){
		$('#aok').hide();
	}
	$('#myModals').modal('show');
	
}

