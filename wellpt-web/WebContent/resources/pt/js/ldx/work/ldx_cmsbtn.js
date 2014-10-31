var LDX_BTNS = {};
$(function() {
	// 置顶
	LDX_BTNS.goTop = function(obj){
		var element = $(this);
		var arrayObj = readParam();
		var uuids = new Array();
		for ( var i = 0; i <= arrayObj.length - 1; i++) {
			var uuid = arrayObj[i]["uuid"];
			uuids.push(uuid);
		}
		if (uuids.length == 0) {
			oAlert("请选择要置顶的公告!");
		} else {
				JDS.call({
					service : "viewDataNewService.changeGGTopStatus",
					data : [ uuids.join(";"),"1"],
					success : function(result) {
							refreshWindow(element);
					}
				});
		}
	};
	
	// 取消置顶
	LDX_BTNS.notGoTop = function(obj){
		var element = $(this);
		var arrayObj = readParam();
		var uuids = new Array();
		for ( var i = 0; i <= arrayObj.length - 1; i++) {
			var uuid = arrayObj[i]["uuid"];
			uuids.push(uuid);
		}
		if (uuids.length == 0) {
			oAlert("请选择要取消置顶的公告!");
		} else {
				JDS.call({
					service : "viewDataNewService.changeGGTopStatus",
					data : [ uuids.join(";"),"0"],
					success : function(result) {
							refreshWindow(element);
					}
				});
		}
	};
});
//获取url参数
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