I18nLoader.load("/resources/pt/js/fileManager");
var File = {};
$(function() {
	//打开添加页面
	File.addLineCapacity = function() {
		window.open(ctx + "/productionPlan/lineCapacity/add");
	};
	
	//删除标准产能
	File.deleteLineCapacity = function() {
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
			arr[ind++]=obj.zXH+","+obj.zRQ;
		});
		JDS.call({
			service : "productionLineCapacityService.deleteLineCapacity",
			data : [arr.join(";")],
			async : false,
			success : function() {
				oAlert(fileManager.deleteSuccess, function() {
					refreshWindow(element);
				});
			}
		});
	};
	
	//复制标准产能
	File.copyCapacity = function() {
		window.open(ctx + "/productionPlan/lineCapacity/copy");	
	};
	
	//产能调整
	File.adjustCapacity = function() {
		window.open(ctx + "/productionPlan/lineCapacity/adjust");	
	};
	
	//批量删除
	File.deleteBatch = function() {
		window.open(ctx + "/productionPlan/lineCapacity/batchDel");	
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