var XZSP_PROJECT = {};
$(function() {
	//获取项目进程基础方法
	XZSP_PROJECT.getProjectProcess = function(projectFormIdFieldValue,projectProcessType) {
		window.open(ctx + "/xzsp/project/projectprocess?projectFormIdFieldValue=" + projectFormIdFieldValue
				+ "&projectProcessType=" + projectProcessType);
	};
	
	//办件库获取项目进程
	XZSP_PROJECT.getProjectProcessByBanjian = function(banjianUuid) {
		var projectFormIdFieldValue = '';
		var projectProcessType = '';
		JDS.call({
			service : "banJianService.getBanjianInfoByUuid",
			data : [banjianUuid],
			success : function(result) {
				  $.each(result.data,function(i,item){  
	                    if(i == 'projectFormIdFieldValue'){
	                    	projectFormIdFieldValue = item;
	                    } else if(i == 'projectProcessType'){
	                    	projectProcessType = item;
	                    }
	               });
				  XZSP_PROJECT.getProjectProcess(projectFormIdFieldValue,projectProcessType);
			}
		});
	};
});