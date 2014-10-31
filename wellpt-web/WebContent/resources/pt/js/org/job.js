var selectedDepartment = {};
var userDetails = SpringSecurityUtils.getUserDetails();
if (userDetails != null) {
	selectedDepartment.id = userDetails["departmentId"];
	selectedDepartment.name = userDetails["departmentName"];
	selectedDepartment.path = userDetails["departmentPath"];
}
$(function() {
	var bean = {
		"uuid" : null,
		"id" : null,
		"code" : null,
		"name" : null,
		"departmentName" : null,
		"departmentId" : null,
		"departmentUuid" : null,
		"remark" : null,
		"dutyUuid":null,
		"dutyName":null,
		"roles" : [],
		"privileges" : [],
		"externalId":null,
		"leaders" : [],
		"functionNames" : null,
		"leaderNames" : null
	};
	var departmentUuid = "";
	$("#list").jqGrid($.extend($.common.jqGrid.settings, {
		url : ctx + '/org/department/job/list?departmentUuid=' + departmentUuid,
		colNames : [ "uuid","名称", "编号",  "职务","职能线", "部门"],
		colModel : [ {
			name : "uuid",
			index : "uuid",
			width : "180",
			hidden : true
		}, {
			name : "name",
			index : "name",
			width : "180"
		},{
			name : "code",
			index : "code",
			width : "180"
		},  {
			name : "dutyName",
			index : "dutyName",
			width : "180"
		},{
			name : "functionNames",
			index : "functionNames",
			width : "180"
		},
		{
			name : "departmentName",
			index : "departmentName",
			width : "180"
		} ],// 行选择事件
		onSelectRow : function(id) {
			getJobById(id);
		}
	}));

	// 根据用户ID获取用户信息
	function getJobById(id) {
		var user = {};
		user.id = id;
		JDS.call({
			service : "jobService.getBeanById",
			data : [ id ],
			success : function(result) {
				bean = result.data;
		
				$("#job_form").json2form(bean);

				// 设置角色树
				loadRoleTree(bean.uuid);
				
				//加载权限树 20140806+ by zky
				//loadPrivilegeTree(bean.uuid);
				
				// 加载部门权限树+ by zky
				//loadUserPrivilegeTree(bean.uuid);

				var active = $(".tabs").tabs("option", "active");
				if (active == 2) {
					loadUserRoleNestedRoleTree(bean.uuid);
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
			service : "jobService.getPrivilegeTree",
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
			service : "jobService.getJobPrivilegeTree",
			data : uuid,
			success : function(result) {
				var zTree = $.fn.zTree
						.init($("#job_privilege_tree"), {},
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
	$("#btn_upload").button();
	// JQuery UI页签
	$(".tabs").tabs({
		activate : function(event, ui) {
			if ((ui.newPanel.attr("id") == "tabs-3")) {
				// 岗位角色嵌套树tab激活时才加载
				if (bean.uuid != null && bean.uuid != "") {
					loadUserRoleNestedRoleTree(bean.uuid);
				} else {
					var ztree = $.fn.zTree.getZTreeObj($("job_role_nested_role_tree"));
					if (ztree != null) {
						ztree.destroy();
					}
				}
			}
		}
	});
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
			service : "jobService.getRoleTree",
			data : [ uuid ],
			success : function(result) {
				var zTree = $.fn.zTree.init($("#role_tree"), setting, result.data);
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
	// 设置角色树，角色树只加载一次
	// @Deprecated
	function toRoleTree(bean) {
		var zTree = $.fn.zTree.getZTreeObj("role_tree");
		if (zTree == null) {
			loadRoleTree(bean.uuid);
		} else {
			// 取消树选择
			zTree.checkAllNodes(false);

			var roles = bean["roles"];
			for ( var index = 0; index < roles.length; index++) {
				var node = zTree.getNodeByParam("id", roles[index].uuid);
				if (node) {
					zTree.checkNode(node, true);
				}
			}
			// 设置已选中的角色到多选下拉框
			setSelectedRole();
		}
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
	// 加载用户角色嵌套树
	function loadUserRoleNestedRoleTree(uuid) {
		JDS.call({
			service : "jobService.getJobRoleNestedRoleTree",
			data : uuid,
			success : function(result) {
				var zTree = $.fn.zTree.init($("#job_role_nested_role_tree"), {}, result.data);
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
		$("#btn_save").show();

		var departmentName = selectedDepartment.path;
		var departmentId = selectedDepartment.id;
		// 所在部门
		var isDepartmentAdmin = $("#isDepartmentAdmin").val();
		if (isDepartmentAdmin == "1") {
			$("#departmentName").unbind("click");
			$("#departmentName").val(departmentName);
			$("#departmentName").attr("readonly", "readonly");
			$("#departmentId").val(departmentId);
		} else {
			$("#departmentName").val(departmentName);
			$("#departmentId").val(departmentId);
		}
	});
	function clear() {
		$("#job_form").clearForm(true);
		bean.uuid = null;
		// 清空角色树
		var zTree = $.fn.zTree.getZTreeObj("role_tree");
		if (zTree) {
			zTree.checkAllNodes(false);
		}
		// 清空
		$("#selected_role").html("");
		// 销毁岗位角色嵌套树
		var tree = $.fn.zTree.getZTreeObj("job_role_nested_role_tree");
		if (tree != null) {
			tree.destroy();
		}
		
	/*	//清空权限树
		$("#selected_privilege").html("");
		var prtree = $.fn.zTree.getZTreeObj("privilege_tree");
		if (prtree != null) {
			prtree.checkAllNodes(false);
		}
		
		//清空权限树
		var prtree1 = $.fn.zTree.getZTreeObj("job_privilege_tree");
		if (prtree1 != null) {
			prtree1.destroy();
		}*/
		
	}
	
	var otherParam={"serviceName" : "dataDictionaryService",
			"methodName" : "getAsTreeAsyncForUuid",
			"data":"FUNCTION_TYPE"};
	//初始化职能树
	initDataDictTree(otherParam,"functionNames", "functionUuids", true,200,150);
	
	var otherParam1={"serviceName" : "dutyService",
			"methodName" : "createDutyTreeSelect"};
	//初始化职务树
	initDataDictTree(otherParam1,"dutyName", "dutyUuid", false,220,380);
	
	//--------------初始化职能树---------------------------------
	function initDataDictTree(otherParam,nameField,IdField,mutiselect,width,height){
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
							//$("#"+inputId).val(treeNode.name);
							$("#"+inputId).val(treeNode.data);
							$("#"+IdField).val(treeNode.id);
					}
				}
			};
	
		$("#"+nameField).comboTree({
			labelField : nameField,
			valueField : IdField,
			width: width,
			height: height,
			treeSetting : setting
		});
	}
	//--------------初始化职能树结束---------------------------------

	// 保存用户信息
	$("#btn_save").click(function() {
		// 收集表单数据
		// toObject($("#user_form"), bean);
		$("#job_form").form2json(bean);
		// 收集角色树
		rolesToObject(bean);
		//收集权限树
		//privilegeToObject(bean);
		JDS.call({
			service : "jobService.saveBean",
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
		// var rowids = $("#list").jqGrid('getGridParam', 'selarrrow');
		if ($(this).attr("id") == "btn_del") {
			var id = $("#list").jqGrid('getGridParam', 'selrow');
			if (id == "" || id == null) {
				alert("请选择记录！");
				return true;
			}
			// 设置要删除的id
			bean.id = id;
		} else {
			if (bean.id == "" || bean.id == null) {
				alert("请选择记录！");
				return true;
			}
		}
		var name = $("#list").getRowData(bean.id)["name"];
		if (confirm("确定要删除岗位[" + name + "]吗？")) {
			JDS.call({
				service : "jobService.deleteById",
				data : [ bean.id ],
				success : function(result) {
					alert("删除成功!");
					$.common.json.clearJson(bean);
					$("#user_form").json2form(bean);
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
				service : "jobService.deleteByIds",
				data : [ rowids ],
				success : function(result) {
					alert("删除成功!");
					$.common.json.clearJson(bean);
					$("#user_form").json2form(bean);
					// 删除成功刷新列表
					$("#list").trigger("reloadGrid");
				}
			});
		}
	});

	
	// 工作信息
	// 所在部门
		$("#departmentName").click(function() {
			$.unit.open({
				title : "选择部门",
				labelField : "departmentName",
				valueField : "departmentId",
				type : "Dept",
				multiple:false,
				selectType : 2,
				showType : false
			});
		});

		// 上级领导
		$("#leaderNames").click(function() {
			$.unit.open({
				title : "选择人员",
				labelField : "leaderNames",
				valueField : "leaderIds",
				selectType : 4
			});
		});
		
	// 列表查询
	$("#query_job").keypress(function(e) {
		if (e.keyCode == 13) {
			$("#btn_query").trigger("click");
		}
	});

	$("#btn_query").click(function(e) {
		var queryValue = $("#query_job").val();
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

	// 页面布局
	Layout.layout();
	sizePane("west", "53%");

});
// 重新加载用户列表
function reloadJobGrid(department) {
	selectedDepartment = department;
	var url = ctx + '/org/department/job/list?departmentUuid=' + department.uuid;
	$("#list").jqGrid("setGridParam", {
		postData : {},
		page : 1,
		url : url
	}).trigger("reloadGrid");
}
