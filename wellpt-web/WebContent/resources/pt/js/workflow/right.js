//加载全局国际化资源
I18nLoader.load("/resources/pt/js/global");
$(function() {
	var bean = {
		"uuid" : null,
		"name" : null,
		"code" : null,
		"value" : null,
		"isDefault" : null
	};
	$("#list").jqGrid({
		url : ctx + '/common/jqgrid/query?queryType=right',
		mtype : 'POST',
		datatype : "json",
		colNames : [ "uuid", "名称", "编号", "控制值", "是否默认值" ],
		colModel : [ {
			name : "uuid",
			index : "uuid",
			width : "180",
			hidden : true,
		}, {
			name : "name",
			index : "name",
			width : "180"
		}, {
			name : "code",
			index : "code",
			width : "180"
		}, {
			name : "value",
			index : "value",
			width : "180"
		}, {
			name : "isDefault",
			index : "isDefault",
			width : "180",
			formatter : function(cellvalue, options, rowObject) {
				if (cellvalue == true) {
					return "是";
				} else if (cellvalue == false) {
					return "否";
				}
				return cellvalue;
			},
			unformat : function(cellvalue, options, cell) {
				if (cellvalue == "是") {
					return true;
				} else if (cellvalue == "否") {
					return false;
				}
				return cellvalue;
			}
		} ],
		rowNum : 20,
		rownumbers : true,
		rowList : [ 10, 20, 50, 100, 200 ],
		rowId : "uuid",
		pager : "#pager",
		sortname : "code",
		recordpos : "right",
		viewrecords : true,
		sortable : true,
		sortorder : "asc",
		multiselect : false,
		autowidth : true,
		height : 450,
		jsonReader : {
			root : "dataList",
			total : "totalPages",
			page : "currentPage",
			records : "totalRows",
			repeatitems : false
		},// 行选择事件
		onSelectRow : function(id) {
			var rowData = $(this).getRowData(id);
			$("#right_form").json2form(rowData);
			$("#right_form").form2json(bean);
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
					$('#' + gridId).setGridHeight(pane.height() - 44);
				} else if (Browser.isChrome()) {
					$('#' + gridId).setGridWidth(pane.width() - 10);
					$('#' + gridId).setGridHeight(pane.height() - 54);
				} else if (Browser.isMozila()) {
					$('#' + gridId).setGridWidth(pane.width() - 2);
					$('#' + gridId).setGridHeight(pane.height() - 44);
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
		},
		east : {
			fxName : "slide",
			minSize : 600,
			maxSize : 600
		}
	});

	// 新增操作
	$("#btn_add").click(function() {
		$("#right_form").clearForm(true);
	});

	// 保存流程权限
	$("#btn_save").click(function() {
		$("#right_form").form2json(bean);
		JDS.call({
			service : "flowRightService.save",
			data : [ bean ],
			success : function(result) {
				// toForm(bean, $("#right_form"));
				$("#right_form").json2form(bean);

				$("#list").trigger("reloadGrid");
			}
		});
	});

	// 删除操作
	$("#btn_del").click(function() {
		if (bean.uuid == null || bean.uuid == "") {
			alert("请选择记录！");
			return true;
		}
		var name = bean.name;
		if (confirm("确定要删除流程权限[" + name + "]吗？")) {
			JDS.call({
				service : "flowRightService.removeByPk",
				data : [ bean.uuid ],
				success : function(result) {
					// 删除成功刷新列表
					$("#list").trigger("reloadGrid");
				}
			});
		}
	});

});