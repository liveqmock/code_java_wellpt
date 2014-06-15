//加载全局国际化资源
I18nLoader.load("/resources/pt/js/global");
$(function() {
	// 流程列表树ID
	var flow_def_list_selector = "#list";
	// 流程定义分类树ID
	var flow_def_tree_selector = "#flow_def_tree";

	var bean = {
		"uuid" : null,
		"name" : null,
		"id" : null,
		"version" : null,
		"category" : null
	};

	$(flow_def_list_selector).jqGrid({
		treeGrid : true,
		treeGridModel : 'adjacency',
		ExpandColumn : 'name',
		url : ctx + '/workflow/define/flow/list/tree.action',
		datatype : 'json',
		mtype : "POST",
		colNames : [ "uuid", "名称", "ID", "版本", "状态", "使用表单", "分类" ],
		colModel : [ {
			name : "uuid",
			index : "uuid",
			width : "100",
			hidden : true,
			key : true
		}, {
			name : "name",
			index : "name",
			width : "150"
		}, {
			name : "id",
			index : "id",
			width : "80"
		}, {
			name : "version",
			index : "version",
			width : "100",
			hidden : true,
		}, {
			name : "enabled",
			index : "enabled",
			width : "50",
			formatter : function(cellvalue, options, rowObject) {
				if (cellvalue == true) {
					return "启用";
				} else if (cellvalue == false) {
					return "禁用";
				}
				return cellvalue;
			}
		}, {
			name : "formName",
			index : "formName",
			width : "100"
		}, {
			name : "category",
			index : "category",
			width : "300"
		} ],
		rowNum : 500,
		viewrecords : true,
		pager : "#pager",
		height : 'auto',
		scrollOffset : 0,
		jsonReader : {
			root : "dataList",
			total : "totalPages",
			page : "currentPage",
			records : "totalRows"
		},// 行选择事件
		onSelectRow : function(id) {
			var rowData = $(this).getRowData(id);
			bean.uuid = rowData.uuid;
			bean.name = rowData.name;
			bean.id = rowData.id;
			bean.version = rowData.version;
			bean.category = rowData.category;
		},
		ondblClickRow : function(id) {
			var rowData = $(this).getRowData(id);
			if (rowData.uuid != null && rowData.uuid != "") {
				window.open(ctx + '/workflow/index?open&id=' + rowData.uuid);
			}
		}
	});

	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$("#tabs").tabs();

	// JQuery zTree设置
	var flowCategorySetting = {
		async : {
			enable : true,
			contentType : "application/json",
			url : ctx + "/json/data/services",
			otherParam : {
				"serviceName" : "flowCategoryService",
				"methodName" : "getAsTreeAsync"
			},
			type : "POST"
		},
		callback : {
			beforeClick : beforeClick,
			onAsyncSuccess : function(event, treeId, treeNode, msg) {
				var zTree = $.fn.zTree.getZTreeObj(treeId);
				var nodes = zTree.getNodes();
				// 默认展开第一个节点
				if (nodes.length > 0) {
					var node = nodes[0];
					zTree.expandNode(node, true, false, false, true);
				}
			}
		}
	};
	// 树结点点击处理
	function beforeClick(treeId, treeNode) {
		// 最新选择的树结点
//		if (StringUtils.isNotBlank(treeNode.id) && treeNode.id != -1
//				&& StringUtils.isNotBlank(treeNode.data)) {
			var postData = {
				"queryPrefix" : "query",
				"query_LIKES_categoryCode" : treeNode.data
			};
			$(flow_def_list_selector).jqGrid("setGridParam", {
				postData : null
			});
			$(flow_def_list_selector).jqGrid("setGridParam", {
				postData : postData,
				page : 1
			}).trigger("reloadGrid");
//		}
		return true;
	}
	// JQyery zTree加载流程定义树
	function loadFlowDefinitionTree() {
		$.fn.zTree.init($(flow_def_tree_selector), flowCategorySetting);
	}
	loadFlowDefinitionTree();

	// 新增操作
	$("#btn_add").click(function() {
		window.open(ctx + '/workflow/index?open&id=<NEW>');
	});

	// 编辑流程定义
	$("#btn_edit").click(function() {
		if (bean.uuid == null || bean.uuid == "") {
			alert("请选择记录！");
			return true;
		}
		window.open(ctx + '/workflow/index?open&id=' + bean.uuid);
	});

	// 删除操作
	$("#btn_del").click(function() {
		var rowid = $(flow_def_list_selector).jqGrid('getGridParam', 'selrow');
		if (rowid == null || $.trim(rowid) == "") {
			alert("请选择流程定义!");
			return;
		}
		var rowData = $(flow_def_list_selector).getRowData(rowid);
		var name = rowData.name;
		if (confirm("确定要删除流程定义[" + name + "]吗?")) {
			JDS.call({
				service : "flowDefineService.remove",
				data : [ rowid ],
				success : function(result) {
					// 删除成功刷新列表
					alert("删除成功!");
					$(flow_def_list_selector).trigger("reloadGrid");
				}
			});
		}
	});

	// 列表查询
	$("#query_flow").keypress(function(e) {
		if (e.keyCode == 13) {
			$("#btn_query").trigger("click");
		}
	});
	$("#btn_query").click(function(e) {
		var queryValue = $("#query_flow").val();
		var listPostData = $(flow_def_list_selector).jqGrid("getPostData");
		var categoryCode = listPostData["query_LIKES_categoryCode"];
		var postData = {
			"queryPrefix" : "query",
			"queryOr" : true,
			"query_LIKES_name_OR_id_OR_formName_OR_category" : queryValue,
			"query_LIKES_categoryCode" : categoryCode
		};
		if (queryValue == "启用") {
			postData["query_EQB_enabled"] = true;
		} else if (queryValue == "禁用") {
			postData["query_EQB_enabled"] = false;
		}
		$(flow_def_list_selector).jqGrid("setGridParam", {
			postData : null
		});
		$(flow_def_list_selector).jqGrid("setGridParam", {
			postData : postData,
			page : 1
		}).trigger("reloadGrid");
	});

	// JQuery layout布局变化时，更新jqGrid宽度
	function resizeJqGrid(position, pane, paneState) {
		if (grid = $('.ui-jqgrid-btable')) {
			grid.each(function(index) {
				var paneWidth = pane.width() - 22;
				var paneHeight = pane.height() - 104;
				$(this).setGridWidth(paneWidth);
				$(this).setGridHeight(paneHeight);
			});
		}
	}
	// 页面布局
	Layout.layout({
		west__size : 250,
		center_list_id : "list",
		center_onresize : resizeJqGrid
	});
});