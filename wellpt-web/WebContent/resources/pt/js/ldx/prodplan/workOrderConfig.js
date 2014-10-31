I18nLoader.load("/resources/pt/js/fileManager");
var File = {};
$(function() {
	//打开添加页面
	File.addWoConfig = function() {
		window.open(ctx + "/productionPlan/workOrder/add");
	};
	
	//删除工单数量配置
	File.deleteWoConfig = function() {
		var element = $(this);
		var datas = readParam();
		if(datas.length<1){
			oAlert2(fileManager.pleaseChooseRecord);
			return false;
		}
		if(!confirm(fileManager.deleteConfirm)){
			return false;
		}
		var arr = [];
		var ind = 0;
		datas.forEach(function (obj) {
			arr[ind++]=obj.kEY+","+obj.tYPE;
		});
		JDS.call({
			service : "productionWorkOrderService.deleteWoConfigs",
			data : [arr.join(";")],
			async : false,
			success : function(res) {
				oAlert(fileManager.deleteSuccess, function() {
					refreshWindow(element);
				});
			}
		});
	};
});

function reloadFileParentWindow() {
	window.location.reload();
}

function readParam() {
	var arrayObj = new Array();
	$(".checkeds:checked").each(
			function(i) {
				var s = new Object();
				var jsonstr = $(this).parent().parent(".dataTr").attr("jsonstr");
				var jsonObj = eval("(" + urldecode(jsonstr) + ")");
				arrayObj.push(jsonObj);
			});
	return arrayObj;
}