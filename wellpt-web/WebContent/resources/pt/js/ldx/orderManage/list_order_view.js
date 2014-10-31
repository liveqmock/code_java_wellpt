var File = {};
$(function() {
	//打开添加页面
	File.updateStock = function(parentUuid) {
		$.ajax({
			async : false,
			url : ctx + "/orderManage/orderLineItemDetail/update",
			success : function(data) {
				var json = new Object();
				json.content = data;
				json.title = "明细信息维护";
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
		debugger;
		alert($("#vbeln").val());
		window.open(ctx + "/orderManage/orderLineItemDetail/show?vbeln="+$("#vbeln").val());
	};
	File.showProductionStatus = function() {
		window.open(ctx + "/orderManage/productionSearch/findStatus");
	};
	File.shipSearchStatus = function() {
		window.open(ctx + "/orderManage/shipSearch/findStatus");
	};
	File.orderSearchStatus = function() {
		window.open(ctx + "/orderManage/orderSearch/findStatus");
	};
	File.sava = function(){
		JDS.call({
			service : "orderManageService.updateOrderLine",
			data : [$("#vbeln").val(),$("#posnr").val(),$("#zhfkr").val(),$("#zyhrq").val()
			        ,$("#zyhjg").val(),$("#zyhbz").val(),$("#zchsl").val(),$("#zchrq").val()
			        ,$("#zyqbm").val(),$("#zycdl").val(),$("#zyqbz").val(),$("#omchjh").val()],
			async : false,
			success : function(result) {
				if(result.data.res=="success"){
					oAlert2(result.data.msg);
					window.close();
				}else{
					oAlert2(result.data.msg);
				}
			}
		});
	}
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

function readParam(){
	var arrayObj = new Array();
	$(".checkeds:checked").each(function(i){
		var s = new Object(); 
		var index = $(this).parent().parent(".dataTr").attr("src").indexOf("?");
		var search = $(this).parent().parent(".dataTr").attr("src").substr(index);
		var searchArray = search.replace("?", "").split("&");
		for(var i=0;i<searchArray.length;i++){
			var paraArray = searchArray[i].split("=");
			var key = paraArray[0];
			var value = paraArray[1];
			s[key] = value;
		}
		arrayObj.push(s);
	});
	return arrayObj;
}