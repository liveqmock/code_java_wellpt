$(function() {
	var xzsp_view = "/resources/pt/js/file/list_view.js";
	
	var reg_id = "B022001011";
	var btn_register = "<button id='"+reg_id+"' name='"+reg_id+"'>登记</button>";
	
	//判断用户是否有操作登记的权限
	var resultData = "";
	JDS.call({
		async: false,
		service: "demoService.isGrant",
		data: [reg_id],
		success: function(result){
			resultData = result.data;
		}
	});
	if(resultData) {
		WorkFlow.addButton(btn_register);
		//登记按钮
		WorkFlow.bind({
			id: reg_id,
			functionName: "save",
			onSuccess: function(data){
				oAlert("文档保存成功！",gotoReload(data));
			}
		});
	}
	
	function gotoReload(data){
		$.getScript(ctx + xzsp_view, function() {
			var paramJson = {"fmFile_workflowRegister":JSON.stringify(data)};
			File.addFileWithTitleAndMethodNameAndScriptUrl.call(this,'77cae0e7-fb23-4b86-a6de-9830bd561153',
					'新建收文登记','','/resources/schooldemo/js/school_receivefile.js',paramJson);
			if(WorkFlow.isExistsWorkData()) {
				window.location.reload();
			} else {
				window.location = ctx + "/workflow/work/view/draft?flowInstUuid=" + data["flowInstUuid"];
			}	
		});
	}
	
	// 提交前服务处理
	//WorkFlow.setWorkData("beforeSubmitService", "demoService.beforeSubmit");
	// 提交后服务处理
	//WorkFlow.setWorkData("afterSubmitService", "demoService.afterSubmit");
	
//	WorkFlow.afterSuccessSubmit = function(bean) {
//		$("button").each(function(){
//			$(this).attr("disabled", true);
//			$(this).css("color", "gray");
//		});
//		oConfirm("提交成功，是否跳转到收文登记簿？", gotoGWDJB, bean); //先这样处理：先打开登记簿，然后关闭流程页
//		return true;
//	}
	
//	function gotoGWDJB(bean) {
//		$.getScript(ctx + xzsp_view, function() {
//			var formData = {"formUuid":bean.formUuid,"dataUuid":bean.rootFormDataBean.formDatas[0].dataUuid};
//			var paramJson = {"fmFile_workflowSubmit":JSON.stringify(formData)};
//			File.addFileWithTitleAndMethodNameAndScriptUrl.call(this,'77cae0e7-fb23-4b86-a6de-9830bd561153',
//					'新建收文登记','closeDemoWindow','/resources/schooldemo/js/school_receivefile.js',paramJson);
//		});
//	}
});

//function closeDemoWindow(){
//	window.close();
//}
