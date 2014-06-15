$(function() {
	
	//校办登记发文编号 环节ID
	var XBDJ_FWBH = "T727";
	var taskId = WorkFlow.getTaskId();
	if(taskId == XBDJ_FWBH) {
		var xzsp_view = "/resources/pt/js/file/list_view.js";
		
		//登记
		var check_in_id = "B022001012";
		var btn_check_in = "<button id='"+check_in_id+"' name='"+check_in_id+"'>登记</button>";
		
		var result_edit_text = "";
		JDS.call({
			async: false,
			service: "demoService.isGrant",
			data: [check_in_id],
			success: function(result){
				result_edit_text = result.data;
			}
		});
		if(result_edit_text) {
			WorkFlow.addButton(btn_check_in);
			//登记按钮
			WorkFlow.bind({
				id: check_in_id,
				functionName: "save",
				onSuccess: function(data){
					oAlert("文档保存成功！",gotoSendText(data));
				}
			});
		}
		
		function gotoSendText(data){
			$.getScript(ctx + xzsp_view, function() {
				var paramJson = {"fmFile_workflowSendRegister":JSON.stringify(data)};
				File.addFileWithTitleAndMethodNameAndScriptUrl.call(this,'beb44a80-2729-482d-b287-aec51de511cd',
						'新建发文登记','','/resources/schooldemo/js/school_sendfile.js',paramJson);
				if(WorkFlow.isExistsWorkData()) {
					window.location.reload();
				} else {
					window.location = ctx + "/workflow/work/view/draft?flowInstUuid=" + data["flowInstUuid"];
				}	
			});
		}
	}
	
	// 提交前服务处理
	//WorkFlow.setWorkData("beforeSubmitService", "demoService.beforeSubmit");
	// 提交后服务处理
	//WorkFlow.setWorkData("afterSubmitService", "demoService.afterSubmit");
	

});
