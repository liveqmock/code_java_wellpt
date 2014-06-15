$(function() {
	$("#list").jqGrid($.extend($.common.jqGrid.settings, {
		url : ctx + '/common/jqgrid/query?queryType=userOperationLog',
		colNames : [ "uuid", "模块", "模块ID", "内容", "操作", "用户名", "时间", "明细" ],
		colModel : [ {
			name : "uuid",
			index : "uuid",
			width : "100",
			hidden : true,
		}, {
			name : "moduleName",
			index : "moduleName",
			width : "100"
		}, {
			name : "moduleId",
			index : "moduleId",
			width : "100"
		}, {
			name : "content",
			index : "content",
			width : "100"
		}, {
			name : "operation",
			index : "operation",
			width : "100"
		}, {
			name : "creatorName",
			index : "creatorName",
			width : "100"
		}, {
			name : "createTime",
			index : "createTime",
			width : "150"
		}, {
			name : "details",
			index : "details",
			width : "500"
		} ],
		sortname : "createTime",
		sortorder : "desc",
	}));

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
	// 列表查询
	$("#query_log").keypress(function(e) {
		if (e.keyCode == 13) {
			$("#btn_query").trigger("click");
		}
	});
	$("#btn_query").button().click(function(e) {
		var queryValue = $("#query_log").val();
		var postData = {
			"queryPrefix" : "query",
			"queryOr" : true,
			"query_LIKES_moduleName_OR_moduleId_OR_content" : queryValue,
			"query_LIKES_operation_OR_creatorName_OR_details" : queryValue
		};
		$("#list").jqGrid("setGridParam", {
			postData : null
		});
		$("#list").jqGrid("setGridParam", {
			postData : postData,
			page : 1
		}).trigger("reloadGrid");
	});
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