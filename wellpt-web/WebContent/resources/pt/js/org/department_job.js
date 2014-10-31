$(function() {
	// JQuery zTree设置
	var setting = {
		callback : {
			beforeClick : function(treeId, treeNode) {
				if (treeNode == null) {
					return;
				}
				$("#departmentJobIf")[0].contentWindow.reloadJobGrid(treeNode.data);
				return true;
			}
		}
	};
	// JQyery zTree加载资源树结点
	function loadTree() {
		JDS.call({
			service : "departmentJobService.getDepartmentAsTree",
			data : [ -1 ],
			success : function(result) {
				var treeNodes = result.data.children;
				var zTree = $.fn.zTree.init($("#department_nested_department_tree"), setting, treeNodes);
				var nodes = zTree.getNodes();
				// 默认展开第一个节点
				if (nodes.length > 0) {
					var node = nodes[0];
					zTree.expandNode(node, true, false, false, true);
				}
			}
		});
	}
	loadTree();

	// 页面布局
	Layout.layout();
	sizePane("west", "16%");
	// $("#departmentUserIf")[0].contentWindow.sizePane("west", 400);
});