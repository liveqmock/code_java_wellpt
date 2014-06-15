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

<!-- Project -->
<script type="text/javascript"
	src="${ctx}/resources/jquery/1.8/jquery.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/ligerUI/js/ligerui.min.js"></script>
<script src="${ctx}/resources/pt/js/global.js"></script>
<script type="text/javascript">
	var layout, grid;
	function itemclick(item) {
		var selected = grid.getSelected();
		switch (item.id) {
		case "add":
			$(location).attr('href', '${ctx}/org/position/create?parentUuid=${parentUuid}');
			break;
		case "modify":
			if (!selected) {

			} else {
				top.addTab(selected['uuid'], '修改部门信息',
						'${ctx}/org/position/update/' + selected['uuid']);
			}
			break;
		case "delete":
			break;
		}
	}

	$(function() {
		//页面布局
		$("#layout1").ligerLayout({
			leftWidth : 200
		});
		var config = {
			"grid" : {
				columns : [ {
					display : "名称",
					name : "name",
					width : 180,
					type : "text",
					align : "left"
				}, {
					display : "编号",
					name : "code",
					width : 180,
					type : "text",
					align : "left"
				}, {
					display : "备注",
					name : "remark",
					width : 180,
					type : "text",
					align : "left"
				} ]
			}
		};
		grid = $("#maingrid").ligerGrid({
			columns : config.grid.columns,
			url : "${ctx}/org/position/list",
			width : '100%',
			height : '100%',
			heightDiff : -10,
			root : 'rows',
			record : 'total',
			rownumbers : true,
			alternatingRow : false,
			checkbox : false,
			autoCheckChildren : false,
			toolbar : {
				items : [ {
					id : "add",
					text : '增加',
					click : itemclick,
					icon : 'add'
				}, {
					line : true
				}, {
					id : "modify",
					text : '修改',
					click : itemclick,
					icon : 'modify'
				}, {
					line : true
				}, {
					id : "delete",
					text : '删除',
					click : itemclick,
					icon : 'delete'
				} ]
			}
		});
	});
</script>
</head>
<body style="overflow: hidden;">
	<div id="layout1">
		<div position="center">
			<div id="maingrid"></div>
		</div>
	</div>

</body>
</html>