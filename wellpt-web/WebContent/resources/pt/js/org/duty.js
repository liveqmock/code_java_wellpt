$(function() {
	var list_selector = "#list";
	var form_selector = "#duty_form";
	var bean = {
		"uuid" : null,
		"code" : null,
		"name" : null,
		"dutyLevel" : null,
		"seriesName" : null,
		"seriesUuid" : null,
		"remark" : null,
		"externalId":null,
		"id" : null
	};
	$(list_selector).jqGrid($.extend($.common.jqGrid.settings, {
		url : ctx + '/common/jqgrid/query?queryType=duty',
		colNames : [ "uuid", "名称","编号",  "职级", "职位系列"],
		colModel : [ {
			name : "uuid",
			index : "uuid",
			width : "180",
			hidden : true,
			key : true
		},{
			name : "name",
			index : "name",
			width : "180"
		}, {
			name : "code",
			index : "code",
			width : "100"
		},  {
			name : "dutyLevel",
			index : "dutyLevel",
			width : "100"
		}, {
			name : "seriesName",
			index : "seriesName",
			width : "100",
		}
		], // 行选择事件
		onSelectRow : function(id) {
			var rowData = $(this).getRowData(id);
			getDuty(rowData.uuid);
		}
	}));
	
	var otherParam={"serviceName" : "dataDictionaryService",
			"methodName" : "getAsTreeAsyncForUuid",
			"data":"JOB_SERIES"};
	//初始化职能树
	initDataDictTree(otherParam,"seriesName", "seriesUuid", false);
	
	//--------------初始化系列树---------------------------------
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

	// 根据UUID获取职务代理人
	function getDuty(uuid) {
		var option = {};
		option.uuid = uuid;
		JDS.call({
			service : "dutyService.getBean",
			data : option.uuid,
			success : function(result) {
				bean = result.data;
				$("#btn_del").show();
				$(form_selector).json2form(bean);
				
				// 设置角色树
				loadRoleTree(bean.uuid);
				
				var active = $(".tabs").tabs("option", "active");
				if (active == 2) {
					loadDutyRoleNestedRoleTree(bean.uuid);
				}
			}
		});
	}
	
	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$(".tabs").tabs({
		activate : function(event, ui) {
			if ((ui.newPanel.attr("id") == "tabs-3")) {
				// 群组角色嵌套树tab激活时才加载
				if (bean.uuid != null && bean.uuid != "") {
					loadDutyRoleNestedRoleTree(bean.uuid);
				} else {
					var ztree = $.fn.zTree.getZTreeObj($("duty_role_nested_role_tree"));
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
			service : "dutyService.getRoleTree",
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
	function loadDutyRoleNestedRoleTree(uuid) {
		JDS.call({
			service : "dutyService.getDutyRoleNestedRoleTree",
			data : uuid,
			success : function(result) {
				var zTree = $.fn.zTree.init($("#duty_role_nested_role_tree"), {}, result.data);
				var nodes = zTree.getNodes();
				// 默认展开第一个节点
				if (nodes.length > 0) {
					var node = nodes[0];
					zTree.expandNode(node, true, false, false, true);
				}
			}
		});
	}

	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$(".tabs").tabs();


	// 新增操作
	$("#btn_add").click(function() {
		$(form_selector).clearForm(true);
		$("#btn_del").hide();
	});

	// 保存用户信息
	$("#btn_save").click(function() {
		$(form_selector).form2json(bean);
		// 收集角色树
		rolesToObject(bean);
		
		JDS.call({
			service : "dutyService.saveBean",
			data : bean,
			success : function(result) {
				alert("保存成功!");
				// 删除成功刷新列表
				$(list_selector).trigger("reloadGrid");
			}
		});
	});

	// 删除操作
	$("#btn_del").click(function() {
		if (bean.uuid == "" || bean.uuid == null) {
			alert("请选择记录！");
			return true;
		}
		var name = bean.name;
		if (confirm("确定要删除职务[" + name + "]吗？")) {
			JDS.call({
				service : "dutyService.remove",
				data : bean.uuid,
				success : function(result) {
					alert("删除成功!");
					$(form_selector).clearForm(true);
					// 删除成功刷新列表
					$(list_selector).trigger("reloadGrid");
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
				service : "dutyService.removeAll",
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
	$("#query_duty").keypress(function(e) {
		if (e.keyCode == 13) {
			$("#btn_query").trigger("click");
		}
	});
	$("#btn_query").click(function(e) {
		var queryValue = $("#query_duty").val();
		var postData = {
			"queryPrefix" : "query",
			"queryOr" : true,
			"query_LIKES_code_OR_name_OR_dutyLevel_OR_seriesName" : queryValue
		};
		if (queryValue == "激活") {
			postData["query_EQI_status"] = 1;
		} else if (queryValue == "终止") {
			postData["query_EQI_status"] = 0;
		}
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