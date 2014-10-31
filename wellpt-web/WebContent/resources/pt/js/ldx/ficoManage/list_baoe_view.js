I18nLoader.load("/resources/pt/js/fileManager");
var File = {};
$(function() {
	//打开添加页面
	File.addBaoe = function() {
		window.open(ctx + "/ficoDefault/Baoe/add");
	};
	
	//删除保额
	File.deleteBaoe = function() {		
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
			arr[ind++]=obj.kUNNR;
		});
		JDS.call({
			service : "defaultBaoeDataService.deleteBaoe",
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

//获取jsonstr参数
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