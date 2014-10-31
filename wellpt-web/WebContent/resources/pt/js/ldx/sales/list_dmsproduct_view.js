I18nLoader.load("/resources/pt/js/fileManager");
var File = {};
$(function() {

	File.publishObject = function() {
		var arrayObj = readParamUuid();
		if (arrayObj.length == 0) {
			oAlert(fileManager.pleaseChooseRecord);
		} else {
			if (arrayObj.length > 1) {
				oAlert(global.selectARecord);
			} else {
				if ('未发布' != arrayObj[0]["status"]) {
					oAlert("只能发布未发布商品！");
				} else {
					window.open(ctx + "/sales/preUpdateProduct?matnr="
							+ arrayObj[0]["matnr"]);
				}
			}
		}

	};

	File.updateObject = function() {
		var arrayObj = readParamUuid();
		if (arrayObj.length == 0) {
			oAlert(fileManager.pleaseChooseRecord);
		} else {
			if (arrayObj.length > 1) {
				oAlert(global.selectARecord);
			} else {
				if ('已发布' != arrayObj[0]["status"]) {
					oAlert("只能修改已发布商品！");
				} else {
					window.open(ctx + "/sales/preUpdateProduct?matnr="
							+ arrayObj[0]["matnr"]);
				}
			}
		}
	};

	File.cancelObject = function(parentUuid) {
		var element = $(this);
		var arrayObj = readParamUuid();
		var ids = new Array();
		for ( var i = 0; i <= arrayObj.length - 1; i++) {
			var matnr = arrayObj[i]["matnr"];
			ids.push(matnr);
		}
		if (ids.length == 0) {
			oAlert(fileManager.pleaseChooseRecord);
		} else {
			oConfirm("确认要取消发布吗？", function() {
				JDS.call({
					service : "dmsProductService.cancelProduct",
					data : [ ids.join(";") ],
					success : function(result) {
						oAlert("取消发布成功！", function() {
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

function readParamUuid() {
	var arrayObj = new Array();
	$(".checkeds:checked").each(function(i) {
		var s = new Object();
		var jsonstr = $(this).parent().parent(".dataTr").attr("jsonstr");
		var jsonObj = eval("(" + urldecode(jsonstr) + ")");
		for ( var p in jsonObj) {
			s[p.trim().toLowerCase()] = jsonObj[p];
		}
		arrayObj.push(s);
	});
	return arrayObj;
}