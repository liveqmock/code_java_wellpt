$(function() {
	$("#list").jqGrid($.extend($.common.jqGrid.settings, {
		url : ctx + '/common/jqgrid/query?queryType=appModule',
		colNames : [ "uuid", "名称", "ID", "编号", "描述" ],
		colModel : [ {
			name : "uuid",
			index : "uuid",
			width : "100",
			hidden : true,
		}, {
			name : "name",
			index : "name",
			width : "100"
		}, {
			name : "id",
			index : "id",
			width : "100"
		}, {
			name : "code",
			index : "code",
			width : "60"
		}, {
			name : "remark",
			index : "remark",
			width : "500"
		} ]
	}));

	// JQuery layout布局变化时，更新jqGrid高度与宽度
	function resizeJqGrid(position, pane, paneState) {
		if (grid = $('.ui-jqgrid-btable:visible')) {
			grid.each(function(index) {
				var paneWidth = pane.width();
				var paneHeight = pane.height();
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