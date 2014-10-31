I18nLoader.load("/resources/pt/js/fileManager");
var File = {};
$(function() {

	File.editObject = function(parentUuid) {
		var arrayObj = readParamUuid();
		if (arrayObj.length == 0) {
			oAlert(fileManager.pleaseChooseRecord);
		} else {
			if (arrayObj.length > 1) {
				oAlert(global.selectARecord);
			} else {
				if('审核通过'==arrayObj[0]["status"]){
					window
					.open(ctx + "/sales/preEditOrder?objectUuid="
							+ arrayObj[0]["id"]);
				}else{
					oAlert("只能选择审核通过的数据！");
				}
			}
		}
	};

	File.viewObject = function(parentUuid) {
		var arrayObj = readParamUuid();
		var uuids = new Array();
		for ( var i = 0; i < arrayObj.length; i++) {
			var uuid = arrayObj[i]["id"];
			uuids.push(uuid);
		}
		if (uuids.length == 0) {
			oAlert(fileManager.pleaseChooseRecord);
		} else {
			if (uuids.length > 1) {
				oAlert(global.selectARecord);
			} else {
				window
						.open(ctx + "/sales/preViewOrder?objectUuid="
								+ uuids[0]);
			}
		}
	};

	File.auditObject = function(parentUuid) {
		var arrayObj = readParamUuid();
		if (arrayObj.length == 0) {
			oAlert(fileManager.pleaseChooseRecord);
		} else {
			if (arrayObj.length > 1) {
				oAlert(global.selectARecord);
			} else {
				if('未审核'==arrayObj[0]["status"]){
					window
					.open(ctx + "/sales/preAuditOrder?objectUuid="
							+ arrayObj[0]["id"]);
				}else{
					oAlert("只能选择未审核的数据！");
				}
			}
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
				s['id'] = jsonObj.id;
				s['status'] = jsonObj.sTATUS.trim();
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
