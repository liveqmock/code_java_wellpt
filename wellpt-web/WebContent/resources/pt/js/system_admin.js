// TOGGLER CUSTOMIZATION SETTINGS
var Layout = {};
Layout.layout = function(option) {
	var westDefaultSize = 550;
	var updateWestPaneSize = true;
	var toggleButtons = '<div class="btnToggler"></div>'
			+ '<div class="btnReset"></div>' + '<div class="btnExpand"></div>';
	var options = $.extend(true, {
		resizerDragOpacity : 1,
		resizerDblClickToggle : false,
		resizeWhileDragging : true,
		onresize : resizeJqGrid,
		fxName : "slide",
		west__size : westDefaultSize,
		spacing_open : 8,
		spacing_closed : 8,
		west__togglerLength_closed : 75,
		west__togglerLength_open : 75,
		west__togglerContent_closed : toggleButtons,
		west__togglerContent_open : toggleButtons,
		layout_selector : "body"
	}, option);
	westDefaultSize = options.west__size;
	// CREATE THE LAYOUT
	var layout = $(options.layout_selector).layout(options);

	// SET OBJECT POINTERS FOR CONVENIENCE
	var $westToggler = layout.togglers.west;

	// UN-BIND DEFAULT TOGGLER FUNCTIONALITY
	$westToggler.unbind("click");
	$(".ui-layout-resizer").unbind("click");
	$(".ui-layout-toggler").unbind("click");
	$westToggler.unbind("dbclick");
	$(".ui-layout-resizer").unbind("dbclick");
	$(".ui-layout-toggler").unbind("dbclick");

	// BIND CUSTOM WEST METHODS
	$westToggler.find(".btnToggler").one("click", toggleWest);
	$westToggler.find(".btnReset").click(resetWest);
	$westToggler.find(".btnExpand").one("click", expandWest);

	// 初始化隐藏
	// $westToggler.find(".btnReset").hide();

	// ADD TOOLTIPS TO CUSTOM BUTTONS
	$(".btnToggler").attr("title", "向左");
	$(".btnReset").attr("title", "拖动");
	$(".btnExpand").attr("title", "向右");

	// CUSTOM WEST METHODS
	function toggleWest(evt) {
		layout.togglers.west.find(".btnExpand").unbind("click", expandWest);
		layout.togglers.west.find(".btnExpand").one("click", resetWest);

		layout.togglers.west.find(".btnToggler").hide();
		layout.togglers.west.find(".btnReset").hide();
		layout.togglers.west.find(".btnExpand").show();

		layout.close("west");
		evt.stopPropagation();
	}
	function expandWest(evt) {
		updateWestPaneSize = false;
		layout.togglers.west.find(".btnToggler").unbind("click", toggleWest);
		layout.togglers.west.find(".btnToggler").one("click", resetWest);

		layout.togglers.west.find(".btnToggler").show();
		layout.togglers.west.find(".btnReset").hide();
		layout.togglers.west.find(".btnExpand").hide();

		layout.sizePane("west", "100%");
		layout.open("west");
		evt.stopPropagation();
	}
	function resetWest(evt) {
		updateWestPaneSize = true;
		layout.togglers.west.find(".btnToggler").unbind("click", toggleWest);
		layout.togglers.west.find(".btnExpand").unbind("click", expandWest);
		layout.togglers.west.find(".btnToggler").one("click", toggleWest);
		layout.togglers.west.find(".btnExpand").one("click", expandWest);

		layout.togglers.west.find(".btnToggler").show();
		layout.togglers.west.find(".btnReset").show();
		layout.togglers.west.find(".btnExpand").show();

		sizePane("west", westDefaultSize);
	}

	// GENERIC HELPER FUNCTION
	function sizePane(pane, size) {
		layout.sizePane(pane, size);
		layout.open(pane); // open pane if not already
	}
	window.sizePane = sizePane;

	// JQuery layout布局变化时，更新jqGrid高度与宽度
	function resizeJqGrid(position, pane, paneState) {
		// 容器直接带jqGrid列表的panel直接隐藏滚动条
		$(".ui-layout-pane>div>.ui-jqgrid").each(function() {
			$(this).parent().parent().css("overflow", "hidden");
		});
		$(".ui-layout-resizer").unbind("click");
		$(".ui-layout-toggler").unbind("click");
		if ("west" === position && updateWestPaneSize) {
			westDefaultSize = pane.width() + 2;
		}
		if ("center" === position && options.center_onresize != undefined) {
			options.center_onresize.call(this, position, pane, paneState);
			return;
		}
		if (paneState.isClosed == undefined || paneState.isClosed != false) {
			return;
		}
		if (grid = $('.ui-jqgrid-btable:visible')) {
			grid.each(function(index) {
				// 如果找到中间区域列表且处理事件不为空直接返回，不再处理
				if (this.id === options.center_list_id
						&& options.center_onresize) {
					return;
				}
				var paneWidth = pane.width() - 22;
				var paneHeight = pane.height() - 104;
				$(this).setGridWidth(paneWidth);
				$(this).setGridHeight(paneHeight);
			});
		}
	}

	sizePane("west", westDefaultSize);
};
// 后台管理公共区
(function($) {
	$.common = $.common || {};
	$.common.jqGrid = $.common.jqGrid || {};
	$.common.jqGrid.settings = {
		mtype : 'POST',
		datatype : "json",
		rowNum : 20,
		rownumbers : true,
		rowList : [ 10, 20, 50, 100, 200 ],
		rowId : "uuid",
		pager : "#pager",
		recordpos : "right",
		viewrecords : true,
		sortable : true,
		sortname : "code",
		sortorder : "asc",
		multiselect : true,
		multiboxonly : true,
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
			if (data.dataList.length > 0) {
				$(this).setSelection($(this).getDataIDs()[0]);
			}
		}
	};
})(jQuery);