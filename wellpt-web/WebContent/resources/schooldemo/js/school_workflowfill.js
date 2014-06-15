$(function() {
	$(".selector").dytable("setFieldValue", {mappingName: "gongwen_type", value : $("#gongwen_type_1").val()});
	$(".selector").dytable("setFieldValue", {mappingName: "shouwen_biaoti", value : $("#shouwen_biaoti_1").val()});
	$(".selector").dytable("setFieldValue", {mappingName: "text_number", value : $("#text_number_1").val()});
	$(".selector").dytable("setFieldValue", {mappingName: "dispatch_unit", value : $("#dispatch_unit_1").val()});
	$(".selector").dytable("setFieldValue", {mappingName: "shouwen_code", value : $("#shouwen_code_1").val()});
	$(".selector").dytable("setFieldValue", {mappingName: "emergency_degree", value : $("#emergency_degree_1").val(), type:"select"});
	$(".selector").dytable("setFieldValue", {mappingName: "file_laiyuan", value : $("#file_laiyuan_1").val(), type:"radio"});
	
	var fj = $("#fileupload_1").val();
	if(fj!=undefined) {
		var fjVal = [];
		var fjArr = fj.split(",");
		for(var i=0; i<fjArr.length; i++) {
			var fjStr = fjArr[i].split(";");
			fjVal[i] = {fileID:fjStr[0],fileName:fjStr[1],isNew:false};
		}
		$(".selector").dytable("setFieldValue", {mappingName: "fileupload", value : fjVal});
	}
	WorkFlow.afterSuccessSubmit = function(bean) {
		return false;
	}
	
});
