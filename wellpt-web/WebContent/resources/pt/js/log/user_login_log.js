$(function() {
	var bean = {
		"uuid" : null,
		"id" : null,
		"loginName" : null,
		"userName" : null,
		"employeeNumber" : null,
		"departmentName" : null,
		"loginTime" : null,
		"loginIp" : null
	};

	$("#list").jqGrid(
			$.extend($.common.jqGrid.settings, {
				url : ctx + '/passport/user/loginlog/list',
				colNames : [ "uuid", "登录名", "姓名", "部门"
						, "登录时间", "登录IP" ],
				colModel : [ {
					name : "uuid",
					index : "uuid",
					width : "180",
					hidden : true
				}, {
					name : "loginName",
					index : "loginName",
					width : "180"
				}, {
					name : "userName",
					index : "userName",
					width : "180"
				}, {
					name : "departmentName",
					index : "departmentName",
					width : "180"
				}, {
					name : "loginTime",
					index : "loginTime",
					width : "180"
				}, {
					name : "loginIp",
					index : "loginIp",
					width : "180"
				}],
				sortname : "loginTime",
				sortorder : "desc",
			}));
	

	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	$("#btn_upload").button();
	// JQuery UI页签
	$(".tabs")
			.tabs(
					{
						activate : function(event, ui) {
							if ((ui.newPanel.attr("id") == "tabs-4")) {
								// 用户角色嵌套树tab激活时才加载
								if (bean.uuid != null && bean.uuid != "") {
									loadUserRoleNestedRoleTree(bean.uuid);
								} else {
									var ztree = $.fn.zTree
											.getZTreeObj($("user_role_nested_role_tree"));
									if (ztree != null) {
										ztree.destroy();
									}
								}
							}
						}
					});

	// 删除操作
	$("#btn_del").click(function() {
		// var rowids = $("#list").jqGrid('getGridParam', 'selarrrow');
		if ($(this).attr("id") == "btn_del") {
			var id = $("#list").jqGrid('getGridParam', 'selrow');
			if (id == "" || id == null) {
				alert("请选择记录！");
				return true;
			}
			// 设置要删除的id
			bean.id = id;
		} else {
			if (bean.id == "" || bean.id == null) {
				alert("请选择记录！");
				return true;
			}
		}
		var userName = $("#list").getRowData(bean.id)["userName"];
		if (confirm("确定要删除用户[" + userName + "]吗？")) {
			JDS.call({
				service : "userService.deleteById",
				data : [ bean.id ],
				success : function(result) {
					alert("删除成功!");
					// 删除成功刷新列表
					$("#list").trigger("reloadGrid");
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
		var uuids = new Array(); 
		for (var i=0;i<rowids.length;i++) {
			var rowDatas = $("#list").jqGrid('getRowData', rowids[i]);
			uuids.push(rowDatas["uuid"]);
		}
		if (confirm("确定要删除所选记录吗?")) {
			JDS.call({
				service : "userLoginLogService.deleteByUuids",
				data : [ uuids ],
				success : function(result) {
					alert("删除成功!");
					// 删除成功刷新列表
					$("#list").trigger("reloadGrid");
				}
			});
		}
	});


	// 列表查询
	$("#query_user").keypress(function(e) {
		if (e.keyCode == 13) {
			$("#btn_query").trigger("click");
		}
	});
	$("#btn_query").click(function(e) {
		var userName = $("#query_user").val();
		var postData = {"userName":userName};
		$("#list").jqGrid("setGridParam", {
			postData : postData,
			page : 1
		}).trigger("reloadGrid");	
	});
	
	// JQuery layout布局变化时，更新jqGrid高度与宽度
	function resizeJqGrid(position, pane, paneState) {
		if (grid = $('.ui-jqgrid-btable:visible')) {
			grid.each(function(index) {
				var paneWidth = pane.width();
				var paneHeight = pane.height() - 25;
				if (Browser.isIE()) {// 检测是否是IE浏览器
					$(this).setGridWidth(paneWidth - 22);
					$(this).setGridHeight(paneHeight - 84);
				} else if (Browser.isChrome()) {// 检测是否是chrome浏览器
					$(this).setGridWidth(paneWidth - 30);
					$(this).setGridHeight(paneHeight - 114);
				} else if (Browser.isMozila()) {// 检测是否是Firefox浏览器
					$(this).setGridWidth(paneWidth - 22);
					$(this).setGridHeight(paneHeight - 84);
				} else {
					$(this).setGridWidth(paneWidth);
					$(this).setGridHeight(pane.height());
				}
			});
		}
	}
	// JQuery layout布局
	$('#container').layout({
		center : {
			closable : false,
			resizable : false,
			slidable : false,
			onresize : resizeJqGrid,
			minSize : 500,
			triggerEventsOnLoad : true
		}
	});
});