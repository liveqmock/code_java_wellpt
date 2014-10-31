$(function() {
	var bean = {
		"memberNames" : null,
		"users" : [],
		"remark" : null,
		"children" : [],
		"departments" : [],
		"parent" : null,
		"code" : null,
		"rangeNames" : null,
		"id" : null,
		"memberIds" : null,
		"name" : null,
		"rangeIds" : null,
		"roles" : [],
		"uuid" : null,
		"privileges" : [],
		"category":null
	};

	$("#list").jqGrid($.extend($.common.jqGrid.settings, {
		url : ctx + '/org/group/list/tree',
		colNames : [ "id", "uuid", "名称", "编号","分类", "备注" ],
		colModel : [ {
			name : "id",
			index : "id",
			width : "180",
			hidden : true,
			key : true
		}, {
			name : "uuid",
			index : "uuid",
			width : "180",
			hidden : true
		}, {
			name : "name",
			index : "name",
			width : "180"
		}, {
			name : "code",
			index : "code",
			width : "100"
		}, {
			name : "category",
			index : "category",
			width : "100"
		}, {
			name : "remark",
			index : "remark",
			width : "200"
		} ],// 行选择事件
		onSelectRow : function(id) {
			var rowData = $(this).getRowData(id);
			getGroupById(rowData.id);
		}
	}));

	// 根据用户ID获取用户信息
	function getGroupById(id) {
		var group = {};
		group.id = id;
		JDS.call({
			service : "groupService.getBeanById",
			data : [ group.id ],
			success : function(result) {
				bean = result.data;
				$("#btn_del").show();

				$("#group_form").json2form(bean);

				// 设置角色树
				loadRoleTree(bean.uuid);
				
				//加载权限树 20140805+ by zky
				//loadPrivilegeTree(bean.uuid);
				
				// 加载部门权限树+ by zky
				//loadGroupPrivilegeTree(bean.uuid);

				var active = $(".tabs").tabs("option", "active");
				if (active == 2) {
					loadGroupRoleNestedRoleTree(bean.uuid);
				}
			}
		});
	}
	
	/**-----------初始化权限树开始 ------------------------------------------**/
	var privilegesetting = {
			check : {
				enable : true
			},
			callback : {
				onCheck : setSelectedPrivilege
			}
		};
	
	// 设置已选中的角色和权限到多选下拉框
	function setSelectedPrivilege(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("privilege_tree");
		var checkNodes = zTree.getCheckedNodes(true);
		// 清空
		$("#selected_privilege").html("");
		$.each(checkNodes, function(index) {
			var id = this.id;
			var name = this.name;
			var option = "<option value='" + id + "'>" + name + "</option>";
			$("#selected_privilege").append(option);
		});
	}
	
	// 加载角色权限树，自动选择已选角色权限
	function loadPrivilegeTree(uuid) {
		var role = {};
		role.uuid = uuid;
		JDS.call({
			service : "groupService.getPrivilegeTree",
			data : [ uuid ],
			success : function(result) {
				var zTree = $.fn.zTree.init($("#privilege_tree"), privilegesetting, result.data);
				var nodes = zTree.getNodes();
				// 默认展开第一个节点
				if (nodes.length > 0) {
					var node = nodes[0];
					zTree.expandNode(node, true, false, false, true);
					// 设置已选中的权限到多选下拉框
					setSelectedPrivilege();
				}
			}
		});
	}
	
	// 收集权限树
	function privilegeToObject(bean) {
		var zTree = $.fn.zTree.getZTreeObj("privilege_tree");
		if (zTree != null) {
			var checkNodes = zTree.getCheckedNodes(true);
			bean["privileges"] = [];
			$.each(checkNodes, function(index) {
				var privilege = {};
				privilege.uuid = this.id;
				bean["privileges"].push(privilege);
			});
		}
	}
	
	// 加载部门权限树
	function loadGroupPrivilegeTree(uuid) {
		JDS.call({
			service : "groupService.getGroupPrivilegeTree",
			data : uuid,
			success : function(result) {
				var zTree = $.fn.zTree
						.init($("#group_privilege_tree"), {},
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
	
	/**----------------- 初始化权限树结束 --------------------------------------------**/
	

	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$(".tabs").tabs({
		activate : function(event, ui) {
			if ((ui.newPanel.attr("id") == "tabs-3")) {
				// 群组角色嵌套树tab激活时才加载
				if (bean.uuid != null && bean.uuid != "") {
					loadGroupRoleNestedRoleTree(bean.uuid);
				} else {
					var ztree = $.fn.zTree.getZTreeObj($("group_role_nested_role_tree"));
					if (ztree != null) {
						ztree.destroy();
					}
				}
			}
		}
	});

	// 角色树设置
	var roleSetting = {
		check : {
			enable : true
		},
		callback : {
			onCheck : setSelectedRole
		}
	};
	// 设置已选中的角色到多选下拉框
	function setSelectedRole(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("role_tree");
		var checkNodes = zTree.getCheckedNodes(true);
		// 清空
		$("#selected_role").html("");
		$.each(checkNodes, function(index) {
			var id = this.id;
			var name = this.name;
			var option = "<option value='" + id + "'>" + name + "</option>";
			$("#selected_role").append(option);
		});
	}
	// 加载角色树，自动选择已选角色
	function loadRoleTree(uuid) {
		JDS.call({
			service : "groupService.getRoleTree",
			data : [ uuid ],
			success : function(result) {
				var zTree = $.fn.zTree.init($("#role_tree"), roleSetting, result.data);
				var nodes = zTree.getNodes();
				// 默认展开第一个节点
				if (nodes.length > 0) {
					var node = nodes[0];
					zTree.expandNode(node, true, false, false, true);
					// 设置已选中的角色到多选下拉框
					setSelectedRole();
				}
			}
		});
	}
	// 收集角色树
	function rolesToObject(bean) {
		var zTree = $.fn.zTree.getZTreeObj("role_tree");
		if (zTree != null) {
			var checkNodes = zTree.getCheckedNodes(true);
			bean["roles"] = [];
			$.each(checkNodes, function(index) {
				var role = {};
				role.uuid = this.id;
				bean["roles"].push(role);
			});
		}
	}
	// 加载群组角色嵌套树
	function loadGroupRoleNestedRoleTree(uuid) {
		JDS.call({
			service : "groupService.getGroupRoleNestedRoleTree",
			data : uuid,
			success : function(result) {
				var zTree = $.fn.zTree.init($("#group_role_nested_role_tree"), {}, result.data);
				var nodes = zTree.getNodes();
				// 默认展开第一个节点
				if (nodes.length > 0) {
					var node = nodes[0];
					zTree.expandNode(node, true, false, false, true);
				}
			}
		});
	}

	// 新增操作
	$("#btn_add").click(function() {
		clear();
		$("#btn_del").hide();
	});
	function clear() {
		$("#group_form").clearForm(true);
		bean.uuid = null;
		// 清空角色树
		var zTree = $.fn.zTree.getZTreeObj("role_tree");
		if (zTree) {
			zTree.checkAllNodes(false);
		}
		// 清空
		$("#selected_role").html("");
		// 销毁群组角色嵌套树
		var tree = $.fn.zTree.getZTreeObj("group_role_nested_role_tree");
		if (tree != null) {
			tree.destroy();
		}
		
		/*//清空权限树
		$("#selected_privilege").html("");
		var prtree = $.fn.zTree.getZTreeObj("privilege_tree");
		if (prtree != null) {
			prtree.checkAllNodes(false);
		}
		
		//清空权限树
		var prtree1 = $.fn.zTree.getZTreeObj("group_privilege_tree");
		if (prtree1 != null) {
			prtree1.destroy();
		}*/
		
	}

	// 保存群组信息
	$("#btn_save").click(function() {
		// 收集表单数据
		$("#group_form").form2json(bean);
		// 收集角色树
		rolesToObject(bean);
		//收集权限树
		//privilegeToObject(bean);
		JDS.call({
			service : "groupService.saveBean",
			data : [ bean ],
			success : function(result) {
				alert("保存成功!");
				// 删除成功刷新列表
				$("#list").trigger("reloadGrid");
			}
		});
	});

	// 删除操作
	$("#btn_del").click(function() {
		var id = $("#list").jqGrid('getGridParam', 'selrow');
		if (id == "" || id == null) {
			alert("请选择记录！");
			return true;
		}
		var rowData = $("#list").getRowData(id);
		var groupName = rowData["name"];
		var groupId = rowData["id"];
		if (confirm("确定要删除群组[" + groupName + "]吗？")) {
			JDS.call({
				service : "groupService.removeById",
				data : [ groupId ],
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
				service : "groupService.removeAllById",
				data : [ rowids ],
				success : function(result) {
					alert("删除成功!");
					// 删除成功刷新列表
					$("#list").trigger("reloadGrid");
				}
			});
		}
	});

	// 群组成员
	$("#memberNames").click(function() {
		$.unit.open({
			title : "选择人员",
			labelField : "memberNames",
			valueField : "memberIds",
			afterSelect : afterSelectGroupMember
		});
	});
	// 将返回的群组人员按用户在前，部门在后进行重组
	function afterSelectGroupMember(returnValue) {
		if (returnValue.id == null || $.trim(returnValue.id) == "") {
			$("#memberIds").val("");
			$("#memberNames").val("");
			return;
		}
		var userIds = [];
		var userNames = [];
		var deptIds = [];
		var deptNames = [];
		var groupIds = [];
		var groupNames = [];
		var jobIds = [];
		var jobNames = [];
		var dutyIds = [];
		var dutyNames = [];
		var ids = returnValue.id.split(";");
		var names = returnValue.name.split(";");
		for ( var index = 0; index < ids.length; index++) {
			var type = ids[index].substring(0, 1);// U、D
			if (type == "U") {
				userIds.push(ids[index]);
				userNames.push(names[index]);
			} else if (type == "D") {
				deptIds.push(ids[index]);
				deptNames.push(names[index]);
			} else if (type == "G") {
				groupIds.push(ids[index]);
				groupNames.push(names[index]);
			}else if (type == "J") {
				jobIds.push(ids[index]);
				jobNames.push(names[index]);
			}else if (type == "W") {
				dutyIds.push(ids[index]);
				dutyNames.push(names[index]);
			}
		}
		var memberIds = userIds.join(";");
		var memberNames = userNames.join(";");
		if (deptIds.length != 0) {
			if (userIds != 0) {
				memberIds += ";";
				memberNames += ";";
			}
			memberIds += deptIds.join(";");
			memberNames += deptNames.join(";");
		}
		if (groupIds.length != 0) {
			if (deptIds.length != 0||userIds != 0) {
					memberIds += ";";
					memberNames += ";";
				}
			memberIds += groupIds.join(";");
			memberNames += groupNames.join(";");
		}
		if (jobIds.length != 0) {
			if (deptIds.length != 0||userIds != 0||groupIds != 0) {
				memberIds += ";";
				memberNames += ";";
			}
			memberIds += jobIds.join(";");
			memberNames += jobNames.join(";");
		}
		if (dutyIds.length != 0) {
			if (deptIds.length != 0||userIds != 0||groupIds != 0||jobIds != 0) {
				memberIds += ";";
				memberNames += ";";
			}
			memberIds += dutyIds.join(";");
			memberNames += dutyNames.join(";");
		}
		$("#memberIds").val(memberIds);
		$("#memberNames").val(memberNames);
	}
	// 使用范围
	$("#rangeNames").click(function() {
		$.unit.open({
			title : "选择人员",
			labelField : "rangeNames",
			valueField : "rangeIds"
		});
	});

	// 列表查询
	$("#query_group").keypress(function(e) {
		if (e.keyCode == 13) {
			$("#btn_query").trigger("click");
		}
	});
	$("#btn_query").click(function(e) {
		var queryValue = $("#query_group").val();
		var postData = {
			"queryPrefix" : "query",
			"queryOr" : true,
			"query_LIKES_name_OR_code_OR_remark_OR_category_OR_userName" : queryValue
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

