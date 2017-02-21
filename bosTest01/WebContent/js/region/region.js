/**
 * 导入数据
 */
$(function(){
	$("#button-import").upload({
		action:'{pageContext.request.contextPath}/regionAction_importxls.action',
		name:myFile,
		onComplete:   function(data){
				if(data='1'){
					//上传成功
					$.message.alert("提示信息","区域数据导入成功！","info");
				}else{
            		//失败
            		$.messager.alert("提示信息","区域数据导入失败！","warning");
            	}
		}
	});
});