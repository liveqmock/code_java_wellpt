$(function() {
	var close_id = "B022002001";
	var btn_close = "<button id='"+close_id+"' name='"+close_id+"'>关闭</button>";
	
	//流程的flowInstUuid、dataUuid
	var fmFile_workflowRegister = $("#fmFile_workflowRegister").val();
	var fmFile_workflowSubmit = $("#fmFile_workflowSubmit").val();
	
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
	
	if(fmFile_workflowRegister!=undefined) {
		fmFile_workflowRegister = JSON.parse(fmFile_workflowRegister);
		var flowInstUuid = fmFile_workflowRegister["flowInstUuid"];
		$.ajax({
			type : "GET",
			async: false,
			url : ctx+"/demo/getFields/flowInst/"+flowInstUuid,
			success : function (result) {
				fillFormData(result);
			}
		});
	}
	
	if(fmFile_workflowSubmit!=undefined) {
		fmFile_workflowSubmit = JSON.parse(fmFile_workflowSubmit);
		var formUuid = fmFile_workflowSubmit["formUuid"];
		var dataUuid = fmFile_workflowSubmit["dataUuid"];
		$.ajax({
			type: "GET",
			async: false,
			url : ctx+"/demo/getFields/formData/"+formUuid+"/"+dataUuid,
			success: function(result) {
				fillFormData(result);
			}
		});
	}
	
	function fillFormData(result) {
		$(".selector").dytable("setFieldValue", {mappingName: "gongwen_type", value : result["gongwen_type_1"]});
		$(".selector").dytable("setFieldValue", {mappingName: "shouwen_biaoti", value : result["shouwen_biaoti_1"]});
		$(".selector").dytable("setFieldValue", {mappingName: "text_number", value : result["text_number_1"]});
		$(".selector").dytable("setFieldValue", {mappingName: "dispatch_unit", value : result["dispatch_unit_1"]});
		$(".selector").dytable("setFieldValue", {mappingName: "shouwen_code", value : result["shouwen_code_1"]});
		$(".selector").dytable("setFieldValue", {mappingName: "emergency_degree", value : result["emergency_degree_1"], type:"select"});
		$(".selector").dytable("setFieldValue", {mappingName: "file_laiyuan", value : result["file_laiyuan_1"], type:"radio"});
		
		var fj = result["fileupload_1"];
		if(fj!=undefined) {
			var fjVal = [];
			var fjArr = fj.split(",");
			for(var i=0; i<fjArr.length; i++) {
				var fjStr = fjArr[i].split(";");
				fjVal[i] = {fileID:fjStr[0],fileName:fjStr[1],isNew:false};
			}
			$(".selector").dytable("setFieldValue", {mappingName: "fileupload", value : fjVal});
		}
	}
	
});

File.afterSaveFileSuccess = function(fileUuid) {
	window.close();
}

