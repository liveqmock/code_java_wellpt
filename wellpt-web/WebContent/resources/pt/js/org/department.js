$(function() {
	var bean = {
		"uuid" : null,
		"id" : null,
		"name" : null,
		"shortName" : null,
		"code" : null,
		"parent" : null,
		"departmentUsers" : [],
		"departmentPrincipals" : [],
		"remark" : null,
		"commonUnitId" : null,
		"isVisible":null,
		"children" : [],
		"groups" : [],
		"parentName" : null,
		"parentId" : null,
		"principalLeaderNames" : null,
		"principalLeaderIds" : null,
		"branchedLeaderNames" : null,
		"branchedLeaderIds" : null,
		"managerNames" : null,
		"managerIds" : null,
		"privileges" : [],
		"functionName":null,
		"functionId":null,
		"departmentlevel":null,
		"externalId":null,
		"functionNames" : null
	};

	$("#list").jqGrid({
		treeGrid : true,
		treeGridModel : 'adjacency',
		ExpandColumn : 'name',
		url : ctx + '/org/department/list/tree.action',
		datatype : 'json',
		mtype : "POST",
		colNames : [ "id", "uuid", "名称", "编号", "负责人", "分管领导", "管理员" ],
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
			width : "300"
		}, {
			name : "code",
			index : "code",
			width : "100"
		}, {
			name : "principalLeaderNames",
			index : "principalLeaderNames",
			width : "180"
		}, {
			name : "branchedLeaderNames",
			index : "branchedLeaderNames",
			width : "180"
		}, {
			name : "managerNames",
			index : "managerNames",
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
		},
		onSelectRow : function(id) {// 行选择事件
			getDepartmentById(id);
			// var rowData = $(this).getRowData(id);
			// toForm(department, $("#dept_form"));
		},
		loadComplete : function(data) {
			$("#list").setSelection($("#list").getDataIDs()[0]);
		}
	});

	// 根据部门ID获取部门信息
	function getDepartmentById(id) {
		JDS.call({
			service : "departmentService.getBeanById",
			data : id,
			success : function(result) {
				bean = result.data;
				$("#btn_del").show();
				// toForm(bean, $("#dept_form"));
				$("#dept_form").json2form(bean);

				// 加载角色树
				loadRoleTree(bean.uuid);
				
				// 加载部门角色嵌套树
				loadUserRoleNestedRoleTree(bean.uuid);
				
				//加载权限树 20140805+ by zky
				//loadPrivilegeTree(bean.uuid);
				
				// 加载部门权限树+ by zky
				//loadUserPrivilegeTree(bean.uuid);
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
			service : "departmentService.getPrivilegeTree",
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
	function loadUserPrivilegeTree(uuid) {
		JDS.call({
			service : "departmentService.getDepartmentPrivilegeTree",
			data : uuid,
			success : function(result) {
				var zTree = $.fn.zTree
						.init($("#department_privilege_tree"), {},
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
	$(".tabs").tabs();

	var setting = {
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
			service : "departmentService.getRoleTree",
			data : [ uuid ],
			success : function(result) {
				var zTree = $.fn.zTree.init($("#role_tree"), setting,
						result.data);
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
	// 加载部门角色嵌套树
	function loadUserRoleNestedRoleTree(uuid) {
		JDS.call({
			service : "departmentService.getDepartmentRoleNestedRoleTree",
			data : uuid,
			success : function(result) {
				var zTree = $.fn.zTree
						.init($("#department_role_nested_role_tree"), {},
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
	
	//--------------初始化职能树---------------------------------
	var otherParam={"serviceName" : "dataDictionaryService",
			"methodName" : "getAsTreeAsyncForUuid",
			"data":"FUNCTION_TYPE"};
	//初始化职能树
	initDataDictTree(otherParam,"functionNames", "functionUuids", true);
	
	//--------------初始化职能树---------------------------------
	function initDataDictTree(otherParam,nameField,IdField,mutiselect){
		var setting = {
				async : {
					otherParam : otherParam
				},
				view : {
					showLine : true
				},
				check : {//复选框的选择做成可配置项
					enable:mutiselect
				},
				callback : {
					onClick:function (event, treeId, treeNode) {
						var inputId = treeId.replace("_ztree","");
							$("#"+inputId).val(treeNode.name);
							$("#"+IdField).val(treeNode.id);
					}
				}
			};
	
		$("#"+nameField).comboTree({
			labelField : nameField,
			valueField : IdField,
			width: 200,
			height: 150,
			treeSetting : setting
		});
	}
	//--------------初始化职能树结束---------------------------------
	

	// 新增部门信息
	$("#btn_add").click(function() {
		$("#dept_form").clearForm(true);
		$("#btn_del").hide();
	});

	// 保存部门信息
	$("#btn_save").click(function() {
		// toObject($("#dept_form"), bean);
		$("#dept_form").form2json(bean);
		// 收集角色树
		rolesToObject(bean);
		//收集权限树
		//privilegeToObject(bean);
		JDS.call({
			service : "departmentService.saveBean",
			data : bean,
			success : function(result) {
				alert("保存成功!");
				// 保存成功刷新列表
				$("#list").trigger("reloadGrid");
			}
		});
	});

	// 删除部门信息
	$("#btn_del").click(function() {
		if ($(this).attr("id") == "btn_del") {
			var ids = $("#list").jqGrid('getGridParam', 'selrow');
			if (ids.length == 0) {
				alert("请选择记录！");
				return true;
			}
		} else {
			if (bean.id == "" || bean.id == null) {
				alert("请选择记录！");
				return true;
			}
		}
		var name = $("#list").getRowData(bean.id)["name"];
		if (confirm("确定要删除部门[" + name + "]吗？")) {
			JDS.call({
				service : "departmentService.removeById",
				data : bean.id,
				success : function(result) {
					alert("删除成功!");
					// 保存成功刷新列表
					$("#list").trigger("reloadGrid");
				}
			});
		}
	});

	// 选择上级部门
	$("#parentName").click(function(e) {
		$.unit.open({
			title : "选择部门",
			labelField : "parentName",
			valueField : "parentId",
			type : "Dept",
			multiple:false,
			showType : false
		});
		return false;
	});
	// 选择部门负责人
	$("#principalLeaderNames").click(function(e) {
		$.unit.open({
			title : "选择人员",
			labelField : "principalLeaderNames",
			valueField : "principalLeaderIds",
			selectType : 36
		});
		return false;
	});
	// 选择分管领导
	$("#branchedLeaderNames").click(function() {
		$.unit.open({
			title : "选择人员",
			labelField : "branchedLeaderNames",
			valueField : "branchedLeaderIds",
			selectType : 4
		});
		return false;
	});
	// 选择管理员
	$("#managerNames").click(function() {
		$.unit.open({
			title : "选择人员",
			labelField : "managerNames",
			valueField : "managerIds",
			selectType : 4
		});
		return false;
	});	
	//租户单位
	$.ajax({
		type:"post",
		async:false,
		url:ctx+"/superadmin/unit/commonUnit/tenantUnitList",
		success:function(result){
			if (result.data != null && result.data.length > 0) {				
				for (var i = 0;i < result.data.length; i++) {
					var option = $("<option></option>");
					option.attr("value",result.data[i].id);
					option.text(result.data[i].name);
					$("#commonUnitId").append(option);
				}
			}
		}
	});

	// 列表查询
	$("#query_department").keypress(function(e) {
		if (e.keyCode == 13) {
			$("#btn_query").trigger("click");
		}
	});
	$("#btn_query")
			.click(
					function(e) {
						var queryValue = $("#query_department").val();
						var postData = {
							"queryPrefix" : "query",
							"queryOr" : true,
							"query_LIKES_name_OR_code_OR_principalLeaderNames_OR_branchedLeaderNames_OR_managerNames" : queryValue
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
