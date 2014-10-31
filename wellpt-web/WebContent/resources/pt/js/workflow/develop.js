$(function() {
	var bean = {
		"uuid" : null,
		"name" : null,
		"code" : null,
		"id" : null
	};
	$("#list").jqGrid($.extend($.common.jqGrid.settings, {
		url : ctx + '/common/jqgrid/query?queryType=flowDefinition',
		colNames : [ "uuid", "名称", "ID", "编号" ],
		colModel : [ {
			name : "uuid",
			index : "uuid",
			width : "180",
			hidden : true,
			key : true
		}, {
			name : "name",
			index : "name",
			width : "180"
		}, {
			name : "id",
			index : "id",
			width : "180"
		}, {
			name : "code",
			index : "code",
			width : "100"
		} ],// 行选择事件
		onSelectRow : function(id) {
			var rowData = $(this).getRowData(id);
			// toForm(rowData, $("#flow_develop_form"));
			getFlowDefinition(rowData.uuid);
		}
	}));

	// 根据UUID获取组织选择项
	function getFlowDefinition(uuid) {
		var option = {};
		option.uuid = uuid;
		JDS.call({
			service : "flowDevelopService.getBean",
			data : option.uuid,
			success : function(result) {
				bean = result.data;
				$("#btn_del").show();
				$("#flow_develop_form").json2form(bean);
			}
		});
	}

	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$(".tabs").tabs();
	// JQuery layout布局
	// $('#container').layout({
	// center : {
	// closable : false,
	// resizable : false,
	// slidable : false,
	// onresize : resizeJqGrid,
	// minSize : 500,
	// triggerEventsOnLoad : true
	// },
	// east : {
	// fxName : "slide",
	// minSize : 600,
	// maxSize : 600
	// }
	// });

	// 新增操作
	$("#btn_add").click(function() {
		$("#flow_develop_form").clearForm(true);
	});

	// 保存用户信息
	$("#btn_save").click(function() {
		$("#flow_develop_form").form2json(bean);
		JDS.call({
			service : "flowDevelopService.saveBean",
			data : bean,
			success : function(result) {
				alert("保存成功!");
				// 删除成功刷新列表
				$("#list").trigger("reloadGrid");
			}
		});
	});

	// 列表查询
	$("#query_flowDefinition").keypress(function(e) {
		if (e.keyCode == 13) {
			$("#btn_query").trigger("click");
		}
	});
	$("#btn_query").click(function(e) {
		var queryValue = $("#query_flowDefinition").val();
		var postData = {
			"queryPrefix" : "query",
			"queryOr" : true,
			"query_LIKES_name_OR_code_OR_id" : queryValue
		};
		if (queryValue == "是") {
			postData["query_EQB_show"] = true;
		} else if (queryValue == "否") {
			postData["query_EQB_show"] = false;
		}
		$("#list").jqGrid("setGridParam", {
			postData : null
		});
		$("#list").jqGrid("setGridParam", {
			postData : postData,
			page : 1
		}).trigger("reloadGrid");
	});

	// 界面布局
	Layout.layout();
});