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
		"loginName" : null,
		"userName" : null,
		"password" : null,
		"sex" : null,
		"accountNonExpired" : null,
		"accountNonLocked" : null,
		"credentialsNonExpired" : null,
		"enabled" : null,
		"lastLoginTime" : null,
		"employeeNumber" : null,
		"departmentName" : null,
		"jobName" : null,
		"leaderNames" : null,
		"mobilePhone" : null,
		"officePhone" : null,
		"fax" : null,
		"photoUuid" : null,
		"idNumber" : null,
		"remark" : null,
		"roles" : [],
		"groups" : [],
		"departmentUsers" : [],
		"leaders" : [],
		"minorJobs" : [],
		"subjectDN" : null,
		"receiveSmsMessage" : null
	};
	var isDepartmentAdmin = $("#isDepartmentAdmin").val();
	var departmentUuid = $("#departmentUuid").val();
	$("#list").jqGrid($.extend($.common.jqGrid.settings, {
		url : ctx + '/org/department/user/list?departmentUuid=' + departmentUuid,
		colNames : [ "uuid", "登录名", "姓名", "性别", "员工编号", "部门", "岗位", "上级领导", "状态" ],
		colModel : [ {
			name : "uuid",
			index : "uuid",
			width : "180",
			hidden : true
		}, {
			name : "loginName",
			index : "loginName",
			width : "180"
		}, {
			name : "userName",
			index : "userName",
			width : "180"
		}, {
			name : "sex",
			index : "sex",
			width : "180",
			formatter : function(cellvalue, options, rowObject) {
				if (cellvalue == 1) {
					return '男';
				} else if (cellvalue == 2) {
					return '女';
				}
				return cellvalue;
			}
		}, {
			name : "employeeNumber",
			index : "employeeNumber",
			width : "180"
		}, {
			name : "departmentName",
			index : "departmentName",
			width : "180"
		}, {
			name : "jobName",
			index : "jobName",
			width : "180"
		}, {
			name : "leaderNames",
			index : "leaderNames",
			width : "180"
		}, {
			name : "enabled",
			index : "enabled",
			width : "180",
			formatter : function(cellvalue, options, rowObject) {
				if (cellvalue == true) {
					return '可用';
				} else if (cellvalue == false) {
					return '不可用';
				}
				return cellvalue;
			}
		} ],// 行选择事件
		onSelectRow : function(id) {
			// toForm(rowData, $("#user_form"));
			getUserById(id);
		}
	}));

	// 根据用户ID获取用户信息
	function getUserById(id) {
		var user = {};
		user.id = id;
		JDS.call({
			service : "userService.getBeanById",
			data : [ id ],
			success : function(result) {
				bean = result.data;
				if ($("#currentUserId").val() == id) {
					$("#btn_del").hide();
					$("#btn_del_all").hide();
				} else {
					$("#btn_del").show();
					$("#btn_del_all").show();
				}
				// toForm(bean, $("#user_form"));
				$("#user_form").json2form(bean);

				// 显示照片
				if (bean.photoUuid != null && $.trim(bean.photoUuid) != "") {
					var photoUrl = ctx + "/org/user/view/photo/" + bean.photoUuid;
					$("#user_photo").attr("src", photoUrl);
					$("#user_photo").show();
				} else {
					$("#user_photo").attr("src", "#");
					$("#user_photo").hide();
				}
				// 设置角色树
				loadRoleTree(bean.uuid);

				var active = $(".tabs").tabs("option", "active");
				if (active == 3) {
					loadUserRoleNestedRoleTree(bean.uuid);
				}
			}
		});
	}

	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	$("#btn_upload").button();
	// JQuery UI页签
	$(".tabs").tabs({
		activate : function(event, ui) {
			if ((ui.newPanel.attr("id") == "tabs-4")) {
				// 用户角色嵌套树tab激活时才加载
				if (bean.uuid != null && bean.uuid != "") {
					loadUserRoleNestedRoleTree(bean.uuid);
				} else {
					var ztree = $.fn.zTree.getZTreeObj($("user_role_nested_role_tree"));
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
		$("#roleUserUuid").val(uuid);
		JDS.call({
			service : "userService.getRoleTree",
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
			service : "userService.getUserRoleNestedRoleTree",
			data : uuid,
			success : function(result) {
				var zTree = $.fn.zTree.init($("#user_role_nested_role_tree"), {}, result.data);
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
		$("#user_form").clearForm(true);
		bean.uuid = null;
		// 清空角色树
		var zTree = $.fn.zTree.getZTreeObj("role_tree");

		// 由于部门管理员不能修改自己的权限（角色信息设为不可选择），所以部门管理员添加新人员时除了清除表单之外还要将角色信息前的复选框显示出来
		if (isDepartmentAdmin == "1") {
			JDS.call({
				service : "userService.getRoleTree",
				data : [ $("#roleUserUuid").val() + ",add" ],
				async : false,
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

		zTree.checkAllNodes(false);
		// 清空
		$("#selected_role").html("");
		// 销毁用户角色嵌套树
		var tree = $.fn.zTree.getZTreeObj("user_role_nested_role_tree");
		if (tree != null) {
			tree.destroy();
		}
		// 清空照片
		$("#user_photo").attr("src", "");
		$("#user_photo").hide();
		// 只能以证书登录
		$("input[name=onlyLogonWidthCertificate]").attr("checked", "checked");
	}

	// 导入用户信息
	$("#btn_imp").click(function() {
		$("#importUser").dialog({
			autoOpen : true,
			height : 200,
			width : 400,
			modal : true,
			buttons : {
				"确定" : function() {
					var file = $("#uploadfile").val();
					if (file == '') {
						alert("请选择xls文件");
						return;
					}
					if (file.indexOf(".") < 0) {
						return;
					}
					var fileType = file.substring(file.lastIndexOf("."), file.length);
					if (fileType == ".xls" || fileType == ".xlsx") {
						$.ajaxFileUpload({
							url : ctx + "/org/department/user/importUser?ruleId=" + $('#ruleId').val(),// 链接到服务器的地址
							secureuri : false,
							fileElementId : 'uploadfile',// 文件选择框的ID属性
							dataType : 'text', // 服务器返回的数据格式
							success : function(data, status) {
								if (data == "success") {
									alert("导入成功");
									;
									window.parent.document.location.reload();
								} else {
									alert(data);
								}
							},
							error : function(data, status, e) {
								alert("导入失败");
							}
						});
					} else {
						alert("请选择xls文件");
						return;
					}
				},
				"取消" : function() {
					$(this).dialog("close");
				}
			}
		});
	});

	// 从集团中导入用户信息
	/**
	 * $("#btn_imp_commonuser").click(function() {
	 * $("#importCommonUser").dialog({ autoOpen: true, height: 200, width: 400,
	 * modal: true, buttons: { "确定": function() { var commonUserNames =
	 * $("#commonUserNames").val(); if (commonUserNames == "") { alert("请选择人员");
	 * return ; } var commonUserIds = $("#commonUserIds").val(); var
	 * enableTenantId = true; if ($("#enableTenantId").attr("checked")) {
	 * enableTenantId = false; } $.ajax({ type:"post", data :
	 * {"commonUserNames":commonUserNames,"commonUserIds":commonUserIds,"enableTenantId":enableTenantId},
	 * async:false, url:ctx+"/org/user/saveCommonUsers",
	 * success:function(result){ if (result.success) { alert("保存成功!"); //
	 * 保存成功刷新列表 $("#list").trigger("reloadGrid"); $("#importCommonUser").dialog(
	 * "close" ); } else { alert(result.msg); } } }); }, "取消": function() {
	 * $(this).dialog( "close" ); } } }); });
	 */

	// 集团通讯录
	$("#btn_imp_commonuser").click(function() {
		$.unit.open({
			title : "从集团中导入用户",
			labelField : "commonUserNames",
			valueField : "commonUserIds",
			type : "UnitUser",
			selectType : 4,
			// excludeValues : $("#tenantId").val(),
			afterSelect : saveUsers
		});
	});

	window.saveUsers = function() {
		var commonUserNames = $("#commonUserNames").val();
		if (commonUserNames == "") {
			alert("请选择人员");
			return;
		}
		var commonUserIds = $("#commonUserIds").val();
		$.ajax({
			type : "post",
			data : {
				"commonUserNames" : commonUserNames,
				"commonUserIds" : commonUserIds
			},
			async : false,
			url : ctx + "/org/user/saveCommonUsers",
			success : function(result) {
				if (result.success) {
					alert("保存成功!");
					// 保存成功刷新列表
					$("#list").trigger("reloadGrid");
				} else {
					alert(result.msg);
				}
			}
		});
	};

	$("#btn_upload").click(function() {
		// 上传文件
		$.ajaxFileUpload({
			url : ctx + "/org/user/upload/photo",// 链接到服务器的地址
			secureuri : false,
			fileElementId : 'upload',// 文件选择框的ID属性
			dataType : 'text', // 服务器返回的数据格式
			success : function(data, status) {
				$("#photoUuid").val(data);
				$("#user_photo").attr("src", ctx + "/org/user/view/photo/" + data);
				$("#user_photo").show();
			},
			error : function(data, status, e) {
				$.jBox.info(dymsg.fileUploadFailure, dymsg.tipTitle);// 弹出对话框
			}
		});
	});

	// 保存用户信息
	$("#btn_save").click(function() {
		// 收集表单数据
		// toObject($("#user_form"), bean);
		$("#user_form").form2json(bean);
		// 收集角色树
		rolesToObject(bean);
		JDS.call({
			service : "userService.saveBean",
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
		var userName = $("#list").getRowData(bean.id)["userName"];
		if (confirm("确定要删除用户[" + userName + "]吗？")) {
			JDS.call({
				service : "userService.deleteById",
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
				service : "userService.deleteByIds",
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
	if ($("#isDepartmentAdmin").val() != "1") {
		$("#departmentName").click(function() {
			$.unit.open({
				title : "选择部门",
				labelField : "departmentName",
				valueField : "departmentId",
				type : "Dept",
				selectType : 2
			});
		});
	}
	// 上级领导
	$("#leaderNames").click(function() {
		$.unit.open({
			title : "选择人员",
			labelField : "leaderNames",
			valueField : "leaderIds",
			selectType : 4
		});
	});
	// 职务代理人
	$("#deputyNames").click(function() {
		$.unit.open({
			title : "选择人员",
			labelField : "deputyNames",
			valueField : "deputyIds",
			selectType : 4
		});
	});
	// 所属群组
	$("#groupNames").click(function() {
		$.unit.open({
			title : "选择群组",
			labelField : "groupNames",
			valueField : "groupIds",
			type : "PublicGroup",
			selectType : 2
		});
	});

	// 列表查询
	$("#query_user").keypress(function(e) {
		if (e.keyCode == 13) {
			$("#btn_query").trigger("click");
		}
	});

	$("#btn_query").click(function(e) {
		var queryValue = $("#query_user").val();
		var postData = {
			"queryPrefix" : "query",
			"queryOr" : true,
			"query_LIKES_loginName_OR_userName_OR_remark" : queryValue
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
function reloadUserGrid(department) {
	selectedDepartment = department;
	var url = ctx + '/org/department/user/list?departmentUuid=' + department.uuid;
	$("#list").jqGrid("setGridParam", {
		postData : {},
		page : 1,
		url : url
	}).trigger("reloadGrid");
}
