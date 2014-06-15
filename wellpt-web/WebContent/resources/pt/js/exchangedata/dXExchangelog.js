$(function() {
	var bean = {
		"uuid": null,	
		"batchId": null,
		"fromUnitId" : null,
		"toUnitId": null,
		"createTime":null,
		"msg": null
	};
	
	$("#list").jqGrid(
		$.extend($.common.jqGrid.settings, {
			url :  ctx +"/common/jqgrid/query?queryType=exchangeLog",
			datatype : 'json',
			mtype : "POST",
			colNames : [ "uuid", "批次号", "源单位" , "创建时间"],
			colModel : [ {
				name : "uuid",
				index : "uuid",
				width : "180",
				hidden : true,
				key : true
			}, {
				name : "batchId",
				index : "batchId",
				width : "40"
			},{
				name : "fromUnitId",
				index : "fromUnitId",
				width : "30"
			},{
				name : "createTime",
				index : "createTime",
				width : "30"
			}],
			rowNum : 20,
			rownumbers : true,
			rowList : [ 10, 20, 50, 100, 200 ],
			rowId : "uuid",
			pager : "#pager",
			sortname : "createTime",
			recordpos : "right",
			viewrecords : true,
			sortable : true,
			sortorder : "desc",
			multiselect : true,
			autowidth : true,
			height : 450,
			scrollOffset : 0,
			jsonReader : {
				root : "dataList",
				total : "totalPages",
				page : "currentPage",
				records : "totalRows",
				repeatitems : false
			},// 行选择事件
			onSelectRow : function(id) {
				getModuleById(id);
			}
		}));

	// 根据模块ID获取模块信息
	function getModuleById(uuid) {
		JDS.call({
			service : "exchangeDataConfigService.getDXExchangeDataLog",
			data : [ uuid ],
			success : function(result) {
				bean = result.data;
				$("#module_form").json2form(bean);
			}
		});
	}

	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$("#tabs").tabs();

	// 删除
	$("#btn_del").click(function() {
		if (bean.uuid == null || bean.uuid == "") {
			alert("请选择记录！");
			return true;
		}
		if (confirm("确定要删除吗？")) {
			JDS.call({
				service : "exchangeDataConfigService.removeDXLog",
				data : [ bean.uuid ],
				success : function(result) {
					// 删除成功刷新列表
					$("#list").trigger("reloadGrid");
				}
			});
		}
	});
	// 批量删除
	$("#btn_delAll").click(function() {
		var rowids = $("#list").jqGrid('getGridParam', 'selarrrow');
		if (rowids.length == 0) {
			alert("请选择记录!");
			return;
		}
		if (confirm("确定要删除所选资源？")) {
			JDS.call({
				service:"exchangeDataConfigService.removeDXLogs",
				data:[rowids],
				success:function(result) {
					alert("删除成功!");
					//删除成功刷新列表
					$("#list").trigger("reloadGrid");
				}
			});
		}
	});
	//查询
	$("#btn_query").click(function() {
		var queryValue = $("#query_keyWord").val();
		$("#list").jqGrid("setGridParam",{
			postData : {
				"queryPrefix" : "query",
				"queryOr" : true,
				"query_LIKES_batchId_OR_dataId_OR_toUnitId" : queryValue,
			},
			page : 1
		}).trigger("reloadGrid");
	});
	//回车查询
	$("#query_keyWord").keypress(function(e) {
		if (e.keyCode == 13) {
			$("#btn_query").trigger("click");
		}
	});
	Layout.layout({
		west__size : 480
	});
});
