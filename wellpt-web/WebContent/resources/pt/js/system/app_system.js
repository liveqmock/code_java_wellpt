$(function() {
	var bean = {
		"uuid" : null,
		"name" : null,
		"code" : null,
		"enabled" : null,
		"remark" : null,
		"appModules" : []
	};

	$("#list").jqGrid($.extend($.common.jqGrid.settings, {
		url : ctx + '/common/jqgrid/query?queryType=appSystem',
		colNames : [ "uuid", "名称", "编号", "状态", "备注" ],
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
			name : "code",
			index : "code",
			width : "100"
		}, {
			name : "enabled",
			index : "enabled",
			width : "100",
			formatter : function(cellvalue, options, rowObject) {
				if (cellvalue == true) {
					return '激活';
				} else if (cellvalue == false) {
					return '禁用';
				}
				return cellvalue;
			}
		}, {
			name : "remark",
			index : "remark",
			width : "300"
		} ],
		// 行选择事件
		onSelectRow : function(id) {
			var rowData = $(this).getRowData(id);
			getAppSystem(rowData.uuid);
		}
	}));

	// 根据应用系统UUID获取应用系统信息
	function getAppSystem(uuid) {
		JDS.call({
			service : "appSystemService.getBean",
			data : uuid,
			success : function(result) {
				bean = result.data;
				$("#btn_del").show();
				$("#app_system_form").json2form(bean);
				$.each(bean.appModules, function() {
					$("#" + this.uuid).attr("checked", "checked");
				});
			}
		});
	}

	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$(".tabs").tabs();
	// 新增操作
	$("#btn_add").click(function() {
		clear();
		$("#btn_del").hide();
	});
	function clear() {
		$("#app_system_form").clearForm(true);
	}

	// 保存系统信息
	$("#btn_save").click(function() {
		// 收集表单数据
		$("#app_system_form").form2json(bean);

		JDS.call({
			service : "appSystemService.saveBean",
			data : [ bean ],
			success : function(result) {
				alert("保存成功!");
				// 保存成功刷新列表
				$("#list").trigger("reloadGrid");
			}
		});
	});

	// 删除操作
	$("#btn_del").click(function() {
		if (bean.uuid == null && $.trim(bean.uuid) == "") {
			alert("请选择记录!");
			return true;
		}
		var name = bean.name;
		if (confirm("确定要删除系统[" + name + "]吗？")) {
			JDS.call({
				service : "appSystemService.remove",
				data : [ bean.uuid ],
				success : function(result) {
					alert("删除成功!");
					// 删除成功刷新列表
					$("#list").trigger("reloadGrid");
				}
			});
		}
	});
	// 批量删除操作
	$("#btn_del_all").click(function() {
		var rowids = $("#list").jqGrid('getGridParam', 'selarrrow');
		if (rowids.length == 0) {
			alert("请选择记录!");
			return true;
		}
		if (confirm("确定要删除所选记录吗?")) {
			JDS.call({
				service : "appSystemService.removeAll",
				data : [ rowids ],
				success : function(result) {
					alert("删除成功!");
					// 删除成功刷新列表
					$("#list").trigger("reloadGrid");
				}
			});
		}
	});

	// 列表查询
	$("#query_app_system").keypress(function(e) {
		if (e.keyCode == 13) {
			$("#btn_query").trigger("click");
		}
	});
	$("#btn_query").click(function(e) {
		var queryValue = $("#query_app_system").val();
		var postData = {
			"queryPrefix" : "query",
			"queryOr" : true,
			"query_LIKES_name_OR_code_OR_remark" : queryValue
		};
		if (queryValue == "激活") {
			postData["query_EQB_enabled"] = true;
		} else if (queryValue == "禁用") {
			postData["query_EQB_enabled"] = false;
		}
		$("#list").jqGrid("setGridParam", {
			postData : null
		});
		$("#list").jqGrid("setGridParam", {
			postData : postData,
			page : 1
		}).trigger("reloadGrid");
	});

	// 页面布局
	Layout.layout({
		west_width : 450
	});
});