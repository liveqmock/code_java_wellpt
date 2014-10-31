//加载全局国际化资源
I18nLoader.load("/resources/pt/js/global");
$(function() {
	var bean = {
		"previousAssignee" : null,
		"dueTime" : null,
		"title" : null,
		"taskUuid" : null,
		"flowInstUuid" : null,
		"readFlag" : null,
		"arrivalTime" : null,
		"taskName" : null,
		"flowName" : null
	};
	$("#list").jqGrid(
			{
				url : ctx + '/common/jqgrid/query?serviceName=workTodoService',
				mtype : 'POST',
				datatype : "json",
				colNames : [ "taskUuid", "flowInstUuid", "readFlag", "标题", "当前环节", "前办理人", "到达时间", "到期时间",
						"流程" ],
				colModel : [ {
					name : "taskUuid",
					index : "taskUuid",
					width : "180",
					hidden : true,
				}, {
					name : "flowInstUuid",
					index : "flowInstUuid",
					width : "180",
					hidden : true,
				}, {
					name : "readFlag",
					index : "readFlag",
					width : "180",
					hidden : true,
				}, {
					name : "title",
					index : "title",
					width : "280"
				}, {
					name : "taskName",
					index : "taskName",
					width : "180"
				}, {
					name : "previousAssignee",
					index : "previousAssignee",
					width : "180"
				}, {
					name : "arrivalTime",
					index : "arrivalTime",
					width : "180"
				}, {
					name : "dueTime",
					index : "dueTime",
					width : "180"
				}, {
					name : "flowName",
					index : "flowName",
					width : "180"
				} ],
				rowNum : 20,
				rownumbers : true,
				rowList : [ 10, 20, 50, 100, 200 ],
				rowId : "taskUuid",
				pager : "#pager",
				sortname : "arrivalTime",
				recordpos : "right",
				viewrecords : true,
				sortable : true,
				sortorder : "desc",
				multiselect : false,
				autowidth : true,
				height : 450,
				scrollOffset : 0,
				jsonReader : {
					root : "dataList",
					total : "totalPages",
					page : "currentPage",
					records : "totalRows",
					repeatitems : false
				},// 行选择事件
				onSelectRow : function(id) {
					var rowData = $(this).getRowData(id);
					bean.taskUuid = rowData.taskUuid;
					bean.flowInstUuid = rowData.flowInstUuid;
					bean.title = rowData.title;
					bean.taskName = rowData.taskName;
					bean.previousAssignee = rowData.previousAssignee;
					bean.arrivalTime = rowData.arrivalTime;
					bean.dueTime = rowData.dueTime;
					bean.flowName = rowData.flowName;
				},
				ondblClickRow : function(id) {
					var rowData = $(this).getRowData(id);
					if (rowData.taskUuid != null && rowData.taskUuid != "") {
						window.open(ctx + "/workflow/work/view/todo?taskUuid=" + rowData["taskUuid"]
								+ "&flowInstUuid=" + rowData["flowInstUuid"], "_blank");
					}
				},
				afterInsertRow : function(rowid, rowData) {
					if (rowData["readFlag"] == false) {
						$(this).jqGrid("setRowData", rowid, false, "unread");
					}
				},
				loadComplete : function(data) {
					$("#list").setSelection($("#list").getDataIDs()[0]);
				}
			});

	// JQuery layout布局变化时，更新jqGrid高度与宽度
	function resizeJqGrid(position, pane, paneState) {
		if (grid = $('.ui-jqgrid-btable:visible')) {
			grid.each(function(index) {
				var gridId = $(this).attr('id');
				$('#' + gridId).setGridWidth(pane.width() - 2);
				$('#' + gridId).setGridHeight(pane.height() - 44);
				if (Browser.isIE()) {
					$('#' + gridId).setGridWidth(pane.width() - 2);
					$('#' + gridId).setGridHeight(pane.height() - 89);
				} else if (Browser.isChrome()) {
					$('#' + gridId).setGridWidth(pane.width() - 10);
					$('#' + gridId).setGridHeight(pane.height() - 99);
				} else if (Browser.isMozila()) {
					$('#' + gridId).setGridWidth(pane.width() - 2);
					$('#' + gridId).setGridHeight(pane.height() - 89);
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
	$("#tabs").tabs();
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
	
	// 列表查询
	$("#query_task").keypress(function(e) {
		if (e.keyCode == 13) {
			$("#btn_query").trigger("click");
		}
	});
	$("#btn_query").click(function(e) {
		var queryValue = $("#query_task").val();
		var postData = {
			"queryPrefix" : "query",
			"queryOr" : true,
			"query_LIKES_title_OR_taskName" : queryValue
		};
		$("#list").jqGrid("setGridParam", {
			postData : null
		});
		$("#list").jqGrid("setGridParam", {
			postData : postData,
			page : 1
		}).trigger("reloadGrid");
	});

	// 查看待办工作
	$("#btn_todo_view").click(
			function() {
				if (bean.taskUuid == null || bean.taskUuid == "") {
					alert("请选择工作！");
					return true;
				}
				window.open(ctx + "/workflow/work/view/todo?taskUuid=" + bean["taskUuid"] + "&flowInstUuid="
						+ bean["flowInstUuid"], "_blank");
			});

});