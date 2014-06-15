$(function() {
	
	var bean = {
			"stockId":null,
			"stockName":null,
			"stockNumber":null,
			"stockType":null,
			"fromDytable":null,
			"fromDytableId":null,
			"stockCircle":null,
			"balance":null,
			"toplimit":null,
			"lowlimit":null,
	};
	
	// 检测是否是IE浏览器
	function isIE() {
		var _uaMatch = $.uaMatch(navigator.userAgent);
		var _browser = _uaMatch.browser;
		if (_browser == 'msie') {
			return true;
		} else {
			return false;
		}
	}
	// 检测是否是chrome浏览器
	function isChrome() {
		var _uaMatch = $.uaMatch(navigator.userAgent);
		var _browser = _uaMatch.browser;
		if (_browser == 'chrome' || _browser == 'webkit') {
			return true;
		} else {
			return false;
		}
	}
	// 检测是否是Firefox浏览器
	function isMozila() {
		var _uaMatch = $.uaMatch(navigator.userAgent);
		var _browser = _uaMatch.browser;
		if (_browser == 'mozilla') {
			return true;
		} else {
			return false;
		}
	}
	
	$("#list").jqGrid (
			$.extend($.common.jqGrid.settings, {
				url:ctx + '/common/jqgrid/query?queryType=stock',
				mtype:"POST",
				datatype:"json",
				colNames:["id","库存名称","编号","库存类型","来自的表","表uuid"],
				colModel:[{
					name:"id",
					index:"id",
					hidden:true,
				},{
					name:"stockName",
					index:"stockName",
					width:"50",
				},{
					name:"stockNumber",
					index:"stockNumber",
					width:"30",
				},{
					name:"stockType",
					index:"stockType",
					width:"30",
				},{
					name:"fromDytable",
					index:"fromDytable",
					width:"30",
				},
				{
					name:"formuuid",
					index:"formuuid",
					hidden:true,
				},
				],
				rowNum : 20,
				rownumbers : true,
				rowList : [ 10, 20, 50, 100, 200 ],
				rowId : "uuid",
				pager : "#pager",
				sortname : "stockNumber",
				viewrecords : true,
				sortable : true,
				sortorder : "asc",
				multiselect : true,
				autowidth : true,
				height : 600,
				scrollOffset : 0,
				jsonReader : {
					root : "dataList",
					total : "totalPages",
					page : "currentPage",
					records : "totalRows",
					repeatitems : false
				},
				// 行选择事件
				onSelectRow : function(id) {
					$("#selectCond").jqGrid("clearGridData");
					getStockById(id);
				},
//				loadComplete:function(data) {
//					$("#list").setSelection($("#list").getDataIDs()[0]);
//				}
			}	
		));
	
	function getStockById(id) {
		JDS.call({
			service:"stockService.getStockById",
			data:[id],
			success:function(result) {
				
			}
		});
	}
	
	//保存操作
	$("#btn_save").click(function(){
		$("#column_form").form2json(bean);
		JDS.call({
			service:"stockService.save",
			data:[bean],
			success:function(result) {
				alert("保存成功！");
				$("#list").trigger("reloadGrid");
			},
			error:function(result) {
				alert("保存失败！");
			}
		});
	});
	
	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$(".tabs").tabs();
	
	// JQuery layout布局变化时，更新jqGrid宽度
	function resizeJqGrid(position, pane, paneState) {
		if (grid = $('.ui-jqgrid-btable[id=column_list],.ui-jqgrid-btable[id=selectCond]')) {
			grid.each(function(index) {
				var tabmargin = 60;
				if (Browser.isIE()) {
					$(this).setGridWidth(pane.width() - tabmargin);
				} else if (Browser.isChrome()) {
					$(this).setGridWidth(pane.width() - tabmargin);
				} else if (Browser.isMozila()) {
					$(this).setGridWidth(pane.width() - tabmargin);
				} else {
					$(this).setGridWidth(pane.width() - tabmargin);
				}
			});
		}
	}
	//页面布局
	Layout.layout({
		west__size :420,
		center_onresize : resizeJqGrid
	});
});