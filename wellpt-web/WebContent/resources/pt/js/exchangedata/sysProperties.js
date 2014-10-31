$(function() {
	function getdata(){
		JDS.call({
			service : "exchangeDataConfigService.getAllSysProperties",
			data: [""],
			async:false,
			validate : true,
			success : function(result) {
				var dataMap = result.data;
				for(var key in dataMap){
					if($("#"+key).length>0){
						$("#"+key).val(dataMap[key]["proValue"]);
					}
				}
			}
		});
	}
	getdata();
	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$("#tabs").tabs();
	// 保存
	$("#btn_save").click(function() {
		var itemList = new Array();
		$("input").each(function(){
			var item = new Object();
			var proCnName = $(this).parent().prev().text();
			var proEnName = $(this).attr("id");
			var proValue = $(this).val();
			item["proCnName"] = proCnName;
			item["proEnName"] = proEnName;
			item["proValue"] = proValue;
			item["moduleId"] = "SYSPROPERTIES";
			itemList.push(item);
		});
		$("select").each(function(){
			var item = new Object();
			var proCnName = $(this).parent().prev().text();
			var proEnName = $(this).attr("id");
			var proValue = $(this).val();
			item["proCnName"] = proCnName;
			item["proEnName"] = proEnName;
			item["proValue"] = proValue;
			item["moduleId"] = "SYSPROPERTIES";
			itemList.push(item);
		});
		JDS.call({
			service : "exchangeDataConfigService.saveSysPropertiesList",
			data : [ itemList ],
			async:false,
			validate : true,
			success : function(result) {
				if(result.data){
					
					alert("保存成功！");
				}else{
					alert("保存异常！");
				}
			}
		});
	});
});
