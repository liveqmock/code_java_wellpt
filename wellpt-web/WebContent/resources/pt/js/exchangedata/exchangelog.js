$(function() {
	var bean = {
		"uuid": null,	
		"batchId": null,
		"dataId": null,	
		"recVer": null,
		"code": null,
		"node": null,
		"toUnitId": null,
		"fromUnitId" : null,
		"status": null,
		"msg": null,
	};
	
	$("#list").jqGrid(
		$.extend($.common.jqGrid.settings, {
			url :  ctx +"/common/jqgrid/query?queryType=exchangeDataLog",
			datatype : 'json',
			mtype : "POST",
			colNames : [ "uuid", "环节", "批次号", "统一查询号", "版本号", "状态" , "单位"],
			colModel : [ {
				name : "uuid",
				index : "uuid",
				width : "180",
				hidden : true,
				key : true
			}, {
				name : "node",
				index : "node",
				width : "40"
			}, {
				name : "batchId",
				index : "batchId",
				width : "40"
			},{
				name : "dataId",
				index : "dataId",
				width : "30"
			},{
				name : "recVer",
				index : "recVer",
				width : "30"
			},{
				name : "status",
				index : "status",
				width : "30",
				formatter : function(cellvalue, options, rowObject) {
					if (cellvalue == true) {
						return '成功';
					} else  {
						return '失败';
					}
					return cellvalue;
				}
			},{
				name : "toUnitId",
				index : "toUnitId",
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
		var exchangeDataTransform = {};
		exchangeDataTransform.uuid = uuid;
		JDS.call({
			service : "exchangeDataConfigService.getExchangeDataLog",
			data : [ uuid ],
			success : function(result) {
				bean = result.data;
				if(bean.status == '1'){
					bean.status = '成功';
				} else {
					bean.status = '失败';
				}
				
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
		var name = bean.name;
		if (confirm("确定要删除吗？")) {
			JDS.call({
				service : "exchangeDataConfigService.removeLog",
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
				service:"exchangeDataConfigService.removeLogs",
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
