I18nLoader.load("/resources/pt/js/fileManager");
var File = {};
$(function() {

	File.editObject = function(parentUuid) {
		var arrayObj = readParamUuid();
		var vbelns = new Array();
		for ( var i = 0; i < arrayObj.length; i++) {
			var vbeln = arrayObj[i]["vbeln"];
			vbelns.push(vbeln);
		}
		if (vbelns.length == 0) {
			oAlert(fileManager.pleaseChooseRecord);
		} else {
			if (vbelns.length > 1) {
				oAlert(global.selectARecord);
			} else {
				window.open(ctx + "/sales/preAddShipSche?vbeln=" + vbelns[0]);
			}
		}
	};

	File.transferObjects = function() {
		var element = $(this);
		var arrayObj = readParamUuid();
		var vbelns = new Array();
		for ( var i = 0; i <= arrayObj.length - 1; i++) {
			var vbeln = arrayObj[i]["vbeln"];
			vbelns.push(vbeln);
		}
		if (vbelns.length == 0) {
			oAlert(fileManager.pleaseChooseRecord);
		} else {
			oConfirm("确定要完成所选数据的付款申请吗？", function() {
				JDS.call({
					service : "shipScheService.updateObjects",
					data : [ vbelns.join(";") ],
					success : function(result) {
						oAlert("完成付款申请！", function() {
							refreshWindow(element);
						});
					}
				});
			});
		}
	};

});

function reloadFileParentWindow() {
	window.location.reload();
}

// 获取jsonstr参数
function readParamUuid() {
	var arrayObj = new Array();
	$(".checkeds:checked").each(
			function(i) {
				var s = new Object();
				var jsonstr = $(this).parent().parent(".dataTr").attr("jsonstr");
				var jsonObj = eval("(" + urldecode(jsonstr) + ")");
				s['vbeln'] = jsonObj.vBELN;
				arrayObj.push(s);
			});
	return arrayObj;
}

function readParam() {
	var arrayObj = new Array();
	$(".checkeds:checked").each(
			function(i) {
				var s = new Object();
				var index = $(this).parent().parent(".dataTr").attr("src")
						.indexOf("?");
				var search = $(this).parent().parent(".dataTr").attr("src")
						.substr(index);
				var searchArray = search.replace("?", "").split("&");
				for ( var i = 0; i < searchArray.length; i++) {
					var paraArray = searchArray[i].split("=");
					var key = paraArray[0];
					var value = paraArray[1];
					s[key] = value;
				}
				arrayObj.push(s);
			});
	return arrayObj;
}
