$(function() {
	$("#list").jqGrid(
	    $.extend($.common.jqGrid.settings, {
			url : ctx + '/common/jqgrid/query?queryType=shortMessage',
			datatype : 'json',
			mtype : "POST",
			colNames : [ "uuid", "发送人", "发送内容", "发送时间", "接收人", "接收的手机号码", "发送状态", "回执描述" ],
			colModel : [ {
				name : "uuid",
				index : "uuid",
				width : "100",
				hidden : true,
				key : true
			}, {
				name : "senderName",
				index : "senderName",
				width : "100"
			}, {
				name : "body",
				index : "body",
				width : "400"
			}, {
				name : "sendTime",
				index : "sendTime",
				width : "150"
			}, {
				name : "recipientName",
				index : "recipientName",
				width : "100"
			}, {
				name : "recipientMobilePhone",
				index : "recipientMobilePhone",
				width : "100"
			}, {
				name : "sendStatus",
				index : "sendStatus",
				width : "150",
				formatter : function(cellvalue, options, rowObject) {
					if (cellvalue === 0) {
						return '已发送待回执';
					} else if(cellvalue === 1){
						return '发送成功';
					} else if(cellvalue === 3){
						return '重发成功';
					} else {
						return '发送失败';
					}
					return cellvalue;
				}
			}, {
				name : "recMsg",
				index : "recMsg",
				width : "150"
			} ],
			
			rowNum : 20,
			rownumbers : true,
			rowList : [ 10, 20, 50, 100, 200 ],
			rowId : "uuid",
			pager : "#pager",
			sortname : "sendTime",
			recordpos : "right",
			viewrecords : true,
			sortable : true,
			sortorder : "desc",
			multiselect : true,
			autowidth : true,
			height : 450,
			scrollOffset : 0,
			jsonReader : {
				root : "dataList",
				total : "totalPages",
				page : "currentPage",
				records : "totalRows",
				repeatitems : false
			},
			
			loadComplete : function(data) {
				$("#list").setSelection($("#list").getDataIDs()[0]);
			}
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
	$("#btn_query").click(function() {
		var queryValue = $("#query_log").val();
		$("#list").jqGrid("setGridParam", {
			postData : {
				"queryPrefix" : "query",
				"queryOr" : true,
				"query_LIKES_senderName_OR_body_OR_recipientName_OR_recipientMobilePhone_OR_recMsg" : queryValue
			},
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