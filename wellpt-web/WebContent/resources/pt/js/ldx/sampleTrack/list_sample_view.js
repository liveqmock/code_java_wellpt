var File = {};
$(function() {
	
	File.showParam = function(parentUuid){
		$.ajax({
			async : false,
			url : ctx + "/prodResult/show",
			success : function(data) {
				var json = new Object();
				json.content = data;
				json.title = "样品课反馈结果维护";
				json.height = 500;
				json.width = 400;
				json.buttons = {
					"保存" : function sure() {
						JDS.call({
							service : "productionStockService.saveStocks",
							data : [$("#dwerks").val(),$("#lgort").val(),$("#lvorm").val()],
							async : false,
							success : function() {
								oAlert2("保存成功");
							}
						});
					}
				};
				json.close = function() {
					$(this).dialog("close");
				};
				showDialog(json);
			}
		});
	};
	File.show = function() {
		window.open(ctx + "/prodResult/show?lineItemId=80052520");
	};
	File.customerShow = function() {
		window.open(ctx + "/customerResult/show?lineItemId=80052520");
	};
	File.qcShow = function() {
		window.open(ctx + "/qcCheckResult/show?lineItemId=80052520");
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
				var valStr = $(this).parent().parent(".dataTr").attr("jsonstr").replace("{","").replace("}","").split(",");
				for(var i=0;i<valStr.length;i++){
					if('uuid'==valStr[i].trim().substring(0,4)){
						var paraArray = valStr[i].split("=");
						var value = paraArray[1];
						s['uuid'] = value;
						break;
					}
				}
				arrayObj.push(s);
			});
	return arrayObj;
}

