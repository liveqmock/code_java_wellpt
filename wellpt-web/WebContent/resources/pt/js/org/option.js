$(function() {
	var bean = {
		"uuid" : null,
		"name" : null,
		"code" : null,
		"id" : null,
		"owner" : null,
		"show" : false,
		"remark" : null
	};
	$("#list").jqGrid($.extend($.common.jqGrid.settings, {
		url : ctx + '/common/jqgrid/query?queryType=option',
		colNames : [ "uuid", "名称", "ID", "编号", "默认显示", "备注" ],
		colModel : [ {
			name : "uuid",
			index : "uuid",
			width : "180",
			hidden : true,
			key : true
		}, {
			name : "name",
			index : "name",
			width : "180"
		}, {
			name : "id",
			index : "id",
			width : "180"
		}, {
			name : "code",
			index : "code",
			width : "100"
		}, {
			name : "show",
			index : "show",
			width : "180",
			formatter : function(cellvalue, options, rowObject) {
				if (cellvalue == true) {
					return "是";
				} else if (cellvalue == false) {
					return "否";
				}
				return cellvalue;
			}
		}, {
			name : "remark",
			index : "remark",
			width : "300",
			hidden : true
		} ],// 行选择事件
		onSelectRow : function(id) {
			var rowData = $(this).getRowData(id);
			// toForm(rowData, $("#option_form"));
			getOption(rowData.uuid);
		}
	}));

	// 根据UUID获取组织选择项
	function getOption(uuid) {
		var option = {};
		option.uuid = uuid;
		JDS.call({
			service : "optionService.getBean",
			data : option.uuid,
			success : function(result) {
				bean = result.data;
				$("#btn_del").show();
				$("#option_form").json2form(bean);
			}
		});
	}

	// JQuery layout布局变化时，更新jqGrid高度与宽度
	function resizeJqGrid(position, pane, paneState) {
		if (grid = $('.ui-jqgrid-btable:visible')) {
			grid.each(function(index) {
				var gridId = $(this).attr('id');
				$('#' + gridId).setGridWidth(pane.width() - 2);
				$('#' + gridId).setGridHeight(pane.height() - 44);
				// 检测是否是IE浏览器
				if (Browser.isIE()) {
					$('#' + gridId).setGridWidth(pane.width() - 2);
					$('#' + gridId).setGridHeight(pane.height() - 84);
				} else if (Browser.isChrome()) {// 检测是否是chrome浏览器
					$('#' + gridId).setGridWidth(pane.width() - 10);
					$('#' + gridId).setGridHeight(pane.height() - 94);
				} else if (Browser.isMozila()) {// 检测是否是Firefox浏览器
					$('#' + gridId).setGridWidth(pane.width() - 2);
					$('#' + gridId).setGridHeight(pane.height() - 84);
				} else {
					$('#' + gridId).setGridWidth(pane.width());
					$('#' + gridId).setGridHeight(pane.height());
				}
			});
		}
	}
	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$(".tabs").tabs();
	// JQuery layout布局
	// $('#container').layout({
	// center : {
	// closable : false,
	// resizable : false,
	// slidable : false,
	// onresize : resizeJqGrid,
	// minSize : 500,
	// triggerEventsOnLoad : true
	// },
	// east : {
	// fxName : "slide",
	// minSize : 600,
	// maxSize : 600
	// }
	// });

	// 新增操作
	$("#btn_add").click(function() {
		$("#option_form").clearForm(true);

		$("#btn_del").hide();
	});

	// 保存用户信息
	$("#btn_save").click(function() {
		$("#option_form").form2json(bean);
		JDS.call({
			service : "optionService.saveBean",
			data : bean,
			success : function(result) {
				alert("保存成功!");
				// 删除成功刷新列表
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
		var name = bean.name;
		if (confirm("确定要删除组织选择项[" + name + "]吗？")) {
			JDS.call({
				service : "optionService.remove",
				data : bean.uuid,
				success : function(result) {
					alert("删除成功!");
					$("#option_form").clearForm(true);
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
		if (confirm("确定要删除所选记录吗?")) {
			JDS.call({
				service : "optionService.removeAll",
				data : [ rowids ],
				success : function(result) {
					alert("删除成功!");
					// 删除成功刷新列表
					$("#list").trigger("reloadGrid");
				}
			});
		}
	});
	
	// 使用人包含用户、部门、群组
	$("#ownerName").click(function() {
		$.unit.open({
			title : "选择人员",
			labelField : "ownerName",
			valueField : "owner",
			afterSelect : afterSelectOwner
		});
	});
	
	// 将返回的群组人员按用户在前，部门在后进行重组
	function afterSelectOwner(returnValue){
		if (returnValue.id == null || $.trim(returnValue.id) == "") {
			$("#owner").val("");
			$("#ownerName").val("");
			return;
		}
		var userIds = [];
		var userNames = [];
		var deptIds = [];
		var deptNames = [];
		var groupIds = [];
		var groupNames = [];
		var ids = returnValue.id.split(";");
		var names = returnValue.name.split(";");
		for ( var index = 0; index < ids.length; index++) {
			var type = ids[index].substring(0, 1);// U、D
			if (type == "U") {
				userIds.push(ids[index]);
				userNames.push(names[index]);
			} else if (type == "D") {
				deptIds.push(ids[index]);
				deptNames.push(names[index]);
			} else if (type == "G") {
				groupIds.push(ids[index]);
				groupNames.push(names[index]);
			}
		}
		var memberIds = userIds.join(";");
		var memberNames = userNames.join(";");
		if (deptIds.length != 0) {
			if (userIds != 0) {
				memberIds += ";";
				memberNames += ";";
			}
			memberIds += deptIds.join(";");
			memberNames += deptNames.join(";");
		}
		if (groupIds.length != 0) {
			memberIds += ";";
			memberNames += ";";
			memberIds += groupIds.join(";");
			memberNames += groupNames.join(";");
		}
		$("#owner").val(memberIds);
		$("#ownerName").val(memberNames);
	}

	// 列表查询
	$("#query_option").keypress(function(e) {
		if (e.keyCode == 13) {
			$("#btn_query").trigger("click");
		}
	});
	$("#btn_query").click(function(e) {
		var queryValue = $("#query_option").val();
		var postData = {
			"queryPrefix" : "query",
			"queryOr" : true,
			"query_LIKES_name_OR_code_OR_id" : queryValue
		};
		if (queryValue == "是") {
			postData["query_EQB_show"] = true;
		} else if (queryValue == "否") {
			postData["query_EQB_show"] = false;
		}
		$("#list").jqGrid("setGridParam", {
			postData : null
		});
		$("#list").jqGrid("setGridParam", {
			postData : postData,
			page : 1
		}).trigger("reloadGrid");
	});

	// 界面布局
	Layout.layout();
});