$(function() {
	
	//分发
	var TASK_ID_FF = "T_SWFF";
	
	// 环节表单字段处理
	var taskId = WorkFlow.getTaskId();
	WorkFlow.beforeSubmit = function(bean) {
		if(taskId == TASK_ID_FF) {
			//处理子流程创建实例字段
			if($("#shili_ziduan").val() != ""){
				var cbry_ycz = disposeFields($("#shili_ziduan").val(), $("#shili_ziduan").attr("hiddenvalue"));
			    $("#cbry_ycz").val(cbry_ycz);
			}
		}
		return true;
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
});

