I18nLoader.load("/resources/pt/js/fileManager");
var File = {};
$(function() {
	//打开添加页面
	File.addLineManager = function() {
		window.open(ctx + "/productionPlan/lineManage/add");
	};
	
	//删除当前库存记录
	File.deleteLineManager = function(parentUuid) {
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
			arr[ind++]=obj.zSG+","+obj.zOAUSER;
		});
		JDS.call({
			service : "productionLineManagerService.deleteLineManager",
			data : [arr.join(";")],
			async : false,
			success : function() {
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