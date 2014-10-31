I18nLoader.load("/resources/pt/js/fileManager");
var File = {};
$(function() {

	File.distributeGc = function() {
		var arrayObj = readParamWl();
		if (arrayObj.length == 0) {
			oAlert(fileManager.pleaseChooseRecord);
		} else {
			window.open(ctx + "/maindata/preDistributeGc?wlids="+encodeURIComponent(encodeURIComponent(JSON.stringify(arrayObj))));	
		}
	};

	File.defaultDistributeGc = function() {
		var arrayObj = readParam();
		if (arrayObj.length == 0) {
			oAlert(fileManager.pleaseChooseRecord);
		} else {
			JDS.call({
				service : "mainDataWlService.saveDefaultWlgc",
				data : [ JSON.stringify(arrayObj) ],
				async : false,
				success : function(result) {
					reloadFileParentWindow();
				}
			});
		}
	};

});

function reloadFileParentWindow() {
	window.location.reload();
}

//获取jsonstr参数
function readParamWl() {
	var arrayObj = new Array();
	$(".checkeds:checked").each(
			function(i) {
				var s = new Object();
				var jsonstr = $(this).parent().parent(".dataTr").attr("jsonstr");
				var jsonObj = eval("(" + urldecode(jsonstr) + ")");
				s['wl'] = jsonObj.wl;
				s['shortdesc'] = jsonObj.shortdesc;
				arrayObj.push(s);
			});
	return arrayObj;
}

// 获取jsonstr参数
function readParam() {
	var arrayObj = new Array();
	$(".checkeds:checked").each(
			function(i) {
				var jsonstr = $(this).parent().parent(".dataTr").attr("jsonstr");
				var jsonObj = eval("(" + urldecode(jsonstr) + ")");
				arrayObj.push(jsonObj);
			});
	return arrayObj;
}
