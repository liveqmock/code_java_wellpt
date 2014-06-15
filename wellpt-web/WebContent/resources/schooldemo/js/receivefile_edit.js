$(function() {
	var btn_close = "<button id='btn_close' name='btn_close'>关闭</button>";
	var btn_trans = "<button id='btn_trans' name='btn_trans'>转流程</button>";
	
	$("#saveNormal").html("保存");
	function hasAuthority(btnId,btnName) {
		//判断用户是否有相应按钮的权限
		var resultData = "";
		JDS.call({
			async: false,
			service: "demoService.isGrant",
			data: [btnId],
			success: function(result){
				resultData = result.data;
			}
		});
		if(resultData) {
			File.addButton(btnName);
		}
		return resultData;
	}
	
	if(hasAuthority("B022002001",btn_close)) {
		$("#btn_close").live("click", function(){
			oConfirm("数据还未保存，是否继续关闭？", function(){window.close();});
		});
	}
	
	if(hasAuthority("B022002002",btn_trans)) {
		$("#btn_trans").live("click", function(){
			if($("#saveNormal").attr("id")) {
				//先保存文档再发起新流程
				$("#saveNormal").click();
			}else {
				transferProcess();
			} 
		});
	}
	
	function transferProcess() {
		window.open(ctx+"/demo/flowDefUuid/newfill?flowDefUuid=327fba68-196e-42b2-a5e9-ca1dd1894fdc&fileUuid="+$("#uuid").val()
				+"&scriptUrl=/resources/schooldemo/js/school_workflowfill.js&rtscriptUrl=/resources/schooldemo/js/receivetext_workflow.js");
	}
	
	function reloadFileWindow(fileUuid) {
		//oConfirm("文档已经自动保存，是否继续发起流程？", transferProcess); 暂时不弹提示
		transferProcess();
	}
	
	File.afterSaveFileSuccess = function(fileUuid){
		transferProcess();
		//window.close();
	};
	
});
