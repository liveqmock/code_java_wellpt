$(function() {
	var bean = {
		"uuid" : null,
		"name" : null,
		"remark" : null,
		"issys" : null,
		"nestedRoles" : [],
		"privileges" : []
	};

	$("#list").jqGrid($.extend($.common.jqGrid.settings, {
		url : ctx + '/common/jqgrid/query?queryType=role',
		colNames : [ "uuid", "名称", "ID", "编号", "备注" ],
		colModel : [ {
			name : "uuid",
			index : "uuid",
			width : "180",
			hidden : true,
			key : true
		}, {
			name : "name",
			index : "name",
			width : "200"
		}, {
			name : "id",
			index : "id",
			width : "200"
		}, {
			name : "code",
			index : "code",
			width : "60"
		}, {
			name : "remark",
			index : "remark",
			width : "100"
		} ],// 行选择事件
		onSelectRow : function(id) {
			var rowData = $(this).getRowData(id);
			getRole(rowData.uuid);
		}
	}));

	// 根据UUID获取角色
	function getRole(uuid) {
		var role = {};
		role.uuid = uuid;
		JDS.call({
			service : "roleService.getBean",
			data : [ uuid ],
			success : function(result) {
				bean = result.data;
				$("#btn_del").show();
				// 设置表单数据
				$("#role_form").json2form(bean);

				// 设置角色权限树
				// toRolePrivilegeTree(bean);
				loadRolePrivilegeTree(bean.uuid);

				// var active = $("#role_basic").tabs("option", "active");
				// if (active == 1) {
				loadRoleNestedRoleTree(bean.uuid);
				// }

				// 设置人员/部门/群组列表
				loadRoleOwnerList(bean.uuid);
			}
		});
	}
	// 设置人员/部门/群组列表
	function loadRoleOwnerList(uuid) {
		var $roleOwnerList = $("#role_owner_list");
		$roleOwnerList.jqGrid("clearGridData");
		JDS.call({
			service : "roleService.getRoleRoleOwners",
			data : uuid,
			success : function(result) {
				var data = result.data;
				$.each(data, function(i) {
					$roleOwnerList.jqGrid("addRowData", this.uuid, this);
				});
			}
		});
	}
	// 角色的人员/部门/群组列表
	$("#role_owner_list").jqGrid({
		datatype : "local",
		colNames : [ "uuid", "名称", "类型" ],
		colModel : [ {
			name : "uuid",
			index : "uuid",
			width : "100",
			hidden : true
		}, {
			name : "name",
			index : "name",
			width : "100"
		}, {
			name : "type",
			index : "type",
			width : "300",
			formatter : function(cellvalue, options, rowObject) {
				if (cellvalue == "User") {
					return "人员";
				} else if (cellvalue == "Department") {
					return "部门";
				} else if (cellvalue == "Group") {
					return "群组";
				}
				return cellvalue;
			}
		} ],
		sortable : false,
		multiselect : true,
		cellEdit : true,// 表示表格可编辑
		cellsubmit : "clientArray", // 表示在本地进行修改
		autowidth : true,
		height : 300
	});

	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$("#role_basic").tabs({
		activate : function(event, ui) {
			// 角色树激活时隐藏角色/权限tab
			if ((ui.newPanel.attr("id") == "tabs-3")) {
				$("#role_privilege").hide();
				// 角色树tab激活时才加载角色树
				if (bean.uuid != null && bean.uuid != "") {
					loadRoleNestedRoleTree(bean.uuid);
				} else {
					var ztree = $.fn.zTree.getZTreeObj($("role_nested_role_tree"));
					if (ztree != null) {
						ztree.destroy();
					}
				}
			} else {
				$("#role_privilege").show();
			}
		}
	});
	$("#role_privilege").tabs();
	// JQuery zTree
	var setting = {
		check : {
			enable : true
		},
		view : {// 角色结点不显示图标
			showIcon : function showIconForTree(treeId, treeNode) {
				return treeNode.id.substring(0, 1) != "R";
			}
		},
		callback : {
			onCheck : setSelectedRolePrivilege
		}
	};
	// 设置已选中的角色和权限到多选下拉框
	function setSelectedRolePrivilege(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("role_privilege_tree");
		var checkNodes = zTree.getCheckedNodes(true);
		// 清空
		$("#selected_role_privilege").html("");
		$.each(checkNodes, function(index) {
			var id = this.id;
			var name = this.name;
			var option = "<option value='" + id + "'>" + name + "</option>";
			$("#selected_role_privilege").append(option);
		});
	}
	// 加载角色嵌套树
	function loadRoleNestedRoleTree(uuid) {
		JDS.call({
			service : "roleService.getRoleNestedRoleTree",
			data : uuid,
			success : function(result) {
				var zTree = $.fn.zTree.init($("#role_nested_role_tree"), {}, result.data);
				var nodes = zTree.getNodes();
				// 默认展开第一个节点
				if (nodes.length > 0) {
					var node = nodes[0];
					zTree.expandNode(node, true, false, false, true);
				}
			}
		});
	}
	// 加载角色权限树，自动选择已选角色权限
	function loadRolePrivilegeTree(uuid) {
		var role = {};
		role.uuid = uuid;
		JDS.call({
			service : "roleService.getRolePrivilegeTree",
			data : [ uuid ],
			success : function(result) {
				var zTree = $.fn.zTree.init($("#role_privilege_tree"), setting, result.data);
				var nodes = zTree.getNodes();
				// 默认展开第一个节点
				if (nodes.length > 0) {
					var node = nodes[0];
					zTree.expandNode(node, true, false, false, true);
					// 设置已选中的角色和权限到多选下拉框
					setSelectedRolePrivilege();
				}
			}
		});
	}
	// 对象数据设置到角色权限树，角色权限树只加载一次
	// @Deprecated
	function toRolePrivilegeTree(bean) {
		var zTree = $.fn.zTree.getZTreeObj("role_privilege_tree");
		if (zTree == null) {
			loadRolePrivilegeTree(bean.uuid);
		} else {
			// 取消树选择
			zTree.checkAllNodes(false);
			// 选中角色
			var nestedRoles = bean["nestedRoles"];
			for ( var index = 0; index < nestedRoles.length; index++) {
				var node = zTree.getNodeByParam("id", "R" + nestedRoles[index].roleUuid);
				if (node) {
					zTree.checkNode(node, true);
				}
			}
			// 选中权限
			var privileges = bean["privileges"];
			for ( var index = 0; index < privileges.length; index++) {
				var node = zTree.getNodeByParam("id", "P" + privileges[index].uuid);
				if (node) {
					zTree.checkNode(node, true);
				}
			}
			// 设置已选中的角色和权限到多选下拉框
			setSelectedRolePrivilege();
		}
	}
	// 收集角色权限树
	function resourcesToObject() {
		var zTree = $.fn.zTree.getZTreeObj("role_privilege_tree");
		if (zTree != null) {
			var checkNodes = zTree.getCheckedNodes(true);
			bean["nestedRoles"] = [];
			bean["privileges"] = [];
			$.each(checkNodes, function(index) {
				if (this.id.substring(0, 1) == "R") {
					var nestedRole = {};
					// 重写Java的NestedRole对象equals、hashCode方法
					// nestedRole.uuid = this.id.substring(1, this.id.length);
					nestedRole.roleUuid = this.id.substring(1, this.id.length);
					bean["nestedRoles"].push(nestedRole);
				} else if (this.id.substring(0, 1) == "P") {
					var privilege = {};
					privilege.uuid = this.id.substring(1, this.id.length);
					bean["privileges"].push(privilege);
				}
			});
		}
	}
	// 新增操作
	$("#btn_add").click(function() {
		$("#role_form").clearForm(true);
		$("#btn_del").hide();
		bean.uuid = null;
		// 清空权限资源树
		var zTree = $.fn.zTree.getZTreeObj("role_privilege_tree");
		zTree.checkAllNodes(false);
		// 清空
		$("#selected_role_privilege").html("");

		// 销毁角色嵌套树
		var tree = $.fn.zTree.getZTreeObj("role_nested_role_tree");
		if (tree != null) {
			tree.destroy();
		}
	});

	// 保存用户信息
	$("#btn_save").click(function() {
		// 收集表单数据
		$("#role_form").form2json(bean);
		// 收集角色权限树
		resourcesToObject(bean);
		JDS.call({
			service : "roleService.saveBean",
			data : [ bean ],
			success : function(result) {
				alert("保存成功!");
				$("#list").trigger("reloadGrid");
			}
		});
	});

	// 删除操作
	$("#btn_del").click(function() {
		if (!bean || bean.uuid == "" || bean.uuid == null) {
			alert("请选择记录！");
			return true;
		}
		var name = bean.name;
		if (confirm("确定要删除角色[" + name + "]吗？")) {
			JDS.call({
				service : "roleService.remove",
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
		var rowids = $("#list").jqGrid("getGridParam", "selarrrow");
		if (rowids.length == 0) {
			alert("请选择记录!");
			return true;
		}
		if (confirm("确定要删除所选记录吗?")) {
			JDS.call({
				service : "roleService.removeAll",
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
	$("#query_role").keypress(function(e) {
		if (e.keyCode == 13) {
			$("#btn_query").trigger("click");
		}
	});
	$("#btn_query").click(function(e) {
		var queryValue = $("#query_role").val();
		var postData = {
			"queryPrefix" : "query",
			"queryOr" : true,
			"query_LIKES_name_OR_code_OR_remark" : queryValue
		};
		$("#list").jqGrid("setGridParam", {
			postData : null
		});
		$("#list").jqGrid("setGridParam", {
			postData : postData,
			page : 1
		}).trigger("reloadGrid");
	});

	// JQuery layout布局变化时，更新jqGrid宽度
	function resizeJqGrid(position, pane, paneState) {
		if (grid = $('.ui-jqgrid-btable[id=role_owner_list]')) {
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
		center_onresize : resizeJqGrid
	});
});