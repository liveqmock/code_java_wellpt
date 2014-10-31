var selectCategory={};
$(function() {
	var bean = {
		"uuid" : null,
		"name" : null,
		"enabled" : null,
		"remark" : null,
		"roles" : [],
		"resources" : [],
		"viewFieldUuid":[],
		"categoryName":null,
		"categoryUuid":null
	};
	var categoryUuid="";
	$("#list").jqGrid($.extend($.common.jqGrid.settings, {
		url : ctx + '/security/category/privilege/list?categoryUuid=' + categoryUuid,
		mtype : 'POST',
		datatype : "json",
		colNames : [ "uuid", "名称", "编号", "备注" ],
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
			name : "code",
			index : "code",
			width : "60"
		}, {
			name : "remark",
			index : "remark",
			width : "300"
		} ],// 行选择事件
		onSelectRow : function(id) {
			var rowData = $(this).getRowData(id);
			getPrivilege(rowData.uuid);
		}
	}));

	// 根据UUID获取组织选择项
	function getPrivilege(uuid) {
		var privilege = {};
		privilege.uuid = uuid;
		JDS.call({
			service : "privilegeService.getBean",
			data : [ uuid ],
			success : function(result) {
				bean = result.data;
				$("#btn_del").show();
				// 设置表单数据
				$("#privilege_form").json2form(bean);

				// 设置权限资源树
				// toResourceTree(bean);
				loadResourceTree(bean.uuid);
				loadViewResourceTree(bean.uuid);
			}
		});
	}

	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$(".tabs").tabs();
	// JQuery zTree
	var setting = {
		check : {
			enable : true
		}
	};
	// 加载视图资源树，自动选择已选资源
	function loadViewResourceTree(uuid) {
		var privilege = {};
		privilege.uuid = uuid;
		JDS.call({
			service : "privilegeService.getViewResourceTree",
			data : [ uuid ],
			success : function(result) {
				var zTree = $.fn.zTree.init($("#view_resource_tree"), setting,
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
	
	// 加载资源树，自动选择已选资源
	function loadResourceTree(uuid) {
		var privilege = {};
		privilege.uuid = uuid;
		JDS.call({
			service : "privilegeService.getResourceTree",
			data : [ uuid ],
			success : function(result) {
				var zTree = $.fn.zTree.init($("#resource_tree"), setting,
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
	// 对象数据设置到资源树，资源树只加载一次
	// @Deprecated
	function toResourceTree(bean) {
		var zTree = $.fn.zTree.getZTreeObj("resource_tree");
		if (zTree == null) {
			loadResourceTree(bean.uuid);
		} else {
			// 取消树选择
			zTree.checkAllNodes(false);

			var resources = bean["resources"];
			for ( var index = 0; index < resources.length; index++) {
				var node = zTree.getNodeByParam("id", resources[index].uuid);
				if (node) {
					zTree.checkNode(node, true);
				}
			}
		}
	}
	// 收集权限资源树
	function resourcesToObject(bean) {
		var zTree = $.fn.zTree.getZTreeObj("resource_tree");
		var viewzTree = $.fn.zTree.getZTreeObj("view_resource_tree");
		if (zTree != null) {
			var checkNodes = zTree.getCheckedNodes(true);
			bean["resources"] = [];
			$.each(checkNodes, function(index) {
				var resource = {};
				resource = this.id;
				bean["resources"].push(resource);
			});
		}
		
		if(viewzTree !=null) {
			var checkNodes = viewzTree.getCheckedNodes(true);
			bean["viewFieldUuid"] = [];
			$.each(checkNodes, function(index) {
				if(this.isParent !=true) {
					var viewFieldUuid = {};
					viewFieldUuid = this.id;
					bean["viewFieldUuid"].push(viewFieldUuid);
				}
			});
		}
	}

	var otherParam={"serviceName" : "dataDictionaryService",
			"methodName" : "getFromTypeAsTreeAsync",
			"data":"SECURITY_CATEGORY"};
	//初始化职能树
	initDataDictTree(otherParam,"categoryName", "categoryUuid", false,200,300);
	
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
							$("#"+inputId).val(treeNode.name);
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
	
	// 新增操作
	$("#btn_add").click(function() {
		// 清空表单
		$("#privilege_form").clearForm(true);
		$("#btn_del").hide();
		// 清空权限资源树
		var zTree = $.fn.zTree.getZTreeObj("resource_tree");
		zTree.checkAllNodes(false);
	
		if(selectCategory.uuid!='NOCATEGORY'){
			$("#categoryName").val(selectCategory.name);
			$("#categoryUuid").val(selectCategory.uuid);
		}
		
		
	});

	// 保存用户信息
	$("#btn_save").click(function() {
		// 收集表单数据
		$("#privilege_form").form2json(bean);
		// 收集权限资源树
		resourcesToObject(bean);
		JDS.call({
			service : "privilegeService.saveBean",
			data : [ bean ],
			success : function(result) {
				alert("保存成功!");
				$("#list").trigger("reloadGrid");
			}
		});
	});

	// 删除操作
	$("#btn_del").click(function() {
		if (bean.uuid == "" || bean.uuid == null) {
			alert("请选择记录！");
			return true;
		}
		var name = bean["name"];
		if (confirm("确定要删除权限[" + name + "]吗？")) {
			JDS.call({
				service : "privilegeService.remove",
				data : [ bean.uuid ],
				success : function(result) {
					alert("删除成功!");
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
				service : "privilegeService.removeAll",
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
	$("#query_privilege").keypress(function(e) {
		if (e.keyCode == 13) {
			$("#btn_query").trigger("click");
		}
	});
	$("#btn_query").click(function(e) {
		var queryValue = $("#query_privilege").val();
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

//重新加载用户列表
function reloadCategoryGrid(category) {
	selectCategory=category;
	var url = ctx + '/security/category/privilege/list?categoryUuid=' + selectCategory.uuid;
	$("#list").jqGrid("setGridParam", {
		postData : {},
		page : 1,
		url : url
	}).trigger("reloadGrid");
}