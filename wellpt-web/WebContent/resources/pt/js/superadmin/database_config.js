$(function() {
	var bean = {
		"uuid" : null,
		"name" : null,
		"code" : null,
		"loginName" : null,
		"password" : null,
		"host" : null,
		"port" : null,
		"databaseName" : null,
		"type" : null,
		"typeName" : null
	};
	$("#list").jqGrid({
		url : ctx + '/common/jqgrid/query?queryType=databaseConfig',
		mtype : 'POST',
		datatype : "json",
		colNames : [ "uuid", "名称", "编号", "类型", "地址" ],
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
			name : "typeName",
			index : "typeName",
			width : "200"
		}, {
			name : "host",
			index : "host",
			width : "300"
		} ],
		rowNum : 20,
		rownumbers : true,
		rowList : [ 10, 20, 50, 100, 200 ],
		rowId : "uuid",
		pager : "#pager",
		sortname : "code",
		recordpos : "right",
		viewrecords : true,
		sortable : true,
		sortorder : "asc",
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
			var rowData = $(this).getRowData(id);
			getDatabaseConfig(rowData.uuid);
		},
		loadComplete : function(data) {
			$("#list").setSelection($("#list").getDataIDs()[0]);
		}
	});

	// 根据UUID获取组织选择项
	function getDatabaseConfig(uuid) {
		var config = {};
		config.uuid = uuid;
		JDS.call({
			service : "databaseConfigService.get",
			data : config.uuid,
			success : function(result) {
				bean = result.data;
				$("#btn_del").show();
				$("#btn_check_connecton_status").show();
				$("#database_config_form").json2form(bean);
			}
		});
	}

	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$(".tabs").tabs();

	// 新增操作
	$("#btn_add").click(function() {
		$("#database_config_form").clearForm(true);

		$("#btn_del").hide();
		$("#btn_check_connecton_status").hide();
	});

	// 保存数据库配置信息
	$("#btn_save").click(function() {
		$("#database_config_form").form2json(bean);
		JDS.call({
			service : "databaseConfigService.save",
			data : bean,
			success : function(result) {
				alert("保存成功!");
				// 删除成功刷新列表
				$("#list").trigger("reloadGrid");
			}
		});
	});

	// 删除操作
	$("#btn_del").click(function() {
		if (bean.uuid == "" || bean.uuid == null) {
			alert("请选择记录!");
			return true;
		}
		var name = bean.name;
		if (confirm("确定要删除数据库配置[" + name + "]吗?")) {
			JDS.call({
				service : "databaseConfigService.remove",
				data : bean.uuid,
				success : function(result) {
					alert("删除成功!");
					$("#database_config_form").clearForm(true);
					// 删除成功刷新列表
					$("#list").trigger("reloadGrid");
				}
			});
		}
	});
	// 批量删除操作
	$("#btn_del_all").click(function() {
		var rowids = $("#list").jqGrid("getGridParam", "selarrrow");
		if (rowids.length == 0) {
			alert("请选择记录!");
			return true;
		}
		if (confirm("确定要删除所选记录吗?")) {
			JDS.call({
				service : "databaseConfigService.removeAll",
				data : [ rowids ],
				success : function(result) {
					alert("删除成功!");
					$("#database_config_form").clearForm(true);
					// 删除成功刷新列表
					$("#list").trigger("reloadGrid");
				}
			});
		}
	});

	// 连接性测试
	$("#btn_check_connecton_status").click(function(e) {
		var databaseConfigUuid = $("#uuid").val();
		if (databaseConfigUuid == null || $.trim(databaseConfigUuid) == "") {
			alert("请先保存配置!");
			return;
		}
		JDS.call({
			service : "databaseConfigService.checkConnectionStatus",
			data : databaseConfigUuid,
			success : function(result) {
				if (result.data === true) {
					alert("测试成功!");
				} else {
					alert("测试失败!");
				}
			}
		});
	});

	// 列表查询
	$("#query_database_config").keypress(function(e) {
		if (e.keyCode == 13) {
			$("#btn_query").trigger("click");
		}
	});
	$("#btn_query").click(function(e) {
		var queryValue = $("#query_database_config").val();
		var postData = {
			"queryPrefix" : "query",
			"queryOr" : true,
			"query_LIKES_name_OR_code_OR_typeName_OR_host" : queryValue
		};
		$("#list").jqGrid("setGridParam", {
			postData : null
		});
		$("#list").jqGrid("setGridParam", {
			postData : postData,
			page : 1
		}).trigger("reloadGrid");
	});

	// 页面布局
	Layout.layout();
});