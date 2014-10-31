I18nLoader.load("/resources/pt/js/fileManager");
var File = {};
$(function() {

	File.editObject = function(parentUuid) {
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
				window.open(ctx + "/sales/preUpdateUser?objectUuid=" + uuids[0]
						+ "&isEdit=true");
			}
		}
	};

	File.addObject = function() {
		window.open(ctx + "/sales/preUpdateUser?objectUuid=&isEdit=false");
	};

	File.deleteObjects = function() {
		var element = $(this);
		var arrayObj = readParamUuid();
		var uuids = new Array();
		for ( var i = 0; i <= arrayObj.length - 1; i++) {
			var uuid = arrayObj[i]["id"];
			uuids.push(uuid);
		}
		if (uuids.length == 0) {
			oAlert(fileManager.pleaseChooseRecord);
		} else {
			oConfirm(fileManager.deleteConfirm, function() {
				JDS.call({
					service : "dmsUserService.deleteObjects",
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
