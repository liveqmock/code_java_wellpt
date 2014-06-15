$(function() {
	hideDelButton();
	// 最新选择的树结点
	var latestSelectedNode = null;
	// 数据字典表单ID
	var datadict_form_selector = "#datadict_form";
	// 数据字典对ID
	var datadict_tree_selector = "#datadict_tree";
	// 数据字典列表ID
	var child_datadict_list_selector = "#child_datadict_list";
	// 数据字典属性列表
	var datadict_attr_list_selector = "#datadict_attribute_list";

	var bean = {
		"uuid" : null,
		"name" : null,
		"code" : null,
		"type" : null,
		"parent" : null,
		"owners" : [],
		"id" : null,
		"parentName" : null,
		"parentUuid" : null,
		"ownerNames" : null,
		"ownerIds" : null,
		"children" : [],
		"changedChildren" : [],
		"attributes" : [],
		"changedAttributes" : [],
		"deletedAttributes" : []
	};

	// 根据用户ID获取用户信息
	function getDataDictionary(uuid) {
		JDS.call({
			service : "dataDictionaryService.getBean",
			data : [ uuid ],
			success : function(result) {
				bean = result.data;
				showDelButton();
				// toForm(bean, $(datadict_form_selector));
				$(datadict_form_selector).json2form(bean);
				// 设置字典列表数据
				toChildDatadictList(bean);
				// 设置属性列表数据
				toDatadictAttrList(bean);
			}
		});
	}

	// JQuery jqGrid
	$(child_datadict_list_selector).jqGrid({
		datatype : "local",
		colNames : [ "uuid", "名称", "代码", "类型" ],
		colModel : [ {
			name : "uuid",
			index : "uuid",
			width : "20",
			hidden : true
		}, {
			name : "name",
			index : "name",
			width : "20",
			editable : true
		}, {
			name : "code",
			index : "code",
			width : "20",
			editable : true
		}, {
			name : "type",
			index : "type",
			width : "20",
			editable : true
		} ],
		sortable : false,
		multiselect : true,
		cellEdit : true,// 表示表格可编辑
		cellsubmit : "clientArray", // 表示在本地进行修改
		autowidth : true,
		height : "100%",
		scrollOffset : 0,
		afterEditCell : function(rowid, cellname, value, iRow, iCol) {
			// Modify event handler to save on blur.
			$("#" + iRow + "_" + cellname, child_datadict_list_selector).one('blur', function() {
				$(child_datadict_list_selector).saveCell(iRow, iCol);
			});
		}
	});
	$(datadict_attr_list_selector).jqGrid({
		datatype : "local",
		colNames : [ "uuid", "域名", "域值" ],
		colModel : [ {
			name : "uuid",
			index : "uuid",
			width : "20",
			hidden : true
		}, {
			name : "name",
			index : "name",
			width : "20",
			editable : true
		}, {
			name : "value",
			index : "value",
			width : "20",
			editable : true
		} ],
		sortable : false,
		multiselect : true,
		cellEdit : true,// 表示表格可编辑
		cellsubmit : "clientArray", // 表示在本地进行修改
		autowidth : true,
		height : "100%",
		scrollOffset : 0,
		afterEditCell : function(rowid, cellname, value, iRow, iCol) {
			// Modify event handler to save on blur.
			$("#" + iRow + "_" + cellname, datadict_attr_list_selector).one('blur', function() {
				$(datadict_attr_list_selector).saveCell(iRow, iCol);
			});
		}
	});
	// 设置字典列表数据
	function toChildDatadictList(bean) {
		var $childrenList = $(child_datadict_list_selector);
		// 清空字典列表
		$childrenList.jqGrid("clearGridData");
		var children = bean["children"];
		for ( var index = 0; index < children.length; index++) {
			$childrenList.jqGrid("addRowData", children[index].uuid, children[index]);
		}
	}
	// 设置字典属性列表
	function toDatadictAttrList(bean) {
		var $datadictAttrList = $(datadict_attr_list_selector);
		// 清空字典属性列表
		$datadictAttrList.jqGrid("clearGridData");
		var attributes = bean["attributes"];
		for ( var index = 0; index < attributes.length; index++) {
			$datadictAttrList.jqGrid("addRowData", attributes[index].uuid, attributes[index]);
		}
	}
	// 收集改变的子结点及属性
	function collectChildrenDatadicList(bean) {
		var changes = $(child_datadict_list_selector).getChangedCells('all');
		bean["children"] = [];
		bean["changedChildren"] = changes;

		var changedAttributes = $(datadict_attr_list_selector).getChangedCells('all');
		bean["attributes"] = [];
		bean["changedAttributes"] = changedAttributes;
		// 设置排序
		for ( var i = 0; i < changedAttributes.length; i++) {
			changedAttributes[i].attrOrder = i;
		}
	}
	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$(".tabs").tabs();

	// JQuery zTree设置
	var dataDataDictionarySetting = {
		async : {
			enable : true,
			contentType : "application/json",
			url : ctx + "/json/data/services",
			otherParam : {
				"serviceName" : "dataDictionaryService",
				"methodName" : "getAsTreeAsync"
			},
			type : "POST"
		},
		callback : {
			beforeClick : beforeClick
		}
	};
	// 树结点点击处理
	function beforeClick(treeId, treeNode) {
		// 最新选择的树结点
		latestSelectedNode = treeNode;
		if (treeNode.id != null && treeNode.id != -1) {
			// 查看详细
			getDataDictionary(treeNode.id);
		}
		return true;
	}
	// JQyery zTree加载数据字典树结点
	function loadDataDictionaryTree() {
		$.fn.zTree.init($(datadict_tree_selector), dataDataDictionarySetting);
	}
	loadDataDictionaryTree();

	// 隐藏删除按钮
	function hideDelButton() {
		$("#btn_del").hide();
	}
	// 显示删除按钮
	function showDelButton() {
		$("#btn_del").show();
	}

	// 新增操作
	$("#btn_add").click(function() {
		clear();
		hideDelButton();
	});
	function clear() {
		$(datadict_form_selector).clearForm(true);
		bean.uuid = null;

		// 清空字典列表
		var $childrenList = $(child_datadict_list_selector);
		$childrenList.jqGrid("clearGridData");
		// 清空属性列表
		var $datadictAttrList = $(datadict_attr_list_selector);
		$datadictAttrList.jqGrid("clearGridData");

		if (latestSelectedNode) {
			$("#parentName", $(datadict_form_selector)).val(latestSelectedNode.name);
			$("#parentUuid", $(datadict_form_selector)).val(latestSelectedNode.id);
		}
	}

	// 保存群组信息
	$("#btn_save").click(function() {
		// 收集表单数据
		// toObject($(datadict_form_selector), bean);
		$(datadict_form_selector).form2json(bean);
		// 收集改变的子结点
		collectChildrenDatadicList(bean);
		JDS.call({
			service : "dataDictionaryService.saveBean",
			data : [ bean ],
			success : function(result) {
				alert("保存成功!");
				// 保存成功刷新树
				loadDataDictionaryTree();
				showDelButton();
			}
		});
	});

	// 删除操作
	$("#btn_del").click(function() {
		if (latestSelectedNode == null || latestSelectedNode.id == -1) {
			alert("请选择记录！");
			return;
		}
		var name = latestSelectedNode.name;
		var id = latestSelectedNode.id;
		if (confirm("确定要删除数据字典[" + name + "]吗？")) {
			JDS.call({
				service : "dataDictionaryService.remove",
				data : [ id ],
				success : function(result) {
					alert("删除成功!");
					// 删除成功刷新树
					loadDataDictionaryTree();
					// 清空表单等
					clear();
				}
			});
		}
	});
	// 删除操作
	$("#btn_delAll").click(function() {
		if (latestSelectedNode == null || latestSelectedNode.id == -1) {
			alert("请选择记录！");
			return;
		}
		var name = latestSelectedNode.name;
		var id = latestSelectedNode.id;
		if (confirm("确定要删除数据字典[" + name + "]吗？")) {
			JDS.call({
				service : "dataDictionaryService.remove",
				data : [ id ],
				success : function(result) {
					alert("删除成功!");
					// 删除成功刷新树
					loadDataDictionaryTree();
					// 清空表单等
					clear();
				}
			});
		}
	});

	// 增加字典
	$("#btn_datadict_add").click(function(e) {
		// 判断是否已选择字典
		var mydata = {
			uuid : null
		};
		if (latestSelectedNode) {
			var newDate = new Date().getTime();
			$(child_datadict_list_selector).jqGrid("addRowData", newDate, mydata);
		} else {
			alert("请选择字典");
		}
	});
	// 删除字典
	$("#btn_datadict_del").click(function(e) {
		var rowids = $(child_datadict_list_selector).jqGrid('getGridParam', 'selarrrow');
		if (rowids.length == 0) {
			alert("请选择属性!");
			return;
		}
		for ( var i = (rowids.length - 1); i >= 0; i--) {
			var rowData = $(child_datadict_list_selector).getRowData(rowids[i]);
			if (rowData.uuid != null && $.trim(rowData.uuid) != "") {
				bean["deletedChildren"].push(rowData);
			}
			$(child_datadict_list_selector).jqGrid('delRowData', rowids[i]);
		}
	});

	// 增加属性
	$("#btn_attr_add").click(function(e) {
		// 判断是否已选择字典
		var mydata = {
			uuid : null
		};
		if (latestSelectedNode) {
			var newDate = new Date().getTime();
			$(datadict_attr_list_selector).jqGrid("addRowData", newDate, mydata);
		} else {
			alert("请选择字典");
		}
	});
	// 删除属性
	$("#btn_attr_del").click(function(e) {
		var rowids = $(datadict_attr_list_selector).jqGrid('getGridParam', 'selarrrow');
		if (rowids.length == 0) {
			alert("请选择属性!");
			return;
		}
		for ( var i = (rowids.length - 1); i >= 0; i--) {
			var rowData = $(datadict_attr_list_selector).getRowData(rowids[i]);
			if (rowData.uuid != null && $.trim(rowData.uuid) != "") {
				bean["deletedAttributes"].push(rowData);
			}
			$(datadict_attr_list_selector).jqGrid('delRowData', rowids[i]);
		}
	});

	// 选择字典所有者
	$("#ownerNames").click(function() {
		$.unit.open({
			title : "选择人员",
			labelField : "ownerNames",
			valueField : "ownerIds"
		});
	});

	// JQuery layout布局变化时，更新jqGrid宽度
	function resizeJqGrid(position, pane, paneState) {
		if (grid = $('.ui-jqgrid-btable')) {
			grid.each(function(index) {
				var tabmargin = 60;
				if (Browser.isIE()) {
					$(this).setGridWidth(pane.width() - tabmargin);
				} else if (Browser.isChrome()) {
					$(this).setGridWidth(pane.width() - tabmargin);
				} else if (Browser.isMozila()) {
					$(this).setGridWidth(pane.width() - tabmargin);
				} else {
					$(this).setGridWidth(pane.width() - tabmargin);
				}
			});
		}
	}
	// 页面布局
	Layout.layout({
		west__size : 250,
		onresize : resizeJqGrid
	});
});