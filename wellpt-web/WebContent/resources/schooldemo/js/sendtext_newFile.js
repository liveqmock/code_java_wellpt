$(function() {
	var close_id = "B022003003";
	var btn_close = "<button id='"+close_id+"' name='"+close_id+"'>关闭</button>";
	
	$("#saveNormal").html("保存");
	$("#save").remove();
	
	//判断用户是否有操作关闭的权限
	var resultData = "";
	JDS.call({
		async: false,
		service: "demoService.isGrant",
		data: [close_id],
		success: function(result){
			resultData = result.data;
		}
	});
	if(resultData) {
		File.addButton(btn_close);
		$("#"+close_id).live("click", function(){
			oConfirm("数据还未保存，是否继续关闭？", function(){window.close();});
		});
	}
});

File.afterSaveFileSuccess = function(fileUuid) {
	window.close();
}
