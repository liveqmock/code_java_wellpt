$(function() {
	// 最新选择的树结点
	var lastSelectNode = null;

	var bean = {
		"uuid" : null,
		"name" : null,
		"code" : null,
		"type" : null,
		"target" : null,
		"url" : null,
		"enabled" : null,
		"dynamic" : null,
		"isDefault" : null,
		"issys" : null,
		"remark" : null,
		"parent" : null,
		"children" : [],
		"privileges" : [],
		"parentName" : null,
		"parentUuid" : null,
		"buttons" : [],
		"changedButtons" : [],
		"deletedButtons" : [],
		"methods" : [],
		"changedMethods" : [],
		"deletedMethods" : []
	};

	// 缓存对象
	var cache = {
		key : "SECURITY_DYBTN",
		label : "name",
		value : "code",
		data : []
	};
	function getLabelByValue(value) {
		if (value == null) {
			return "";
		}
		var values = value.split(";");
		var data = cache.data;
		var label = "";
		for ( var i = 0; i < values.length; i++) {
			for ( var j = 0; j < data.length; j++) {
				var v = values[i];
				if (v == data[j][cache.value]) {
					if (label == "") {
						label = data[j][cache.label];
					} else {
						label = label + ";" + data[j][cache.label];
					}
					break;
				}
			}
		}
		if (label == "") {
			return value;
		}

		return label;
	}
	function getValuesByLabel(label) {
		if (label == null) {
			return "";
		}
		var labels = label.split(";");
		var data = cache.data;
		var value = "";
		for ( var i = 0; i < labels.length; i++) {
			for ( var j = 0; j < data.length; j++) {
				var v = labels[i];
				if (v == data[j][cache.label]) {
					if (value == "") {
						value = data[j][cache.value];
					} else {
						value = value + ";" + data[j][cache.value];
					}
					break;
				}
			}
		}
		if (value == "") {
			return label;
		}
		return value;
	}

	JDS.call({
		"service" : "resourceService.getDataDictionariesByType",
		"data" : "SECURITY_DYBTN",
		success : function(result) {
			cache.data = result.data;
		}
	});
	// 根据用户ID获取用户信息
	function getResource(uuid) {
		var resource = {};
		resource.uuid = uuid;
		JDS.call({
			service : "resourceService.getBean",
			data : [ resource.uuid ],
			success : function(result) {
				bean = result.data;
				$("#btn_del").show();
				// 对象数据设置到表单中
				$("#resource_form").json2form(bean);
				// 设置按钮、方法列表
				toBtnAndMehtodList(bean);
			}
		});
	}

	// JQuery jqGrid
	$("#btn_list").jqGrid({
		datatype : "local",
		colNames : [ "uuid", "名称", "编号", "URL", "JS代码", "动态按钮", "默认显示", "应用于", "备注" ],
		colModel : [ {
			name : "uuid",
			index : "uuid",
			width : "120",
			hidden : true
		}, {
			name : "name",
			index : "name",
			width : "120",
			editable : true
		}, {
			name : "code",
			index : "code",
			width : "120",
			editable : true
		}, {
			name : "url",
			index : "url",
			width : "120",
			editable : true
		}, {
			name : "target",
			index : "target",
			width : "120",
			editable : true,
			edittype : "textarea",
			editoptions : {
				rows : "5",
				cols : "50"
			}
		}, {
			name : "dynamic",
			index : "dynamic",
			width : "100",
			align : "center",
			editable : true,
			edittype : "checkbox",
			editoptions : {
				value : "true:false"
			},
			formatter : "checkbox",
			hidden : true
		}, {
			name : "isDefault",
			index : "isDefault",
			width : "100",
			align : "center",
			editable : true,
			edittype : "checkbox",
			editoptions : {
				value : "true:false"
			},
			formatter : "checkbox"
		}, {
			name : "applyTo",
			index : "applyTo",
			width : "150",
			editable : true,
			formatter : function(cellvalue, options, rowObject) {
				return getLabelByValue(cellvalue);
			},
			unformat : function(cellvalue, options, cell) {
				return getValuesByLabel(cellvalue);
			}
		}, {
			name : "remark",
			index : "remark",
			width : "120",
			editable : true
		} ],
		sortable : false,
		multiselect : true,
		cellEdit : true,// 表示表格可编辑
		cellsubmit : "clientArray", // 表示在本地进行修改
		autowidth : true,
		height : 300,
		afterEditCell : function(rowid, cellname, value, iRow, iCol) {
			var cellId = iRow + "_" + cellname;
			if (cellname == "applyTo") {
				$("#" + cellId).val(getLabelByValue(value));
				$("#" + cellId).focus(function() {
					$("#dlg_cell").popupTreeWindow({
						initValues : value,
						afterCancel : function() {
							$('#btn_list').saveCell(iRow, iCol);
						},
						afterSelect : function(retVal) {
							$("#" + cellId).val(retVal.value);
							$('#btn_list').saveCell(iRow, iCol);
							$("#dlg_cell").html("");
						}
					});
					$("#dlg_cell").popupTreeWindow("open");
				});
				return;
			}
			// Modify event handler to save on blur.
			$("#" + cellId, "#btn_list").one('blur', function() {
				$('#btn_list').saveCell(iRow, iCol);
			});
		}
	});
	$("#method_list").jqGrid({
		datatype : "local",
		colNames : [ "uuid", "名称", "编号", "服务调用", "备注" ],
		colModel : [ {
			name : "uuid",
			index : "uuid",
			width : "180",
			hidden : true
		}, {
			name : "name",
			index : "name",
			width : "180",
			editable : true
		}, {
			name : "code",
			index : "code",
			width : "180",
			editable : true
		}, {
			name : "target",
			index : "target",
			width : "180",
			editable : true
		}, {
			name : "remark",
			index : "remark",
			width : "180",
			editable : true
		} ],
		sortable : false,
		multiselect : true,
		cellEdit : true,// 表示表格可编辑
		cellsubmit : "clientArray", // 表示在本地进行修改
		autowidth : true,
		height : 300,
		afterEditCell : function(rowid, cellname, value, iRow, iCol) {
			// Modify event handler to save on blur.
			$("#" + iRow + "_" + cellname, "#method_list").one('blur', function() {
				$('#method_list').saveCell(iRow, iCol);
			});
		}
	});
	// 收集改变的按钮、方法
	function collectBtnsAndMethods(bean) {
		var changes1 = $("#btn_list").getChangedCells('all');
		bean["changedButtons"] = changes1;
		var changes2 = $("#method_list").getChangedCells('all');
		bean["changedMethods"] = changes2;
	}
	// 设置按钮、方法列表
	function toBtnAndMehtodList(bean) {
		// 1、设置按钮列表
		var $btnList = $("#btn_list");
		$btnList.jqGrid("clearGridData");
		var buttons = bean["buttons"];
		for ( var index = 0; index < buttons.length; index++) {
			$btnList.jqGrid("addRowData", buttons[index].uuid, buttons[index]);
		}
		// 2、设置方法列表
		var $methodList = $("#method_list");
		$methodList.jqGrid("clearGridData");
		var methods = bean["methods"];
		for ( var index = 0; index < methods.length; index++) {
			$methodList.jqGrid("addRowData", methods[index].uuid, methods[index]);
		}
	}

	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$(".tabs").tabs();

	// JQuery zTree设置
	var setting = {
		view : {
			dblClickExpand : false,
			showLine : true,
			selectedMulti : false,
			expandSpeed : ($.browser.msie && parseInt($.browser.version) <= 6) ? "" : "fast"
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pid",
				rootPId : "-1"
			}
		},
		callback : {
			beforeClick : function(treeId, treeNode) {
				// 最新选择的树结点
				lastSelectNode = treeNode;
				var zTree = $.fn.zTree.getZTreeObj("tree");
				if (treeNode.isParent) {
					zTree.expandNode(treeNode);
					// 清空表单、按钮、方法列表
					clear();
				}
				if (treeNode.id != null && treeNode.id != -1) {
					// 查看详细
					getResource(treeNode.id);
				}
				return true;
			}
		}
	};
	// JQyery zTree加载资源树结点
	function loadTree() {
		JDS.call({
			service : "resourceService.getMenuAsTree",
			data : [ -1 ],
			success : function(result) {
				var treeNodes = result.data.children;
				$.fn.zTree.init($("#tree"), setting, treeNodes);
			}
		});
	}
	loadTree();

	// 新增操作
	$("#btn_add").click(function() {
		// 清空表单、按钮、方法列表
		clear();
		$("#btn_del").hide();
		// 设置父节点信息
		if (lastSelectNode) {
			$("#type").val(lastSelectNode.type);
			$("#parentName").val(lastSelectNode.name);
			$("#parentUuid").val(lastSelectNode.id);
		}
	});
	// 清空表单、按钮、方法列表
	function clear() {
		// 清空表单
		$("#resource_form").clearForm(true);
		// 清空按钮、方法列表
		$("#btn_list").jqGrid("clearGridData");
		$("#method_list").jqGrid("clearGridData");
	}

	// 保存群组信息
	$("#btn_save").click(function() {
		// toObject($("#resource_form"), bean);
		// 表单数据收集到对象中
		$("#resource_form").form2json(bean);
		// 收集改变的按钮、方法
		collectBtnsAndMethods(bean);

		JDS.call({
			service : "resourceService.saveBean",
			data : [ bean ],
			success : function(result) {
				alert("保存成功!");
				// 加载资源树结点
				loadTree();
			}
		});
	});

	// 删除操作
	$("#btn_del").click(function() {
		if (lastSelectNode == null || lastSelectNode.id == -1) {
			alert("请选择记录！");
		}
		var resourceName = lastSelectNode.name;
		var resource = {};
		resource.uuid = lastSelectNode.id;
		if (confirm("确定要删除资源[" + resourceName + "]吗？")) {
			JDS.call({
				service : "resourceService.remove",
				data : [ resource.uuid ],
				success : function(result) {
					alert("删除成功!");
					// 删除成功刷新树
					loadTree();
					// 清空表单、按钮、方法列表
					clear();
				}
			});
		}
	});
	// 批量删除操作
	$("#btn_del_all").click(function() {
		$("#btn_del").trigger("click");
	});
	// 新增按钮操作
	$("#btn_add_btn").click(function() {
		// 判断是否已选择菜单按钮
		var mydata = {
			uuid : null,
			dynamic : true
		};
		if (lastSelectNode) {
			var newDate = new Date().getTime();
			$("#btn_list").jqGrid("addRowData", newDate, mydata);
		} else {
			alert("请选择菜单");
		}
	});
	// 删除按钮操作
	$("#btn_del_btn").click(function() {
		var rowids = $("#btn_list").jqGrid('getGridParam', 'selarrrow');
		if (rowids.length == 0) {
			alert("请选择按钮!");
			return;
		}
		for ( var i = (rowids.length - 1); i >= 0; i--) {
			var rowData = $("#btn_list").getRowData(rowids[i]);
			if (rowData.uuid != null && $.trim(rowData.uuid) != "") {
				bean["deletedButtons"].push(rowData);
			}
			$("#btn_list").jqGrid('delRowData', rowids[i]);
		}
	});
	// 新增方法操作
	$("#btn_add_method").click(function() {
		// 判断是否已选择菜单按钮
		var mydata = {
			uuid : null
		};
		if (lastSelectNode) {
			var newDate = new Date().getTime();
			$("#method_list").jqGrid("addRowData", newDate, mydata);
		} else {
			alert("请选择菜单");
		}
	});
	// 删除方法操作
	$("#btn_del_method").click(function() {
		var rowids = $("#method_list").jqGrid('getGridParam', 'selarrrow');
		if (rowids.length == 0) {
			alert("请选择方法!");
			return;
		}
		for ( var i = (rowids.length - 1); i >= 0; i--) {
			var rowData = $("#method_list").getRowData(rowids[i]);
			if (rowData.uuid != null && $.trim(rowData.uuid) != "") {
				bean["deletedButtons"].push(rowData);
			}
			$("#method_list").jqGrid('delRowData', rowids[i]);
		}
	});

	// 群组成员
	$("#memberNames").click(function() {
		$.unit.open({
			title : "选择人员",
			labelField : "memberNames",
			valueField : "memberIds"
		});
	});
	// 使用范围
	$("#rangeNames").click(function() {
		$.unit.open({
			title : "选择人员",
			labelField : "rangeNames",
			valueField : "rangeIds"
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