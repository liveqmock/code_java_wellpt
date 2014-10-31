$(function() {
	// JQuery zTree设置
	
	var dataDataDictionarySetting = {
		async : {
			enable : true,
			contentType : "application/json",
			url : ctx + "/json/data/services",
			otherParam : {
				"serviceName" : "categoryRoleService",
				"methodName" : "getAsTreeAsync",
			},
			type : "POST"
		},
		callback : {
			beforeClick : function(treeId, treeNode) {
				if (treeNode == null) {
					return;
				}
				$("#categoryroleIf")[0].contentWindow.reloadCategoryGrid(treeNode.data);
				return true;
			}
		}
	};
	// 树结点点击处理
	function beforeClick(treeId, treeNode) {
		// 最新选择的树结点
		latestSelectedNode = treeNode;
		if (treeNode.id != null && treeNode.id != -1) {
			// 查看详细
			getDataDictionary(treeNode.id);
		}
		return true;
	}
	// JQyery zTree加载数据字典树结点
	function loadDataDictionaryTree() {
		$.fn.zTree.init($("#category_tree"), dataDataDictionarySetting);
	}
	loadDataDictionaryTree();

	// 页面布局
	Layout.layout();
	sizePane("west", "16%");
});


