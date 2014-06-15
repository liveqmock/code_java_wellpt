File.afterSaveFileSuccess = function(fileUuid) {
	window.close();
}

$(function() {
	var close_id = "B022003003";
	var btn_close = "<button id='"+close_id+"' name='"+close_id+"'>关闭</button>";
	
	//流程的flowInstUuid、dataUuid
	var fmFile_workflowRegister = $("#fmFile_workflowSendRegister").val();
	
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
			url : ctx+"/demo/getSendTextFields/flowInst/"+flowInstUuid,
			success : function (result) {
				fillFormData(result);
			}
		});
	}
	
	function fillFormData(result) {
		var nigao_bumen = result["nigao_bumen_1"];
		var nigao_bumen_arr = result["nigao_bumen_1"].split(",");
		if(nigao_bumen_arr.length>0){
			nigao_bumen = nigao_bumen_arr[0];
		}
		
		var fawen_danwei = result["fawen_danwei_1"];
		var fawen_danwei_arr = result["fawen_danwei_1"].split(",");
		if(fawen_danwei_arr.length>0){
			fawen_danwei = fawen_danwei_arr[0];
		}
		
		$(".selector").dytable("setFieldValue", {mappingName: "qianfa_date", value : result["qianfa_date_1"]});
		$(".selector").dytable("setFieldValue", {mappingName: "text_title", value : result["text_title_1"]});
		$(".selector").dytable("setFieldValue", {mappingName: "fawen_leixing", value : result["fawen_leixing_1"]});
		$(".selector").dytable("setFieldValue", {mappingName: "fawen_wenhao", value : result["fawen_wenhao_1"]});
		$(".selector").dytable("setFieldValue", {mappingName: "fawen_danwei", value : fawen_danwei});
		$(".selector").dytable("setFieldValue", {mappingName: "nigao_bumen", value : nigao_bumen});
		$(".selector").dytable("setFieldValue", {mappingName: "nigao_man", value : result["nigao_man_1"]});
		$(".selector").dytable("setFieldValue", {mappingName: "nigao_date", value : result["nigao_date_1"]});
		$(".selector").dytable("setFieldValue", {mappingName: "qianfa_person", value : result["qianfa_person_1"]});
		
		var fj = result["fileupload_1"];
		if(fj!=undefined) {
			var fjVal = [];
			var fjArr = fj.split(",");
			for(var i=0; i<fjArr.length; i++) {
				var fjStr = fjArr[i].split(";");
				fjVal[i] = {fileID:fjStr[0],fileName:fjStr[1],isNew:false};
			}
			//$(".selector").dytable("setFieldValue", {mappingName: "fileupload", value : fjVal});
		}
	}
	
});


