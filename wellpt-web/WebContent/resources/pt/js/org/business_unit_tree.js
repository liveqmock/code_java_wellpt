$(function() {
	// 最新选择的树结点
	var latestSelectedNode = null;
	var form_selector = "#business_unit_tree_form";
	var bean = {
		"businessTypeUuid" : null,
		"uuid" : null,
		"parentUuid" : null,
		"parentName" : null,
		"code" : null,
		"unitName" : null,
		"unitId" : null
	};

	var selectUnitName = function() {
		window.parent.selectUnitName($("#unitName").val(), $("#unitId").val());
		/**
		 * $.unit.open({ title : "选择单位", labelField : "unitName", valueField :
		 * "unitUuid", type : "CommonUnitTree", selectType : 1, multiple : false
		 * });
		 */
	};

	// 根据用户ID获取用户信息
	function getUnit(unitTreeUuid) {
		JDS.call({
			service : "businessUnitTreeService.getBean",
			data : [ unitTreeUuid ],
			success : function(result) {
				bean = result.data;
				var businessTypeUuid = $("#businessTypeUuid").val();
				// 设置表单数据
				$(form_selector).json2form(bean);

				$("#businessTypeUuid").val(businessTypeUuid);

				// 单元
				if (bean.unitId != null && bean.unitId != "") {
					$("#type").val("1");
					$("#unitName").unbind("click", selectUnitName);
					$("#unitName").bind("click", selectUnitName);
				} else {
					$("#type").val("2");
					$("#unitName").unbind("click", selectUnitName);
				}
				$("#row_parent").show();
				$("#row_name").show();
				$("#row_code").show();
				$("#btn_save").show();
				$("#btn_del").show();
			}
		});
	}

	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$(".tabs").tabs();
	// 隐藏按钮
	$("#btn_save").hide();
	$("#btn_del").hide();

	// JQuery zTree设置
	var setting = {
		view : {
			dblClickExpand : false,
			showLine : true,
			selectedMulti : false,
			expandSpeed : ($.browser.msie && parseInt($.browser.version) <= 6) ? "" : "fast"
		},
		callback : {
			beforeClick : function(treeId, treeNode) {
				// 最新选择的树结点
				latestSelectedNode = treeNode;
				var zTree = $.fn.zTree.getZTreeObj("business_unit_tree");
				if (treeNode.isParent) {
					zTree.expandNode(treeNode);
					// 清空form表单数据
					// clear();
				}
				if (treeNode.id != null && treeNode.id != -1) {
					// 查看详细
					getUnit(treeNode.id);
				}
				$("#row_parent").show();
				$("#row_name").show();
				$("#row_code").show();
				return true;
			}
		}
	};

	// JQyery zTree加载组织单元树结点
	window.loadUnitTree = function(businessTypeUuid) {
		JDS.call({
			service : "businessUnitTreeService.getAsTree",
			data : [ businessTypeUuid ],
			success : function(result) {
				var zTree = $.fn.zTree.init($("#business_unit_tree"), setting, result.data);
				var nodes = zTree.getNodes();
				// 默认展开第一个节点
				if (nodes.length > 0) {
					var node = nodes[0];
					zTree.expandNode(node, true, false, false, true);
				}
				latestSelectedNode = null;
			}
		});
	};

	// 新增单位操作
	$("#btn_unit_add").click(function() {
		var businessTypeUuid = $("#businessTypeUuid").val();
		clear();
		$("#businessTypeUuid").val(businessTypeUuid);
		if (latestSelectedNode != null && latestSelectedNode.id != null && latestSelectedNode.id != -1) {
			$("#parentUuid").val(latestSelectedNode.id);
			$("#parentName").val(latestSelectedNode.name);
		}
		$("#type").val("1");
		$("#row_parent").show();
		$("#row_name").show();
		$("#row_code").show();
		$("#btn_save").show();
		$("#btn_del").hide();
		$("#unitName").unbind("click", selectUnitName);
		$("#unitName").bind("click", selectUnitName);
	});
	// 新增分类操作
	$("#btn_unit_type").click(function() {
		// 清空表单等
		var businessTypeUuid = $("#businessTypeUuid").val();
		clear();
		$("#businessTypeUuid").val(businessTypeUuid);
		$("#btn_del").hide();
		if (latestSelectedNode != null && latestSelectedNode.id != null && latestSelectedNode.id != -1) {
			$("#parentUuid").val(latestSelectedNode.id);
			$("#parentName").val(latestSelectedNode.name);
		}
		$("#type").val("2");
		$("#row_parent").show();
		$("#row_name").show();
		$("#row_code").show();
		$("#btn_save").show();
		$("#btn_del").hide();
		$("#unitName").unbind("click", selectUnitName);
	});
	// 业务单位
	$("#unitName").bind("click", selectUnitName);

	// 清空表单及成员列表数据
	function clear() {
		// 清空表单
		$(form_selector).clearForm(true);

		bean = {
			"businessTypeUuid" : null,
			"uuid" : null,
			"parentUuid" : null,
			"parentName" : null,
			"code" : null,
			"unitName" : null,
			"unitId" : null
		};
	}

	// 保存单位信息
	$("#btn_save").click(function() {
		if ($("#row_name #unitName").val() == '') {
			alert("请填写单位名称");
			return;
		}
		$("#business_unit_tree_form").form2json(bean);

		// 收集表单数据
		$(form_selector).form2json(bean);
		JDS.call({
			service : "businessUnitTreeService.saveBean",
			data : [ bean ],
			success : function(result) {
				alert("保存成功!");
				// 加载组织单元树结点
				var businessTypeUuid = $("#businessTypeUuid").val();
				loadUnitTree(businessTypeUuid);
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
		if (confirm("确定要删除[" + name + "]吗？")) {
			JDS.call({
				service : "businessUnitTreeService.remove",
				data : [ uuid ],
				success : function(result) {
					alert("删除成功!");
					var businessTypeUuid = $("#businessTypeUuid").val();
					// 删除成功刷新树
					loadUnitTree(businessTypeUuid);
					// 清空表单等
					clear();
					$("#businessTypeUuid").val(businessTypeUuid);
				}
			});
		}
	});

	// 页面布局
	Layout.layout();
	sizePane("west", "42%");
});
function setBusinessTypeUuid(businessTypeUuid) {
	loadUnitTree(businessTypeUuid);
	$("#business_unit_tree_form").clearForm(true);
	$("#businessTypeUuid").val(businessTypeUuid);
}
function setUnit(unitName, unitId) {
	$("#unitName").val(unitName);
	$("#unitId").val(unitId);
}