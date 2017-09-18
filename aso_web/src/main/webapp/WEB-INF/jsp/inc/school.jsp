<%@ page contentType="text/html;charset=UTF-8" language="java" %>
	<select name="schoolId" title="学校" class="mustFill" id="schoolEdit"  style="width: 100px">
	<option value="">请选择</option>
     </select> 
	
	
<script>
var school_edit=document.getElementById("school_edit").value;



queryschoolEdit();
function queryschoolEdit(){
	$("#schoolId").html("<option value=''>请选择</option>");
	$.ajax({
		type:'post',
		url:'schoolmechanisminfo/getschool',
		dataType:'json',
		success:function(data){
		var school="<option value=''>请选择</option>";
		for(var i=0;i<data.length;i++){
			var status="";
			if(school_edit==data[i].schoolId){
				status="selected";
				
			}
			school+="<option "+status+" value="+data[i].schoolId+">"+data[i].schoolName+"</option>";
			}
		$("#schoolEdit").html(school);
			},
			error:function(){
			alert("没有信息");
			}
		});
}


</script>