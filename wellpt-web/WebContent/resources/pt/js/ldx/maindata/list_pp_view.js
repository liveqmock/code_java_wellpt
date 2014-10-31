I18nLoader.load("/resources/pt/js/fileManager");
var File = {};
$(function() {
	$(".dataTr").unbind("dblclick");
	$(".dataTr").live("dblclick",function(){
			var jsonstr = $(this).attr("jsonstr");
			var jsonObj = eval("(" + urldecode(jsonstr) + ")");
			window.open(ctx + "/maindata/preAddPp?objectUuid=" + jsonObj.uuid);
		});	

	File.editObject = function(parentUuid) {
		var arrayObj = readParamUuid();
		var uuids = new Array();
		for ( var i = 0; i < arrayObj.length; i++) {
			var uuid = arrayObj[i]["uuid"];
			uuids.push(uuid);
		}
		if (uuids.length == 0) {
			oAlert(fileManager.pleaseChooseRecord);
		} else {
			if (uuids.length > 1) {
				oAlert(global.selectARecord);
			} else {
				window.open(ctx + "/maindata/preAddPp?objectUuid=" + uuids[0]);
			}
		}
	};

	File.addObject = function() {
		window.open(ctx + "/maindata/preAddPp?objectUuid=");
	};

	File.deleteObjects = function() {
		var element = $(this);
		var arrayObj = readParamUuid();
		var uuids = new Array();
		for ( var i = 0; i <= arrayObj.length - 1; i++) {
			var uuid = arrayObj[i]["uuid"];
			uuids.push(uuid);
		}
		if (uuids.length == 0) {
			oAlert(fileManager.pleaseChooseRecord);
		} else {
			oConfirm(fileManager.deleteConfirm, function() {
				JDS.call({
					service : "mainDataPpService.deleteObjects",
					data : [ uuids.join(";") ],
					success : function(result) {
						oAlert(fileManager.deleteSuccess, function() {
							reloadFileParentWindow(element);
						});
					}
				});
			});
		}
	};

	File.transferObjects = function() {
		var arrayObj = readParamUuid();
		var uuids = new Array();
		for ( var i = 0; i <= arrayObj.length - 1; i++) {
			var uuid = arrayObj[i]["uuid"];
			uuids.push(uuid);
		}
		if (uuids.length == 0) {
			oAlert(fileManager.pleaseChooseRecord);
		} else {
			JDS.call({
				service : "mainDataPpService.transferObjects",
				data : [ uuids.join(";") ],
				success : function(result) {
					var json = result.data;
					var str = "";
					for ( var p in json) {
						str += json[p] + ";\n";
					}
					if ("" != str) {
						alert(str);
					}
					reloadFileParentWindow();
				}
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
				s['uuid'] = jsonObj.uuid;
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
