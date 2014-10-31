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
	
	//标记重点工程项目
	XZSP_PROJECT.markKeyProject = function() {
		var element = $(this);
		var arrayObj = readParam();
		var uuids = new Array();
		for (var i=0;i<=arrayObj.length-1;i++) { 
			var uuid = arrayObj[i]["uuid"];
			uuids.push(uuid+ ",file");
		}
		if (uuids.length == 0) {
			oAlert(fileManager.pleaseChooseRecord);
		} else {
			alert(11);
			oConfirm(fileManager.deleteConfirm, function() {
				JDS.call({
					service : "projectService.markKeyProjects",
					data : [ uuids.join(";") ],
					success : function(result) {
						oAlert(fileManager.deleteSuccess, function() {
							refreshWindow(element);
						});
					}
				});
			});
		}
	};
});

function reloadFileParentWindow(){
	window.location.reload();
}

//获取url参数
function readParam(){
	var arrayObj = new Array();
	$(".checkeds:checked").each(function(i){
		var s = new Object(); 
		var index = $(this).parent().parent(".dataTr").attr("src").indexOf("?");
		var search = $(this).parent().parent(".dataTr").attr("src").substr(index);
		var searchArray = search.replace("?", "").split("&");
		for(var i=0;i<searchArray.length;i++){
			var paraArray = searchArray[i].split("=");
			var key = paraArray[0];
			var value = paraArray[1];
			s[key] = value;
		}
		arrayObj.push(s);
	});
	return arrayObj;
}