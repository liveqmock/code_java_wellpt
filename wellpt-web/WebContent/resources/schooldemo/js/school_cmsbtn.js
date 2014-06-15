var DEMO_BTNS = {};

$(function() {
	
	//发起收文流程
	DEMO_BTNS.launchReceive = function(obj) {
		var object = $(obj).parents(".viewContent").find("input[class=checkeds]:checked");
		if(object.length==0) {
			oAlert("请选择一条记录！");
			return false;
		}
		if(object.length!=1) {
			oAlert("只能选择一条记录！");
			return false;
		}
		var receiveFileUuid = $(object).val();
		
		window.open(ctx+"/demo/flowDefUuid/newfill?flowDefUuid=327fba68-196e-42b2-a5e9-ca1dd1894fdc&fileUuid="
				+receiveFileUuid+"&scriptUrl=/resources/schooldemo/js/school_workflowfill.js&rtscriptUrl=/resources/schooldemo/js/receivetext_workflow.js");
		
	};
	
	//启动督查
	DEMO_BTNS.supervision = function(taskUuid) {
		var element = $(this);
		var taskUuids = [];
		if (isTopBtn(element)) {
			$("input[class=checkeds]:checked").each(function() {
				taskUuids.push($(this).val());
			});
			if (taskUuids.length == 0) {
				oAlert("请选择一条记录!");
				return;
			}
		} else {
			taskUuids.push(taskUuid);
		}
		if (taskUuids.length > 1) {
			oAlert("只能选择一条记录!");
			return;
		}

		window.open(ctx+"/demo/taskUuid/newfill?flowDefUuid=990c9296-bb16-48af-a728-cb9c975d27e8&taskUuid="+taskUuids[0]+"&scriptUrl=/resources/schooldemo/js/school_workflowfill.js&rtscriptUrl=/resources/schooldemo/js/doban_workflow.js");
		
	}
	        
});
