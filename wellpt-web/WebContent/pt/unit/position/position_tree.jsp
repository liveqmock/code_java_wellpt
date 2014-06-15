<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>User List</title>
<jsp:include page="/pt/common/meta.jsp" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/ligerUI/skins/Aqua/css/ligerui-all.css" />
<link href="${ctx}/resources/ligerUI/skins/ligerui-icons.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" />

<!-- Project -->
<script type="text/javascript"
	src="${ctx}/resources/jquery/1.8/jquery.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/ligerUI/js/ligerui.min.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/ztree/js/jquery.ztree.all-3.5.min.js"></script>
<script src="${ctx}/resources/pt/js/global.js"></script>
<script type="text/javascript">
	var positionframe;
	//ztree设置
	var setting = {
		view : {
			dblClickExpand : false,
			showLine : true,
			selectedMulti : false,
			expandSpeed : ($.browser.msie && parseInt($.browser.version) <= 6) ? ""
					: "fast"
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pid",
				rootPId : ""
			}
		},
		callback : {
			beforeClick : function(treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("tree");
				if (treeNode.isParent) {
					zTree.expandNode(treeNode);
				}
				$(".l-layout-header").html(treeNode.name);
				if (treeNode.children) {
					positionframe.attr("src", ctx + treeNode.defaultUrl);
				} else {
					//查看详细
					positionframe.attr("src", ctx + "/org/position/view/"
							+ treeNode.id);
				}
				return true;
			}
		}
	};

	$(function() {
		//页面布局
		$("#layout1").ligerLayout({
			leftWidth : 200
		});
		positionframe = $("#positionframe");
		//加载资源树结点
		$.post("${ctx}/org/position/get/tree/-1", function(result) {
			var zTreeObj = $.fn.zTree.init($("#tree"), setting, result);
			var nodes = zTreeObj.getNodes();
			//默认展开第一个节点
			if (nodes.length > 0) {
				var node = nodes[0];
				zTreeObj.expandNode(node, true, false, false, true);
			}
		});
	});
</script>
</head>
<body style="overflow: hidden;">
	<div id="layout1">
		<div position="left" title="岗位设置">
			<ul id="tree" class="ztree"></ul>
		</div>
		<div id="center" position="center" title="岗位列表">
			<iframe id="positionframe" name="positionframe" frameborder="0"
				style="width: 100%; height: 100%;"
				src="${ctx}/org/position?parentUuid=-1" />
		</div>
	</div>
</body>
</html>