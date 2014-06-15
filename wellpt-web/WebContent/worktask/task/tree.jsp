<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<!DOCTYPE html> 

<script src="${ctx}/resources/jquery/jquery.js"></script>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
	var uid = null;
	function check(userid) {
		uid = userid;
	}
	function gourl(url) {
		if (uid == null || uid == '') {
			url = url + "?userId=" + uid;
		}
		window.parent.share_iframe.src = url;
	}
	var ctx = "${ctx}";
	window.onload = function() {
		var zTreeSetting = {
			async : {
				enable : true,
				contentType : "application/json",
				url : ctx + "/json/data/services",
				otherParam : {
					"serviceName" : "unitTreeService",
					"methodName" : "parseUnitTree"
				},
				type : "POST"
			},
			callback : {
				onClick : onClick
			}
		};

		$.fn.zTree.init($("#selector"), zTreeSetting);
	}
	function onClick(event, treeId, treeNode, clickFlag) {
		window.parent.check(treeNode.id);
	}
</script>
<body>
	<div id="selector" class="divborder" style="cursor: pointer;"></div>
	<script type="text/javascript"
		src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>