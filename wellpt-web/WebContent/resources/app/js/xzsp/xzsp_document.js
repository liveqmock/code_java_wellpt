$(function() {
	var formUuid = $("#formUuid").val();
	var dataUuid = $("#dataUuid").val();
	var formDatas = loadFormDefinitionData(formUuid, dataUuid);
	$("#fileDynamicForm").dyform(
			{
				definition:formDatas.formDefinition,
				data:formDatas.formDatas,
				displayAsFormModel:false,
				success:function(){
					console.log("表单解析完毕");
				},
				error:function(){
					console.log("表单解析失败");
				}
			} 
	); 
	$("#fileDynamicForm").dyform("showAsLabel");
});



$("#btn_xzsp_print").click(function(){
		var xzsp_document_second_print_reason_dialog_content = "<div ><div id='dialog_form'><div class='dialog_form_content'><table width='100%'><tbody><tr ><td class='value'><div ><textarea id='xzsp_document_second_print_reason_dialog_content'  rows='11' cols='100' style='width: 299px;' name='xzsp_document_second_print_reason_dialog_content'></textarea></div></td></tr></tbody></table></div></div></div>";
		var json = new Object(); 
        json.content = xzsp_document_second_print_reason_dialog_content;
        json.title = "请输入打印原因：";
        json.height= 350;
        json.width= 350;
        var buttons = new Object(); 
        buttons.确定 = getDocumentFile;
        json.buttons = buttons;
        showDialog(json);
});

//保存二次打印记录并获取对应回执单附件
function getDocumentFile() {
	closeDialog();
	window.open(ctx+"/xzsp/printrecord/getDocumentFile?formUuid="+$("#formUuid").val()+"&dataUuid="+$("#dataUuid").val()+"&secondPrintReason="+$("#xzsp_document_second_print_reason_dialog_content").val());
}

//从url中获取参数值
function readSearch() {
	var search = window.location.search;
	var s = new Object();
	var searchArray = search.replace("?", "").split("&");
	for ( var i = 0; i < searchArray.length; i++) {
		var paraArray = searchArray[i].split("=");
		var key = paraArray[0];
		var value = paraArray[1];
		s[key] = value;
	}
	return s;
}