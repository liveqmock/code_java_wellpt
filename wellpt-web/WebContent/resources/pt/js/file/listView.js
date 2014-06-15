I18nLoader.load("/resources/pt/js/fileManager");
$(function() {
	$(".folderList").click(function(){
		var objId = $(this).attr("objId");
		var objType = $(this).attr("objType");
		$("#currentId").val(objId);
		$("#objType").val(objType);
	});
	
	$("#move").click(function(){
		var currentId = $("#currentId").val();
		if(currentId.length <=0){
			oAlert(fileManager.pleaseChooseAndOperate);
			return ;
		}else{
			$("#currentOperate").val("move");
			showFolderTree();
		}
		
	});
	
	$("#copy").click(function(){
		var currentId = $("#currentId").val();
		if(currentId.length <=0){
			oAlert(fileManager.pleaseChooseAndOperate);
			return ;
		}else{
			$("#currentOperate").val("copy");
			showFolderTree();
		}
	});
	
	
	
	
	// JQuery zTree设置
	var dataFolderSetting = {
		async : {
			enable : true,
			contentType : "application/json",
			url : ctx + "/json/data/services",
			otherParam : {
				"serviceName" : "folderManagerService",
				"methodName" : "getLibTree",
				"data":viewId
			},
			type : "POST"
		},
		callback : {
			beforeClick : beforeClick
		}
	};
	// 树结点点击处理
	function beforeClick(treeId, treeNode) {
		// 最新选择的树结点
		latestSelectedNode = treeNode;
		if (treeNode.id != null && treeNode.id != -1) {
			// 查看详细
			//$("#moveToFolderId").val(treeNode.id);
			$("#fileListIframe").attr("src",ctx+"/fileManager/folder/fileList?id="+treeNode.id);
			$("#fileListIframe").reload();
		}
		return true;
	}
	// JQyery zTree加载数据字典树结点
	function loadFolderTree() {
		$.fn.zTree.init($("#folder_tree"), dataFolderSetting);
	}
	loadFolderTree();
	
	var showTreeDialogOptions = {
			autoOpen:false,
		    bgiframe: true,
		    modal: true,
		    overlay: {
		        backgroundColor: '#000',
		        opacity: 0.5
		    },
			buttons : {}
		};

	showTreeDialogOptions.buttons[fileManager.buttonYes] = function() {
		oConfirm(fileManager.sureToDoList, function(){
    		JDS.call({
    			service : "folderManagerService.updateFolderDest",
    			data : [ $("#currentId").val(),$("#objType").val(),$("#currentOperate").val(),$("#moveToFolderId").val()],
    			success : function(result) {
    				
    				window.location.href=ctx+'/fileManager/folder/list?id='+$("#parentUuid").val();
    			}
    		});
        	$("#treeDialog").oDialog('close');
    	});
	};

	showTreeDialogOptions.buttons[fileManager.buttonCancel] = function() {
			$("#treeDialog").oDialog('close');
		};
	
	
	function showFolderTree(){
		 
		$.fn.zTree.init($("#folder_tree"), dataFolderSetting);
		
		$("#treeDialog").oDialog("open");
	}
	
	
	
	 /*
	function loadFolderTree(uuid) {
		JDS.call({
			service : "folderManagerService.getFolderTree",
			data : uuid,
			success : function(result) {
				var zTree = $.fn.zTree.init($("#folder_tree"),
						{}, result.data);
				var nodes = zTree.getNodes();
				// 默认展开第一个节点
				if (nodes.length > 0) {
					var node = nodes[0];
					zTree.expandNode(node, true, false, false, true);
				}
			}
		});
	}*/

	
});