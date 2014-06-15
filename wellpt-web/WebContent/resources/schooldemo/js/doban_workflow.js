$(function() {
	// 提交前服务处理
	//WorkFlow.setWorkData("beforeSubmitService", "demoService.beforeSubmit");
	// 提交后服务处理
	//WorkFlow.setWorkData("afterSubmitService", "demoService.afterSubmit");
	
	//督办在办发起
	var TASK_ID_FQ = "T975";
	
	//督办催办发起
	var TASK_ID_FQ_CBFQ = "T257";
	
	//督办在办签批
	var TASK_ID_QP = "T725";
	
	//督办催办签批
	var TASK_ID_CBQP = "T328";
	
	//督办反馈
	var TASK_ID_DCFK = "T190";
	
	//催办反馈
	var TASK_ID_CBFK = "T805";
	
	//收文督查反馈单
	var PRINT_TEMPLATE_SWDCFKD = "GWGL_SWDCFKD";
	
	// 环节表单字段处理
	var taskId = WorkFlow.getTaskId();
	WorkFlow.beforeSubmit = function(bean) {
		if(taskId == TASK_ID_QP || taskId == TASK_ID_CBQP) {
			//处理两个子流程创建实例字段
			if($("#main_department").val() != "" && $("#part_department").val() != "") {
				var zrzbsl_zb = disposeFields($("#main_department").val(), $("#main_department").attr("hiddenvalue"));
				var zrzbsl_ph = disposeFields($("#part_department").val(), $("#part_department").attr("hiddenvalue"));
				$("#zrzbsl").val(zrzbsl_zb +";"+ zrzbsl_ph);
			}
		}
		return true;
	}
	//督办反馈单二次开发（反馈单，套打）
	if(taskId == TASK_ID_DCFK || taskId == TASK_ID_CBFK) {
		var flowInstUuid = WorkFlow.getFlowInstUuid();
		JDS.call({
			service : "demoService.getShareData",
			data : [ flowInstUuid ],
			success : function(result) {
				var dobanList = '<div id="share_flow_doban"><div class="share_flow_content"><table class="table view_process_table">'+
				'<thead><tr>'+
				'<th>名称</th>'+
				'<th>办理对象</th>'+
				'<th>办理意见</th>'+
				'<th>办理状态</th>'+
				'</tr></thead>'+
				'<tbody id="share_flow_body_doban"></tbody>'+
				'</table></div></div>';
			
				//添加督办反馈单
				$("#share_flow").after(dobanList);
				
				$("#share_flow_body_doban").html("");
				$(result.data).each(
						function() {
							var rowData = "<tr>" + "<td>" + this.title + "</td>" + "<td>" + this.todoUser
								+ "</td>" + "<td>" + this.opinion + "</td>" + "<td>" + this.currentTask
								+ "</td>" + "</tr>";
							
							$("#share_flow_body_doban").append(rowData);
				});
				// 调整自适应表单宽度
				db_adjustWidthToForm();
				$("#share_flow").hide(); //隐藏原主子流程共享信息
			}
		});
	}
	
	// 调整自适应表单宽度
	function db_adjustWidthToForm() {
		var div_body_width = $(window).width() * 0.76;
		$(".form_header").css("width", div_body_width - 5);
		$(".div_body").css("width", div_body_width);

		$(".form_content").css("width", div_body_width - 44);
		$("#process").css("width", div_body_width - 45);

		// 调整子过程办理过程宽度
		$(".share_flow_content").css("width", div_body_width - 45);

		// 显示签署意见
		WorkFlow.showOpinion();
	}
	
	// 创建实例的字段(实例化流程标题1|办理人1;实例化流程标题2|办理人2，多值以“;”分割)
	function disposeFields(inputvalue, hiddenvalue) {
		var str = '';
		var inputvalue = inputvalue.split(";");
		var hiddenvalue = hiddenvalue.split(";");
		for(var i=0; i<inputvalue.length; i++) {
			str = str + ";"  + inputvalue[i] + "|" + hiddenvalue[i];
		}
		return str.substring(1, str.length);
	}
	
	//打印
	WorkFlow.getPrintService = function() {
		return "demoService.print";
	};

	var printOption = {
			name : "打印",
			functionName : "print"
	};
	// 督办反馈环节打印反馈单
	if (taskId == TASK_ID_DCFK || taskId == TASK_ID_CBFK) {
		WorkFlow.setPrintTemplateId(PRINT_TEMPLATE_SWDCFKD);
		WorkFlow.bind(printOption);
	}

	
	

});

