$(function() {
	// 最新选择的树结点
	var latestSelectedNode = null;
	var list_selector = "#unit_member_list";
	var form_selector = "#unit_form";
	var bean = {
		"uuid" : null,
		"id" : null,
		"type" : null,
		"category" : null,
		"name" : null,
		"remark" : null,
		"parent" : null,
		"children" : [],
		"members" : [],
		"parentUuid" : null,
		"memberBeans" : [],
		"changedMemberBeans" : [],
		"userCache" : {}
	};

	// 根据用户ID获取用户信息
	function getUnit(uuid) {
		JDS.call({
			service : "unitService.getBean",
			data : [ uuid ],
			success : function(result) {
				bean = result.data;
				$("#btn_del").show();
				// 设置表单数据
				$(form_selector).json2form(bean);
				// 设置按钮、方法列表
				toUnitMemberList(bean);
			}
		});
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
			expandSpeed : ($.browser.msie && parseInt($.browser.version) <= 6) ? ""
					: "fast"
		},
		callback : {
			beforeClick : function(treeId, treeNode) {
				// 最新选择的树结点
				latestSelectedNode = treeNode;
				var zTree = $.fn.zTree.getZTreeObj("unit_tree");
				if (treeNode.isParent) {
					zTree.expandNode(treeNode);
					// 清空form表单数据
					clear();
				}
				if (treeNode.id != null && treeNode.id != -1) {
					// 查看详细
					getUnit(treeNode.id);
				}
				// 单元
				if (treeNode.data != null && treeNode.data == "2") {
					$("#type").val("2");
					$("#row_name").show();
					$("#row_category").hide();
					$("#row_code").show();
					$("#row_remark").show();
				} else {
					$("#type").val("1");
					$("#row_name").hide();
					$("#row_category").show();
					$("#row_code").show();
					$("#row_remark").hide();
				}
				return true;
			}
		}
	};
	// JQyery zTree加载组织单元树结点
	function loadUnitTree() {
		JDS.call({
			service : "unitService.getAsTree",
			data : [ -1 ],
			success : function(result) {
				var zTree = $.fn.zTree.init($("#unit_tree"), setting,
						result.data);
				var nodes = zTree.getNodes();
				// 默认展开第一个节点
				if (nodes.length > 0) {
					var node = nodes[0];
					zTree.expandNode(node, true, false, false, true);
				}
			}
		});
	}
	loadUnitTree();

	// 新增单元操作
	$("#btn_unit_add").click(
			function() {
				clear();
				$("#btn_del").hide();
				if (latestSelectedNode != null && latestSelectedNode.id != null
						&& latestSelectedNode.id != -1) {
					$("#parentUuid").val(latestSelectedNode.id);
				}
				$("#type").val("2");
				$("#row_name").show();
				$("#row_category").hide();
				$("#row_remark").show();
			});
	// 新增分类操作
	$("#btn_category_add").click(
			function() {
				clear();
				$("#btn_del").hide();
				if (latestSelectedNode != null && latestSelectedNode.id != null
						&& latestSelectedNode.id != -1) {
					$("#parentUuid").val(latestSelectedNode.id);
				}
				$("#type").val("1");
				$("#row_name").hide();
				$("#row_category").show();
				$("#row_remark").hide();
			});
	// 清空表单及成员列表数据
	function clear() {
		// 清空表单
		$(form_selector).clearForm(true);

		// 清空成员列表
		$(list_selector).jqGrid("clearGridData");
	}

	// 保存群组信息
	$("#btn_save").click(function() {
		// 收集表单数据
		$(form_selector).form2json(bean);

		// 收集改变的组织成员
		collectUnitMembers(bean);

		JDS.call({
			service : "unitService.saveBean",
			data : [ bean ],
			success : function(result) {
				alert("保存成功!");
				// 加载组织单元树结点
				loadUnitTree();
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
		var uuid = latestSelectedNode.id;
		if (confirm("确定要删除组织单元[" + name + "]吗？")) {
			JDS.call({
				service : "unitService.remove",
				data : [ uuid ],
				success : function(result) {
					alert("删除成功!");
					// 删除成功刷新树
					loadUnitTree();
					// 清空表单等
					clear();
				}
			});
		}
	});
	// 批量删除操作
	$("#btn_del_all").click(function() {
		$("#btn_del").trigger("click");
	});

	var businessTypeOptionValues = {};
	JDS.call({
		service : "unitService.getBusinessTypes",
		data : [ "BUSINESS_TYPE" ],
		async : false,
		success : function(result) {
			businessTypeOptionValues = result.data;
		}
	});
	var cache = {};
	function getUserNames(rowId, value) {
		if (value == null || value == "") {
			return "";
		}
		if (cache[rowId] == null) {
			var userCache = bean.userCache;
			var label = "";
			var values = value.split(";");
			for ( var index = 0; index < values.length; index++) {
				if (index == values.length - 1) {
					label += userCache[values[index]];
				} else {
					label += userCache[values[index]] + ";";
				}
			}
			cache[rowId] = {};
			cache[rowId].label = label;
			cache[rowId].value = value;
		}
		return cache[rowId].label == null ? value : cache[rowId].label;
	}
	function getUserIds(rowId, label) {
		if (label == null || label == "") {
			return label;
		}
		// 如果不存在缓存或缓存的label不等于新值的label，则添加缓存
		if (cache[rowId] == null || cache[rowId].label != label) {
			addUserCache(rowId, label, label);
		}
		return cache[rowId].value == null ? label : cache[rowId].value;
	}
	function addUserCache(rowId, name, id) {
		if (name == null || id == null) {
			return;
		}
		var userNames = name.split(";");
		var userIds = id.split(";");
		if (userIds.length === userNames.length) {
			for ( var i = 0; i < userIds.length; i++) {
				bean.userCache[userIds[i]] = userNames[i];
			}
		}
		cache[rowId] = {};
		cache[rowId].label = name;
		cache[rowId].value = id;
	}
	function removeCache(rowId) {
		cache[rowId] = null;
	}
	// JQuery jqGrid
	$(list_selector).jqGrid({
		datatype : "local",
		colNames : [ "uuid", "unitUuid", "单元", "业务类型", "成员类型", "成员" ],
		colModel : [ {
			name : "uuid",
			index : "uuid",
			width : "180",
			hidden : true
		}, {
			name : "unitUuid",
			index : "unitUuid",
			width : "180",
			hidden : true
		}, {
			name : "unitName",
			index : "unitName",
			width : "180",
			hidden : true
		}, {
			name : "businessType",
			index : "businessType",
			width : "180",
			editable : true,
			edittype : "select",
			formatter : "select",
			editoptions : {
				value : businessTypeOptionValues,
				dataInit : function(elem) {
					$(elem).css("width", "100%");
				}
			}
		}, {
			name : "memberType",
			index : "memberType",
			width : "180",
			editable : true,
			edittype : "select",
			formatter : "select",
			editoptions : {
				value : {
					1 : '用户',
					2 : '邮件地址'
				},
				dataInit : function(elem) {
					$(elem).css("width", "100%");
				}
			}
		}, {
			name : "member",
			index : "member",
			width : "180",
			editable : true,
			formatter : function(cellvalue, options, rowObject) {
				if (rowObject.memberType == "1") {
					return getUserNames(options.rowId, cellvalue);
				}
				return cellvalue;
			},
			unformat : function(cellvalue, options, cell) {
				return getUserIds(options.rowId, cellvalue);
			}
		} ],
		sortable : false,
		multiselect : true,
		cellEdit : true,// 表示表格可编辑
		cellsubmit : "clientArray", // 表示在本地进行修改
		autowidth : true,
		scrollOffset : 0,
		afterEditCell : function(rowid, cellname, value, iRow, iCol) {
			var rowData = $(list_selector).getRowData(rowid);
			var cell_selector = "#" + iRow + "_" + cellname;
			if (cellname == "member" && rowData.memberType == "1") {
				var name = getUserNames(rowid, value);
				$(cell_selector, list_selector).val(name);
				$(cell_selector, list_selector).one('focus', function() {
					$.unit.open({
						initNames : name,
						initIDs : value,
						selectType : 4,
						afterSelect : function(retVal) {
							// 放入缓存
							addUserCache(rowid, retVal.name, retVal.id);
							$(cell_selector).val(retVal.name);
							$(list_selector).saveCell(iRow, iCol);
						},
						close : function(e) {
							$(list_selector).saveCell(iRow, iCol);
						}
					});
				});
			} else {
				if (cellname == "memberType") {
					$(cell_selector).change(function() {
						removeCache(rowid);
						var rowData = $(list_selector).getRowData(rowid);
						rowData.member = "";
						$(list_selector).setCell(rowid, "member", rowData);
					});
				}
				// Modify event handler to save on blur.
				$(cell_selector, list_selector).one('blur', function() {
					$(list_selector).saveCell(iRow, iCol);
				});
			}
		}
	});

	// 收集改变的组织成员
	function collectUnitMembers(bean) {
		bean["changedMemberBeans"] = $(list_selector).getChangedCells('all');
	}
	// 设置组织成员列表
	function toUnitMemberList(bean) {
		var $memberList = $(list_selector);
		$memberList.jqGrid("clearGridData");
		var memberBeans = bean["memberBeans"];
		for ( var index = 0; index < memberBeans.length; index++) {
			$memberList.jqGrid("addRowData", memberBeans[index].uuid,
					memberBeans[index]);
		}
	}

	// 新增组织成员
	$("#btn_unit_member").click(function() {
		// 判断是否已选择菜单按钮
		var mydata = {
			uuid : null,
			member : ""
		};
		if (latestSelectedNode && latestSelectedNode.data == "2") {
			var newDate = new Date().getTime();
			mydata.unitUuid = latestSelectedNode.id;
			mydata.unitName = latestSelectedNode.name;
			$(list_selector).jqGrid("addRowData", newDate, mydata);
		} else {
			alert("请选择单元");
		}
	});

	// JQuery layout布局变化时，更新jqGrid宽度
	function resizeJqGrid(position, pane, paneState) {
		if (grid = $('.ui-jqgrid-btable[id=unit_member_list]')) {
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
		center_list_id : "unit_member_list",
		center_onresize : resizeJqGrid
	});
});