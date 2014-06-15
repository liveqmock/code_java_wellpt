$(function() {
	// JQuery zTree设置
	var setting = {
		callback : {
			onClick : onClick
		}
	};

	// JQyery zTree加载资源树结点
	function loadTree(uuid) {
		JDS.call({
			service : "departmentAdminService.getDepartmentNestedDepartmentTree",
			data : [ uuid ],
			async : false,
			success : function(result) {
				var zTree = $.fn.zTree.init($("#department_nested_department_tree"), setting, result.data);
				var nodes = zTree.getNodes();
				// 默认展开第一个节点
				if (nodes.length > 0) {
					var node = nodes[0];
					zTree.expandNode(node, true, true, false, true);
					getChildNode(node, zTree);
				}
			}
		});
	}
	loadTree("");

	function getChildNode(node, zTree) {
		var departmentUuid = $("#departmentUuid").val();
		for ( var i = 0; i < node.children.length; i++) {
			var childNode = node.children[i];
			if (childNode.id == departmentUuid) {
				zTree.selectNode(childNode);
			} else {
				getChildNode(childNode, zTree);
			}
		}
	}

	function onClick(event, treeId, treeNode) {
		JDS.call({
			service : "departmentAdminService.isAdminDepartmentUuidList",
			data : [ treeNode.id ],
			success : function(result) {
				if (result.data == true) {
					$("#departmentUserIf")[0].contentWindow.reloadUserGrid(treeNode.data);
				}
			}
		});
	}

	// 页面布局
	Layout.layout();
	sizePane("west", "16%");
	// $("#departmentUserIf")[0].contentWindow.sizePane("west", 400);
});