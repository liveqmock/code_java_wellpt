$(function() {
	var bean = {
		"uuid" : null,
		"type" : null,
		"name" : null,
		"code" : null,
		"isWorkday" : null,
		"fromDate" : null,
		"toDate" : null,
		"fromTime1" : null,
		"toTime1" : null,
		"fromTime2" : null,
		"toTime2" : null,
		"remark" : null,
		"changedFixedHolidays" : [],
		"changedSpecialHolidays" : [],
		"changedMakeups" : [],
		"deletedFixedHolidays" : [],
		"deletedSpecialHolidays" : [],
		"deletedMakeups" : []
	};
	
	// 收集改变的固定节假日、特殊節假日、補班日期
	function collectFixedAndSpecialAndMakeup(bean) {
		//固定节假日
		var changes1 = $("#fixed_holidays_list").getChangedCells('all');
		bean["changedFixedHolidays"] = changes1;
		//特殊节假日
		var changes2 = $("#special_holidays_list").getChangedCells('all');
		bean["changedSpecialHolidays"] = changes2;
		//补班日期
		var changes3 = $("#make_up_list").getChangedCells('all');
		bean["changedMakeups"] = changes3;
		
	}

	/*// JQuery layout布局变化时，更新jqGrid高度与宽度
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
	}*/
	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$(".tabs").tabs();
	/*// JQuery layout布局
	$('#container').layout({
		center : {
			fxName : "slide",
			size:500,
			minSize : 500,
			maxSize : 500
		},
	});*/

	// 新增操作（固定节假日）
	$("#fixed_holidays_btn_add").click(function() {
		var records = $("#fixed_holidays_list").getGridParam('records');
//		alert("固定节假日记录数为："+records);
		$("#fixed_holidays_list").jqGrid("addRowData",(records+1),{"uuid":"","名称":"","开始日期(月-日)":"","结束日期(月-日)":"","备注":""},"");
	});
	
	// 新增操作（特殊节假日）
	$("#special_holidays_btn_add").click(function() {
		var records = $("#special_holidays_list").getGridParam('records');
//		alert("特殊节假日记录数为："+records);
		$("#special_holidays_list").jqGrid("addRowData",(records+1),{"uuid":"","名称":"","开始日期":"","结束日期":"","备注":""},"");
	});
	
	// 新增操作（补班日期）
	$("#make_up_btn_add").click(function() {
		   var records = $("#make_up_list").getGridParam('records');
//		      alert("补班日期记录数为："+records);
		      $("#make_up_list").jqGrid("addRowData",(records+1),{"uuid":"","名称":"","开始日期":"","结束日期":"","备注":""},"");
	});
	

	// 删除操作（固定节假日）
	$("#fixed_holidays_btn_del").click(function() {
		var rowids = $("#fixed_holidays_list").jqGrid('getGridParam', 'selarrrow');
		if (rowids.length == 0) {
			alert("请选择记录!");
			return;
		}
		for ( var i = (rowids.length - 1); i >= 0; i--) {
			var rowData = $("#fixed_holidays_list").getRowData(rowids[i]);
			if (rowData.uuid != null && $.trim(rowData.uuid) != "") {
				bean["deletedFixedHolidays"].push(rowData);
			}
			$("#fixed_holidays_list").jqGrid('delRowData', rowids[i]);
		}
	});
	// 删除操作（特殊节假日）
	$("#special_holidays_btn_del").click(function() {
		var rowids = $("#special_holidays_list").jqGrid('getGridParam', 'selarrrow');
		if (rowids.length == 0) {
			alert("请选择记录!");
			return;
		}
		for ( var i = (rowids.length - 1); i >= 0; i--) {
			var rowData = $("#special_holidays_list").getRowData(rowids[i]);
			if (rowData.uuid != null && $.trim(rowData.uuid) != "") {
				bean["deletedSpecialHolidays"].push(rowData);
			}
			$("#special_holidays_list").jqGrid('delRowData', rowids[i]);
		}
	});
	// 删除操作（补班日期）
	$("#make_up_btn_del").click(function() {
		var rowids = $("#make_up_list").jqGrid('getGridParam', 'selarrrow');
		if (rowids.length == 0) {
			alert("请选择记录!");
			return;
		}
		for ( var i = (rowids.length - 1); i >= 0; i--) {
			var rowData = $("#make_up_list").getRowData(rowids[i]);
			if (rowData.uuid != null && $.trim(rowData.uuid) != "") {
				bean["deletedMakeups"].push(rowData);
			}
			$("#make_up_list").jqGrid('delRowData', rowids[i]);
		}
	});
	
	
	// 保存群组信息
	$("#btn_save").click(function() {
		
		// 表单数据收集到对象中
		$("#work_hour_form").form2json(bean);
		// 收集改变的固定节假日、特殊节假日、补班日期
		collectFixedAndSpecialAndMakeup(bean);
		
		JDS.call({
			service : "workHourService.saveBean",
			data : [ bean ],
			success : function(result) {
				//保存成功刷新列表
				$("#fixed_holidays_list").trigger("reloadGrid");
				$("#special_holidays_list").trigger("reloadGrid");
				$("#make_up_list").trigger("reloadGrid");
				alert("保存成功！");
			}
		});
		
		//工作日
		var fromTime1 = $("#fromTime1").val();
		var toTime1 = $("#toTime1").val();
		var fromTime2 = $("#fromTime2").val();
		var toTime2 = $("#toTime2").val();
		var notCheckedArray = $("input:checkbox").not("input:checked") ;//获取未选中的checkbox
		var notCheckedValArray = new Array();  //存放未选中的checkbox的value值
		var checkedArray = $("input:checked");//获取选中的checkbox
		var checkedValArray = new Array();   //存放选中的checkbox的value值
		
		for (var i = 0; i < checkedArray.length; i++){   
			checkedValArray[i] = checkedArray[i].value;   
		}
	    for (var i = 0; i < notCheckedArray.length; i++){   
	    	notCheckedValArray[i] = notCheckedArray[i].value;   
	    }
		jQuery.ajax({ 
    		   type: "GET",
    		   url: ctx + '/basicdata/workhour/save.action',
    		   data:"fromTime1="+fromTime1+"&toTime1="+toTime1+"&fromTime2="+fromTime2+"&toTime2="+toTime2+"&checkedValArray="+checkedValArray+"&notCheckedValArray="+notCheckedValArray,   
    		   dataType: "text",
    		   success: function(msg){
    			   
    		   }, error: function(result) {
    			   
   			   }
	    });
	});

			//每年度固定节假日
			$("#fixed_holidays_list").jqGrid({
				url : ctx + '/basicdata/workhour/fixedHolidaysList.action',
				datatype : "json",
				mtype : "POST",
				colNames : [ "uuid", "名称", "开始日期(月-日)", "结束日期(月-日)" ,"备注"],
				colModel : [ {
					name : "uuid",
					index : "uuid",
					width : "130",
					hidden : true
				}, {
					name : "name",
					index : "name",
					width : "130",
					editable : true
				}, {
					name : "fromDate",
					index : "fromDate",
					width : "130",
					editable : true
				}, {
					name : "toDate",
					index : "toDate",
					width : "130",
					editable : true
				}, {
					name : "remark",
					index : "remark",
					width : "130",
					editable : true,
				} ],
				rowList : [ 10, 20, 50, 100, 200 ],
				rowId : "uuid",
				pager : "#pager",
				sortname : "name",
				recordpos : "right",
				viewrecords : true,
				sortorder : "asc",
				multiselect : false,
				autowidth : true,
				height : "auto",
				multiselect : true,
				jsonReader : {
					root : "dataList",
					total : "totalPages",
					page : "currentPage",
					records : "totalRows",
					repeatitems : false
				},
				sortable : false,
				cellEdit : true,// 表示表格可编辑
				cellsubmit : "clientArray", // 表示在本地进行修改
				afterEditCell : function(rowid, cellname, value, iRow, iCol) {
					var cellId = iRow + "_" + cellname;
					//判断当前是否为开始日期或者结束日期
					if(cellname == "fromDate" || cellname=="toDate") {
						$("#" + cellId).die().live("click", function(){
							WdatePicker({dateFmt:'MM-dd',onpicked:function(){
								$('#fixed_holidays_list').jqGrid('saveCell',iRow, iCol);;}
							});
							
						});
	        	 	}else{//保存修改的jqgrid列表
	        	 		$("#" + iRow + "_" + cellname, "#fixed_holidays_list").one('blur',
							function() {
								$('#fixed_holidays_list').saveCell(iRow, iCol);
							});
	        	 	}
				}
			});
			
			
			
			//其他特殊节假日
			$("#special_holidays_list").jqGrid({
				url : ctx + '/basicdata/workhour/specialHolidaysList.action',
				datatype : "json",
				mtype : "POST",
				colNames : [ "uuid", "名称", "开始日期", "结束日期" ,"备注"],
				colModel : [ {
					name : "uuid",
					index : "uuid",
					width : "130",
					hidden : true
				}, {
					name : "name",
					index : "name",
					width : "130",
					editable : true
				}, {
					name : "fromDate",
					index : "fromDate",
					width : "130",
					editable : true
				}, {
					name : "toDate",
					index : "toDate",
					width : "130",
					editable : true
				}, {
					name : "remark",
					index : "remark",
					width : "130",
					editable : true
				} ],
				rowList : [ 10, 20, 50, 100, 200 ],
				rowId : "uuid",
				pager : "#pager",
				sortname : "name",
				recordpos : "right",
				viewrecords : true,
				sortorder : "asc",
				multiselect : false,
				autowidth : true,
				height : "auto",
				multiselect : true,
				jsonReader : {
					root : "dataList",
					total : "totalPages",
					page : "currentPage",
					records : "totalRows",
					repeatitems : false
				},
				sortable : false,
				cellEdit : true,// 表示表格可编辑
				cellsubmit : "clientArray", // 表示在本地进行修改
				afterEditCell : function(rowid, cellname, value, iRow, iCol) {
					var cellId = iRow + "_" + cellname;
					//判断当前是否为开始日期或者结束日期
					if(cellname == "fromDate" || cellname=="toDate") {
						$("#" + cellId).die().live("click", function(){
							WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){
								$('#special_holidays_list').jqGrid('saveCell',iRow, iCol);;}
							});
							
						});
	        	 	}else{//保存修改的jqgrid列表
	        	 		$("#" + iRow + "_" + cellname, "#special_holidays_list").one('blur',
							function() {
								$('#special_holidays_list').saveCell(iRow, iCol);
							});
	        	 	}
	        	 }
			});
			
			//补班日期
			$("#make_up_list").jqGrid({
				url : ctx + '/basicdata/workhour/makeUpList.action',
				datatype : "json",
				mtype : "POST",
				colNames : [ "uuid", "名称", "开始日期", "结束日期" ,"备注"],
				colModel : [ {
					name : "uuid",
					index : "uuid",
					width : "130",
					hidden : true
				}, {
					name : "name",
					index : "name",
					width : "130",
					editable : true
				}, {
					name : "fromDate",
					index : "fromDate",
					width : "130",
					editable : true
				}, {
					name : "toDate",
					index : "toDate",
					width : "130",
					editable : true
				}, {
					name : "remark",
					index : "remark",
					width : "130",
					editable : true,
				} ],
				rowList : [ 10, 20, 50, 100, 200 ],
				rowId : "uuid",
				pager : "#pager",
				sortname : "name",
				recordpos : "right",
				viewrecords : true,
				sortorder : "asc",
				multiselect : false,
				autowidth : true,
				height : "auto",
				multiselect : true,
				jsonReader : {
					root : "dataList",
					total : "totalPages",
					page : "currentPage",
					records : "totalRows",
					repeatitems : false
				},
				sortable : false,
				cellEdit : true,// 表示表格可编辑
				cellsubmit : "clientArray", // 表示在本地进行修改
				afterEditCell : function(rowid, cellname, value, iRow, iCol) {
					var cellId = iRow + "_" + cellname;
					//判断当前是否为开始日期或者结束日期
					if(cellname == "fromDate" || cellname=="toDate") {
						$("#" + cellId).die().live("click", function(){
							WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){
								$('#make_up_list').jqGrid('saveCell',iRow, iCol);;}
							});
							
						});
	        	 	}else{//保存修改的jqgrid列表
	        	 		$("#" + iRow + "_" + cellname, "#make_up_list").one('blur',
							function() {
								$('#make_up_list').saveCell(iRow, iCol);
							});
	        	 	}
				}
			});
	
			$(window).resize(function(e) {
				var width = $(window).width();
				var height = $(window).height();
				var dheight = $(document).height();
				var vscroll = (dheight - height) > 0;
				if (vscroll) {
					width = width - 20;
				}
				if (grid = $('.ui-jqgrid-btable:visible')) {
					grid.each(function(index) {
						if (Browser.isIE()) {
							$(this).setGridWidth(width - 30);
						} else if (Browser.isChrome()) {
							$(this).setGridWidth(width - 32);
						} else if (Browser.isMozila()) {
							$(this).setGridWidth(width - 30);
						} else {
							$(this).setGridWidth(width);
						}
					});
				}
			});
			$(window).trigger("resize");
			
});